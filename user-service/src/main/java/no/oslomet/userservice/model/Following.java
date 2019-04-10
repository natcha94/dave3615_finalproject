package no.oslomet.userservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long accountId;
    @ManyToOne
    @Getter(AccessLevel.NONE)
    @JoinColumn(name="user_id")
    private User user;

    public Following (){
    }

    public Following (long accountId, User user)
    {
        this.accountId = accountId;
        this.user = user;
    }

}
