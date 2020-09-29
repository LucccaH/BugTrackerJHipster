package com.mycompany.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ScheduleConfig} entity.
 */
public class ScheduleConfigDTO implements Serializable {
    
    private Long id;

    private Integer privilige;

    private Integer retryTime;

    private Integer retryInterval;


    private Long scheduleCycleConfigId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrivilige() {
        return privilige;
    }

    public void setPrivilige(Integer privilige) {
        this.privilige = privilige;
    }

    public Integer getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(Integer retryTime) {
        this.retryTime = retryTime;
    }

    public Integer getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Long getScheduleCycleConfigId() {
        return scheduleCycleConfigId;
    }

    public void setScheduleCycleConfigId(Long cycleConfigId) {
        this.scheduleCycleConfigId = cycleConfigId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleConfigDTO)) {
            return false;
        }

        return id != null && id.equals(((ScheduleConfigDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleConfigDTO{" +
            "id=" + getId() +
            ", privilige=" + getPrivilige() +
            ", retryTime=" + getRetryTime() +
            ", retryInterval=" + getRetryInterval() +
            ", scheduleCycleConfigId=" + getScheduleCycleConfigId() +
            "}";
    }
}
