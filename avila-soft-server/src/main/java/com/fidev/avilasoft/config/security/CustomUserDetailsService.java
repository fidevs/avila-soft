package com.fidev.avilasoft.config.security;

import com.fidev.avilasoft.entities.Role;
import com.fidev.avilasoft.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.fidev.avilasoft.entities.User> optCustomer = this.repository.findByUsername(username);
        if (optCustomer.isEmpty()) throw new UsernameNotFoundException("No se encontró el usuario: " + username);
        com.fidev.avilasoft.entities.User customer = optCustomer.get();
        boolean disabled = !customer.isActive();

        Role role = customer.getRole();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (!disabled) authorities = role.getAuthorizations().stream()
                .map(p -> new SimpleGrantedAuthority(p.getAuth().getAuth()))
                .collect(Collectors.toList());

        return User.builder()
                .username(username)
                .disabled(!customer.isActive())
                .password(customer.getPassword())
                .roles(role.getName())
                .authorities(authorities)
                .build();
    }
}
