package in.mpApp.JwtWithAWS.filters;

import in.mpApp.JwtWithAWS.dtos.UserDTO;
import in.mpApp.JwtWithAWS.dtos.UserPrincipalDTO;
import in.mpApp.JwtWithAWS.services.IUserService;
import in.mpApp.JwtWithAWS.utils.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private IUserService userService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String authTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authTokenHeader)) {
            log.info("No Authorization Token Found");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), " UNAUTHORIZED");
            return;
        }

        if (!authTokenHeader.startsWith("Bearer")) {
            log.info("No Bearer Token found in Authorization");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), " UNAUTHORIZED");
            return;
        }

        final String jwtToken = authTokenHeader.substring(7);

        if (!StringUtils.hasText(authTokenHeader)) {
            log.info("No Bearer Token Found in Authentication");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), " UNAUTHORIZED");
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                final String username = jwtUtil.getUsername(jwtToken);
                final UserDTO userDTO = userService.getByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
                if(jwtUtil.validateToken(jwtToken, userDTO)) {
                    log.info("JWT {} validated: ", jwtToken);
                    final UserPrincipalDTO userPrincipalDTO = new UserPrincipalDTO(userDTO);
                    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userPrincipalDTO, null, userPrincipalDTO.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                } else {
                    log.info("JWT {} Invalid: ", jwtToken);
                }

            } catch (final ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
                log.info("Error Authentication JWT: \n", e);
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED");
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        final String requestPath = request.getRequestURI();
        if("/authenticate".equals(requestPath) || "/registration".equals(requestPath)
                || "/v2/api-docs".equals(requestPath)) {
            return true;
        }
        return false;
    }
}
