package no.oslomet.userservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
public class Follower{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long ownerId;
    @ManyToOne
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
