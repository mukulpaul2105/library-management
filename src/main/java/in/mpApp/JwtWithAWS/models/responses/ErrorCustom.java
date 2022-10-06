package in.mpApp.JwtWithAWS.models.responses;

import lombok.Data;

@Data
public class ErrorCustom {

    private String code;
    private String description;

    public static ErrorCustom create(final String code, final String description) {
        ErrorCustom error = new ErrorCustom();
        error.setCode(code);
        error.setDescription(description);
        return error;
    }
}
