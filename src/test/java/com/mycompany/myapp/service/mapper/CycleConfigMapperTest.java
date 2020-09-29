package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CycleConfigMapperTest {

    private CycleConfigMapper cycleConfigMapper;

    @BeforeEach
    public void setUp() {
        cycleConfigMapper = new CycleConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cycleConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cycleConfigMapper.fromId(null)).isNull();
    }
}
