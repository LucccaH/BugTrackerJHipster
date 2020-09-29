package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DependencyConditionService;
import com.mycompany.myapp.domain.DependencyCondition;
import com.mycompany.myapp.repository.DependencyConditionRepository;
import com.mycompany.myapp.service.dto.DependencyConditionDTO;
import com.mycompany.myapp.service.mapper.DependencyConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DependencyCondition}.
 */
@Service
@Transactional
public class DependencyConditionServiceImpl implements DependencyConditionService {

    private final Logger log = LoggerFactory.getLogger(DependencyConditionServiceImpl.class);

    private final DependencyConditionRepository dependencyConditionRepository;

    private final DependencyConditionMapper dependencyConditionMapper;

    public DependencyConditionServiceImpl(DependencyConditionRepository dependencyConditionRepository, DependencyConditionMapper dependencyConditionMapper) {
        this.dependencyConditionRepository = dependencyConditionRepository;
        this.dependencyConditionMapper = dependencyConditionMapper;
    }

    @Override
    public DependencyConditionDTO save(DependencyConditionDTO dependencyConditionDTO) {
        log.debug("Request to save DependencyCondition : {}", dependencyConditionDTO);
        DependencyCondition dependencyCondition = dependencyConditionMapper.toEntity(dependencyConditionDTO);
        dependencyCondition = dependencyConditionRepository.save(dependencyCondition);
        return dependencyConditionMapper.toDto(dependencyCondition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DependencyConditionDTO> findAll() {
        log.debug("Request to get all DependencyConditions");
        return dependencyConditionRepository.findAll().stream()
            .map(dependencyConditionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DependencyConditionDTO> findOne(Long id) {
        log.debug("Request to get DependencyCondition : {}", id);
        return dependencyConditionRepository.findById(id)
            .map(dependencyConditionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DependencyCondition : {}", id);
        dependencyConditionRepository.deleteById(id);
    }
}
