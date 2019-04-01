package no.oslomet.clientservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class Role {
    private long id;
    private String roleName;
    private List<User> userList;

}
