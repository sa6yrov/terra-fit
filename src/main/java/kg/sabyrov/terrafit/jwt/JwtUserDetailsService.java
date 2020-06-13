package kg.sabyrov.terrafit.jwt;

import kg.sabyrov.terrafit.entity.Role;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.repository.UserRepository;
import kg.sabyrov.terrafit.service.RoleService;
import kg.sabyrov.terrafit.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findByEmail(email);
        if(user == null){
            return null;
        }

        return JwtUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .isActive(user.getIsActive())
                .authorities(mapToGrantedAuthorities(user.getRoles()))
                .build();
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
