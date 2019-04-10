package no.oslomet.userservice.model;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String profileImage = "/images/profileImage/DefaultAvatar/Twitter-Default-Avatar.png";

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role roleId;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    private List<Follower> followerList = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Following> followingList = new ArrayList<>();

    public User (){

    }

    public User (String firstName, String lastName, String email, String userName, String password, Role role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = userName;
        this.password = password;
        this.roleId = role;
    }


}
