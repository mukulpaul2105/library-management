package in.mpApp.JwtWithAWS.models.responses;

import in.mpApp.JwtWithAWS.enums.BookCategory;
import in.mpApp.JwtWithAWS.enums.BookStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BookCreatedResponse extends IDedResponse {
    private String name;
    private String isbn;
    private BookCategory category;
    private BookStatus status;
}
