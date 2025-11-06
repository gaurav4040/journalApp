package net.gouravjangra.journalApp.services;

import net.gouravjangra.journalApp.entity.Users;
import net.gouravjangra.journalApp.repository.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> saveUser(Users user){
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRoles(Arrays.asList("USER"));
                usersRepository.save(user);
                System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    }

    public boolean saveEntry(String username,ObjectId id){
        try {
            Users user = findByUserName(username);
//            user.setJournalEntries(List.of(id));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ResponseEntity<List<Users>> getAll(){
        List<Users> all = usersRepository.findAll();

        if(all==null||all.isEmpty())return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(all,HttpStatus.OK);
    }


    public boolean deleteEntryByIdInUser(ObjectId id,String username){

            try {
                Users user  = usersRepository.findByUserName(username);
                user.getJournalEntries().removeIf(x->x.getId().equals(id));
                return true;
            }catch (Exception e){
                return false;
            }


    }

    public Users findByUserName(String username){
        return usersRepository.findByUserName(username);
    }

    public void deleteUserByUsername(String username){
        usersRepository.deleteByUserName(username);
    }
}
