package com.mycompany.myapp.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.mycompany.myapp.domain.enumeration.TaskStatus;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Task} entity.
 */
public class TaskDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long taskVersionId;

    private String taskDetail;

    private String taskName;

    private Integer dataType;

    private TaskStatus taskStatusEnum;

    private LocalDate createTime;


    private Long alarmConfigId;

    private Long scheduleConfigId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskVersionId() {
        return taskVersionId;
    }

    public void setTaskVersionId(Long taskVersionId) {
        this.taskVersionId = taskVersionId;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public TaskStatus getTaskStatusEnum() {
        return taskStatusEnum;
    }

    public void setTaskStatusEnum(TaskStatus taskStatusEnum) {
        this.taskStatusEnum = taskStatusEnum;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public Long getAlarmConfigId() {
        return alarmConfigId;
    }

    public void setAlarmConfigId(Long alarmConfigId) {
        this.alarmConfigId = alarmConfigId;
    }

    public Long getScheduleConfigId() {
        return scheduleConfigId;
    }

    public void setScheduleConfigId(Long scheduleConfigId) {
        this.scheduleConfigId = scheduleConfigId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + getId() +
            ", taskVersionId=" + getTaskVersionId() +
            ", taskDetail='" + getTaskDetail() + "'" +
            ", taskName='" + getTaskName() + "'" +
            ", dataType=" + getDataType() +
            ", taskStatusEnum='" + getTaskStatusEnum() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", alarmConfigId=" + getAlarmConfigId() +
            ", scheduleConfigId=" + getScheduleConfigId() +
            "}";
    }
}
