package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Link;
import ru.job4j.domain.WebSite;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {

    Optional<Link> findByUrl(String url);

    Optional<Link> findByCode(String code);

    List<Link> findAllByWebSite(WebSite site);


}
