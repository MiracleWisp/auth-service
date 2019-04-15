package auth.controller;

import auth.dto.Response;
import auth.entity.AppUser;
import auth.feign.UserClient;
import auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    UserClient userClient;

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid AppUser user, BindingResult bindingResult) {
        AppUser userExists = userService.findUserByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("There is already a user registered with the username provided");
        } else {
            Response response = userClient.createUser(user);
            if (response.isSuccessful()) {
                userService.saveUser(user);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("User created");
            } else {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Try again");
            }
        }
    }
}
