package kg.attractor.job_search_java_25.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final DataSource dataSource;
    private final ApplicationConfig appConfig;

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//                .httpBasic(Customizer.withDefaults())
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/ws/**", "/topic/**", "/app/**"));
//                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/auth/login?error=true")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/logout").authenticated()
                        .requestMatchers("/webjars/**", "/static/**", "/css/**", "/js/**", "/images/**").permitAll()

                        .requestMatchers("/", "/auth/**", "/error", "/auth/reset-password").permitAll()
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/topic/**", "/app/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.PATCH, "/api/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.PUT,   "/api/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.PUT,   "/api/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.POST,  "/api/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.POST,  "/api/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.DELETE,"/api/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.DELETE,"/api/resumes/**").hasRole("APPLICANT")

                        .requestMatchers("/profile/**").authenticated()
                        .requestMatchers("/api/profile/avatar").authenticated()

                        .requestMatchers("/resumes/**", "/profile/vacancies", "/vacancy/*/edit","/vacancy/new", "/chat/start/*", "/chat/*", "vacancy/*/chat").hasRole("EMPLOYER")

                        .requestMatchers("/vacancies/**", "/vacancy/**", "/profile/resumes", "/resumes/*/edit", "resume/new").hasRole("APPLICANT")

                        .anyRequest().authenticated()
                );

        return http.build();
    }

}
