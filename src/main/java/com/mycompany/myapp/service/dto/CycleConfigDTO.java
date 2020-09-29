package com.mycompany.myapp.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.mycompany.myapp.domain.enumeration.CycleType;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CycleConfig} entity.
 */
public class CycleConfigDTO implements Serializable {
    
    private Long id;

    private LocalDate effectiveDate;

    private LocalDate assignedDate;

    private LocalDate assignedTime;

    private CycleType cycleEnum;


    private Long conditionRootId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(LocalDate assignedTime) {
        this.assignedTime = assignedTime;
    }

    public CycleType getCycleEnum() {
        return cycleEnum;
    }

    public void setCycleEnum(CycleType cycleEnum) {
        this.cycleEnum = cycleEnum;
    }

    public Long getConditionRootId() {
        return conditionRootId;
    }

    public void setConditionRootId(Long dependencyConditionId) {
        this.conditionRootId = dependencyConditionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CycleConfigDTO)) {
            return false;
        }

        return id != null && id.equals(((CycleConfigDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CycleConfigDTO{" +
            "id=" + getId() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", assignedDate='" + getAssignedDate() + "'" +
            ", assignedTime='" + getAssignedTime() + "'" +
            ", cycleEnum='" + getCycleEnum() + "'" +
            ", conditionRootId=" + getConditionRootId() +
            "}";
    }
}
