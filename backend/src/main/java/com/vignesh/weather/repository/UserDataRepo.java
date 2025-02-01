package com.vignesh.weather.repository;

import com.vignesh.weather.model.UserDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepo extends MongoRepository<String, UserDataModel> {
    UserDataModel findByUserId(String userId);
}
