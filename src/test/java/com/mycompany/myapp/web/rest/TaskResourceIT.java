package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JDemoApp;
import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.domain.ScheduleConfig;
import com.mycompany.myapp.repository.TaskRepository;
import com.mycompany.myapp.service.TaskService;
import com.mycompany.myapp.service.dto.TaskDTO;
import com.mycompany.myapp.service.mapper.TaskMapper;

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

import com.mycompany.myapp.domain.enumeration.TaskStatus;
/**
 * Integration tests for the {@link TaskResource} REST controller.
 */
@SpringBootTest(classes = JDemoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskResourceIT {

    private static final Long DEFAULT_TASK_VERSION_ID = 1L;
    private static final Long UPDATED_TASK_VERSION_ID = 2L;

    private static final String DEFAULT_TASK_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_TASK_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TASK_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DATA_TYPE = 1;
    private static final Integer UPDATED_DATA_TYPE = 2;

    private static final TaskStatus DEFAULT_TASK_STATUS_ENUM = TaskStatus.DONE;
    private static final TaskStatus UPDATED_TASK_STATUS_ENUM = TaskStatus.PAUSED;

    private static final LocalDate DEFAULT_CREATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskMockMvc;

    private Task task;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createEntity(EntityManager em) {
        Task task = new Task()
            .taskVersionId(DEFAULT_TASK_VERSION_ID)
            .taskDetail(DEFAULT_TASK_DETAIL)
            .taskName(DEFAULT_TASK_NAME)
            .dataType(DEFAULT_DATA_TYPE)
            .taskStatusEnum(DEFAULT_TASK_STATUS_ENUM)
            .createTime(DEFAULT_CREATE_TIME);
        // Add required entity
        ScheduleConfig scheduleConfig;
        if (TestUtil.findAll(em, ScheduleConfig.class).isEmpty()) {
            scheduleConfig = ScheduleConfigResourceIT.createEntity(em);
            em.persist(scheduleConfig);
            em.flush();
        } else {
            scheduleConfig = TestUtil.findAll(em, ScheduleConfig.class).get(0);
        }
        task.setScheduleConfig(scheduleConfig);
        return task;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createUpdatedEntity(EntityManager em) {
        Task task = new Task()
            .taskVersionId(UPDATED_TASK_VERSION_ID)
            .taskDetail(UPDATED_TASK_DETAIL)
            .taskName(UPDATED_TASK_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .taskStatusEnum(UPDATED_TASK_STATUS_ENUM)
            .createTime(UPDATED_CREATE_TIME);
        // Add required entity
        ScheduleConfig scheduleConfig;
        if (TestUtil.findAll(em, ScheduleConfig.class).isEmpty()) {
            scheduleConfig = ScheduleConfigResourceIT.createUpdatedEntity(em);
            em.persist(scheduleConfig);
            em.flush();
        } else {
            scheduleConfig = TestUtil.findAll(em, ScheduleConfig.class).get(0);
        }
        task.setScheduleConfig(scheduleConfig);
        return task;
    }

    @BeforeEach
    public void initTest() {
        task = createEntity(em);
    }

    @Test
    @Transactional
    public void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();
        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTaskVersionId()).isEqualTo(DEFAULT_TASK_VERSION_ID);
        assertThat(testTask.getTaskDetail()).isEqualTo(DEFAULT_TASK_DETAIL);
        assertThat(testTask.getTaskName()).isEqualTo(DEFAULT_TASK_NAME);
        assertThat(testTask.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testTask.getTaskStatusEnum()).isEqualTo(DEFAULT_TASK_STATUS_ENUM);
        assertThat(testTask.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task with an existing ID
        task.setId(1L);
        TaskDTO taskDTO = taskMapper.toDto(task);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTaskVersionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setTaskVersionId(null);

        // Create the Task, which fails.
        TaskDTO taskDTO = taskMapper.toDto(task);


        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskVersionId").value(hasItem(DEFAULT_TASK_VERSION_ID.intValue())))
            .andExpect(jsonPath("$.[*].taskDetail").value(hasItem(DEFAULT_TASK_DETAIL)))
            .andExpect(jsonPath("$.[*].taskName").value(hasItem(DEFAULT_TASK_NAME)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE)))
            .andExpect(jsonPath("$.[*].taskStatusEnum").value(hasItem(DEFAULT_TASK_STATUS_ENUM.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.taskVersionId").value(DEFAULT_TASK_VERSION_ID.intValue()))
            .andExpect(jsonPath("$.taskDetail").value(DEFAULT_TASK_DETAIL))
            .andExpect(jsonPath("$.taskName").value(DEFAULT_TASK_NAME))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE))
            .andExpect(jsonPath("$.taskStatusEnum").value(DEFAULT_TASK_STATUS_ENUM.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findById(task.getId()).get();
        // Disconnect from session so that the updates on updatedTask are not directly saved in db
        em.detach(updatedTask);
        updatedTask
            .taskVersionId(UPDATED_TASK_VERSION_ID)
            .taskDetail(UPDATED_TASK_DETAIL)
            .taskName(UPDATED_TASK_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .taskStatusEnum(UPDATED_TASK_STATUS_ENUM)
            .createTime(UPDATED_CREATE_TIME);
        TaskDTO taskDTO = taskMapper.toDto(updatedTask);

        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTaskVersionId()).isEqualTo(UPDATED_TASK_VERSION_ID);
        assertThat(testTask.getTaskDetail()).isEqualTo(UPDATED_TASK_DETAIL);
        assertThat(testTask.getTaskName()).isEqualTo(UPDATED_TASK_NAME);
        assertThat(testTask.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testTask.getTaskStatusEnum()).isEqualTo(UPDATED_TASK_STATUS_ENUM);
        assertThat(testTask.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Delete the task
        restTaskMockMvc.perform(delete("/api/tasks/{id}", task.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
