package it.uniroma3.siw.siw_hotel.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final DataSource dataSource;

    // // Service custom che gestisce il provisioning automatico degli utenti Google
    // @Autowired
    // private CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("SELECT username, password, 1 as enabled FROM credenziali WHERE username=?");
        manager.setAuthoritiesByUsernameQuery("SELECT username, ruolo FROM credenziali WHERE username=?");
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers(HttpMethod.GET, "/", "/camere", "/camere/{id}", "/recensioni", "/registrazione","/cambio-password","/area-personale", "/css/**", "/images/**",
                                            "/favicon.ico").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/registrazione", "/login", "/cambio-password").permitAll();
            // Endpoint gestiti internamente da Spring Security per il flusso OAuth2/Google
            authorize.requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll();
            authorize.requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(Ruolo.ADMIN_ROLE.name());
            authorize.requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(Ruolo.ADMIN_ROLE.name());
            authorize.anyRequest().authenticated();
        });

        httpSecurity.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.successHandler((request, response, authentication) -> {

                // controlliamo se tra i permessi dell'utente c'è ADMIN_ROLE
                boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals(Ruolo.ADMIN_ROLE.name()));

                // Facciamo il redirect in base al ruolo
                if (isAdmin) {
                    response.sendRedirect("/admin/dashboard");
                    } else {
                        response.sendRedirect("/area-personale");
                    }
                });
            form.failureUrl("/login?error=true");
        });

        // Login con Google (OAuth2)
        httpSecurity.oauth2Login(oauth2 -> {
            oauth2.loginPage("/login").permitAll();
          //  oauth2.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService));
            oauth2.successHandler((request, response, authentication) -> {

                // stessa logica del login tradizionale: redirect in base al ruolo
                boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals(Ruolo.ADMIN_ROLE.name()));

                if (isAdmin) {
                    response.sendRedirect("/admin/dashboard");
                } else {
                    response.sendRedirect("/");
                }
            });
            oauth2.failureUrl("/login?error=true");
        });

        httpSecurity.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/home");
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID");
            logout.clearAuthentication(true);
            logout.permitAll();
        });


        return httpSecurity.build();
    }

}