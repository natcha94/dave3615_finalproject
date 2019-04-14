package no.oslomet.userservice.repository;

import no.oslomet.userservice.model.Following;
import no.oslomet.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {
    Optional<List<Following>> findFollowingByOwnerId (long ownerId);
}
