package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ScheduleConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScheduleConfig} and its DTO {@link ScheduleConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {CycleConfigMapper.class})
public interface ScheduleConfigMapper extends EntityMapper<ScheduleConfigDTO, ScheduleConfig> {

    @Mapping(source = "scheduleCycleConfig.id", target = "scheduleCycleConfigId")
    ScheduleConfigDTO toDto(ScheduleConfig scheduleConfig);

    @Mapping(source = "scheduleCycleConfigId", target = "scheduleCycleConfig")
    ScheduleConfig toEntity(ScheduleConfigDTO scheduleConfigDTO);

    default ScheduleConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScheduleConfig scheduleConfig = new ScheduleConfig();
        scheduleConfig.setId(id);
        return scheduleConfig;
    }
}
