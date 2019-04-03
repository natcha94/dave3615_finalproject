package no.oslomet.clientservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Tweet {
    private long id;
    private Date date;
    private String text;
    private long userId;
    List<Retweet> retweets;
    List<Hashtag> hashtags = new ArrayList<>();
}
