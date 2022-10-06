package in.mpApp.JwtWithAWS.controllers;

import in.mpApp.JwtWithAWS.dtos.UserDTO;
import in.mpApp.JwtWithAWS.entities.UserEntity;
import in.mpApp.JwtWithAWS.models.requests.RegistrationRequest;
import in.mpApp.JwtWithAWS.models.responses.ApiResponse;
import in.mpApp.JwtWithAWS.models.responses.RegisteredResponse;
import in.mpApp.JwtWithAWS.services.IUserService;
import in.mpApp.JwtWithAWS.utils.DataMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/registration")
@Slf4j
public class RegistrationController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<RegisteredResponse>> create(@Validated @RequestBody final RegistrationRequest registrationRequest) {
        log.info("Received Registration Request: {} ");
        UserDTO userDTO = DataMapperUtil.convertTo(registrationRequest, UserDTO.class);
        userDTO = userService.create(userDTO);
        log.info("Created User: {} ", userDTO);
        return ResponseEntity.ok(ApiResponse.success(DataMapperUtil.convertTo(userDTO, RegisteredResponse.class)));
    }

}
