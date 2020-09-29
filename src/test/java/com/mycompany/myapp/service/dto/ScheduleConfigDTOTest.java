package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ScheduleConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleConfigDTO.class);
        ScheduleConfigDTO scheduleConfigDTO1 = new ScheduleConfigDTO();
        scheduleConfigDTO1.setId(1L);
        ScheduleConfigDTO scheduleConfigDTO2 = new ScheduleConfigDTO();
        assertThat(scheduleConfigDTO1).isNotEqualTo(scheduleConfigDTO2);
        scheduleConfigDTO2.setId(scheduleConfigDTO1.getId());
        assertThat(scheduleConfigDTO1).isEqualTo(scheduleConfigDTO2);
        scheduleConfigDTO2.setId(2L);
        assertThat(scheduleConfigDTO1).isNotEqualTo(scheduleConfigDTO2);
        scheduleConfigDTO1.setId(null);
        assertThat(scheduleConfigDTO1).isNotEqualTo(scheduleConfigDTO2);
    }
}
