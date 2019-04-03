package no.oslomet.clientservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    private long id;
    private String roleName;
    private List<User> userList;

    public  Role (){

    }


}
