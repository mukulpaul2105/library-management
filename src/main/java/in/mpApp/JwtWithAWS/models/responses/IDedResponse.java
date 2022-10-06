package in.mpApp.JwtWithAWS.models.responses;

import in.mpApp.JwtWithAWS.models.responses.AuditedResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IDedResponse extends AuditedResponse {
    private Long id;
}
