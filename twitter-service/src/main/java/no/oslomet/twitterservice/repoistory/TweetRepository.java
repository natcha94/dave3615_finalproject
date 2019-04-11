package no.oslomet.twitterservice.repoistory;

import no.oslomet.twitterservice.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<List<Tweet>> findTweetsByTextContains(String text);
}
