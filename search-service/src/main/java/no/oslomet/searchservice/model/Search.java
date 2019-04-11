package no.oslomet.searchservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private String searchText;

    public Search(){

    }

    public Search(long userid, String text){
        this.userId = userid;
        this.searchText = text;
    }
}
