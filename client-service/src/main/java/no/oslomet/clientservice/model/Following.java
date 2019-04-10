package no.oslomet.clientservice.model;

import lombok.Data;

@Data
public class Following {
    private long id;
    private long accountId;
    private User user;

    public Following ()
    {

    }

    public Following (long accountId, User user)
    {
        this.accountId = accountId;
        this.user = user;
    }
}
