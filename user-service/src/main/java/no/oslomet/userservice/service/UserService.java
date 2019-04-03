package no.oslomet.userservice.service;

import no.oslomet.userservice.model.Role;
import no.oslomet.userservice.model.User;
import no.oslomet.userservice.repository.RoleRepository;
import no.oslomet.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id){
        return userRepository.findById(id).get();
    }

    public User saveUser(User newUser)
    {
        Role role = roleRepository.findById(newUser.getRoleId().getId()).get();
        newUser.setRoleId(role);
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
