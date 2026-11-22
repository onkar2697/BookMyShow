
package org.example.bookmyshow.bookingservice.mapper;

import org.example.bookmyshow.bookingservice.dto.BookingResponse;
import org.example.bookmyshow.bookingservice.dto.BookingSeatResponse;
import org.example.bookmyshow.bookingservice.entity.Booking;
import org.example.bookmyshow.bookingservice.entity.BookingSeat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingMapper {

    public BookingResponse toBookingResponse(Booking booking) {

        BookingResponse response = new BookingResponse();

        response.setId(booking.getId());
        response.setBookingReference(booking.getBookingReference());
        response.setUserId(booking.getUserId());
        response.setShowId(booking.getShowId());
        response.setTotalAmount(booking.getTotalAmount());
        response.setStatus(booking.getStatus());
        response.setBookingTime(booking.getBookingTime());

        List<BookingSeatResponse> seatResponses =
                booking.getBookingSeats()
                        .stream()
                        .map(this::toBookingSeatResponse)
                        .toList();

        response.setBookingSeats(seatResponses);

        return response;
    }

    public BookingSeatResponse toBookingSeatResponse(BookingSeat bookingSeat) {

        BookingSeatResponse response = new BookingSeatResponse();

        response.setId(bookingSeat.getId());
        response.setSeatId(bookingSeat.getSeatId());
        response.setSeatNumber(bookingSeat.getSeatNumber());
        response.setPrice(bookingSeat.getPrice());

        return response;
    }
}
