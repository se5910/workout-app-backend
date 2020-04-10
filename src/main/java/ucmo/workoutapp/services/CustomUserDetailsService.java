package ucmo.workoutapp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucmo.workoutapp.entities.Role;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if (user == null) new UsernameNotFoundException("User not found");


    // List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
    // return buildUserForAuthentication(user, authorities);
    return user;
  }

  @Transactional
  public User loadUserById(Long id) {
    User user = userRepository.getById(id);

    if (user == null) new UsernameNotFoundException("User not found");

    return user;
  }


  private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
    Set<GrantedAuthority> roles = new HashSet<>();
    for (Role role : userRoles) {
      roles.add(new SimpleGrantedAuthority(role.getRole()));
    }
    return new ArrayList<>(roles);
  }

  private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
            user.isEnabled(), true, true, true, authorities);
  }
}
