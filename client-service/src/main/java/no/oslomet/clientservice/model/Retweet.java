package no.oslomet.clientservice.model;

import lombok.Data;

@Data
public class Retweet {
    private long id;
    private long userId;
    private Tweet tweet;

    public Retweet() {

    }
    public Retweet(long userId, Tweet tweet) {
        this.userId = userId;
        this.tweet = tweet;
    }
}
