package no.oslomet.twitterservice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Getter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "hashtags", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Tweet> tweets = new ArrayList<>();

    public Hashtag() {

    }

    public Hashtag(String name) {
        this.name = name;
    }
}
