package com.ucareer.finalProject.menusItems;

import com.ucareer.finalProject.LandingPage.Landing;
import com.ucareer.finalProject.core.CoreResponseBody;
import com.ucareer.finalProject.core.JWT;
import com.ucareer.finalProject.users.User;
import com.ucareer.finalProject.users.UserRepository;
import com.ucareer.finalProject.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menusItems")
@CrossOrigin("*")
public class MenusItemsController {

    @Autowired
    private MenusItemsService menusItemsService;

    @Autowired
    private MenusItemRepository menusItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWT jwt;


    @GetMapping("/")
    public ResponseEntity<CoreResponseBody> getAllItems(){

            List<MenusItem> menusItems = this.menusItemsService.getAll();
            CoreResponseBody responseBody = new CoreResponseBody(menusItems,"",null);
            return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/me")
    public ResponseEntity<CoreResponseBody> getAllMyItems(@RequestHeader("Authorization") String token){
        String username = jwt.verifyLoginToken(token);
        if (token == null){
            CoreResponseBody responseBody = new CoreResponseBody(null, "user doesn't exist",null);
            return ResponseEntity.ok(responseBody);
        }else {
            User user = userService.findUser(username);
            List<MenusItem> menusItems = user.getLanding().getMenusItems();
            CoreResponseBody responseBody = new CoreResponseBody(menusItems,"",null);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<CoreResponseBody> getOneById (@RequestHeader("Authorization") String token,@PathVariable Long id){
        String username = jwt.verifyLoginToken(token);
        if (token == null) {
            CoreResponseBody responseBody = new CoreResponseBody(null, "user doesn't exist",null);
            return ResponseEntity.ok(responseBody);
        }else {
            User user = userService.findUser(username);
            MenusItem menusItem = menusItemRepository.findById(id).orElse(null);
            if (user.getLanding().getId() != menusItem.getLanding().getId()){
                CoreResponseBody responseBody = new CoreResponseBody(null, "invalid action",null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
            }
            if (menusItem == null){
                CoreResponseBody responseBody = new CoreResponseBody(user.getLanding().getMenusItems(),"",null);
                return ResponseEntity.ok(responseBody);
            } else {
                CoreResponseBody responseBody = new CoreResponseBody(menusItem,"",null);
                return ResponseEntity.ok(responseBody);
            }
        }
    }

    @PutMapping("/me/{id}")
    public ResponseEntity<CoreResponseBody> update (@RequestHeader("Authorization") String token,@PathVariable Long id, @RequestBody MenusItem menusItem){
        String username = jwt.verifyLoginToken(token);
        if (token==null){
            CoreResponseBody responseBody = new CoreResponseBody(null, "user doesn't exist",null);
            return ResponseEntity.ok(responseBody);
        }else {
            User user = userService.findUser(username);
            MenusItem menusItemFound = menusItemRepository.findById(id).orElse(null);
            if (user.getLanding().getId() != menusItemFound.getLanding().getId()){
                CoreResponseBody responseBody = new CoreResponseBody(null, "invalid action",null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
            } else {
                MenusItem updatedItem = menusItemsService.update(id, menusItem);
                CoreResponseBody response = new CoreResponseBody(updatedItem,"updated successfully",null);
                return ResponseEntity.ok(response);
            }
        }
    }

    @DeleteMapping("/me/{id}")
    public ResponseEntity<CoreResponseBody> deleteOne (@RequestHeader("Authorization") String token, @PathVariable Long id){
        String username = jwt.verifyLoginToken(token);
        if (token == null){
            CoreResponseBody responseBody = new CoreResponseBody(null, "this record doesn't exist",null);
            return ResponseEntity.ok(responseBody);
        } else {
            User user = userService.findUser(username);
            MenusItem menusItemFound = menusItemRepository.findById(id).orElse(null);
            if (user.getLanding().getId() != menusItemFound.getLanding().getId()){
                CoreResponseBody responseBody = new CoreResponseBody(null, "invalid action",null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
            }
            if (menusItemFound == null){
                CoreResponseBody responseBody = new CoreResponseBody(null, "this record doesn't exist",null);
                return ResponseEntity.ok(responseBody);
            } else {
                menusItemFound.setLanding(null);
                menusItemRepository.deleteById(menusItemFound.getId());
                CoreResponseBody response = new CoreResponseBody(true,"delete successfully",null);
                return ResponseEntity.ok(response);
            }
        }
    }

    @PostMapping("/me")
    public ResponseEntity<CoreResponseBody> createOne (@RequestHeader("Authorization") String token,@RequestBody MenusItem menusItem){
        String username = jwt.verifyLoginToken(token);
        CoreResponseBody coreResponseBody = null;
        if(username != null){
            User currentUser = userRepository.findByUsername(username);
            Landing currentLanding =currentUser.getLanding();

            MenusItem savingMenusItem = new MenusItem();
            savingMenusItem.setDescription(menusItem.getDescription());
            savingMenusItem.setImg_url(menusItem.getImg_url());
            savingMenusItem.setName(menusItem.getName());
            savingMenusItem.setPrice(menusItem.getPrice());
            savingMenusItem.setLanding(currentLanding);

            MenusItem savedMenusItems = menusItemRepository.save(savingMenusItem);
            coreResponseBody = new CoreResponseBody(savedMenusItems, "created new item ",null);
        }else {
            coreResponseBody = new CoreResponseBody(null, "invalid",null);
        }
        return ResponseEntity.ok(coreResponseBody);
    }
}
