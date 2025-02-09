package board.security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.stereotype.Component;
import board.entity.UserEntity;
import board.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());

        //request.getSession().setAttribute("user", userEntity);

        //토큰 발행시간 & 만료시간
        Date now =new Date();
        Long expirationTime=Long.parseLong(env.getProperty("token.expiration-time"));

        //JWT 토큰 생성
        String jwtToken = Jwts.builder()
                        .claim("name",userEntity.getName())
                .claim("email",userEntity.getEmail())
                        .subject(userEntity.getUsername())
                                .id(String.valueOf(userEntity.getSeq()))
                                        .issuedAt(now)
                                                .expiration(new Date(now.getTIme()+expirationTime))
                                                        .compact();
        log.debug(jwtToken);



        response.sendRedirect("/");
    }
}
