package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JDemoApp;
import com.mycompany.myapp.domain.AlarmConfig;
import com.mycompany.myapp.repository.AlarmConfigRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlarmConfigResource} REST controller.
 */
@SpringBootTest(classes = JDemoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlarmConfigResourceIT {

    private static final String DEFAULT_ALARM_METHODB = "AAAAAAAAAA";
    private static final String UPDATED_ALARM_METHODB = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_INFO = "BBBBBBBBBB";

    @Autowired
    private AlarmConfigRepository alarmConfigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlarmConfigMockMvc;

    private AlarmConfig alarmConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmConfig createEntity(EntityManager em) {
        AlarmConfig alarmConfig = new AlarmConfig()
            .alarmMethodb(DEFAULT_ALARM_METHODB)
            .accountInfo(DEFAULT_ACCOUNT_INFO);
        return alarmConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmConfig createUpdatedEntity(EntityManager em) {
        AlarmConfig alarmConfig = new AlarmConfig()
            .alarmMethodb(UPDATED_ALARM_METHODB)
            .accountInfo(UPDATED_ACCOUNT_INFO);
        return alarmConfig;
    }

    @BeforeEach
    public void initTest() {
        alarmConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarmConfig() throws Exception {
        int databaseSizeBeforeCreate = alarmConfigRepository.findAll().size();
        // Create the AlarmConfig
        restAlarmConfigMockMvc.perform(post("/api/alarm-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmConfig)))
            .andExpect(status().isCreated());

        // Validate the AlarmConfig in the database
        List<AlarmConfig> alarmConfigList = alarmConfigRepository.findAll();
        assertThat(alarmConfigList).hasSize(databaseSizeBeforeCreate + 1);
        AlarmConfig testAlarmConfig = alarmConfigList.get(alarmConfigList.size() - 1);
        assertThat(testAlarmConfig.getAlarmMethodb()).isEqualTo(DEFAULT_ALARM_METHODB);
        assertThat(testAlarmConfig.getAccountInfo()).isEqualTo(DEFAULT_ACCOUNT_INFO);
    }

    @Test
    @Transactional
    public void createAlarmConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmConfigRepository.findAll().size();

        // Create the AlarmConfig with an existing ID
        alarmConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmConfigMockMvc.perform(post("/api/alarm-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmConfig)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmConfig in the database
        List<AlarmConfig> alarmConfigList = alarmConfigRepository.findAll();
        assertThat(alarmConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlarmConfigs() throws Exception {
        // Initialize the database
        alarmConfigRepository.saveAndFlush(alarmConfig);

        // Get all the alarmConfigList
        restAlarmConfigMockMvc.perform(get("/api/alarm-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].alarmMethodb").value(hasItem(DEFAULT_ALARM_METHODB)))
            .andExpect(jsonPath("$.[*].accountInfo").value(hasItem(DEFAULT_ACCOUNT_INFO)));
    }
    
    @Test
    @Transactional
    public void getAlarmConfig() throws Exception {
        // Initialize the database
        alarmConfigRepository.saveAndFlush(alarmConfig);

        // Get the alarmConfig
        restAlarmConfigMockMvc.perform(get("/api/alarm-configs/{id}", alarmConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alarmConfig.getId().intValue()))
            .andExpect(jsonPath("$.alarmMethodb").value(DEFAULT_ALARM_METHODB))
            .andExpect(jsonPath("$.accountInfo").value(DEFAULT_ACCOUNT_INFO));
    }
    @Test
    @Transactional
    public void getNonExistingAlarmConfig() throws Exception {
        // Get the alarmConfig
        restAlarmConfigMockMvc.perform(get("/api/alarm-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarmConfig() throws Exception {
        // Initialize the database
        alarmConfigRepository.saveAndFlush(alarmConfig);

        int databaseSizeBeforeUpdate = alarmConfigRepository.findAll().size();

        // Update the alarmConfig
        AlarmConfig updatedAlarmConfig = alarmConfigRepository.findById(alarmConfig.getId()).get();
        // Disconnect from session so that the updates on updatedAlarmConfig are not directly saved in db
        em.detach(updatedAlarmConfig);
        updatedAlarmConfig
            .alarmMethodb(UPDATED_ALARM_METHODB)
            .accountInfo(UPDATED_ACCOUNT_INFO);

        restAlarmConfigMockMvc.perform(put("/api/alarm-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlarmConfig)))
            .andExpect(status().isOk());

        // Validate the AlarmConfig in the database
        List<AlarmConfig> alarmConfigList = alarmConfigRepository.findAll();
        assertThat(alarmConfigList).hasSize(databaseSizeBeforeUpdate);
        AlarmConfig testAlarmConfig = alarmConfigList.get(alarmConfigList.size() - 1);
        assertThat(testAlarmConfig.getAlarmMethodb()).isEqualTo(UPDATED_ALARM_METHODB);
        assertThat(testAlarmConfig.getAccountInfo()).isEqualTo(UPDATED_ACCOUNT_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarmConfig() throws Exception {
        int databaseSizeBeforeUpdate = alarmConfigRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmConfigMockMvc.perform(put("/api/alarm-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmConfig)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmConfig in the database
        List<AlarmConfig> alarmConfigList = alarmConfigRepository.findAll();
        assertThat(alarmConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlarmConfig() throws Exception {
        // Initialize the database
        alarmConfigRepository.saveAndFlush(alarmConfig);

        int databaseSizeBeforeDelete = alarmConfigRepository.findAll().size();

        // Delete the alarmConfig
        restAlarmConfigMockMvc.perform(delete("/api/alarm-configs/{id}", alarmConfig.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlarmConfig> alarmConfigList = alarmConfigRepository.findAll();
        assertThat(alarmConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
