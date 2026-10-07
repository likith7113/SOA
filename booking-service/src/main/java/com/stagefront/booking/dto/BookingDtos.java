package com.stagefront.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BookingDtos {

    public static class BookingRequest {
        @NotNull
        private Long eventId;

        @NotNull
        @Min(1)
        private Integer seats;

        public Long getEventId() { return eventId; }
        public void setEventId(Long eventId) { this.eventId = eventId; }
        public Integer getSeats() { return seats; }
        public void setSeats(Integer seats) { this.seats = seats; }
    }
}
