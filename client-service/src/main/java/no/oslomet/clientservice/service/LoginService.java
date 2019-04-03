package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService  implements UserDetailsService {
    String BASE_URL = "http://localhost:9090/users";
    private RestTemplate restTemplate = new RestTemplate();

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
