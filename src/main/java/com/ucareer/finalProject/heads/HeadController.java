package com.ucareer.finalProject.heads;

import com.ucareer.finalProject.core.CoreResponseBody;
import com.ucareer.finalProject.menusItems.MenusItem;
import com.ucareer.finalProject.menusItems.MenusItemRepository;
import com.ucareer.finalProject.users.User;
import com.ucareer.finalProject.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/heads")
@CrossOrigin("*")
public class HeadController {

    @Autowired
    private HeadService headService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenusItemRepository menusItemRepository;

    @PostMapping("/")
    public ResponseEntity<CoreResponseBody> addOne (@RequestBody Head head){

        Head addedOne = this.headService.addOne(head);
        CoreResponseBody response = new CoreResponseBody(addedOne,"",null);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoreResponseBody> update (@PathVariable Long id, @RequestBody Head head){

        Head headResult = this.headService.update(id, head);
        CoreResponseBody response = new CoreResponseBody(headResult,"",null);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CoreResponseBody> deleteOne (@PathVariable Long id){

        CoreResponseBody responseBody;
        try {
            this.headService.deleteById(id);
            responseBody = new CoreResponseBody(true,"deleted successfully",null);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e){
            responseBody = new CoreResponseBody(null, "deleted failed",e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/")
    public ResponseEntity<CoreResponseBody> getAll(){

        List<Head> results = this.headService.getAll();
        CoreResponseBody responseBody = new CoreResponseBody(results,"",null);

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoreResponseBody> getOneById (@PathVariable Long id){

        Head result = this.headService.getOneById(id);
        CoreResponseBody responseBody = new CoreResponseBody(result,"",null);

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/users")
    public ResponseEntity<CoreResponseBody> getUser(){
        List<User> foundBody = this.userRepository.findAll();
        CoreResponseBody response = new CoreResponseBody(foundBody, "display successfully", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menusItems")
    public ResponseEntity<CoreResponseBody> getMenusItem(){
        List<MenusItem> foundBody = this.menusItemRepository.findAll();
        CoreResponseBody response = new CoreResponseBody(foundBody, "display successfully", null);
        return ResponseEntity.ok(response);
    }
}
