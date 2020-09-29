package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleConfigMapperTest {

    private ScheduleConfigMapper scheduleConfigMapper;

    @BeforeEach
    public void setUp() {
        scheduleConfigMapper = new ScheduleConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(scheduleConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scheduleConfigMapper.fromId(null)).isNull();
    }
}
