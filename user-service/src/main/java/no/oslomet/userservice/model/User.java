package no.oslomet.userservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
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
    private String userName;
    private String password;
    //@JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="role_id")
    private Role roleId;
/*    @OneToMany(mappedBy = "user")
    private List<Follower> followerList;
    @OneToMany(mappedBy = "user")
    private List<Following> followingList;*/

    public User (){

    }

    public User (String firstName, String lastName, String email, String userName, String password, Role role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.roleId = role;
    }


}
