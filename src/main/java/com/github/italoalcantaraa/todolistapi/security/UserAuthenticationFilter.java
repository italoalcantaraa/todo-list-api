package com.github.italoalcantaraa.todolistapi.security;

import com.github.italoalcantaraa.todolistapi.model.User;
import com.github.italoalcantaraa.todolistapi.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private UserRepository userRepository;
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(checkIfEndpointIsNotPublic(request)){
            String token = recoveryToken(request);
            if(token != null) {
                String subject = jwtTokenService.getSubjectFromToken(token); // obtem o "dono" do token
                User user = userRepository.findByUsername(subject).get();

                UserDetailsImpl userDetails = new UserDetailsImpl(user);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                return;
            }

            throw new RuntimeException("Token ausente!");
        }
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
            if(authorizationHeader != null) {
                return authorizationHeader.replace("Bearer ", "");
            }

            return  null;
    }


    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestToUri = request.getRequestURI(); // pega a rota acessada

        return !Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestToUri);
    }

}
