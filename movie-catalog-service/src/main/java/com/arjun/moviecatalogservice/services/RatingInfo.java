package com.arjun.moviecatalogservice.services;

import com.arjun.moviecatalogservice.models.Rating;
import com.arjun.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "6"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
    }

    public UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 0),
                new Rating("1235", 0)
        );
        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);
        return userRating;
    }
}
