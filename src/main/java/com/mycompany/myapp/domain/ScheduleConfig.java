package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ScheduleConfig.
 */
@Entity
@Table(name = "schedule_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScheduleConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "privilige")
    private Integer privilige;

    @Column(name = "retry_time")
    private Integer retryTime;

    @Column(name = "retry_interval")
    private Integer retryInterval;

    @OneToOne
    @JoinColumn(unique = true)
    private CycleConfig scheduleCycleConfig;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrivilige() {
        return privilige;
    }

    public ScheduleConfig privilige(Integer privilige) {
        this.privilige = privilige;
        return this;
    }

    public void setPrivilige(Integer privilige) {
        this.privilige = privilige;
    }

    public Integer getRetryTime() {
        return retryTime;
    }

    public ScheduleConfig retryTime(Integer retryTime) {
        this.retryTime = retryTime;
        return this;
    }

    public void setRetryTime(Integer retryTime) {
        this.retryTime = retryTime;
    }

    public Integer getRetryInterval() {
        return retryInterval;
    }

    public ScheduleConfig retryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }

    public CycleConfig getScheduleCycleConfig() {
        return scheduleCycleConfig;
    }

    public ScheduleConfig scheduleCycleConfig(CycleConfig cycleConfig) {
        this.scheduleCycleConfig = cycleConfig;
        return this;
    }

    public void setScheduleCycleConfig(CycleConfig cycleConfig) {
        this.scheduleCycleConfig = cycleConfig;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleConfig)) {
            return false;
        }
        return id != null && id.equals(((ScheduleConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleConfig{" +
            "id=" + getId() +
            ", privilige=" + getPrivilige() +
            ", retryTime=" + getRetryTime() +
            ", retryInterval=" + getRetryInterval() +
            "}";
    }
}
