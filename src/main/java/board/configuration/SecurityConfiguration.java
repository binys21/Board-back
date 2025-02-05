package board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth-> auth
                .requestMatchers("/", "/login", "/home").permitAll()
                .requestMatchers("/board/**", "/api/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
        );
        http.formLogin(auth -> auth
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .permitAll()
        );
        http.csrf(auth -> auth.disable());

    return http.build();
     }
}
