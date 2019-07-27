package com.arjun.ratingsdataservice.controllers;

import com.arjun.ratingsdataservice.models.Rating;
import com.arjun.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataController {

    int counter = 0;

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating("test_movie", 5);
    }

    @GetMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        counter++;
        UserRating userRating = new UserRating();
        try {
            if(counter>2) {
                Thread.sleep(6000);
            }
            List<Rating> ratings = Arrays.asList(
                    new Rating("1234", 4),
                    new Rating("1235", 5)
            );
            userRating.setUserRating(ratings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userRating;
    }

}
