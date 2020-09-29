package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CycleConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CycleConfig.class);
        CycleConfig cycleConfig1 = new CycleConfig();
        cycleConfig1.setId(1L);
        CycleConfig cycleConfig2 = new CycleConfig();
        cycleConfig2.setId(cycleConfig1.getId());
        assertThat(cycleConfig1).isEqualTo(cycleConfig2);
        cycleConfig2.setId(2L);
        assertThat(cycleConfig1).isNotEqualTo(cycleConfig2);
        cycleConfig1.setId(null);
        assertThat(cycleConfig1).isNotEqualTo(cycleConfig2);
    }
}
