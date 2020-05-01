package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Modifying
    @Query(value = "update Link as l set l.count = (l.count + 1) where l.url = :url")
    void countIncrement(@Param("url") String url);

    @Modifying
    @Query (value = "insert into link values (:url, :code, :count, :website)", nativeQuery = true)
    void persist(@Param("url") String url,
                 @Param("code") String code,
                 @Param("count") int count,
                 @Param("website") String website);

}
