package com.stagefront.booking.controller;

import com.stagefront.booking.dto.BookingDtos.BookingRequest;
import com.stagefront.booking.entity.Booking;
import com.stagefront.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest request,
                                            @RequestHeader("X-Auth-Username") String username) {
        try {
            Booking booking = bookingService.createBooking(request, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookingService.getBooking(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<Booking>> getMyBookings(@RequestHeader("X-Auth-Username") String username) {
        return ResponseEntity.ok(bookingService.getBookingsForUser(username));
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<?> confirmBooking(@PathVariable Long id,
                                             @RequestHeader("X-Auth-Username") String username) {
        try {
            return ResponseEntity.ok(bookingService.confirmBooking(id, username));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id,
                                            @RequestHeader("X-Auth-Username") String username) {
        try {
            return ResponseEntity.ok(bookingService.cancelBooking(id, username));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }
}
