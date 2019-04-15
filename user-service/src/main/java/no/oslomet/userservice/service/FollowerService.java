package no.oslomet.userservice.service;

import no.oslomet.userservice.model.Follower;
import no.oslomet.userservice.model.Following;
import no.oslomet.userservice.repository.FollowerRepository;
import no.oslomet.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Follower> getAllFollowers() {
        return followerRepository.findAll();
    }

    public Follower getFollowerById(long id){
        return followerRepository.findById(id).get();
    }

    public List<Follower> getFollowersByOwnerId(long id)
    {
        List<Follower> followerList = new ArrayList<>();
        followerRepository.findAll().forEach(x -> {
            if(x.getOwnerId() == id) followerList.add(x);
        });
        return followerList;
    }


    public Follower saveFollower(Follower newFollower){
        return followerRepository.save(newFollower);
    }

    public void deleteFollowerById(long id){
        followerRepository.deleteById(id);
    }
    public void deleteAUsersFollower(long ownerId, long userId)
    {
        getFollowersByOwnerId(ownerId).forEach( x -> {
            if(x.getUser().getId() == userId) followerRepository.deleteById(x.getId());
        });

    }

    public void deleteFollowingByUser(long userid){
        Optional<List<Follower>> list = followerRepository.findFollowersByUser(userRepository.findById(userid).get());
        if(list.isPresent()){
            list.get().forEach(x -> {
                followerRepository.deleteById(x.getId());
            });
        }
    }

    public void deleteFollowerByOwnerId(long ownerId)
    {
        Optional<List<Follower>> list = followerRepository.findFollowersByOwnerId(ownerId);
        if(list.isPresent()){
            list.get().forEach(x -> {
                followerRepository.deleteById(x.getId());
            });
        }
    }
}
