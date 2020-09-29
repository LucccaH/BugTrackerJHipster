package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AlarmConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmConfig.class);
        AlarmConfig alarmConfig1 = new AlarmConfig();
        alarmConfig1.setId(1L);
        AlarmConfig alarmConfig2 = new AlarmConfig();
        alarmConfig2.setId(alarmConfig1.getId());
        assertThat(alarmConfig1).isEqualTo(alarmConfig2);
        alarmConfig2.setId(2L);
        assertThat(alarmConfig1).isNotEqualTo(alarmConfig2);
        alarmConfig1.setId(null);
        assertThat(alarmConfig1).isNotEqualTo(alarmConfig2);
    }
}
