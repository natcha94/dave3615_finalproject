package no.oslomet.userservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="role_id")
    private Role roleId;
    @OneToMany(mappedBy = "user")
    private List<Follower> followerList;
    @OneToMany(mappedBy = "user")
    private List<Following> followingList;

    public User (){

    }

    public User(String firstName, String lastName, String email, String userName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

}
