package in.mpApp.JwtWithAWS.dtos;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public abstract class AuditedDTO {

    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
