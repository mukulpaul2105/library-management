package in.mpApp.JwtWithAWS.models.responses;

import in.mpApp.JwtWithAWS.enums.RequestStatus;
import lombok.Data;

@Data
public class ApiResponse<D> {

    private RequestStatus status;
    private D data;
    private ErrorCustom error;

    public static <D> ApiResponse<D> success(final D data) {
        ApiResponse<D> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(RequestStatus.SUCCESS);
        apiResponse.setData(data);
        apiResponse.setError(null);
        return apiResponse;
    }

    public static <D> ApiResponse<D> error(final ErrorCustom error) {
        ApiResponse<D> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(RequestStatus.FAILURE);
        apiResponse.setData(null);
        apiResponse.setError(error);
        return apiResponse;
    }
}
