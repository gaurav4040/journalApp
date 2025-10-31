package net.gouravjangra.journalApp.controller;

import net.gouravjangra.journalApp.entity.JournalEntry;
import net.gouravjangra.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){


        return journalEntryService.getAllEntry();
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        return journalEntryService.saveEntry(myEntry)?new ResponseEntity<>(myEntry,HttpStatus.OK):new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
       return journalEntryService.getEntryById(myId);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<Boolean> deleteJournalEntryById(@PathVariable ObjectId myId){
        return journalEntryService.deleteEntryById(myId)?new ResponseEntity<>(true,HttpStatus.OK):new ResponseEntity<>(false,HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId myId,@RequestBody JournalEntry myEntry){
         return journalEntryService.updateEntryById(myId,myEntry)? new ResponseEntity<>(myEntry, HttpStatus.OK):new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
