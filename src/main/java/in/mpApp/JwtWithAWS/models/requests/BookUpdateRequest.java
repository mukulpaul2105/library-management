package in.mpApp.JwtWithAWS.models.requests;

import in.mpApp.JwtWithAWS.enums.BookCategory;
import in.mpApp.JwtWithAWS.enums.BookStatus;
import lombok.Data;

@Data
public class BookUpdateRequest {

    private String name;
    private String isbn;
    private BookCategory category;
    private BookStatus status;
}
