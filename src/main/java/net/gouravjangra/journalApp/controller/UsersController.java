package net.gouravjangra.journalApp.controller;

import net.gouravjangra.journalApp.entity.Users;
import net.gouravjangra.journalApp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        return usersService.getAll();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByUsername(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        usersService.deleteUserByUsername(username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody Users user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        Users tempUser = usersService.findByUserName(username);

        if(tempUser!=null){
            tempUser.setUserName(user.getUserName());
            tempUser.setPassword(user.getPassword());
            return usersService.saveUser(tempUser);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
