package com.mycompany.myapp.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import com.mycompany.myapp.domain.enumeration.DependencyTypeEnum;
import com.mycompany.myapp.domain.enumeration.OperatorEnum;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.DependencyCondition} entity.
 */
public class DependencyConditionDTO implements Serializable {
    
    private Long id;

    private DependencyTypeEnum dependencyType;

    private String flagFiekdId;

    private LocalDate assignedTime;

    private OperatorEnum operator;


    private Long rootId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DependencyTypeEnum getDependencyType() {
        return dependencyType;
    }

    public void setDependencyType(DependencyTypeEnum dependencyType) {
        this.dependencyType = dependencyType;
    }

    public String getFlagFiekdId() {
        return flagFiekdId;
    }

    public void setFlagFiekdId(String flagFiekdId) {
        this.flagFiekdId = flagFiekdId;
    }

    public LocalDate getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(LocalDate assignedTime) {
        this.assignedTime = assignedTime;
    }

    public OperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(OperatorEnum operator) {
        this.operator = operator;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long dependencyConditionId) {
        this.rootId = dependencyConditionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DependencyConditionDTO)) {
            return false;
        }

        return id != null && id.equals(((DependencyConditionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DependencyConditionDTO{" +
            "id=" + getId() +
            ", dependencyType='" + getDependencyType() + "'" +
            ", flagFiekdId='" + getFlagFiekdId() + "'" +
            ", assignedTime='" + getAssignedTime() + "'" +
            ", operator='" + getOperator() + "'" +
            ", rootId=" + getRootId() +
            "}";
    }
}
