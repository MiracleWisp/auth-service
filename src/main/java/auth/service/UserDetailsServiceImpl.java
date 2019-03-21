package auth.service;

import auth.entity.AppUser;
import auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService  {



    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}