package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.WebSite;

import java.util.Optional;

@Repository
public interface WebSiteRepository extends JpaRepository<WebSite, Integer> {

    Optional<WebSite> findByDomain(String domain);

    Optional<WebSite> findByLogin(String login);
}
