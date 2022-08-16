package com.careerdevs.NASAapod.controllers;

import com.careerdevs.NASAapod.models.NasaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//by adding the RequestMapping annotation to this class,
// all the route handler implemented in this class will have /nasa added to their endpoint
// (the URL you need to request to get the method to run):

@RestController
@RequestMapping("/nasa")
public class NasaController {
    @Autowired
    private Environment env;


//These fields are made final therefore they're immutable (unable to be changed).

//    @GetMapping("/apod")
//    public Object apodHandler (RestTemplate restTemplate){
//
//        String key = env.getProperty("APOD_KEY", "DEMO_KEY");
//        final String nasaApodEndpoint = "https://api.nasa.gov/planetary/apod?api_key=" + key;
//        return restTemplate.getForObject(nasaApodEndpoint, Object.class);
//    }
//
//
//
//    @GetMapping ("/today")
//    ResponseEntity<?> apodToday (RestTemplate restTemplate){
//        String key = env.getProperty("APOD_KEY", "DEMO_KEY");
//        final String url = "https://api.nasa.gov/planetary/apod?api_key=" + key;
//        Object response = restTemplate.getForObject(url, Object.class);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/bydate/{date}")
//    ResponseEntity<?> apodByDatePathVariable (RestTemplate restTemplate, @PathVariable String date) {
//        String key = env.getProperty("APOD_KEY");
//        if (key == null){
//            return ResponseEntity.internalServerError().body("Api key is not present");
//        }
//        String url = "https://api.nasa.gov/planetary/apod?api_key=" + key + "&date=" + date;
//        Object response = restTemplate.getForObject(url, Object.class);
//        return ResponseEntity.ok(response);
//
//    }


    @GetMapping("/today")
    ResponseEntity<?> apodToday(RestTemplate restTemplate) {
        String key = env.getProperty("APOD_KEY", "DEMO_KEY");
        final String url = "https://api.nasa.gov/planetary/apod?api_key=" + key;
        NasaModel response = restTemplate.getForObject(url, NasaModel.class);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/bydate/{date}")
    ResponseEntity<?> apodByDatePathVariable(RestTemplate restTemplate, @PathVariable String date) {
        String key = env.getProperty("APOD_KEY");
        if (key == null) {
            return ResponseEntity.internalServerError().body("Api key is not present");
        }
        String url = "https://api.nasa.gov/planetary/apod?api_key=" + key + "&date=" + date;
        NasaModel response = restTemplate.getForObject(url, NasaModel.class);
        return ResponseEntity.ok(response);

    }
}


