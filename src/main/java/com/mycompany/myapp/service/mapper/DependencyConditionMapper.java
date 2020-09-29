package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DependencyConditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DependencyCondition} and its DTO {@link DependencyConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DependencyConditionMapper extends EntityMapper<DependencyConditionDTO, DependencyCondition> {

    @Mapping(source = "root.id", target = "rootId")
    DependencyConditionDTO toDto(DependencyCondition dependencyCondition);

    @Mapping(target = "dependencyConditions", ignore = true)
    @Mapping(target = "removeDependencyCondition", ignore = true)
    @Mapping(source = "rootId", target = "root")
    DependencyCondition toEntity(DependencyConditionDTO dependencyConditionDTO);

    default DependencyCondition fromId(Long id) {
        if (id == null) {
            return null;
        }
        DependencyCondition dependencyCondition = new DependencyCondition();
        dependencyCondition.setId(id);
        return dependencyCondition;
    }
}
