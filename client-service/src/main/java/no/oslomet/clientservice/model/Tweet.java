package no.oslomet.clientservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Tweet {
    private long id;
    private Date date;
    private String text;
    private long userId;
    private List<String> imagePathList = new ArrayList<>();
    private List<Retweet> retweets = new ArrayList<>();
    private List<Hashtag> hashtags = new ArrayList<>();
}
