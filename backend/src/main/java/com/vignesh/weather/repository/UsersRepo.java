package com.vignesh.weather.repository;

import com.vignesh.weather.model.UsersModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends MongoRepository<UsersModel, String> {
    UsersModel findByUsername(String username);
}
