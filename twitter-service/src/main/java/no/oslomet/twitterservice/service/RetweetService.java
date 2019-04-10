package no.oslomet.twitterservice.service;

import no.oslomet.twitterservice.model.Retweet;
import no.oslomet.twitterservice.repoistory.RetweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RetweetService {
    @Autowired
    private RetweetRepository retweetRepository;

    public List<Retweet> getAllRetweets() {
        return retweetRepository.findAll();
    }

    public Retweet getRetweetById(long id) {
        return retweetRepository.findById(id).get();
    }

    public List<Retweet> getRetweetsByUserId(long id) {
        List<Retweet> retweetList = new ArrayList<>();
        getAllRetweets().forEach(tw -> {
            if(tw.getUserId() == id) retweetList.add(tw);
        });
        return retweetList;
    }

    public Retweet saveRetweet(Retweet retweet) {
        return retweetRepository.save(retweet);
    }

    public void deleteRetweetById(long id) {
        retweetRepository.deleteById(id);
    }

    public void deleteRetweetByUserId(long userid)
    {
        getAllRetweets().forEach(tw -> {
            if (tw.getUserId() == userid) retweetRepository.deleteById(tw.getId());
        });

    }
}
