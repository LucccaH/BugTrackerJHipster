package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.mycompany.myapp.domain.enumeration.TaskStatus;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "task_version_id", nullable = false)
    private Long taskVersionId;

    @Column(name = "task_detail")
    private String taskDetail;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "data_type")
    private Integer dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status_enum")
    private TaskStatus taskStatusEnum;

    @Column(name = "create_time")
    private LocalDate createTime;

    @OneToOne
    @JoinColumn(unique = true)
    private AlarmConfig alarmConfig;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private ScheduleConfig scheduleConfig;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskVersionId() {
        return taskVersionId;
    }

    public Task taskVersionId(Long taskVersionId) {
        this.taskVersionId = taskVersionId;
        return this;
    }

    public void setTaskVersionId(Long taskVersionId) {
        this.taskVersionId = taskVersionId;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public Task taskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
        return this;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public String getTaskName() {
        return taskName;
    }

    public Task taskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getDataType() {
        return dataType;
    }

    public Task dataType(Integer dataType) {
        this.dataType = dataType;
        return this;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public TaskStatus getTaskStatusEnum() {
        return taskStatusEnum;
    }

    public Task taskStatusEnum(TaskStatus taskStatusEnum) {
        this.taskStatusEnum = taskStatusEnum;
        return this;
    }

    public void setTaskStatusEnum(TaskStatus taskStatusEnum) {
        this.taskStatusEnum = taskStatusEnum;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public Task createTime(LocalDate createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public AlarmConfig getAlarmConfig() {
        return alarmConfig;
    }

    public Task alarmConfig(AlarmConfig alarmConfig) {
        this.alarmConfig = alarmConfig;
        return this;
    }

    public void setAlarmConfig(AlarmConfig alarmConfig) {
        this.alarmConfig = alarmConfig;
    }

    public ScheduleConfig getScheduleConfig() {
        return scheduleConfig;
    }

    public Task scheduleConfig(ScheduleConfig scheduleConfig) {
        this.scheduleConfig = scheduleConfig;
        return this;
    }

    public void setScheduleConfig(ScheduleConfig scheduleConfig) {
        this.scheduleConfig = scheduleConfig;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", taskVersionId=" + getTaskVersionId() +
            ", taskDetail='" + getTaskDetail() + "'" +
            ", taskName='" + getTaskName() + "'" +
            ", dataType=" + getDataType() +
            ", taskStatusEnum='" + getTaskStatusEnum() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
