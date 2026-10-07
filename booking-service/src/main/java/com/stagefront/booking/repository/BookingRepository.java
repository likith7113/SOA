package com.stagefront.booking.repository;

import com.stagefront.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUsername(String username);
    List<Booking> findByStatusAndHoldExpiresAtBefore(Booking.Status status, LocalDateTime time);
}
