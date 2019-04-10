package no.oslomet.clientservice.service;
import no.oslomet.clientservice.model.Follower;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowerService {
    String BASE_URL = "http://localhost:9090/followers";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Follower> getAllFollowers()
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Follower[].class)
        ).collect((Collectors.toList()));
    }

    public List<Follower> getFollowerByUserId(long userid)
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL+"/user/"+userid, Follower[].class)
        ).collect((Collectors.toList()));
    }

    public Follower getFollowerById(long id)
    {
        Follower follower = restTemplate.getForObject(BASE_URL+"/id/"+id, Follower.class);
        return follower;
    }

    public Follower saveFollower(Follower newFollower)
    {
        System.out.println("saveFollower");
        return restTemplate.postForObject(BASE_URL, newFollower, Follower.class);
    }

    public void updateFollower(long id, Follower updatedFollower){
        restTemplate.put(BASE_URL+"/"+id, updatedFollower);
    }

    public void deleteFollowerById(long id){
        restTemplate.delete(BASE_URL+"/"+id);
    }

/*    public void deleteAUserFollower(long ownerId , long id){
        System.out.println("deleteAUserFollower from service");
        restTemplate.delete(BASE_URL+"/"+ownerId+"/"+id);
    }*/
}
