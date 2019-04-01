package no.oslomet.clientservice.repository;

import no.oslomet.clientservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long>  {
}
