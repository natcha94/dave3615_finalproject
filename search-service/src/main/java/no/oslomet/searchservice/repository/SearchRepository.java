package no.oslomet.searchservice.repository;

import no.oslomet.searchservice.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    Optional<Search> findSearchesByUserId(long userId);
}
