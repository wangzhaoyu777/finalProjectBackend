package com.ucareer.finalProject.heads;

import com.ucareer.finalProject.core.CoreResponseBody;
import com.ucareer.finalProject.core.JWT;
import com.ucareer.finalProject.menusItems.MenusItemRepository;
import com.ucareer.finalProject.users.User;
import com.ucareer.finalProject.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/heads")
@CrossOrigin("*")
public class HeadController {

    @Autowired
    private HeadService headService;

    @Autowired
    private UserService userService;

    @Autowired
    private HeadRepository headRepository;

    @Autowired
    private JWT jwt;

    @Autowired
    private MenusItemRepository menusItemRepository;
//todo:

    @GetMapping("/me")
    public ResponseEntity<CoreResponseBody> getHead (@RequestHeader("Authorization") String token){
        String username = jwt.verifyLoginToken(token);
        if (token == null){
            CoreResponseBody responseBody = new CoreResponseBody(null,"user is not exist",null);
            return ResponseEntity.ok(responseBody);
        }else {
            User user = userService.findUser(username);
            Head foundHead = user.getLanding().getHead();
            CoreResponseBody responseBody = new CoreResponseBody(foundHead,"head displayed successfully",null);
            return ResponseEntity.ok(responseBody);
        }
    }

    @PutMapping("/me")
    public ResponseEntity<CoreResponseBody> update (@RequestHeader("Authorization") String token, @RequestBody Head head){
        String username = jwt.verifyLoginToken(token);
        if (token == null){
            CoreResponseBody responseBody = new CoreResponseBody(null,"user is not exist",null);
            return ResponseEntity.ok(responseBody);
        }else {
            User user = userService.findUser(username);
            Head foundHead = user.getLanding().getHead();
            foundHead.setTitle(head.getTitle());
            foundHead.setImg_url(head.getImg_url());
            foundHead.setDescription(head.getDescription());
            headRepository.save(foundHead);
            CoreResponseBody responseBody = new CoreResponseBody(foundHead,"updated",null);
            return ResponseEntity.ok(responseBody);
        }
    }
}
