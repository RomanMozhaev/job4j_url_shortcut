package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.WebSite;

@Repository
public interface WebSiteRepository extends JpaRepository<WebSite, Integer> {

    WebSite findByDomain(String domain);

}
