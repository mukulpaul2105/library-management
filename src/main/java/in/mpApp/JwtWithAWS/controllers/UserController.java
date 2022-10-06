package in.mpApp.JwtWithAWS.controllers;

import in.mpApp.JwtWithAWS.dtos.UserDTO;
import in.mpApp.JwtWithAWS.models.responses.ApiResponse;
import in.mpApp.JwtWithAWS.models.responses.UserCreatedResponse;
import in.mpApp.JwtWithAWS.services.IUserService;
import in.mpApp.JwtWithAWS.utils.DataMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserCreatedResponse>>> getAllUsers() {
        log.info("Received Request for Fetching all the Users");
        List<UserCreatedResponse> userCreatedResponseList = new ArrayList<>();
        userService.getAll().forEach((udo) -> {
            userCreatedResponseList.add(DataMapperUtil.convertTo(udo, UserCreatedResponse.class));
        });
        log.info("Retrieved all the Users: {} ", userCreatedResponseList);
        return ResponseEntity.ok(ApiResponse.success(userCreatedResponseList));
    }

}
