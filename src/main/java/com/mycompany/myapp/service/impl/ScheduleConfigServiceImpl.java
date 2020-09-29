package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ScheduleConfigService;
import com.mycompany.myapp.domain.ScheduleConfig;
import com.mycompany.myapp.repository.ScheduleConfigRepository;
import com.mycompany.myapp.service.dto.ScheduleConfigDTO;
import com.mycompany.myapp.service.mapper.ScheduleConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ScheduleConfig}.
 */
@Service
@Transactional
public class ScheduleConfigServiceImpl implements ScheduleConfigService {

    private final Logger log = LoggerFactory.getLogger(ScheduleConfigServiceImpl.class);

    private final ScheduleConfigRepository scheduleConfigRepository;

    private final ScheduleConfigMapper scheduleConfigMapper;

    public ScheduleConfigServiceImpl(ScheduleConfigRepository scheduleConfigRepository, ScheduleConfigMapper scheduleConfigMapper) {
        this.scheduleConfigRepository = scheduleConfigRepository;
        this.scheduleConfigMapper = scheduleConfigMapper;
    }

    @Override
    public ScheduleConfigDTO save(ScheduleConfigDTO scheduleConfigDTO) {
        log.debug("Request to save ScheduleConfig : {}", scheduleConfigDTO);
        ScheduleConfig scheduleConfig = scheduleConfigMapper.toEntity(scheduleConfigDTO);
        scheduleConfig = scheduleConfigRepository.save(scheduleConfig);
        return scheduleConfigMapper.toDto(scheduleConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleConfigDTO> findAll() {
        log.debug("Request to get all ScheduleConfigs");
        return scheduleConfigRepository.findAll().stream()
            .map(scheduleConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleConfigDTO> findOne(Long id) {
        log.debug("Request to get ScheduleConfig : {}", id);
        return scheduleConfigRepository.findById(id)
            .map(scheduleConfigMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduleConfig : {}", id);
        scheduleConfigRepository.deleteById(id);
    }
}
