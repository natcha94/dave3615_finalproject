package no.oslomet.clientservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
public class Follower {
    private long id;
    private long ownerId;
    private User user;

    public Follower(){

    }
    public Follower (long ownerId, User user)
    {
        this.ownerId = ownerId;
        this.user = user;
    }
}
