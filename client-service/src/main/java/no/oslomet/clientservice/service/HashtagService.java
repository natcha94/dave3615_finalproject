package no.oslomet.clientservice.service;

import no.oslomet.clientservice.model.Hashtag;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashtagService {
    String BASE_URL = "http://localhost:9080/hashtags";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Hashtag> getAllHashtags()
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Hashtag[].class)
        ).collect((Collectors.toList()));
    }

    public Hashtag getHashtagByName(String hashtag)
    {
        System.out.println("getHashtagByName: " +hashtag);
        return restTemplate.getForObject(BASE_URL+"/"+hashtag, Hashtag.class);
    }

    public Hashtag saveHashtag(Hashtag newHashtag)
    {
        return restTemplate.postForObject(BASE_URL, newHashtag, Hashtag.class);
    }

    public void updateHashtag(long id, Hashtag updatedHashtag){
        restTemplate.put(BASE_URL+"/"+id, updatedHashtag);
    }

    public void deleteHashtag(long id){
        restTemplate.delete(BASE_URL+"/"+id);
    }

}
