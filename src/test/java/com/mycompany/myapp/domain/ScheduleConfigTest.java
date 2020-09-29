package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ScheduleConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleConfig.class);
        ScheduleConfig scheduleConfig1 = new ScheduleConfig();
        scheduleConfig1.setId(1L);
        ScheduleConfig scheduleConfig2 = new ScheduleConfig();
        scheduleConfig2.setId(scheduleConfig1.getId());
        assertThat(scheduleConfig1).isEqualTo(scheduleConfig2);
        scheduleConfig2.setId(2L);
        assertThat(scheduleConfig1).isNotEqualTo(scheduleConfig2);
        scheduleConfig1.setId(null);
        assertThat(scheduleConfig1).isNotEqualTo(scheduleConfig2);
    }
}
