package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.WebSite;

import java.util.Optional;

@Repository
public interface WebSiteRepository extends JpaRepository<WebSite, Integer> {

    Optional<WebSite> findByDomain(String domain);

    Optional<WebSite> findByLogin(String login);

    @Query(value = "select w.domain from web_site as w where  w.login= :login", nativeQuery = true)
    String getDomainByLogin(@Param("login") String login);

}
