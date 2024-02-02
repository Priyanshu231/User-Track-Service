package com.bej.muzixservice.service;

import com.bej.muzixservice.domain.Track;
import com.bej.muzixservice.domain.User;
import com.bej.muzixservice.exception.TrackAlreadyExistsException;
import com.bej.muzixservice.exception.TrackNotFoundException;
import com.bej.muzixservice.exception.UserAlreadyExistsException;
import com.bej.muzixservice.exception.UserNotFoundException;

import java.util.List;

public interface ITrackService {

    User registerUser(User user) throws UserAlreadyExistsException;
    User saveUserTrackToWishList(Track track,String userId) throws TrackAlreadyExistsException, UserNotFoundException;
    List<Track> getAllUserTracksFromWishList(String userId) throws Exception;
    User deleteTrack(String userId,String trackId) throws TrackNotFoundException, UserNotFoundException;
    User updateUserTrackWishListWithGivenTrack(String userId,Track track) throws UserNotFoundException, TrackNotFoundException, TrackAlreadyExistsException;
}
