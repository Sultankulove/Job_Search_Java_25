package kg.attractor.job_search_java_25.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final RoleBasedAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/ws/**", "/topic/**", "/app/**"))
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/auth/login?error=true")
                        .successHandler(authenticationSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/webjars/**", "/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/api/chat/**", "/api/respond/**").authenticated()
                        .requestMatchers("/logout").authenticated()
                        .requestMatchers("/publications/*/comments/*/delete").permitAll()
                        .requestMatchers("/profile/publications").authenticated()

                        .requestMatchers("/", "/auth/**", "/error", "/auth/reset-password").permitAll()
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
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
                        .requestMatchers("/chats").authenticated()

                        .requestMatchers(HttpMethod.GET, "/publications/new", "/publications/*/edit").authenticated()
                        .requestMatchers(HttpMethod.GET, "/publications", "/publications/", "/publications/*", "/publications/*/cover").permitAll()
                        .requestMatchers(HttpMethod.POST, "/publications/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,  "/publications/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/publications/**").authenticated()

                        .requestMatchers("/vacancies").permitAll()
                        .requestMatchers("/resumes/**", "/profile/vacancies", "/vacancy/*/edit","/vacancy/new", "/chat/start/*", "vacancy/*/chat").hasRole("EMPLOYER")
                        .requestMatchers("/vacancy/*").permitAll()
                        .requestMatchers("/vacancy/*/respond").hasRole("APPLICANT")

                        .requestMatchers("/vacancies/**", "/vacancy/**", "/profile/resumes", "/resumes/*/edit", "resume/new").hasRole("APPLICANT")
                        .requestMatchers("chat/**").authenticated()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

}
