package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.Tweet;
import no.oslomet.clientservice.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetService {

    String BASE_URL = "http://localhost:9080/tweets";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Tweet> getAllTweets()
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Tweet[].class)
        ).collect((Collectors.toList()));
    }

    public Tweet getTweetById(long id)
    {
        Tweet tweet = restTemplate.getForObject(BASE_URL+"/"+id, Tweet.class);
        return tweet;
    }

    public List<Tweet> getTweetsByUserId(long id)
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL+"/user/"+id, Tweet[].class)
        ).collect((Collectors.toList()));
    }

    public Tweet saveTweet(Tweet newTweet)
    {
        return restTemplate.postForObject(BASE_URL, newTweet, Tweet.class);
    }

    public void updateTweet(long id, User updatedTweet){
        restTemplate.put(BASE_URL+"/"+id, updatedTweet);
    }

    public void deleteTweet(long id){
        restTemplate.delete(BASE_URL+"/"+id);
    }

    public void deleteTweetByUserId(long userid){
        restTemplate.delete(BASE_URL+"/user/"+userid);
    }

    public void deleteTweetRetweetByUserId(long tweetid, long userid){
        restTemplate.delete(BASE_URL+"/retweet/"+tweetid+"/"+userid);
    }


}
