package ru.job4j.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.domain.WebSite;
import ru.job4j.repository.WebSiteRepository;

import java.util.Optional;

/**
 * the server for getting user details.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * the repository where persons are stored.
     */
    private final WebSiteRepository repository;

    public UserDetailsServiceImpl(WebSiteRepository repository) {
        this.repository = repository;
    }

    /**
     * it loads user details by username.
     * @param login - the login/ user name.
     * @return - user details.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<WebSite> site = this.repository.findByLogin(login);
        if (site.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        return site.get();
    }
}