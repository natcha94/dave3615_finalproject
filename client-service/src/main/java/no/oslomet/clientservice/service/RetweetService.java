package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.Retweet;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetweetService {
    String BASE_URL = "http://localhost:9080/retweets";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Retweet> getAllRetweets()
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Retweet[].class)
        ).collect((Collectors.toList()));
    }

    public Retweet getRetweetById(long id)
    {
        Retweet reTweet = restTemplate.getForObject(BASE_URL+"/"+id, Retweet.class);
        return reTweet;
    }

    public List<Retweet> getRetweetsByUserId(long id)
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL+"/user/"+id, Retweet[].class)
        ).collect((Collectors.toList()));
    }

    public Retweet saveRetweet(Retweet newRetweet)
    {
        return restTemplate.postForObject(BASE_URL, newRetweet, Retweet.class);
    }

    public void updateRetweet(long id, Retweet updatedRetweet){
        restTemplate.put(BASE_URL+"/"+id, updatedRetweet);
    }

    public void deleteRetweet(long id){
        restTemplate.delete(BASE_URL+"/"+id);
    }

    public void deleteRetweetByUserId(long userid){
        restTemplate.delete(BASE_URL+"/user/"+userid);
    }


}
