package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService{

    String BASE_URL = "http://localhost:9090/users";
    private RestTemplate restTemplate = new RestTemplate();

    public List<User> getAllUsers()
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, User[].class)
        ).collect((Collectors.toList()));
    }

    public User getUserByUserName(String username)
    {
        User user = restTemplate.getForObject(BASE_URL+"/"+username, User.class);
        return user;
    }

    public User getUserById(long id)
    {
        User user = restTemplate.getForObject(BASE_URL+"/id/"+id, User.class);
        return user;
    }

    public User saveUser(User newUser)
    {
        System.out.println("saveUser");
        System.out.println(newUser.getRoleId().getRoleName());
        System.out.println("saveUser updateFollowerList: " + newUser.getFollowerList().size());
        return restTemplate.postForObject(BASE_URL, newUser, User.class);
    }

    public void updateUser(long id, User updatedUser){
        restTemplate.put(BASE_URL+"/"+id, updatedUser);
    }

    public void deleteUser(long id){
        restTemplate.delete(BASE_URL+"/"+id);
    }

}
