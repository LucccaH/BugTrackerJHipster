package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.DependencyTypeEnum;

import com.mycompany.myapp.domain.enumeration.OperatorEnum;

/**
 * A DependencyCondition.
 */
@Entity
@Table(name = "dependency_condition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DependencyCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dependency_type")
    private DependencyTypeEnum dependencyType;

    @Column(name = "flag_fiekd_id")
    private String flagFiekdId;

    @Column(name = "assigned_time")
    private LocalDate assignedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator")
    private OperatorEnum operator;

    @OneToMany(mappedBy = "root")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<DependencyCondition> dependencyConditions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "dependencyConditions", allowSetters = true)
    private DependencyCondition root;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DependencyTypeEnum getDependencyType() {
        return dependencyType;
    }

    public DependencyCondition dependencyType(DependencyTypeEnum dependencyType) {
        this.dependencyType = dependencyType;
        return this;
    }

    public void setDependencyType(DependencyTypeEnum dependencyType) {
        this.dependencyType = dependencyType;
    }

    public String getFlagFiekdId() {
        return flagFiekdId;
    }

    public DependencyCondition flagFiekdId(String flagFiekdId) {
        this.flagFiekdId = flagFiekdId;
        return this;
    }

    public void setFlagFiekdId(String flagFiekdId) {
        this.flagFiekdId = flagFiekdId;
    }

    public LocalDate getAssignedTime() {
        return assignedTime;
    }

    public DependencyCondition assignedTime(LocalDate assignedTime) {
        this.assignedTime = assignedTime;
        return this;
    }

    public void setAssignedTime(LocalDate assignedTime) {
        this.assignedTime = assignedTime;
    }

    public OperatorEnum getOperator() {
        return operator;
    }

    public DependencyCondition operator(OperatorEnum operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(OperatorEnum operator) {
        this.operator = operator;
    }

    public Set<DependencyCondition> getDependencyConditions() {
        return dependencyConditions;
    }

    public DependencyCondition dependencyConditions(Set<DependencyCondition> dependencyConditions) {
        this.dependencyConditions = dependencyConditions;
        return this;
    }

    public DependencyCondition addDependencyCondition(DependencyCondition dependencyCondition) {
        this.dependencyConditions.add(dependencyCondition);
        dependencyCondition.setRoot(this);
        return this;
    }

    public DependencyCondition removeDependencyCondition(DependencyCondition dependencyCondition) {
        this.dependencyConditions.remove(dependencyCondition);
        dependencyCondition.setRoot(null);
        return this;
    }

    public void setDependencyConditions(Set<DependencyCondition> dependencyConditions) {
        this.dependencyConditions = dependencyConditions;
    }

    public DependencyCondition getRoot() {
        return root;
    }

    public DependencyCondition root(DependencyCondition dependencyCondition) {
        this.root = dependencyCondition;
        return this;
    }

    public void setRoot(DependencyCondition dependencyCondition) {
        this.root = dependencyCondition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DependencyCondition)) {
            return false;
        }
        return id != null && id.equals(((DependencyCondition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DependencyCondition{" +
            "id=" + getId() +
            ", dependencyType='" + getDependencyType() + "'" +
            ", flagFiekdId='" + getFlagFiekdId() + "'" +
            ", assignedTime='" + getAssignedTime() + "'" +
            ", operator='" + getOperator() + "'" +
            "}";
    }
}
