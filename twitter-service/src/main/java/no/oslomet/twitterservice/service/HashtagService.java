package no.oslomet.twitterservice.service;

import no.oslomet.twitterservice.model.Hashtag;
import no.oslomet.twitterservice.repoistory.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HashtagService {
    @Autowired
    private HashtagRepository hashtagRepository;

    public List<Hashtag> getAllHashtags() {
        return hashtagRepository.findAll();
    }

    public Hashtag getHashtagById(long id) {
        return hashtagRepository.findById(id).get();
    }

    public Hashtag saveHashtag(Hashtag hashtag) {
        return hashtagRepository.save(hashtag);
    }

    public void deleteHashtagById(long id) {
        hashtagRepository.deleteById(id);
    }

    public Hashtag getHashtagByName(String hashtag){
        String name = hashtag;
        Optional<Hashtag> tag = hashtagRepository.findHashtagByName(name);
        if(tag.isPresent()){
            return tag.get();
        }else{
            return null;
        }
    }
}
