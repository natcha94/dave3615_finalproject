package no.oslomet.twitterservice.service;

import no.oslomet.twitterservice.model.Hashtag;
import no.oslomet.twitterservice.repoistory.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
