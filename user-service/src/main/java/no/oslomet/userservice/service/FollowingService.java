package no.oslomet.userservice.service;

import no.oslomet.userservice.model.Following;
import no.oslomet.userservice.repository.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class FollowingService {
    @Autowired
    private FollowingRepository followingRepository;

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
}
