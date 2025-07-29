package kg.attractor.job_search_java_25.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final DataSource dataSource;

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String userQuery = "select email, password, enabled from USERS\n" +
                "where EMAIL = ?;";
        String authorityQuery = "select email, authority from AUTHORITIES\n" +
                "where EMAIL = ?;";
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(authorityQuery);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/profile/avatar").authenticated()
                                .requestMatchers(HttpMethod.GET, "/profile/avatar").permitAll()
                                .requestMatchers(HttpMethod.GET, "/profile").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/profile").authenticated()
                                .requestMatchers(HttpMethod.GET, "/resumes/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.PUT, "/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.DELETE, "/resumes/**").hasRole("APPLICANT")

                        .requestMatchers(HttpMethod.GET, "/vacancies/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.PUT, "/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.DELETE, "/vacancies/**").hasRole("EMPLOYER")

                        .requestMatchers(HttpMethod.POST, "/vacancies/*/respond").hasRole("APPLICANT")

                        .requestMatchers(HttpMethod.GET, "/vacancies/*/responses").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()
                        .anyRequest().denyAll()
                );

        return http.build();
    }

}
