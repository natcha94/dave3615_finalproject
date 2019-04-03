package no.oslomet.twitterservice.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date date;
    private String text;
    private long userId;

    @OneToMany(mappedBy = "tweet")
    List<Retweet> retweets;

    @ManyToMany
    @JoinTable(name="tweetHashtags",
            joinColumns = { @JoinColumn(name = "tweet_id") },
            inverseJoinColumns = { @JoinColumn(name = "hashtag_id")})
    List<Hashtag> hashtags = new ArrayList<>();

    public Tweet() {

    }
}
