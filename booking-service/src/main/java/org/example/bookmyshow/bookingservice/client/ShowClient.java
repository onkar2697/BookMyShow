package org.example.bookmyshow.bookingservice.client;


import org.example.bookmyshow.bookingservice.dto.ShowResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "show-service")
public interface ShowClient {

    @GetMapping("/shows/{id}")
    ShowResponse getShowById(@PathVariable("id") Long id);
}
