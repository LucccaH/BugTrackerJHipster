package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JDemoApp;
import com.mycompany.myapp.domain.DependencyCondition;
import com.mycompany.myapp.repository.DependencyConditionRepository;
import com.mycompany.myapp.service.DependencyConditionService;
import com.mycompany.myapp.service.dto.DependencyConditionDTO;
import com.mycompany.myapp.service.mapper.DependencyConditionMapper;

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

import com.mycompany.myapp.domain.enumeration.DependencyTypeEnum;
import com.mycompany.myapp.domain.enumeration.OperatorEnum;
/**
 * Integration tests for the {@link DependencyConditionResource} REST controller.
 */
@SpringBootTest(classes = JDemoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DependencyConditionResourceIT {

    private static final DependencyTypeEnum DEFAULT_DEPENDENCY_TYPE = DependencyTypeEnum.TASK;
    private static final DependencyTypeEnum UPDATED_DEPENDENCY_TYPE = DependencyTypeEnum.FILE;

    private static final String DEFAULT_FLAG_FIEKD_ID = "AAAAAAAAAA";
    private static final String UPDATED_FLAG_FIEKD_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ASSIGNED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ASSIGNED_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final OperatorEnum DEFAULT_OPERATOR = OperatorEnum.AND;
    private static final OperatorEnum UPDATED_OPERATOR = OperatorEnum.OR;

    @Autowired
    private DependencyConditionRepository dependencyConditionRepository;

    @Autowired
    private DependencyConditionMapper dependencyConditionMapper;

    @Autowired
    private DependencyConditionService dependencyConditionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDependencyConditionMockMvc;

    private DependencyCondition dependencyCondition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DependencyCondition createEntity(EntityManager em) {
        DependencyCondition dependencyCondition = new DependencyCondition()
            .dependencyType(DEFAULT_DEPENDENCY_TYPE)
            .flagFiekdId(DEFAULT_FLAG_FIEKD_ID)
            .assignedTime(DEFAULT_ASSIGNED_TIME)
            .operator(DEFAULT_OPERATOR);
        return dependencyCondition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DependencyCondition createUpdatedEntity(EntityManager em) {
        DependencyCondition dependencyCondition = new DependencyCondition()
            .dependencyType(UPDATED_DEPENDENCY_TYPE)
            .flagFiekdId(UPDATED_FLAG_FIEKD_ID)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .operator(UPDATED_OPERATOR);
        return dependencyCondition;
    }

    @BeforeEach
    public void initTest() {
        dependencyCondition = createEntity(em);
    }

    @Test
    @Transactional
    public void createDependencyCondition() throws Exception {
        int databaseSizeBeforeCreate = dependencyConditionRepository.findAll().size();
        // Create the DependencyCondition
        DependencyConditionDTO dependencyConditionDTO = dependencyConditionMapper.toDto(dependencyCondition);
        restDependencyConditionMockMvc.perform(post("/api/dependency-conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependencyConditionDTO)))
            .andExpect(status().isCreated());

        // Validate the DependencyCondition in the database
        List<DependencyCondition> dependencyConditionList = dependencyConditionRepository.findAll();
        assertThat(dependencyConditionList).hasSize(databaseSizeBeforeCreate + 1);
        DependencyCondition testDependencyCondition = dependencyConditionList.get(dependencyConditionList.size() - 1);
        assertThat(testDependencyCondition.getDependencyType()).isEqualTo(DEFAULT_DEPENDENCY_TYPE);
        assertThat(testDependencyCondition.getFlagFiekdId()).isEqualTo(DEFAULT_FLAG_FIEKD_ID);
        assertThat(testDependencyCondition.getAssignedTime()).isEqualTo(DEFAULT_ASSIGNED_TIME);
        assertThat(testDependencyCondition.getOperator()).isEqualTo(DEFAULT_OPERATOR);
    }

    @Test
    @Transactional
    public void createDependencyConditionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dependencyConditionRepository.findAll().size();

        // Create the DependencyCondition with an existing ID
        dependencyCondition.setId(1L);
        DependencyConditionDTO dependencyConditionDTO = dependencyConditionMapper.toDto(dependencyCondition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDependencyConditionMockMvc.perform(post("/api/dependency-conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependencyConditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DependencyCondition in the database
        List<DependencyCondition> dependencyConditionList = dependencyConditionRepository.findAll();
        assertThat(dependencyConditionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDependencyConditions() throws Exception {
        // Initialize the database
        dependencyConditionRepository.saveAndFlush(dependencyCondition);

        // Get all the dependencyConditionList
        restDependencyConditionMockMvc.perform(get("/api/dependency-conditions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependencyCondition.getId().intValue())))
            .andExpect(jsonPath("$.[*].dependencyType").value(hasItem(DEFAULT_DEPENDENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].flagFiekdId").value(hasItem(DEFAULT_FLAG_FIEKD_ID)))
            .andExpect(jsonPath("$.[*].assignedTime").value(hasItem(DEFAULT_ASSIGNED_TIME.toString())))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())));
    }
    
    @Test
    @Transactional
    public void getDependencyCondition() throws Exception {
        // Initialize the database
        dependencyConditionRepository.saveAndFlush(dependencyCondition);

        // Get the dependencyCondition
        restDependencyConditionMockMvc.perform(get("/api/dependency-conditions/{id}", dependencyCondition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dependencyCondition.getId().intValue()))
            .andExpect(jsonPath("$.dependencyType").value(DEFAULT_DEPENDENCY_TYPE.toString()))
            .andExpect(jsonPath("$.flagFiekdId").value(DEFAULT_FLAG_FIEKD_ID))
            .andExpect(jsonPath("$.assignedTime").value(DEFAULT_ASSIGNED_TIME.toString()))
            .andExpect(jsonPath("$.operator").value(DEFAULT_OPERATOR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDependencyCondition() throws Exception {
        // Get the dependencyCondition
        restDependencyConditionMockMvc.perform(get("/api/dependency-conditions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDependencyCondition() throws Exception {
        // Initialize the database
        dependencyConditionRepository.saveAndFlush(dependencyCondition);

        int databaseSizeBeforeUpdate = dependencyConditionRepository.findAll().size();

        // Update the dependencyCondition
        DependencyCondition updatedDependencyCondition = dependencyConditionRepository.findById(dependencyCondition.getId()).get();
        // Disconnect from session so that the updates on updatedDependencyCondition are not directly saved in db
        em.detach(updatedDependencyCondition);
        updatedDependencyCondition
            .dependencyType(UPDATED_DEPENDENCY_TYPE)
            .flagFiekdId(UPDATED_FLAG_FIEKD_ID)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .operator(UPDATED_OPERATOR);
        DependencyConditionDTO dependencyConditionDTO = dependencyConditionMapper.toDto(updatedDependencyCondition);

        restDependencyConditionMockMvc.perform(put("/api/dependency-conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependencyConditionDTO)))
            .andExpect(status().isOk());

        // Validate the DependencyCondition in the database
        List<DependencyCondition> dependencyConditionList = dependencyConditionRepository.findAll();
        assertThat(dependencyConditionList).hasSize(databaseSizeBeforeUpdate);
        DependencyCondition testDependencyCondition = dependencyConditionList.get(dependencyConditionList.size() - 1);
        assertThat(testDependencyCondition.getDependencyType()).isEqualTo(UPDATED_DEPENDENCY_TYPE);
        assertThat(testDependencyCondition.getFlagFiekdId()).isEqualTo(UPDATED_FLAG_FIEKD_ID);
        assertThat(testDependencyCondition.getAssignedTime()).isEqualTo(UPDATED_ASSIGNED_TIME);
        assertThat(testDependencyCondition.getOperator()).isEqualTo(UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingDependencyCondition() throws Exception {
        int databaseSizeBeforeUpdate = dependencyConditionRepository.findAll().size();

        // Create the DependencyCondition
        DependencyConditionDTO dependencyConditionDTO = dependencyConditionMapper.toDto(dependencyCondition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDependencyConditionMockMvc.perform(put("/api/dependency-conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependencyConditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DependencyCondition in the database
        List<DependencyCondition> dependencyConditionList = dependencyConditionRepository.findAll();
        assertThat(dependencyConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDependencyCondition() throws Exception {
        // Initialize the database
        dependencyConditionRepository.saveAndFlush(dependencyCondition);

        int databaseSizeBeforeDelete = dependencyConditionRepository.findAll().size();

        // Delete the dependencyCondition
        restDependencyConditionMockMvc.perform(delete("/api/dependency-conditions/{id}", dependencyCondition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DependencyCondition> dependencyConditionList = dependencyConditionRepository.findAll();
        assertThat(dependencyConditionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
