package com.ucareer.finalProject.menusItems;

import com.ucareer.finalProject.core.CoreResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api3")
@CrossOrigin("*")
public class MenusItemsController {

    @Autowired
    private MenusItemsService menusItemsService;

    @Autowired
    private MenusItemRepository menusItemRepository;

    @PostMapping("/menusItems")
    public ResponseEntity<CoreResponseBody> addOne (@RequestBody MenusItem menusItem){

        MenusItem addedOne = this.menusItemsService.addOne(menusItem);
        CoreResponseBody response = new CoreResponseBody(addedOne,"",null);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/menusItems/{id}")
    public ResponseEntity<CoreResponseBody> update (@PathVariable Long id, @RequestBody MenusItem menusItem){

        MenusItem menusItemResult = this.menusItemsService.update(id, menusItem);
        CoreResponseBody response = new CoreResponseBody(menusItemResult,"",null);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/menusItems/{id}")
    public ResponseEntity<CoreResponseBody> deleteOne (@PathVariable Long id){

        CoreResponseBody responseBody;
        try {
            this.menusItemsService.deleteById(id);
            responseBody = new CoreResponseBody(true,"deleted successfully",null);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e){
            responseBody = new CoreResponseBody(null, "deleted failed",e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/menusItems")
    public ResponseEntity<CoreResponseBody> getAll(){

        List<MenusItem> results = this.menusItemsService.getAll();
        CoreResponseBody responseBody = new CoreResponseBody(results,"",null);

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/menusItems{id}")
    public ResponseEntity<CoreResponseBody> getOneById (@PathVariable Long id){

        MenusItem result = this.menusItemsService.getOneById(id);
        CoreResponseBody responseBody = new CoreResponseBody(result,"",null);

        return ResponseEntity.ok(responseBody);
    }

}
