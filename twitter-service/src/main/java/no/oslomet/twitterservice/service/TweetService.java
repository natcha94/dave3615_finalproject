package no.oslomet.twitterservice.service;

import no.oslomet.twitterservice.model.Hashtag;
import no.oslomet.twitterservice.model.Tweet;
import no.oslomet.twitterservice.repoistory.RetweetRepository;
import no.oslomet.twitterservice.repoistory.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TweetService {
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private HashtagService hashtagRepository;
    @Autowired
    private RetweetRepository retweetRepository;

    public List<Tweet> getAllTweets() {
        List<Tweet> allTweets = tweetRepository.findAll();

        Comparator<Tweet> tweetDateTimeComparator = Comparator.comparing(Tweet::getDateTime);
        Collections.sort(allTweets, tweetDateTimeComparator);

        reverseTweetList(allTweets);
        return allTweets;
    }

    public void reverseTweetList(List<Tweet> tweets){
        Collections.reverse(tweets);
    }

    public Tweet getTweetById(long id) {
        return tweetRepository.findById(id).get();
    }

    public List<Tweet> getTweetsByUserId(long id) {
        List<Tweet> tweetList = new ArrayList<>();
        getAllTweets().forEach(tw -> {
            if(tw.getUserId() == id) tweetList.add(tw);
        });

        Comparator<Tweet> tweetDateTimeComparator = Comparator.comparing(Tweet::getDateTime);
        Collections.sort(tweetList, tweetDateTimeComparator);

        reverseTweetList(tweetList);

        return tweetList;
    }

    public Tweet saveTweet(Tweet tweet) {
        Pattern pattern = Pattern.compile("#\\S+");
        List<String> hashtags = new ArrayList<>();

        Matcher matcher = pattern.matcher(tweet.getText());
        while(matcher.find()) hashtags.add(matcher.group(0));

        hashtags.forEach(tag -> {
            String word = tag.substring(1, tag.length());
            if(hashtagRepository.getHashtagByName(word) != null){
                tweet.getHashtags().add(hashtagRepository.getHashtagByName(word));
            }else{
                tweet.getHashtags().add(new Hashtag(word));
            }
        });

        return tweetRepository.save(tweet);
    }

    public void deleteTweetById(long id) {
        tweetRepository.deleteById(id);
    }

    public void deleteTweetByUserId(long userid)
    {
        getAllTweets().forEach(tw -> {
            if (tw.getUserId() == userid) tweetRepository.deleteById(tw.getId());
        });

    }

    public void deleteTweetRetweetByUserId(long tweetid, long userId)
    {
        getTweetById(tweetid).getRetweets().forEach( x -> {
            if(x.getUserId()== userId) retweetRepository.deleteById(x.getId());
        });

    }

    public List<Tweet> getTweetByText(String text){
        String[] split = text.split(" ");
        List<Tweet> tweet = new ArrayList<>();
        for(String a : split){
            if(tweetRepository.findTweetsByTextContains(a).isPresent()){
                tweetRepository.findTweetsByTextContains(a).get().forEach(tw -> {
                    if(!tweet.contains(tw)) tweet.add(tw);
                });
            }
        }
        return tweet;
    }
}
