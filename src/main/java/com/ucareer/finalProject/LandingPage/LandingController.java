package com.ucareer.finalProject.LandingPage;


import com.ucareer.finalProject.core.CoreResponseBody;
import com.ucareer.finalProject.heads.HeadService;
import com.ucareer.finalProject.menusItems.MenusItem;
import com.ucareer.finalProject.menusItems.MenusItemRepository;
import com.ucareer.finalProject.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api2")
@CrossOrigin("*")
public class LandingController {

    @Autowired
    private HeadService headService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenusItemRepository menusItemRepository;


    @GetMapping("/menus_Items")
    public ResponseEntity<CoreResponseBody> getMenusItem(){
        List<MenusItem> foundBody = this.menusItemRepository.findAll();
        CoreResponseBody response = new CoreResponseBody(foundBody, "display successfully", null);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/head")
//    public ResponseEntity<CoreResponseBody> getHead(){
//
//        List<Head> results = this.headService.getAll();
//        CoreResponseBody responseBody = new CoreResponseBody(results,"",null);
//
//        return ResponseEntity.ok(responseBody);
//    }
    //get  landing  entity




}
