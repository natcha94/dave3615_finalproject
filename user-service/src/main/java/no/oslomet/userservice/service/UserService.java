package no.oslomet.userservice.service;

import no.oslomet.userservice.model.Role;
import no.oslomet.userservice.model.User;
import no.oslomet.userservice.repository.FollowerRepository;
import no.oslomet.userservice.repository.FollowingRepository;
import no.oslomet.userservice.repository.RoleRepository;
import no.oslomet.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FollowingRepository followingRepository;
    @Autowired
    private FollowerRepository followerRepository;

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
        Optional<User> user = userRepository.findUserByUsername((username));
        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
    }

    public List<User> getFriendByUserId(long userid) {
        List<User> peopleThisUserAreFollowing = new ArrayList<>();
        List<User> friends = new ArrayList<>();
        followingRepository.findFollowingByOwnerId(userid).get().forEach(x -> peopleThisUserAreFollowing.add(x.getUser()));

        peopleThisUserAreFollowing.forEach(x -> {
            if (followingRepository.findFollowingByOwnerId(x.getId()).isPresent()){
                followingRepository.findFollowingByOwnerId(x.getId()).get().forEach(flw -> {
                    if(flw.getUser().getId() == userid) friends.add(x);
                });
            }
        });
        return friends;
    }

    public List<User> getFollowerByUserId(long userid) {
       List<User> list = new ArrayList<>();
       followerRepository.findAll().forEach(f -> {
           if(f.getUser().getId() == userid) list.add(userRepository.findById(f.getOwnerId()).get());
       });
        return list;
    }

}
