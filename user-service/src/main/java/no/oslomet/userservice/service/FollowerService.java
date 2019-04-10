package no.oslomet.userservice.service;

import no.oslomet.userservice.model.Follower;
import no.oslomet.userservice.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowerService {

    @Autowired
    private FollowerRepository followerRepository;

    public List<Follower> getAllFollowers() {
        return followerRepository.findAll();
    }

    public Follower getFollowerById(long id){
        return followerRepository.findById(id).get();
    }

    public List<Follower> getFollowersByUserId(long id)
    {
        List<Follower> followerList = new ArrayList<>();
        followerRepository.findAll().forEach(x -> {
            if(x.getAccountId() == id) followerList.add(x);
        });
        return followerList;
    }


    public Follower saveFollower(Follower newFollower){
        return followerRepository.save(newFollower);
    }

    public void deleteFollowerById(long id){
        followerRepository.deleteById(id);
    }
/*    public void deleteAUsersFollower(long ownerId, long userId)
    {
        getFollowersByOwnerId(ownerId).forEach( x -> {
            if(x.getUser().getId() == userId) followerRepository.deleteById(x.getId());
        });

    }*/
}
