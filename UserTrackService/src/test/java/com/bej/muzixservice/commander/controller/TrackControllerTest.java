package com.bej.muzixservice.commander.controller;

import com.bej.muzixservice.controller.TrackController;
import com.bej.muzixservice.domain.Artist;
import com.bej.muzixservice.domain.Track;
import com.bej.muzixservice.domain.User;
import com.bej.muzixservice.exception.UserAlreadyExistsException;
import com.bej.muzixservice.service.TrackServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TrackControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrackServiceImpl trackService;

    @InjectMocks
    private TrackController trackController;
    private Track track1, track2;
    List<Track> trackList;
    Artist artist1,artist2;
    User user;

    @BeforeEach
    void setUp() {

        artist1 = new Artist("1001","artist1");
        track1 = new Track("TR001","track1","Good Track1",4,artist1);
        artist2 = new Artist("1002","artist2");
        track2 = new Track("TR002","track2","Good Track2",4,artist2);
        trackList = Arrays.asList(track1,track2);
        user = new User();
        user.setUserId("U1234");
        user.setAge(35);
        user.setUserName("John");
        user.setEmail("John@gmail.com");
        user.setTrackList(trackList);
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
    }
    @AfterEach
    void tearDown() {
        user=null;
    }
    @Test
    public void registerUserSuccess() throws Exception {
        when(trackService.registerUser(any())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJSONString(user)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void registerUserFailure() throws Exception {
        when(trackService.registerUser(any())).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJSONString(user)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }
    private static String asJSONString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}

