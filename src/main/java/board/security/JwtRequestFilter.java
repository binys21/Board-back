package board.security;

import java.io.IOException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import board.common.JwtUtils;
import board.entity.UserEntity;
import board.repository.UserRepository;
import board.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;



@Component
@Slf4j
public class JwtRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)throws ServletException, IOException{
        String jwtToken=null;
        String subject=null;

        //Authorization 요청 헤더 포함 여부 확인, 헤더 정보 추출
        String authorizationHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            jwtToken=authorizationHeader.substring(7);
            subject=jwtUtils.getSubjectFromToken(jwtToken);
        }else{
            log.error("Authorization 헤더 누락 or 토큰 형식 오류");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT Token");
            response.getWriter().flush();
            return;
        }
        UserEntity userEntity=userRepository.findByUsername(subject);
        if(!jwtUtils.validateToken(jwtToken, userEntity)){
            log.error("사용자 정보가 일치하지 않습니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT Token");
            response.getWriter().flush();
            return;
        }

        UserDetails userDetails=this.userDetailsService.loadUserByUsername(subject);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)throws ServletException{
        String[] excludePath = { "/loginProc", "/joinProc" };

        String uri=request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(uri::startsWith);

    }
}
