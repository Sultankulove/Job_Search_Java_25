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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final DataSource dataSource;
    private final ApplicationConfig appConfig;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String usersByUsername = """
            select u.email as username,
                   u.password as password,
                   u.enabled as enabled
            from users u
            where u.email = ?
            """;

        String authoritiesByUsername = """
            select u.email as username,
                   case
                       when u.account_type = 'APPLICANT' then 'ROLE_APPLICANT'
                       when u.account_type = 'EMPLOYER'  then 'ROLE_EMPLOYER'
                       when u.account_type = 'ADMIN'     then 'ROLE_ADMIN'
                       else 'ROLE_APPLICANT'
                   end as authority
            from users u
            where u.email = ?
            """;

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(appConfig.passwordEncoder())
                .usersByUsernameQuery(usersByUsername)
                .authoritiesByUsernameQuery(authoritiesByUsername);
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);

        jdbc.setUsersByUsernameQuery("""
        select u.email as username,
               u.password as password,
               u.enabled as enabled
        from users u
        where u.email = ?
    """);

        jdbc.setAuthoritiesByUsernameQuery("""
        select u.email as username,
               case
                   when u.account_type = 'APPLICANT' then 'ROLE_APPLICANT'
                   when u.account_type = 'EMPLOYER'  then 'ROLE_EMPLOYER'
                   when u.account_type = 'ADMIN'     then 'ROLE_ADMIN'
                   else 'ROLE_APPLICANT'
               end as authority
        from users u
        where u.email = ?
    """);

        return jdbc;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/error").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/profile/register").permitAll()

                .requestMatchers(HttpMethod.GET,  "/api/vacancies/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/vacancies/**").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.PUT,  "/api/vacancies/**").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.PATCH,"/api/vacancies/**").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.DELETE,"/api/vacancies/**").hasRole("EMPLOYER")


                .requestMatchers(HttpMethod.GET,  "/api/resumes/search").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.GET,  "/api/resumes/{id}").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.POST, "/api/resumes/**").hasRole("APPLICANT")
                .requestMatchers(HttpMethod.PUT,  "/api/resumes/**").hasRole("APPLICANT")
                .requestMatchers(HttpMethod.PATCH,"/api/resumes/**").hasRole("APPLICANT")
                .requestMatchers(HttpMethod.DELETE,"/api/resumes/**").hasRole("APPLICANT")


                .requestMatchers("/api/profile/**").authenticated()
                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
