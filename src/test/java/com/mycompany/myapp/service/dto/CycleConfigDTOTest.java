package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CycleConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CycleConfigDTO.class);
        CycleConfigDTO cycleConfigDTO1 = new CycleConfigDTO();
        cycleConfigDTO1.setId(1L);
        CycleConfigDTO cycleConfigDTO2 = new CycleConfigDTO();
        assertThat(cycleConfigDTO1).isNotEqualTo(cycleConfigDTO2);
        cycleConfigDTO2.setId(cycleConfigDTO1.getId());
        assertThat(cycleConfigDTO1).isEqualTo(cycleConfigDTO2);
        cycleConfigDTO2.setId(2L);
        assertThat(cycleConfigDTO1).isNotEqualTo(cycleConfigDTO2);
        cycleConfigDTO1.setId(null);
        assertThat(cycleConfigDTO1).isNotEqualTo(cycleConfigDTO2);
    }
}
