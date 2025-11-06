package net.gouravjangra.journalApp.controller;

import net.gouravjangra.journalApp.entity.JournalEntry;
import net.gouravjangra.journalApp.entity.Users;
import net.gouravjangra.journalApp.services.JournalEntryService;
import net.gouravjangra.journalApp.services.UsersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntryOfUser(@PathVariable String username){

        Users user = usersService.findByUserName(username);

        if(user!=null){
//            List<JournalEntry> j = new ArrayList<>();
//            user.getJournalEntries().forEach(id-> {
//                 j.add(journalEntryService.getEntryById(id));
//            });
            return new ResponseEntity<>(user.getJournalEntries(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String username){
        myEntry.setDate(LocalDateTime.now());

        JournalEntry jE = journalEntryService.saveEntry(myEntry);

        if(jE==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Users user = usersService.findByUserName(username);

        if(user==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            user.getJournalEntries().add(jE);

            return usersService.saveUser(user);

        }catch (Exception e){
            journalEntryService.deleteEntryById(jE.getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
       return new ResponseEntity<>(journalEntryService.getEntryById(myId),HttpStatus.OK);
    }

    @DeleteMapping("{username}/id/{myId}")
    public ResponseEntity<Boolean> deleteJournalEntryById(@PathVariable ObjectId myId,@PathVariable String username){
        return journalEntryService.deleteEntryById(myId)&&usersService.deleteEntryByIdInUser(myId,username)?new ResponseEntity<>(true,HttpStatus.OK):new ResponseEntity<>(false,HttpStatus.NO_CONTENT);
    }

    @PutMapping("{username}/id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry myEntry,
            @PathVariable String username){
         return journalEntryService.updateEntryById(myId,myEntry)? new ResponseEntity<>(myEntry, HttpStatus.OK):new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
