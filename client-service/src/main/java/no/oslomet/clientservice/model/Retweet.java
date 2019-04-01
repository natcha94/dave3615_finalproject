package no.oslomet.clientservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Retweet {
    private long id;
    private long userId;
    private Tweet tweet;
}
