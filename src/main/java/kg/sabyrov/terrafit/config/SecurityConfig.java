package kg.sabyrov.terrafit.config;

import kg.sabyrov.terrafit.jwt.JwtAuthenticationEntryPoint;
import kg.sabyrov.terrafit.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Value("${jwt.get.token.uri}")
    private String loginPath;
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/user").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/user/find").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/role").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/employee/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/employee").hasRole("ADMIN")


                .antMatchers(HttpMethod.POST, "/api/training-group").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/training-group/subscription/buy").hasAnyRole("USER", "ADMIN")

                .antMatchers(HttpMethod.GET, "/api/subscription/my").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.GET, "/api/subscription/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/subscription/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/wallet/my/replenish").hasAnyRole("ADMIN", "USER")

                .antMatchers(HttpMethod.POST, "/api/visit-history/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET,"api/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "api/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/user/person-parameters").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/user/person-parameters").hasRole("USER")

                .anyRequest().authenticated()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.POST, loginPath)
                .antMatchers(HttpMethod.POST, "/register")
                .antMatchers(HttpMethod.POST, "/recovery")
                .antMatchers(HttpMethod.POST, "/confirm")
                .antMatchers(HttpMethod.GET, "/api/training-group")
                .antMatchers(HttpMethod.GET, "/api/employee")
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**");

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
