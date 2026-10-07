package com.stagefront.booking.client;

import com.stagefront.booking.dto.EventDtos.EventResponse;
import com.stagefront.booking.dto.EventDtos.SeatUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// Talks to event-service directly by its Eureka-registered name (server-to-server,
// so it bypasses the API Gateway). Load-balanced automatically by the Eureka client.
@FeignClient(name = "event-service")
public interface EventClient {

    @GetMapping("/events/{id}")
    EventResponse getEvent(@PathVariable("id") Long id);

    @PutMapping("/events/{id}/seats/reserve")
    EventResponse reserveSeats(@PathVariable("id") Long id,
                                @RequestBody SeatUpdateRequest request,
                                @RequestHeader("X-Auth-Username") String username);

    @PutMapping("/events/{id}/seats/release")
    EventResponse releaseSeats(@PathVariable("id") Long id,
                                @RequestBody SeatUpdateRequest request,
                                @RequestHeader("X-Auth-Username") String username);
}
