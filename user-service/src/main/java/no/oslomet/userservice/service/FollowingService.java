package no.oslomet.userservice.service;

import no.oslomet.userservice.model.Following;
import no.oslomet.userservice.model.User;
import no.oslomet.userservice.repository.FollowingRepository;
import no.oslomet.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowingService {
    @Autowired
    private FollowingRepository followingRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Following> getAllFollowings() {
        return followingRepository.findAll();
    }

    public Following getFollowingById(long id){
        return followingRepository.findById(id).get();
    }

    public List<Following> getFollowingsByOwnerId(long id)
    {
        List<Following> followingList = new ArrayList<>();
        followingRepository.findAll().forEach(x -> {
            if(x.getOwnerId() == id) followingList.add(x);
        });
        return followingList;
    }

    public Following saveFollowing(Following newFollowing){
        return followingRepository.save(newFollowing);
    }

    public void deleteFollowingById(long id){
        followingRepository.deleteById(id);
    }
    public void deleteAUsersFollowing(long ownerId, long userId)
    {
        getFollowingsByOwnerId(ownerId).forEach( x -> {
            if(x.getUser().getId() == userId) followingRepository.deleteById(x.getId());
        });

    }

    public void deleteFollowingByUser(long userid){
        Optional<List<Following>> list = followingRepository.findFollowingByUser(userRepository.findById(userid).get());
        if(list.isPresent()){
            list.get().forEach(x -> {
                followingRepository.deleteById(x.getId());
            });
        }
    }

    public void deleteFollowingByOwnerId(long ownerId)
    {
        Optional<List<Following>> list = followingRepository.findFollowingByOwnerId(ownerId);
        if(list.isPresent()){
            list.get().forEach(x -> {
                followingRepository.deleteById(x.getId());
            });
        }
    }
}
