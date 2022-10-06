package in.mpApp.JwtWithAWS.controllers;

import in.mpApp.JwtWithAWS.dtos.TokenDTO;
import in.mpApp.JwtWithAWS.dtos.UserDTO;
import in.mpApp.JwtWithAWS.models.requests.AuthenticationRequest;
import in.mpApp.JwtWithAWS.models.responses.AuthenticatedTokenResponse;
import in.mpApp.JwtWithAWS.services.IUserService;
import in.mpApp.JwtWithAWS.utils.DataMapperUtil;
import in.mpApp.JwtWithAWS.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
@Slf4j
public class HomeController {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticatedTokenResponse>  authenticate(@Validated @RequestBody final AuthenticationRequest authenticationRequest) {
        log.info("Received Authentication Request: {} ", authenticationRequest);
        final UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(token);
        } catch (final DisabledException | BadCredentialsException | LockedException e) {
            log.info("Error Authenticating Username: {} ", authenticationRequest.getUsername());
            if(e instanceof BadCredentialsException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        final UserDTO userDTO = userService.getByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new RuntimeException("User with Username " + authenticationRequest.getUsername() + " not found"));
        final TokenDTO tokenDTO = jwtUtil.generateToken(userDTO);
        log.info("Generated Token DTO: {} ", tokenDTO);
        return ResponseEntity.ok(DataMapperUtil.convertTo(tokenDTO, AuthenticatedTokenResponse.class));
    }
}
