package no.oslomet.clientservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Role roleId;
    private List<Follower> followerList;
    private List<Following> followingList;

}
