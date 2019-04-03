package no.oslomet.userservice.model;

import com.fasterxml.jackson.annotation.*;
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
    //@JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roleId", cascade = CascadeType.ALL)
    @Getter(AccessLevel.NONE)
    private List<User> userList = new ArrayList<>();


    public Role (){
    }
}
