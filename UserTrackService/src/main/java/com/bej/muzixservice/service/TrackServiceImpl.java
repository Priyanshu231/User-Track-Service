package com.bej.muzixservice.service;

import com.bej.muzixservice.domain.Track;
import com.bej.muzixservice.domain.User;
import com.bej.muzixservice.exception.TrackAlreadyExistsException;
import com.bej.muzixservice.exception.TrackNotFoundException;
import com.bej.muzixservice.exception.UserAlreadyExistsException;
import com.bej.muzixservice.exception.UserNotFoundException;

import com.bej.muzixservice.repository.UserTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImpl implements ITrackService{

    // Autowire the UserTrackRepository using constructor autowiring
    private UserTrackRepository userTrackRepository;
    @Autowired
    public TrackServiceImpl(UserTrackRepository userTrackRepository) {
        this.userTrackRepository = userTrackRepository;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        // Register a new user
        if(userTrackRepository.findById(user.getUserId()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        return userTrackRepository.save(user);
    }

    @Override
    public User saveUserTrackToWishList(Track track, String userId) throws TrackAlreadyExistsException, UserNotFoundException {
        // Save the tracks to the play list of user.
        User user = userTrackRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<Track> tracks = user.getTrackList();
        if (tracks == null) {
            tracks = new ArrayList<>();
        }
        tracks.add(track);
        user.setTrackList(tracks);

        return userTrackRepository.save(user);
    }

    @Override
    public List<Track> getAllUserTracksFromWishList(String userId) throws Exception {
        // Get all the tracks for a specific user
        if(userTrackRepository.findById(userId).isEmpty()){
            throw new Exception();
        }
        return userTrackRepository.findById(userId).get().getTrackList();
    }

    @Override
    public User deleteTrack(String userId, String trackId) throws TrackNotFoundException, UserNotFoundException {
      // delete the user details specified
        boolean trackIdIsPresent=false;
        if (userTrackRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        User user =userTrackRepository.findById(userId).get();
        List<Track> tracks=user.getTrackList();
        trackIdIsPresent=tracks.removeIf(x->x.getTrackId().equals(trackId));
        if(!trackIdIsPresent){
            throw new TrackNotFoundException();
        }
        user.setTrackList(tracks);
        return userTrackRepository.save(user);
    }


    @Override
    public User updateUserTrackWishListWithGivenTrack(String userId, Track track) throws UserNotFoundException, TrackNotFoundException, TrackAlreadyExistsException {
      // Update the specific details of User
        Optional<User> optUser = userTrackRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optUser.get();
        if (track.getTrackName() != null) {
            String newTrackName = track.getTrackName();
            boolean trackExists = userTrackRepository.existsByTrackListTrackName(newTrackName);
            if (!trackExists) {
                throw new TrackNotFoundException();
            }
            if (user.getTrackList().contains(newTrackName)) {
                throw new TrackAlreadyExistsException();
            }
            user.getTrackList().add(track);
        }
        userTrackRepository.save(user);

        return user;
    }
}
