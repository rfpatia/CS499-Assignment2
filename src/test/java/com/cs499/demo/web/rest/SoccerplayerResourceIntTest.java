package com.cs499.demo.web.rest;

import com.cs499.demo.Assignment2App;

import com.cs499.demo.domain.Soccerplayer;
import com.cs499.demo.domain.Country;
import com.cs499.demo.domain.Club;
import com.cs499.demo.repository.SoccerplayerRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SoccerplayerResource REST controller.
 *
 * @see SoccerplayerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Assignment2App.class)
public class SoccerplayerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_GOAL = 1;
    private static final Integer UPDATED_GOAL = 2;

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    @Inject
    private SoccerplayerRepository soccerplayerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSoccerplayerMockMvc;

    private Soccerplayer soccerplayer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SoccerplayerResource soccerplayerResource = new SoccerplayerResource();
        ReflectionTestUtils.setField(soccerplayerResource, "soccerplayerRepository", soccerplayerRepository);
        this.restSoccerplayerMockMvc = MockMvcBuilders.standaloneSetup(soccerplayerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soccerplayer createEntity(EntityManager em) {
        Soccerplayer soccerplayer = new Soccerplayer()
                .name(DEFAULT_NAME)
                .goal(DEFAULT_GOAL)
                .position(DEFAULT_POSITION);
        // Add required entity
        Country country = CountryResourceIntTest.createEntity(em);
        em.persist(country);
        em.flush();
        soccerplayer.setCountry(country);
        // Add required entity
        Club club = ClubResourceIntTest.createEntity(em);
        em.persist(club);
        em.flush();
        soccerplayer.setClub(club);
        return soccerplayer;
    }

    @Before
    public void initTest() {
        soccerplayer = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoccerplayer() throws Exception {
        int databaseSizeBeforeCreate = soccerplayerRepository.findAll().size();

        // Create the Soccerplayer

        restSoccerplayerMockMvc.perform(post("/api/soccerplayers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soccerplayer)))
            .andExpect(status().isCreated());

        // Validate the Soccerplayer in the database
        List<Soccerplayer> soccerplayerList = soccerplayerRepository.findAll();
        assertThat(soccerplayerList).hasSize(databaseSizeBeforeCreate + 1);
        Soccerplayer testSoccerplayer = soccerplayerList.get(soccerplayerList.size() - 1);
        assertThat(testSoccerplayer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSoccerplayer.getGoal()).isEqualTo(DEFAULT_GOAL);
        assertThat(testSoccerplayer.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    public void createSoccerplayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = soccerplayerRepository.findAll().size();

        // Create the Soccerplayer with an existing ID
        Soccerplayer existingSoccerplayer = new Soccerplayer();
        existingSoccerplayer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoccerplayerMockMvc.perform(post("/api/soccerplayers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSoccerplayer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Soccerplayer> soccerplayerList = soccerplayerRepository.findAll();
        assertThat(soccerplayerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = soccerplayerRepository.findAll().size();
        // set the field null
        soccerplayer.setName(null);

        // Create the Soccerplayer, which fails.

        restSoccerplayerMockMvc.perform(post("/api/soccerplayers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soccerplayer)))
            .andExpect(status().isBadRequest());

        List<Soccerplayer> soccerplayerList = soccerplayerRepository.findAll();
        assertThat(soccerplayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGoalIsRequired() throws Exception {
        int databaseSizeBeforeTest = soccerplayerRepository.findAll().size();
        // set the field null
        soccerplayer.setGoal(null);

        // Create the Soccerplayer, which fails.

        restSoccerplayerMockMvc.perform(post("/api/soccerplayers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soccerplayer)))
            .andExpect(status().isBadRequest());

        List<Soccerplayer> soccerplayerList = soccerplayerRepository.findAll();
        assertThat(soccerplayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = soccerplayerRepository.findAll().size();
        // set the field null
        soccerplayer.setPosition(null);

        // Create the Soccerplayer, which fails.

        restSoccerplayerMockMvc.perform(post("/api/soccerplayers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soccerplayer)))
            .andExpect(status().isBadRequest());

        List<Soccerplayer> soccerplayerList = soccerplayerRepository.findAll();
        assertThat(soccerplayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSoccerplayers() throws Exception {
        // Initialize the database
        soccerplayerRepository.saveAndFlush(soccerplayer);

        // Get all the soccerplayerList
        restSoccerplayerMockMvc.perform(get("/api/soccerplayers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soccerplayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].goal").value(hasItem(DEFAULT_GOAL)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())));
    }

    @Test
    @Transactional
    public void getSoccerplayer() throws Exception {
        // Initialize the database
        soccerplayerRepository.saveAndFlush(soccerplayer);

        // Get the soccerplayer
        restSoccerplayerMockMvc.perform(get("/api/soccerplayers/{id}", soccerplayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(soccerplayer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.goal").value(DEFAULT_GOAL))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSoccerplayer() throws Exception {
        // Get the soccerplayer
        restSoccerplayerMockMvc.perform(get("/api/soccerplayers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoccerplayer() throws Exception {
        // Initialize the database
        soccerplayerRepository.saveAndFlush(soccerplayer);
        int databaseSizeBeforeUpdate = soccerplayerRepository.findAll().size();

        // Update the soccerplayer
        Soccerplayer updatedSoccerplayer = soccerplayerRepository.findOne(soccerplayer.getId());
        updatedSoccerplayer
                .name(UPDATED_NAME)
                .goal(UPDATED_GOAL)
                .position(UPDATED_POSITION);

        restSoccerplayerMockMvc.perform(put("/api/soccerplayers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSoccerplayer)))
            .andExpect(status().isOk());

        // Validate the Soccerplayer in the database
        List<Soccerplayer> soccerplayerList = soccerplayerRepository.findAll();
        assertThat(soccerplayerList).hasSize(databaseSizeBeforeUpdate);
        Soccerplayer testSoccerplayer = soccerplayerList.get(soccerplayerList.size() - 1);
        assertThat(testSoccerplayer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSoccerplayer.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testSoccerplayer.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void updateNonExistingSoccerplayer() throws Exception {
        int databaseSizeBeforeUpdate = soccerplayerRepository.findAll().size();

        // Create the Soccerplayer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSoccerplayerMockMvc.perform(put("/api/soccerplayers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soccerplayer)))
            .andExpect(status().isCreated());

        // Validate the Soccerplayer in the database
        List<Soccerplayer> soccerplayerList = soccerplayerRepository.findAll();
        assertThat(soccerplayerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSoccerplayer() throws Exception {
        // Initialize the database
        soccerplayerRepository.saveAndFlush(soccerplayer);
        int databaseSizeBeforeDelete = soccerplayerRepository.findAll().size();

        // Get the soccerplayer
        restSoccerplayerMockMvc.perform(delete("/api/soccerplayers/{id}", soccerplayer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Soccerplayer> soccerplayerList = soccerplayerRepository.findAll();
        assertThat(soccerplayerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
