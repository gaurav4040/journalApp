package net.gouravjangra.journalApp.repository;


import net.gouravjangra.journalApp.entity.Users;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users, ObjectId> {
    Users findByUserName(String username);
    void deleteByUserName(String username);
}
