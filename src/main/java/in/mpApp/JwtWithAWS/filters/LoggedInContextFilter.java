package in.mpApp.JwtWithAWS.filters;
import in.mpApp.JwtWithAWS.utils.JWTUtil;
import in.mpApp.JwtWithAWS.utils.LoggedInContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoggedInContextFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        final String authTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authTokenHeader)) {
            log.info("No Authorization Token Found");
            LoggedInContext.setCurrentUser(-999L);
            filterChain.doFilter(request, response);
            return;
        }

        if (!authTokenHeader.startsWith("Bearer")) {
            log.info("No Bearer Token found in Authorization");
            LoggedInContext.setCurrentUser(-999L);
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authTokenHeader.substring(7);

        if (!StringUtils.hasText(authTokenHeader)) {
            log.info("No Bearer Token Found in Authentication");
            LoggedInContext.setCurrentUser(-999L);

        } else {
            final Long currentUser = jwtUtil.getUserId(jwtToken);
            log.info("Current User Id: {} ",currentUser);
            LoggedInContext.setCurrentUser(currentUser);
        }

        filterChain.doFilter(request, response);

    }
}
