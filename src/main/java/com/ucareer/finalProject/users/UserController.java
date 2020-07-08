package com.ucareer.finalProject.users;

import com.ucareer.finalProject.core.CoreResponseBody;
import com.ucareer.finalProject.core.JWT;
import com.ucareer.finalProject.heads.HeadRepository;
import com.ucareer.finalProject.menusItems.MenusItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private JWT jwt;

    @Autowired
    private UserService userService;

    @Autowired
    private HeadRepository headRepository;

    @Autowired
    private MenusItemRepository menusItemRepository;

    @PostMapping("/register")
    public ResponseEntity<CoreResponseBody> register(@RequestBody User useRequestBody) {
        User savedUser = this.userService.userRegister(useRequestBody);
        if(savedUser != null){
            CoreResponseBody response = new CoreResponseBody(savedUser, "register successfully", null);
            return ResponseEntity.ok(response);
        }else{
            CoreResponseBody response = new CoreResponseBody(null, "register failed, username already exist.", null);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<CoreResponseBody> login(@RequestBody LoginRequestBody loginRequestBody) {
        String token = userService.userLogin(loginRequestBody);
        if(token == null){
            CoreResponseBody response = new CoreResponseBody(null, "Username or Password is not correct", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }else{
            CoreResponseBody response = new CoreResponseBody(token, "", null);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<CoreResponseBody> getMe(@RequestHeader("Authorization") String token) {
        //验证
        String username = jwt.verifyLoginToken(token);
        if (username == null) {
            CoreResponseBody response = new CoreResponseBody(null, "token invalided", null);
            return ResponseEntity.ok(response);
        } else {
            User foundUser = userService.findUser(username);
            CoreResponseBody response = new CoreResponseBody(foundUser, "test successfully", null);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/me")
    public ResponseEntity<CoreResponseBody> updateMe(@RequestHeader("Authorization") String token, @RequestBody User userRequestBody) {
        String username = jwt.verifyLoginToken(token);
        if (username == null) {
            CoreResponseBody response = new CoreResponseBody(null, "token invalided", null);
            return ResponseEntity.ok(response);
        } else {
            User updatedUser = userService.updateUser(username, userRequestBody);
            CoreResponseBody response = new CoreResponseBody(updatedUser, "update successfully", null);
            return ResponseEntity.ok(response);
        }
    }
    }
