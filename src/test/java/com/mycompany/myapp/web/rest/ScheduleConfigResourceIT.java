package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JDemoApp;
import com.mycompany.myapp.domain.ScheduleConfig;
import com.mycompany.myapp.repository.ScheduleConfigRepository;
import com.mycompany.myapp.service.ScheduleConfigService;
import com.mycompany.myapp.service.dto.ScheduleConfigDTO;
import com.mycompany.myapp.service.mapper.ScheduleConfigMapper;

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
 * Integration tests for the {@link ScheduleConfigResource} REST controller.
 */
@SpringBootTest(classes = JDemoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScheduleConfigResourceIT {

    private static final Integer DEFAULT_PRIVILIGE = 1;
    private static final Integer UPDATED_PRIVILIGE = 2;

    private static final Integer DEFAULT_RETRY_TIME = 1;
    private static final Integer UPDATED_RETRY_TIME = 2;

    private static final Integer DEFAULT_RETRY_INTERVAL = 1;
    private static final Integer UPDATED_RETRY_INTERVAL = 2;

    @Autowired
    private ScheduleConfigRepository scheduleConfigRepository;

    @Autowired
    private ScheduleConfigMapper scheduleConfigMapper;

    @Autowired
    private ScheduleConfigService scheduleConfigService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduleConfigMockMvc;

    private ScheduleConfig scheduleConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleConfig createEntity(EntityManager em) {
        ScheduleConfig scheduleConfig = new ScheduleConfig()
            .privilige(DEFAULT_PRIVILIGE)
            .retryTime(DEFAULT_RETRY_TIME)
            .retryInterval(DEFAULT_RETRY_INTERVAL);
        return scheduleConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleConfig createUpdatedEntity(EntityManager em) {
        ScheduleConfig scheduleConfig = new ScheduleConfig()
            .privilige(UPDATED_PRIVILIGE)
            .retryTime(UPDATED_RETRY_TIME)
            .retryInterval(UPDATED_RETRY_INTERVAL);
        return scheduleConfig;
    }

    @BeforeEach
    public void initTest() {
        scheduleConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduleConfig() throws Exception {
        int databaseSizeBeforeCreate = scheduleConfigRepository.findAll().size();
        // Create the ScheduleConfig
        ScheduleConfigDTO scheduleConfigDTO = scheduleConfigMapper.toDto(scheduleConfig);
        restScheduleConfigMockMvc.perform(post("/api/schedule-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ScheduleConfig in the database
        List<ScheduleConfig> scheduleConfigList = scheduleConfigRepository.findAll();
        assertThat(scheduleConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduleConfig testScheduleConfig = scheduleConfigList.get(scheduleConfigList.size() - 1);
        assertThat(testScheduleConfig.getPrivilige()).isEqualTo(DEFAULT_PRIVILIGE);
        assertThat(testScheduleConfig.getRetryTime()).isEqualTo(DEFAULT_RETRY_TIME);
        assertThat(testScheduleConfig.getRetryInterval()).isEqualTo(DEFAULT_RETRY_INTERVAL);
    }

    @Test
    @Transactional
    public void createScheduleConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleConfigRepository.findAll().size();

        // Create the ScheduleConfig with an existing ID
        scheduleConfig.setId(1L);
        ScheduleConfigDTO scheduleConfigDTO = scheduleConfigMapper.toDto(scheduleConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleConfigMockMvc.perform(post("/api/schedule-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleConfig in the database
        List<ScheduleConfig> scheduleConfigList = scheduleConfigRepository.findAll();
        assertThat(scheduleConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScheduleConfigs() throws Exception {
        // Initialize the database
        scheduleConfigRepository.saveAndFlush(scheduleConfig);

        // Get all the scheduleConfigList
        restScheduleConfigMockMvc.perform(get("/api/schedule-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].privilige").value(hasItem(DEFAULT_PRIVILIGE)))
            .andExpect(jsonPath("$.[*].retryTime").value(hasItem(DEFAULT_RETRY_TIME)))
            .andExpect(jsonPath("$.[*].retryInterval").value(hasItem(DEFAULT_RETRY_INTERVAL)));
    }
    
    @Test
    @Transactional
    public void getScheduleConfig() throws Exception {
        // Initialize the database
        scheduleConfigRepository.saveAndFlush(scheduleConfig);

        // Get the scheduleConfig
        restScheduleConfigMockMvc.perform(get("/api/schedule-configs/{id}", scheduleConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheduleConfig.getId().intValue()))
            .andExpect(jsonPath("$.privilige").value(DEFAULT_PRIVILIGE))
            .andExpect(jsonPath("$.retryTime").value(DEFAULT_RETRY_TIME))
            .andExpect(jsonPath("$.retryInterval").value(DEFAULT_RETRY_INTERVAL));
    }
    @Test
    @Transactional
    public void getNonExistingScheduleConfig() throws Exception {
        // Get the scheduleConfig
        restScheduleConfigMockMvc.perform(get("/api/schedule-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduleConfig() throws Exception {
        // Initialize the database
        scheduleConfigRepository.saveAndFlush(scheduleConfig);

        int databaseSizeBeforeUpdate = scheduleConfigRepository.findAll().size();

        // Update the scheduleConfig
        ScheduleConfig updatedScheduleConfig = scheduleConfigRepository.findById(scheduleConfig.getId()).get();
        // Disconnect from session so that the updates on updatedScheduleConfig are not directly saved in db
        em.detach(updatedScheduleConfig);
        updatedScheduleConfig
            .privilige(UPDATED_PRIVILIGE)
            .retryTime(UPDATED_RETRY_TIME)
            .retryInterval(UPDATED_RETRY_INTERVAL);
        ScheduleConfigDTO scheduleConfigDTO = scheduleConfigMapper.toDto(updatedScheduleConfig);

        restScheduleConfigMockMvc.perform(put("/api/schedule-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleConfigDTO)))
            .andExpect(status().isOk());

        // Validate the ScheduleConfig in the database
        List<ScheduleConfig> scheduleConfigList = scheduleConfigRepository.findAll();
        assertThat(scheduleConfigList).hasSize(databaseSizeBeforeUpdate);
        ScheduleConfig testScheduleConfig = scheduleConfigList.get(scheduleConfigList.size() - 1);
        assertThat(testScheduleConfig.getPrivilige()).isEqualTo(UPDATED_PRIVILIGE);
        assertThat(testScheduleConfig.getRetryTime()).isEqualTo(UPDATED_RETRY_TIME);
        assertThat(testScheduleConfig.getRetryInterval()).isEqualTo(UPDATED_RETRY_INTERVAL);
    }

    @Test
    @Transactional
    public void updateNonExistingScheduleConfig() throws Exception {
        int databaseSizeBeforeUpdate = scheduleConfigRepository.findAll().size();

        // Create the ScheduleConfig
        ScheduleConfigDTO scheduleConfigDTO = scheduleConfigMapper.toDto(scheduleConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleConfigMockMvc.perform(put("/api/schedule-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleConfig in the database
        List<ScheduleConfig> scheduleConfigList = scheduleConfigRepository.findAll();
        assertThat(scheduleConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheduleConfig() throws Exception {
        // Initialize the database
        scheduleConfigRepository.saveAndFlush(scheduleConfig);

        int databaseSizeBeforeDelete = scheduleConfigRepository.findAll().size();

        // Delete the scheduleConfig
        restScheduleConfigMockMvc.perform(delete("/api/schedule-configs/{id}", scheduleConfig.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScheduleConfig> scheduleConfigList = scheduleConfigRepository.findAll();
        assertThat(scheduleConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
