package no.oslomet.clientservice.model;

import lombok.Data;

@Data
public class Follower {
    private long id;
    private long accountId;
    private User user;

    public Follower(){

    }
    public Follower (long accountId, User user)
    {
        this.accountId = accountId;
        this.user = user;
    }
}
