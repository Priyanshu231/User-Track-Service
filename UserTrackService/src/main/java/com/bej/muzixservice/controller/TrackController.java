package com.bej.muzixservice.controller;

import com.bej.muzixservice.domain.Track;
import com.bej.muzixservice.domain.User;
import com.bej.muzixservice.exception.TrackAlreadyExistsException;
import com.bej.muzixservice.exception.TrackNotFoundException;
import com.bej.muzixservice.exception.UserAlreadyExistsException;
import com.bej.muzixservice.exception.UserNotFoundException;
import com.bej.muzixservice.service.ITrackService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("api/v2")
public class TrackController {
    // Auto wire the service layer object
    private ITrackService trackService;
    private ResponseEntity responseEntity;

    public TrackController(ITrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException{
        // Register a new user and save to db, return 201 status if user is saved else 500 status
        try{
            responseEntity=new ResponseEntity<>(trackService.registerUser(user),HttpStatus.CREATED);
        }catch (UserAlreadyExistsException e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
            throw new UserAlreadyExistsException();
        }
        return responseEntity;
    }

    @PostMapping("user/track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track, HttpServletRequest request)throws TrackAlreadyExistsException, UserNotFoundException {
       // add a track to a specific user, return 201 status if track is saved else 500 status
        try{
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("userId from claims :: " + claims.getSubject());
            String userId = claims.getSubject();
            System.out.println("userId :: "+userId);
            responseEntity=new ResponseEntity<>(trackService.saveUserTrackToWishList(track,userId),HttpStatus.CREATED);
        }catch (TrackAlreadyExistsException e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            throw new TrackAlreadyExistsException();
        }
        return responseEntity;
    }

    @GetMapping("user/tracks")
    public ResponseEntity<?> getAllTracks(HttpServletRequest request)throws Exception {
        // display all the tracks of a specific user, extract user id from claims,
        try{
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("userId from claims :: " + claims.getSubject());
            String userId = claims.getSubject();
            System.out.println("userId :: "+userId);
            responseEntity=new ResponseEntity<>(trackService.getAllUserTracksFromWishList(userId),HttpStatus.OK);
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            throw new UserNotFoundException();
        }
        // return 200 status if user is saved else 500 status
        return responseEntity;
    }

    @DeleteMapping("user/track/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable String trackId,HttpServletRequest request) throws TrackNotFoundException,UserNotFoundException {
        // delete a track based on user id and track id, extract user id from claims
        // return 200 status if user is saved else 500 status
        try{
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("userId from claims :: " + claims.getSubject());
            String userId = claims.getSubject();
            System.out.println("userId :: "+userId);
            responseEntity=new ResponseEntity<>(trackService.deleteTrack(userId,trackId),HttpStatus.OK);
        }catch (TrackNotFoundException e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            throw new TrackNotFoundException();
        }
        return responseEntity;
    }

    @PutMapping("user/track")
    public ResponseEntity<?> updateTrack(@RequestBody Track track, HttpServletRequest request)throws UserNotFoundException,TrackNotFoundException,TrackAlreadyExistsException{
        // update a track based on user id and track id, extract user id from claims
        // return 200 status if user is saved else 500 status
        try{
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("userId from claims :: " + claims.getSubject());
            String userId = claims.getSubject();
            System.out.println("userId :: "+userId);
            responseEntity=new ResponseEntity<>(trackService.updateUserTrackWishListWithGivenTrack(userId,track),HttpStatus.OK);
        }catch (TrackNotFoundException e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            throw new TrackNotFoundException();
        }

       return responseEntity;
    }

}
