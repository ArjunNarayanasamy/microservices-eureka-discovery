package com.arjun.moviecatalogservice.services;

import com.arjun.moviecatalogservice.models.CatalogItem;
import com.arjun.moviecatalogservice.models.Movie;
import com.arjun.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), "test_desc", rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("1234", "default" , 0 );
    }

}
