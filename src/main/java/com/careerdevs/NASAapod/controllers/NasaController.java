package com.careerdevs.NASAapod.controllers;

import com.careerdevs.NASAapod.models.NasaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//by adding the RequestMapping annotation to this class,
// all the route handler implemented in this class will have /nasa added to their endpoint
// (the URL you need to request to get the method to run):

@RestController
@RequestMapping("/nasa")
public class NasaController {
    @Autowired
    private Environment env;

    @Value("${APOD_KEY}")
    private String apiKey;

    private String urlBase = "https://api.nasa.gov/planetary/apod?api_key=";
    @GetMapping("/testKey")
    private String getApiKey(){
        return urlBase;
    }




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
        try {
           // String key = env.getProperty("APOD_KEY", "DEMO_KEY");
            final String url = urlBase + apiKey;
            NasaModel response = restTemplate.getForObject(url, NasaModel.class);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @GetMapping("/bydate/{date}")
    ResponseEntity<?> apodByDatePathVariable(RestTemplate restTemplate, @PathVariable String date) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate requestedDate = LocalDate.parse(date, formatter);
            LocalDate today = LocalDate.now();
            String key = env.getProperty("APOD_KEY");
            if (key == null) {
                return ResponseEntity.internalServerError().body("Api key is not present");
            }
            if (requestedDate.isAfter(today)){
                return ResponseEntity.status(400).body("Invalid date.");
            }
            String url = urlBase + key + "&date=" + date;
            NasaModel response = restTemplate.getForObject(url, NasaModel.class);
            return ResponseEntity.ok(response);
        } catch (DateTimeException e){
            return ResponseEntity.status(400).body("Invalid date format: "+ date +", date format: yyyy-MM-dd");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}


