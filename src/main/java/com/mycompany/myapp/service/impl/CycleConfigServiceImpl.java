package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CycleConfigService;
import com.mycompany.myapp.domain.CycleConfig;
import com.mycompany.myapp.repository.CycleConfigRepository;
import com.mycompany.myapp.service.dto.CycleConfigDTO;
import com.mycompany.myapp.service.mapper.CycleConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CycleConfig}.
 */
@Service
@Transactional
public class CycleConfigServiceImpl implements CycleConfigService {

    private final Logger log = LoggerFactory.getLogger(CycleConfigServiceImpl.class);

    private final CycleConfigRepository cycleConfigRepository;

    private final CycleConfigMapper cycleConfigMapper;

    public CycleConfigServiceImpl(CycleConfigRepository cycleConfigRepository, CycleConfigMapper cycleConfigMapper) {
        this.cycleConfigRepository = cycleConfigRepository;
        this.cycleConfigMapper = cycleConfigMapper;
    }

    @Override
    public CycleConfigDTO save(CycleConfigDTO cycleConfigDTO) {
        log.debug("Request to save CycleConfig : {}", cycleConfigDTO);
        CycleConfig cycleConfig = cycleConfigMapper.toEntity(cycleConfigDTO);
        cycleConfig = cycleConfigRepository.save(cycleConfig);
        return cycleConfigMapper.toDto(cycleConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CycleConfigDTO> findAll() {
        log.debug("Request to get all CycleConfigs");
        return cycleConfigRepository.findAll().stream()
            .map(cycleConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CycleConfigDTO> findOne(Long id) {
        log.debug("Request to get CycleConfig : {}", id);
        return cycleConfigRepository.findById(id)
            .map(cycleConfigMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CycleConfig : {}", id);
        cycleConfigRepository.deleteById(id);
    }
}
