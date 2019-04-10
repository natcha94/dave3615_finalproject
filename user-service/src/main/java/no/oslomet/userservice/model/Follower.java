package no.oslomet.userservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Follower{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long ownerId;
    @ManyToOne
    @Getter(AccessLevel.NONE)
    @JoinColumn(name="user_id")
    private User user;

    public Follower (){
    }

    public Follower (long ownerId, User user)
    {
        this.ownerId = ownerId;
        this.user = user;
    }

}
