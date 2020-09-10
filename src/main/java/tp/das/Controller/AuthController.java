package tp.das.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.das.DTOs.Auth.LoginDTO;
import tp.das.DTOs.Auth.RegisterDTO;
import tp.das.DTOs.Event.UserOperationDTO;
import tp.das.DTOs.User.UserResponseDTO;
import tp.das.Service.AuthService;
import tp.das.Service.EventsService;
import tp.das.Service.UsersService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping(path = "")
    public ResponseEntity getAccounts() throws Exception {
        return ResponseEntity.ok(AuthService.findByAll());
    }

    @PostMapping(path = "/login")
    public ResponseEntity login(@Valid @RequestBody LoginDTO loginDTO) {
        final Long authId = AuthService.checkLogin(loginDTO);
        if (authId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        final UserResponseDTO user = UsersService.findByAccountId(authId);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity logout(@Valid @RequestBody UserOperationDTO operationDTO) {
        EventsService.getInstance().cleanUserRecord(operationDTO.getUserId());
        return ResponseEntity.ok("logout");
    }

    @PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDTO registerDTO) {
        AuthService.createAccount(registerDTO);
        return ResponseEntity.ok().build();
    }
}
