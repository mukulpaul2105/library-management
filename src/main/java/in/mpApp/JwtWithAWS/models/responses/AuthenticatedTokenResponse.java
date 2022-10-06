package in.mpApp.JwtWithAWS.models.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthenticatedTokenResponse {
    private String token;
    private LocalDateTime expiry;
}
