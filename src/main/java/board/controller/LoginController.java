package board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import board.common.JwtUtils;
import board.dto.CustomUserDetails;
import board.dto.LoginRequest;
import board.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/loginProc")
    @ResponseStatus(HttpStatus.OK)
    public String loginProc(@RequestBody LoginRequest loginRequest) {
        //사용자 인증
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthentication(loginRequest.getUsername(),
                        loginRequest.getPassword())
        );
        //인증된 사용자 정보 가져오기
        UserDetails userDetails=(CustomUserDetails) UserDetailsService.loadUserByUsername(loginRequest.getUsername());
        log.debug(">>> "+userDetails.getUsername());

        //JWT 토큰 생성
        String token = jwtUtils.generateToken(userDetails);
        log.debug(">>>> " + token);

        return token;

    }

}
