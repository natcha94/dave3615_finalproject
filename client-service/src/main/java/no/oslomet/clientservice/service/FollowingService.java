package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.Following;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowingService {
    String BASE_URL = "http://localhost:9090/followings";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Following> getAllFollowings()
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Following[].class)
        ).collect((Collectors.toList()));
    }

    public List<Following> getFollowingByUserId(long userid)
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL+"/owner/"+userid, Following[].class)
        ).collect((Collectors.toList()));
    }

    public Following getFollowingById(long id)
    {
        Following following = restTemplate.getForObject(BASE_URL+"/id/"+id, Following.class);
        return following;
    }

    public Following saveFollowing(Following newFollowing)
    {
        System.out.println("saveFollowing");
        return restTemplate.postForObject(BASE_URL, newFollowing, Following.class);
    }

    public void updateFollowing(long id, Following updatedFollowing){
        restTemplate.put(BASE_URL+"/"+id, updatedFollowing);
    }

    public void deleteFollowingById(long id){
        restTemplate.delete(BASE_URL+"/"+id);
    }

/*    public void deleteAUserFollowing(long ownerId , long id){
        System.out.println("deleteAUserFollowing from service");
        restTemplate.delete(BASE_URL+"/"+ownerId+"/"+id);
    }*/
}
