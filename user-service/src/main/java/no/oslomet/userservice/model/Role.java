package no.oslomet.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String roleName;
    @JsonIgnore
    @OneToMany(mappedBy = "roleId")
    private List<User> userList = new ArrayList<>();


    public Role (){
    }

    public Role(String roleName){
        this.roleName = roleName;
    }
}
