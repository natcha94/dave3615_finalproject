package no.oslomet.clientservice.model;

import lombok.Data;

@Data
public class Following {
    private long id;
    private long ownerId;
    private User user;

    public Following ()
    {

    }

    public Following (long ownerId, User user)
    {
        this.ownerId = ownerId;
        this.user = user;
    }
}
