package in.mpApp.JwtWithAWS.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class AuditedEntity {

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_on", nullable = false, columnDefinition = "DATE")
    private LocalDateTime createdOn;

    @Column(name = "updated_on", columnDefinition = "DATE")
    private LocalDateTime updatedOn;
}
