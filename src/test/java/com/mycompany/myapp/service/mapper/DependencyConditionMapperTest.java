package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DependencyConditionMapperTest {

    private DependencyConditionMapper dependencyConditionMapper;

    @BeforeEach
    public void setUp() {
        dependencyConditionMapper = new DependencyConditionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dependencyConditionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dependencyConditionMapper.fromId(null)).isNull();
    }
}
