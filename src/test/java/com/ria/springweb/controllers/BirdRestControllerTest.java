package com.ria.springweb.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ria.springweb.entities.Bird;
import com.ria.springweb.service.BirdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebMvcTest
class BirdRestControllerTest {

    private static final int ID = 1;
    private static final String BIRDNAME = "albatross";
    private static final String UPDATEDBIRDNAME = "sparrow";
    private static final String BIRDFAMILY = "albatrossfamily";
    private static final ArrayList<String> CONTINENTS = new ArrayList<>(Arrays.asList("Asia", "Africa"));
    private static final String BIRDADDED = "addedatthistime";
    private static final Boolean BIRDVISIBLE = true;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BirdService birdService;

    Bird samplebird;
    ObjectMapper objectMapper;

    @BeforeEach
    public void initializer() {
        samplebird = new Bird(ID, BIRDNAME, BIRDFAMILY, CONTINENTS, BIRDADDED, BIRDVISIBLE);
        objectMapper = new ObjectMapper();
    }
    @Test
    public void testGetBirds() throws Exception {
        List<Bird> birdslist = new ArrayList<>(); birdslist.add(samplebird);
        when(birdService.getBirds()).thenReturn(birdslist);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/birds"))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(birdslist)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        assertEquals(200, response.getStatus());
        List<Bird> receivedBird = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Bird>>(){});

        assertEquals(ID, receivedBird.get(0).getId());
        assertEquals(BIRDNAME, receivedBird.get(0).getName());
        assertEquals(BIRDFAMILY, receivedBird.get(0).getFamily());
        assertEquals(BIRDADDED, receivedBird.get(0).getAdded());
        assertEquals(CONTINENTS, receivedBird.get(0).getContinents());
        assertTrue(receivedBird.get(0).isVisible());
        assertThat(samplebird).isEqualToComparingFieldByField(receivedBird.get(0));
    }

    @Test
    public void testGetBirdById() throws Exception{
        when(birdService.getBird(ID)).thenReturn(samplebird);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/birds/{ID}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        Bird receivedBird = objectMapper.readValue(response.getContentAsString(), Bird.class);
        assertEquals(200, response.getStatus());

        assertThat(samplebird).isEqualToComparingFieldByField(receivedBird);


    }@Test
    public void testGetBirdByName() throws Exception{
        when(birdService.getBird(BIRDNAME)).thenReturn(samplebird);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/birds/name?name={BIRDNAME}", BIRDNAME))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        Bird receivedBird = objectMapper.readValue(response.getContentAsString(), Bird.class);
        assertEquals(200, response.getStatus());

        assertThat(samplebird).isEqualToComparingFieldByField(receivedBird);


    }

    @Test
    public void testAddBird() throws Exception {
        when(birdService.addBird(any())).thenReturn(samplebird);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/birds").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(samplebird)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(samplebird), true))
                .andReturn().getResponse();

        Bird addedBird = objectMapper.readValue(response.getContentAsString(), Bird.class);
        assertEquals(201, response.getStatus());

        assertThat(samplebird).isEqualToComparingFieldByField(addedBird);

    }

    @Test
    public void testUpdateBird() throws Exception {
        samplebird.setName(UPDATEDBIRDNAME);
        when(birdService.updateBird(any())).thenReturn(samplebird);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/birds").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(samplebird)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(samplebird), true))
                .andReturn().getResponse();

        Bird updatedBird = objectMapper.readValue(response.getContentAsString(), Bird.class);
        assertEquals(201, response.getStatus());
        assertThat(samplebird).isEqualToComparingFieldByField(updatedBird);

    }

    @Test
    public void testDeleteBird() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/birds/{ID}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

}