package no.oslomet.userservice.controller;

import no.oslomet.userservice.model.Follower;
import no.oslomet.userservice.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class FollowerController {

    @Autowired
    private FollowerService followerService;

    @GetMapping("/followers")
    public List<Follower> getAllFollowers(){
        return followerService.getAllFollowers();
    }

    @GetMapping("/followers/{id}")
    public Follower getFollowerById(@PathVariable long id){
        return followerService.getFollowerById(id);
    }

    @GetMapping("/followers/user/{id}")
    public List<Follower> getFollowerByUserId(@PathVariable long id){
        return followerService.getFollowersByUserId(id);
    }

    @DeleteMapping("/followers/{id}")
    public void deleteFollowerById(@PathVariable long id){
        followerService.deleteFollowerById(id);
    }
/*
    @DeleteMapping("/followers/{userid}/{id}")
    public void deleteAUserFollower(@PathVariable long userid, @PathVariable long id){
        System.out.println("deleteAUserFollowing from rest");
        followerService.deleteAUsersFollower(userid, id);
    }*/

    @PostMapping("/followers")
    public Follower createFollower(@RequestBody Follower newFollower){
        followerService.saveFollower(newFollower);
        return newFollower;
    }

    @PutMapping("/followers/{id}")
    public Follower updateFollower(@PathVariable long id, @RequestBody Follower newFollower){
        newFollower.setId(id);
        return followerService.saveFollower(newFollower);
    }
}
