package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CycleConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CycleConfig} and its DTO {@link CycleConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {DependencyConditionMapper.class})
public interface CycleConfigMapper extends EntityMapper<CycleConfigDTO, CycleConfig> {

    @Mapping(source = "conditionRoot.id", target = "conditionRootId")
    CycleConfigDTO toDto(CycleConfig cycleConfig);

    @Mapping(source = "conditionRootId", target = "conditionRoot")
    CycleConfig toEntity(CycleConfigDTO cycleConfigDTO);

    default CycleConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        CycleConfig cycleConfig = new CycleConfig();
        cycleConfig.setId(id);
        return cycleConfig;
    }
}
