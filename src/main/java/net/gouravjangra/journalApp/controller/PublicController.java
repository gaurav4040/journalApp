package net.gouravjangra.journalApp.controller;

import net.gouravjangra.journalApp.entity.Users;
import net.gouravjangra.journalApp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody Users user){
        return usersService.saveUser(user);
    }
}
