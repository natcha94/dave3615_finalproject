package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public User saveUser(User newUser)
    {
        System.out.println("saveUser");
        System.out.println(newUser.getRoleId().getRoleName());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return restTemplate.postForObject(BASE_URL, newUser, User.class);
    }

    public void updateUser(long id, User updatedUser){
        restTemplate.put(BASE_URL+"/"+id, updatedUser);
    }

    public void deleteUser(long id){
        restTemplate.delete(BASE_URL+"/"+id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = restTemplate.getForObject(BASE_URL+"/"+username, User.class);
        if(user == null) throw new UsernameNotFoundException("Not found user with email: " + username);
        return getUserDetails(user);
    }

    private UserDetails getUserDetails(User user){
        return org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRoleId().getRoleName()).build();
    }
}
