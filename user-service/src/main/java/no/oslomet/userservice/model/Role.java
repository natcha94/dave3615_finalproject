package no.oslomet.userservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Role{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String roleName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roleId", cascade = CascadeType.ALL)
    @Getter(AccessLevel.NONE)
    private List<User> userList = new ArrayList<>();


    public Role (){
    }

    public Role (int id, String rolename){
        this.id = id;
        this.roleName = rolename;
    }
}
