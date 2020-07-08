package com.ucareer.finalProject.LandingPage;


import com.ucareer.finalProject.core.CoreResponseBody;
import com.ucareer.finalProject.core.JWT;
import com.ucareer.finalProject.menusItems.MenusItemRepository;
import com.ucareer.finalProject.users.User;
import com.ucareer.finalProject.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/landing")
@CrossOrigin("*")
public class LandingController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenusItemRepository menusItemRepository;

    @Autowired
    private JWT jwt;

    //landing/me

    @GetMapping("/{username:^(?!me).+}")
    public ResponseEntity<CoreResponseBody> getLanding(@PathVariable String username){
        User user = userService.findUser(username);
        if (user == null){
            CoreResponseBody responseBody = new CoreResponseBody(null, "user doesn't exist",null);
            return ResponseEntity.ok(responseBody);
        }else {
            CoreResponseBody response = new CoreResponseBody(user.getLanding(), "display successfully", null);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<CoreResponseBody> getMyLanding(@RequestHeader ("Authorization") String token){
        //jwt
        String username = jwt.verifyLoginToken(token);
        if (username == null ){
            CoreResponseBody responseBody = new CoreResponseBody(null, "username doesn't exist",null);
            return ResponseEntity.ok(responseBody);
        }else {
            User user = userService.findUser(username);
            CoreResponseBody responseBody = new CoreResponseBody(user.getLanding(),"display successfully",null);
            return ResponseEntity.ok(responseBody);
        }
    }
}
