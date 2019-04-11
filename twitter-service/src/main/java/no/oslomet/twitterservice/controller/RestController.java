package no.oslomet.twitterservice.controller;

import no.oslomet.twitterservice.model.Tweet;
import no.oslomet.twitterservice.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    TweetService tweetService;

    @GetMapping("/")
    public String home() {
        return "This is a rest controller ";
    }

    @GetMapping("/tweets")
    public List<Tweet> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("/tweets/search/{id}")
    public List<Tweet> getTweetByText(@PathVariable String id) {
        return tweetService.getTweetByText(id);
    }

    @GetMapping("/tweets/{id}")
    public Tweet getTweetById(@PathVariable long id) {
        return tweetService.getTweetById(id);
    }
    @GetMapping("/tweets/user/{id}")
    public List<Tweet> getTweetsByUserId(@PathVariable long id) {
        return tweetService.getTweetsByUserId(id);
    }

    @DeleteMapping("/tweets/{id}")
    public void deleteTweetById(@PathVariable long id) {
        tweetService.deleteTweetById(id);
    }

    @DeleteMapping("/tweets/retweet/{id}/{userid}")
    public void deleteTweetRetweetByUserId(@PathVariable long id, @PathVariable long userid) {
        tweetService.deleteTweetRetweetByUserId(id, userid);
    }

    @DeleteMapping("/tweets/user/{id}")
    public void deleteTweetByUserId(@PathVariable long id) {
        tweetService.deleteTweetByUserId(id);
    }

    @PostMapping("/tweets")
    public Tweet createTweet(@RequestBody Tweet tweet) {
        return tweetService.saveTweet(tweet);
    }

    @PutMapping("/tweets/{id}")
    public Tweet updateTweet(@PathVariable long id, @RequestBody Tweet tweet) {
        tweet.setId(id);
        return tweetService.saveTweet(tweet);
    }
}
