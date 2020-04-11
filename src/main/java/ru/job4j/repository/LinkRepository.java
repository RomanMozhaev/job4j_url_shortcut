package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Link;
import ru.job4j.domain.WebSite;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {

    Link findByUrl(String url);

    Link findByCode(String code);

    List<Link> findAllByWebSite(WebSite site);


}
