package com.stagefront.booking.dto;

public class EventDtos {

    // Mirrors event-service's Event entity - only fields booking-service needs.
    public static class EventResponse {
        private Long id;
        private String name;
        private Integer availableSeats;
        private Double price;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAvailableSeats() { return availableSeats; }
        public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
    }

    public static class SeatUpdateRequest {
        private Integer seats;

        public SeatUpdateRequest() {}
        public SeatUpdateRequest(Integer seats) { this.seats = seats; }

        public Integer getSeats() { return seats; }
        public void setSeats(Integer seats) { this.seats = seats; }
    }
}
