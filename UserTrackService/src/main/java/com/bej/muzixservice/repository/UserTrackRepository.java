package com.bej.muzixservice.repository;

import com.bej.muzixservice.domain.Track;
import com.bej.muzixservice.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserTrackRepository extends MongoRepository<User,String> {
    @Query("{'trackList.trackName': ?0}")
    boolean existsByTrackListTrackName(String trackName);
}
