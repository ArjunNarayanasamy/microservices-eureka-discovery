package com.arjun.moviecatalogservice.controllers;

import com.arjun.moviecatalogservice.models.*;
import com.arjun.moviecatalogservice.services.MovieInfo;
import com.arjun.moviecatalogservice.services.RatingInfo;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClient;

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private RatingInfo ratingInfo;

    @GetMapping("/{userId}")
    public UserCatalog getCatalog(@PathVariable("userId") String userId) {
        UserRating ratings = ratingInfo.getUserRating(userId);
        List<CatalogItem> catalogItems = ratings.getUserRating().stream().map(rating -> movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());
        return new UserCatalog(userId, catalogItems);
    }

    /*
     * WebClient is alternative to RestTemplate to invoke the
     * Rest calls asynchronously in Reactive Way
     */
    /*Movie movie = webClient.build()
            .get()
            .uri("http://localhost:8082/movies/"+rating.getMovieId())
            .retrieve()
            .bodyToMono(Movie.class)
            .block();*/
}
