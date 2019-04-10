package no.oslomet.userservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Follower{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long accountId;
    @ManyToOne
    @Getter(AccessLevel.NONE)
    @JoinColumn(name="user_id")
    private User user;

    public Follower (){
    }

    public Follower (long accountId, User user)
    {
        this.accountId = accountId;
        this.user = user;
    }

}
