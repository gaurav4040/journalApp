package net.gouravjangra.journalApp.services;

import net.gouravjangra.journalApp.entity.JournalEntry;
import net.gouravjangra.journalApp.entity.Users;
import net.gouravjangra.journalApp.repository.JournalEntryRepository;
import net.gouravjangra.journalApp.repository.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveEntry(JournalEntry myEntry){
        try {
                return journalEntryRepository.save(myEntry);
            }catch (Exception e){
                return null;
            }
    }



    public JournalEntry getEntryById(ObjectId myId){
        Optional<JournalEntry> je =  journalEntryRepository.findById(myId);

        return je.orElse(null);
    }

    public boolean deleteEntryById(ObjectId myId){
        if(journalEntryRepository.existsById(myId)){
            try {

               journalEntryRepository.deleteById(myId);
                return true;
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }

    public boolean updateEntryById(ObjectId myId,JournalEntry myEntry){


        if(journalEntryRepository.existsById(myId)) {

            Optional<JournalEntry> old = journalEntryRepository.findById(myId);

            if(old.isEmpty())return false;

            if (myEntry.getTitle() == null || myEntry.getTitle().equals("")) {
                old.get().setTitle(old.get().getTitle());
            } else {
                old.get().setTitle(myEntry.getTitle());
            }
            if (myEntry.getContent() == null || myEntry.getContent().equals("")) {
                old.get().setContent(old.get().getContent());
            } else {
                old.get().setContent(myEntry.getContent());
            }
            try {
                journalEntryRepository.save(old.get());
                return true;
            }catch (Exception e){
                return false;
            }

        }

        return false;

    }
}
