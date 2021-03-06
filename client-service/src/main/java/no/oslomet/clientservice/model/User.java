package no.oslomet.clientservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String profileImage = "/images/profileImage/DefaultAvatar/Twitter-Default-Avatar.png";
    private Role roleId;
    private List<Follower> followerList = new ArrayList<>();
    private List<Following> followingList = new ArrayList<>();

    public User(){

    }

}
