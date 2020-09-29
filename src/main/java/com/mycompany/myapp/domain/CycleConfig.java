package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.mycompany.myapp.domain.enumeration.CycleType;

/**
 * A CycleConfig.
 */
@Entity
@Table(name = "cycle_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CycleConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "assigned_time")
    private LocalDate assignedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "cycle_enum")
    private CycleType cycleEnum;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private DependencyCondition conditionRoot;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public CycleConfig effectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public CycleConfig assignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
        return this;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getAssignedTime() {
        return assignedTime;
    }

    public CycleConfig assignedTime(LocalDate assignedTime) {
        this.assignedTime = assignedTime;
        return this;
    }

    public void setAssignedTime(LocalDate assignedTime) {
        this.assignedTime = assignedTime;
    }

    public CycleType getCycleEnum() {
        return cycleEnum;
    }

    public CycleConfig cycleEnum(CycleType cycleEnum) {
        this.cycleEnum = cycleEnum;
        return this;
    }

    public void setCycleEnum(CycleType cycleEnum) {
        this.cycleEnum = cycleEnum;
    }

    public DependencyCondition getConditionRoot() {
        return conditionRoot;
    }

    public CycleConfig conditionRoot(DependencyCondition dependencyCondition) {
        this.conditionRoot = dependencyCondition;
        return this;
    }

    public void setConditionRoot(DependencyCondition dependencyCondition) {
        this.conditionRoot = dependencyCondition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CycleConfig)) {
            return false;
        }
        return id != null && id.equals(((CycleConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CycleConfig{" +
            "id=" + getId() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", assignedDate='" + getAssignedDate() + "'" +
            ", assignedTime='" + getAssignedTime() + "'" +
            ", cycleEnum='" + getCycleEnum() + "'" +
            "}";
    }
}
