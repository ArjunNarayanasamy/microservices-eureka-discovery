package com.arjun.moviecatalogservice.controllers;

import com.arjun.moviecatalogservice.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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

    @GetMapping("/{userId}")
    public UserCatalog getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId, UserRating.class);

        List<CatalogItem> catalogItems = ratings.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);

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

            return new CatalogItem(movie.getName(), "test_desc", rating.getRating());
        }).collect(Collectors.toList());

        return new UserCatalog(userId, catalogItems);
    }
}
