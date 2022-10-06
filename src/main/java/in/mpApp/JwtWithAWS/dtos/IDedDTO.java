package in.mpApp.JwtWithAWS.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IDedDTO extends AuditedDTO {

    private Long id;
}
