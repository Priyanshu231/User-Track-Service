package com.bej.muzixservice.commander.repository;

import com.bej.muzixservice.domain.Artist;
import com.bej.muzixservice.domain.Track;
import com.bej.muzixservice.domain.User;
import com.bej.muzixservice.repository.UserTrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UserTrackRepositoryTest {

    @Autowired
    private UserTrackRepository userTrackRepository;
    private Artist artist1,artist2;
    private Track track1, track2;
    private User user;
    List<Track> trackList;

    @BeforeEach
    void setUp() {
        artist1 = new Artist("1001","artist1");
        track1 = new Track("TR001","track1","Good Track",5,artist1);
        artist2 = new Artist("1002","artist2");
        track1 = new Track("TR002","track2","Good Track2",5,artist2);
        user = new User();
        user.setUserId("U1234");
        user.setAge(35);
        user.setUserName("John");
        user.setEmail("John@gmail.com");
        trackList = Arrays.asList(track1,track2);
        user.setTrackList(trackList);

    }

    @AfterEach
    void tearDown() {
        artist1=artist2 = null;
        track1 = track2 = null;
        userTrackRepository.deleteAll();
    }

    @Test
    void givenUserTrackToSaveShouldReturnSavedTrack() {
        userTrackRepository.save(user);
        User user1 = userTrackRepository.findById(user.getUserId()).get();

        assertNotNull(user1);
        assertEquals(user1.getUserId(), user.getUserId());
    }
    @Test
    public void givenTrackToDeleteShouldDeleteTrack() {
        userTrackRepository.insert(user);
        User user1 = userTrackRepository.findById(user.getUserId()).get();
        userTrackRepository.delete(user1);
        assertEquals(Optional.empty(), userTrackRepository.findById(user.getUserId()));
    }







}

