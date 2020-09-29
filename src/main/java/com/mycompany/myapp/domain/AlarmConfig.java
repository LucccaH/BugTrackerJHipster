package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AlarmConfig.
 */
@Entity
@Table(name = "alarm_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlarmConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alarm_methodb")
    private String alarmMethodb;

    @Column(name = "account_info")
    private String accountInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlarmMethodb() {
        return alarmMethodb;
    }

    public AlarmConfig alarmMethodb(String alarmMethodb) {
        this.alarmMethodb = alarmMethodb;
        return this;
    }

    public void setAlarmMethodb(String alarmMethodb) {
        this.alarmMethodb = alarmMethodb;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public AlarmConfig accountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
        return this;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlarmConfig)) {
            return false;
        }
        return id != null && id.equals(((AlarmConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlarmConfig{" +
            "id=" + getId() +
            ", alarmMethodb='" + getAlarmMethodb() + "'" +
            ", accountInfo='" + getAccountInfo() + "'" +
            "}";
    }
}
