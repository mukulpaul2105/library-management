package in.mpApp.JwtWithAWS.models.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AuditedResponse {

    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
