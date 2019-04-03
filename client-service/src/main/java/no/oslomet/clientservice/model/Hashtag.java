package no.oslomet.clientservice.model;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Hashtag {
    private long id;
    private String name;
    private List<Tweet> tweets = new ArrayList<>();

    public Hashtag(){

    }

    public  Hashtag(String name){
        this.name = name;
    }
}
