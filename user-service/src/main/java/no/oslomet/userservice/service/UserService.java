package no.oslomet.userservice.service;

import no.oslomet.userservice.model.User;
import no.oslomet.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id){
        return userRepository.findById(id).get();
    }

    public User saveUser(User newUser)
    {
        return userRepository.save(newUser);
    }

    public void deleteUserById(long id){
        userRepository.deleteById(id);
    }

    public User getUserByUserName(String username){
        Optional<User> user = userRepository.findUserByUserName((username));
        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
    }

}
