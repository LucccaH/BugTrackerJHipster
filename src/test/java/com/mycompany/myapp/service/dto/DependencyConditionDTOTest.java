package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DependencyConditionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DependencyConditionDTO.class);
        DependencyConditionDTO dependencyConditionDTO1 = new DependencyConditionDTO();
        dependencyConditionDTO1.setId(1L);
        DependencyConditionDTO dependencyConditionDTO2 = new DependencyConditionDTO();
        assertThat(dependencyConditionDTO1).isNotEqualTo(dependencyConditionDTO2);
        dependencyConditionDTO2.setId(dependencyConditionDTO1.getId());
        assertThat(dependencyConditionDTO1).isEqualTo(dependencyConditionDTO2);
        dependencyConditionDTO2.setId(2L);
        assertThat(dependencyConditionDTO1).isNotEqualTo(dependencyConditionDTO2);
        dependencyConditionDTO1.setId(null);
        assertThat(dependencyConditionDTO1).isNotEqualTo(dependencyConditionDTO2);
    }
}
