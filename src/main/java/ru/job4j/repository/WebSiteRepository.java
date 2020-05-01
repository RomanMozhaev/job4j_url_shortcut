package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.WebSite;

import java.util.Optional;

@Repository
public interface WebSiteRepository extends JpaRepository<WebSite, Integer> {

    Optional<WebSite> findByDomain(String domain);

    Optional<WebSite> findByLogin(String login);

    @Modifying
    @Query (value = "insert into web_site values (:domain, :login, :password)", nativeQuery = true)
    void persist(@Param("domain") String domain,
                  @Param("login") String login,
                  @Param("password") String password);

}
