package com.fabg21.mysport.players.web.rest;

import com.fabg21.mysport.players.MySportPlayersApp;
import com.fabg21.mysport.players.domain.Players;
import com.fabg21.mysport.players.repository.PlayersRepository;
import com.fabg21.mysport.players.service.PlayersService;
import com.fabg21.mysport.players.service.dto.PlayersDTO;
import com.fabg21.mysport.players.service.mapper.PlayersMapper;
import com.fabg21.mysport.players.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.fabg21.mysport.players.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlayersResource} REST controller.
 */
@SpringBootTest(classes = MySportPlayersApp.class)
public class PlayersResourceIT {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    @Autowired
    private PlayersRepository playersRepository;

    @Autowired
    private PlayersMapper playersMapper;

    @Autowired
    private PlayersService playersService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPlayersMockMvc;

    private Players players;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayersResource playersResource = new PlayersResource(playersService);
        this.restPlayersMockMvc = MockMvcBuilders.standaloneSetup(playersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Players createEntity(EntityManager em) {
        Players players = new Players()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .avatar(DEFAULT_AVATAR);
        return players;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Players createUpdatedEntity(EntityManager em) {
        Players players = new Players()
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .avatar(UPDATED_AVATAR);
        return players;
    }

    @BeforeEach
    public void initTest() {
        players = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayers() throws Exception {
        int databaseSizeBeforeCreate = playersRepository.findAll().size();

        // Create the Players
        PlayersDTO playersDTO = playersMapper.toDto(players);
        restPlayersMockMvc.perform(post("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playersDTO)))
            .andExpect(status().isCreated());

        // Validate the Players in the database
        List<Players> playersList = playersRepository.findAll();
        assertThat(playersList).hasSize(databaseSizeBeforeCreate + 1);
        Players testPlayers = playersList.get(playersList.size() - 1);
        assertThat(testPlayers.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testPlayers.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testPlayers.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPlayers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPlayers.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPlayers.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPlayers.getAvatar()).isEqualTo(DEFAULT_AVATAR);
    }

    @Test
    @Transactional
    public void createPlayersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playersRepository.findAll().size();

        // Create the Players with an existing ID
        players.setId(1L);
        PlayersDTO playersDTO = playersMapper.toDto(players);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayersMockMvc.perform(post("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Players in the database
        List<Players> playersList = playersRepository.findAll();
        assertThat(playersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = playersRepository.findAll().size();
        // set the field null
        players.setFirstname(null);

        // Create the Players, which fails.
        PlayersDTO playersDTO = playersMapper.toDto(players);

        restPlayersMockMvc.perform(post("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playersDTO)))
            .andExpect(status().isBadRequest());

        List<Players> playersList = playersRepository.findAll();
        assertThat(playersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = playersRepository.findAll().size();
        // set the field null
        players.setLastname(null);

        // Create the Players, which fails.
        PlayersDTO playersDTO = playersMapper.toDto(players);

        restPlayersMockMvc.perform(post("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playersDTO)))
            .andExpect(status().isBadRequest());

        List<Players> playersList = playersRepository.findAll();
        assertThat(playersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlayers() throws Exception {
        // Initialize the database
        playersRepository.saveAndFlush(players);

        // Get all the playersList
        restPlayersMockMvc.perform(get("/api/players?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(players.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR)));
    }
    
    @Test
    @Transactional
    public void getPlayers() throws Exception {
        // Initialize the database
        playersRepository.saveAndFlush(players);

        // Get the players
        restPlayersMockMvc.perform(get("/api/players/{id}", players.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(players.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR));
    }

    @Test
    @Transactional
    public void getNonExistingPlayers() throws Exception {
        // Get the players
        restPlayersMockMvc.perform(get("/api/players/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayers() throws Exception {
        // Initialize the database
        playersRepository.saveAndFlush(players);

        int databaseSizeBeforeUpdate = playersRepository.findAll().size();

        // Update the players
        Players updatedPlayers = playersRepository.findById(players.getId()).get();
        // Disconnect from session so that the updates on updatedPlayers are not directly saved in db
        em.detach(updatedPlayers);
        updatedPlayers
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .avatar(UPDATED_AVATAR);
        PlayersDTO playersDTO = playersMapper.toDto(updatedPlayers);

        restPlayersMockMvc.perform(put("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playersDTO)))
            .andExpect(status().isOk());

        // Validate the Players in the database
        List<Players> playersList = playersRepository.findAll();
        assertThat(playersList).hasSize(databaseSizeBeforeUpdate);
        Players testPlayers = playersList.get(playersList.size() - 1);
        assertThat(testPlayers.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPlayers.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPlayers.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPlayers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPlayers.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPlayers.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPlayers.getAvatar()).isEqualTo(UPDATED_AVATAR);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayers() throws Exception {
        int databaseSizeBeforeUpdate = playersRepository.findAll().size();

        // Create the Players
        PlayersDTO playersDTO = playersMapper.toDto(players);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayersMockMvc.perform(put("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Players in the database
        List<Players> playersList = playersRepository.findAll();
        assertThat(playersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlayers() throws Exception {
        // Initialize the database
        playersRepository.saveAndFlush(players);

        int databaseSizeBeforeDelete = playersRepository.findAll().size();

        // Delete the players
        restPlayersMockMvc.perform(delete("/api/players/{id}", players.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Players> playersList = playersRepository.findAll();
        assertThat(playersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
