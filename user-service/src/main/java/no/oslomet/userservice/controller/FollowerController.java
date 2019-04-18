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

    @GetMapping("/followers/owner/{id}")
    public List<Follower> getFollowerByOwnerId(@PathVariable long id){
        return followerService.getFollowersByOwnerId(id);
    }

    @DeleteMapping("/followers/{id}")
    public void deleteFollowerById(@PathVariable long id){
        followerService.deleteFollowerById(id);
    }

    @DeleteMapping("/followers/{ownerid}/{id}")
    public void deleteAUserFollower(@PathVariable long ownerid, @PathVariable long id){
        followerService.deleteAUsersFollower(ownerid, id);
    }

    @DeleteMapping("/followers/user/{id}")
    public void deleteFollowingByUser(@PathVariable long id){
        followerService.deleteFollowingByUser(id);
    }

    @DeleteMapping("/followers/owner/{id}")
    public void deleteFollowerByOwnerId(@PathVariable long id){
        followerService.deleteFollowerByOwnerId(id);
    }

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
