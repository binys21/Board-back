package board.configuration;

import board.security.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth-> auth
                .requestMatchers("/", "/login", "/home", "/join", "/joinProc").permitAll()
                .requestMatchers("/board/**", "/api/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
        );
        http.formLogin(auth -> auth
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .permitAll()
                .successHandler(successHandler)

        );
        http.csrf(auth -> auth.disable());

    return http.build();
     }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
