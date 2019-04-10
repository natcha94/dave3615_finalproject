package no.oslomet.twitterservice.controller;

import no.oslomet.twitterservice.model.Retweet;
import no.oslomet.twitterservice.service.RetweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RetweetController {
    @Autowired
    RetweetService retweetService;

    @GetMapping("/retweets")
    public List<Retweet> getAllRetweets() {
        return retweetService.getAllRetweets();
    }

    @GetMapping("/retweets/{id}")
    public Retweet getRetweetById(@PathVariable long id) {
        return retweetService.getRetweetById(id);
    }

    @GetMapping("/retweets/user/{id}")
    public List<Retweet> getTweetsByUserId(@PathVariable long id) {
        return retweetService.getRetweetsByUserId(id);
    }

    @DeleteMapping("/retweets/{id}")
    public void deleteRetweetById(@PathVariable long id) {
        retweetService.deleteRetweetById(id);
    }

    @DeleteMapping("/retweets/user/{id}")
    public void deleteRetweetsByUserId(@PathVariable long id) {
        retweetService.deleteRetweetByUserId(id);
    }

    @PostMapping("/retweets")
    public Retweet createRetweet(@RequestBody Retweet retweet) {
        return retweetService.saveRetweet(retweet);
    }

    @PutMapping("/retweets/{id}")
    public Retweet updateRetweet(@PathVariable long id, @RequestBody Retweet retweet) {
        retweet.setId(id);
        return retweetService.saveRetweet(retweet);
    }


}
