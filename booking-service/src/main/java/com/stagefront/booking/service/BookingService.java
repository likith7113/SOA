package com.stagefront.booking.service;

import com.stagefront.booking.client.EventClient;
import com.stagefront.booking.dto.BookingDtos.BookingRequest;
import com.stagefront.booking.dto.EventDtos.SeatUpdateRequest;
import com.stagefront.booking.entity.Booking;
import com.stagefront.booking.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final EventClient eventClient;

    @Value("${booking.hold-duration-minutes:10}")
    private long holdDurationMinutes;

    public BookingService(BookingRepository bookingRepository, EventClient eventClient) {
        this.bookingRepository = bookingRepository;
        this.eventClient = eventClient;
    }

    /**
     * Places a temporary hold on seats. Seat availability itself is only
     * ever mutated inside event-service's pessimistic-lock transaction, so
     * this call is safe even when many requests for the same event arrive
     * at once - event-service will reject the ones that don't fit.
     */
    @Transactional
    public Booking createBooking(BookingRequest request, String username) {
        // This throws (HTTP 409 surfaces to the caller) if not enough seats remain.
        eventClient.reserveSeats(request.getEventId(), new SeatUpdateRequest(request.getSeats()), username);

        Booking booking = new Booking();
        booking.setEventId(request.getEventId());
        booking.setUsername(username);
        booking.setSeats(request.getSeats());
        booking.setStatus(Booking.Status.HELD);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setHoldExpiresAt(LocalDateTime.now().plusMinutes(holdDurationMinutes));

        return bookingRepository.save(booking);
    }

    public Booking getBooking(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Booking not found: " + id));
    }

    public List<Booking> getBookingsForUser(String username) {
        return bookingRepository.findByUsername(username);
    }

    @Transactional
    public Booking confirmBooking(Long id, String username) {
        Booking booking = getOwnedBooking(id, username);

        if (booking.getStatus() != Booking.Status.HELD) {
            throw new IllegalStateException("Only HELD bookings can be confirmed. Current status: " + booking.getStatus());
        }
        if (booking.getHoldExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Hold has expired for this booking");
        }

        booking.setStatus(Booking.Status.CONFIRMED);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking cancelBooking(Long id, String username) {
        Booking booking = getOwnedBooking(id, username);

        if (booking.getStatus() == Booking.Status.CANCELLED || booking.getStatus() == Booking.Status.EXPIRED) {
            throw new IllegalStateException("Booking is already " + booking.getStatus());
        }

        eventClient.releaseSeats(booking.getEventId(), new SeatUpdateRequest(booking.getSeats()), username);
        booking.setStatus(Booking.Status.CANCELLED);
        return bookingRepository.save(booking);
    }

    private Booking getOwnedBooking(Long id, String username) {
        Booking booking = getBooking(id);
        if (!booking.getUsername().equals(username)) {
            throw new IllegalStateException("Booking does not belong to this user");
        }
        return booking;
    }

    /**
     * Runs every 60s: any HELD booking whose hold window has passed gets
     * expired and its seats released back to the event, so seats don't stay
     * locked forever behind an abandoned checkout.
     */
    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void expireStaleHolds() {
        List<Booking> expired = bookingRepository.findByStatusAndHoldExpiresAtBefore(
                Booking.Status.HELD, LocalDateTime.now());

        for (Booking booking : expired) {
            try {
                eventClient.releaseSeats(booking.getEventId(), new SeatUpdateRequest(booking.getSeats()), "system");
                booking.setStatus(Booking.Status.EXPIRED);
                bookingRepository.save(booking);
                log.info("Expired booking {} and released {} seat(s) for event {}",
                        booking.getId(), booking.getSeats(), booking.getEventId());
            } catch (Exception e) {
                log.error("Failed to expire booking {}: {}", booking.getId(), e.getMessage());
            }
        }
    }
}
