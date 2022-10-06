package in.mpApp.JwtWithAWS.models.requests;

import in.mpApp.JwtWithAWS.enums.BookCategory;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BookCreationRequest {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100, message = "Book Name must be within 2 to 100 characters")
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 50, message = "ISBN must be within 4 to 30 characters")
    private String isbn;

    @NotNull
    private BookCategory category;


}
