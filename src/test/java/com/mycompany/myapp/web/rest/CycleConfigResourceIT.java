package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JDemoApp;
import com.mycompany.myapp.domain.CycleConfig;
import com.mycompany.myapp.domain.DependencyCondition;
import com.mycompany.myapp.repository.CycleConfigRepository;
import com.mycompany.myapp.service.CycleConfigService;
import com.mycompany.myapp.service.dto.CycleConfigDTO;
import com.mycompany.myapp.service.mapper.CycleConfigMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.CycleType;
/**
 * Integration tests for the {@link CycleConfigResource} REST controller.
 */
@SpringBootTest(classes = JDemoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CycleConfigResourceIT {

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ASSIGNED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ASSIGNED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ASSIGNED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ASSIGNED_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final CycleType DEFAULT_CYCLE_ENUM = CycleType.YEAR;
    private static final CycleType UPDATED_CYCLE_ENUM = CycleType.MONTH;

    @Autowired
    private CycleConfigRepository cycleConfigRepository;

    @Autowired
    private CycleConfigMapper cycleConfigMapper;

    @Autowired
    private CycleConfigService cycleConfigService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCycleConfigMockMvc;

    private CycleConfig cycleConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CycleConfig createEntity(EntityManager em) {
        CycleConfig cycleConfig = new CycleConfig()
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .assignedDate(DEFAULT_ASSIGNED_DATE)
            .assignedTime(DEFAULT_ASSIGNED_TIME)
            .cycleEnum(DEFAULT_CYCLE_ENUM);
        // Add required entity
        DependencyCondition dependencyCondition;
        if (TestUtil.findAll(em, DependencyCondition.class).isEmpty()) {
            dependencyCondition = DependencyConditionResourceIT.createEntity(em);
            em.persist(dependencyCondition);
            em.flush();
        } else {
            dependencyCondition = TestUtil.findAll(em, DependencyCondition.class).get(0);
        }
        cycleConfig.setConditionRoot(dependencyCondition);
        return cycleConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CycleConfig createUpdatedEntity(EntityManager em) {
        CycleConfig cycleConfig = new CycleConfig()
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .assignedDate(UPDATED_ASSIGNED_DATE)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .cycleEnum(UPDATED_CYCLE_ENUM);
        // Add required entity
        DependencyCondition dependencyCondition;
        if (TestUtil.findAll(em, DependencyCondition.class).isEmpty()) {
            dependencyCondition = DependencyConditionResourceIT.createUpdatedEntity(em);
            em.persist(dependencyCondition);
            em.flush();
        } else {
            dependencyCondition = TestUtil.findAll(em, DependencyCondition.class).get(0);
        }
        cycleConfig.setConditionRoot(dependencyCondition);
        return cycleConfig;
    }

    @BeforeEach
    public void initTest() {
        cycleConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createCycleConfig() throws Exception {
        int databaseSizeBeforeCreate = cycleConfigRepository.findAll().size();
        // Create the CycleConfig
        CycleConfigDTO cycleConfigDTO = cycleConfigMapper.toDto(cycleConfig);
        restCycleConfigMockMvc.perform(post("/api/cycle-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cycleConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the CycleConfig in the database
        List<CycleConfig> cycleConfigList = cycleConfigRepository.findAll();
        assertThat(cycleConfigList).hasSize(databaseSizeBeforeCreate + 1);
        CycleConfig testCycleConfig = cycleConfigList.get(cycleConfigList.size() - 1);
        assertThat(testCycleConfig.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testCycleConfig.getAssignedDate()).isEqualTo(DEFAULT_ASSIGNED_DATE);
        assertThat(testCycleConfig.getAssignedTime()).isEqualTo(DEFAULT_ASSIGNED_TIME);
        assertThat(testCycleConfig.getCycleEnum()).isEqualTo(DEFAULT_CYCLE_ENUM);
    }

    @Test
    @Transactional
    public void createCycleConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cycleConfigRepository.findAll().size();

        // Create the CycleConfig with an existing ID
        cycleConfig.setId(1L);
        CycleConfigDTO cycleConfigDTO = cycleConfigMapper.toDto(cycleConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCycleConfigMockMvc.perform(post("/api/cycle-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cycleConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CycleConfig in the database
        List<CycleConfig> cycleConfigList = cycleConfigRepository.findAll();
        assertThat(cycleConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCycleConfigs() throws Exception {
        // Initialize the database
        cycleConfigRepository.saveAndFlush(cycleConfig);

        // Get all the cycleConfigList
        restCycleConfigMockMvc.perform(get("/api/cycle-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cycleConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].assignedDate").value(hasItem(DEFAULT_ASSIGNED_DATE.toString())))
            .andExpect(jsonPath("$.[*].assignedTime").value(hasItem(DEFAULT_ASSIGNED_TIME.toString())))
            .andExpect(jsonPath("$.[*].cycleEnum").value(hasItem(DEFAULT_CYCLE_ENUM.toString())));
    }
    
    @Test
    @Transactional
    public void getCycleConfig() throws Exception {
        // Initialize the database
        cycleConfigRepository.saveAndFlush(cycleConfig);

        // Get the cycleConfig
        restCycleConfigMockMvc.perform(get("/api/cycle-configs/{id}", cycleConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cycleConfig.getId().intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.assignedDate").value(DEFAULT_ASSIGNED_DATE.toString()))
            .andExpect(jsonPath("$.assignedTime").value(DEFAULT_ASSIGNED_TIME.toString()))
            .andExpect(jsonPath("$.cycleEnum").value(DEFAULT_CYCLE_ENUM.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCycleConfig() throws Exception {
        // Get the cycleConfig
        restCycleConfigMockMvc.perform(get("/api/cycle-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCycleConfig() throws Exception {
        // Initialize the database
        cycleConfigRepository.saveAndFlush(cycleConfig);

        int databaseSizeBeforeUpdate = cycleConfigRepository.findAll().size();

        // Update the cycleConfig
        CycleConfig updatedCycleConfig = cycleConfigRepository.findById(cycleConfig.getId()).get();
        // Disconnect from session so that the updates on updatedCycleConfig are not directly saved in db
        em.detach(updatedCycleConfig);
        updatedCycleConfig
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .assignedDate(UPDATED_ASSIGNED_DATE)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .cycleEnum(UPDATED_CYCLE_ENUM);
        CycleConfigDTO cycleConfigDTO = cycleConfigMapper.toDto(updatedCycleConfig);

        restCycleConfigMockMvc.perform(put("/api/cycle-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cycleConfigDTO)))
            .andExpect(status().isOk());

        // Validate the CycleConfig in the database
        List<CycleConfig> cycleConfigList = cycleConfigRepository.findAll();
        assertThat(cycleConfigList).hasSize(databaseSizeBeforeUpdate);
        CycleConfig testCycleConfig = cycleConfigList.get(cycleConfigList.size() - 1);
        assertThat(testCycleConfig.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testCycleConfig.getAssignedDate()).isEqualTo(UPDATED_ASSIGNED_DATE);
        assertThat(testCycleConfig.getAssignedTime()).isEqualTo(UPDATED_ASSIGNED_TIME);
        assertThat(testCycleConfig.getCycleEnum()).isEqualTo(UPDATED_CYCLE_ENUM);
    }

    @Test
    @Transactional
    public void updateNonExistingCycleConfig() throws Exception {
        int databaseSizeBeforeUpdate = cycleConfigRepository.findAll().size();

        // Create the CycleConfig
        CycleConfigDTO cycleConfigDTO = cycleConfigMapper.toDto(cycleConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCycleConfigMockMvc.perform(put("/api/cycle-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cycleConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CycleConfig in the database
        List<CycleConfig> cycleConfigList = cycleConfigRepository.findAll();
        assertThat(cycleConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCycleConfig() throws Exception {
        // Initialize the database
        cycleConfigRepository.saveAndFlush(cycleConfig);

        int databaseSizeBeforeDelete = cycleConfigRepository.findAll().size();

        // Delete the cycleConfig
        restCycleConfigMockMvc.perform(delete("/api/cycle-configs/{id}", cycleConfig.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CycleConfig> cycleConfigList = cycleConfigRepository.findAll();
        assertThat(cycleConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
