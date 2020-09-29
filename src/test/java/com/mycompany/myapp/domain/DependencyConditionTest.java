package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DependencyConditionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DependencyCondition.class);
        DependencyCondition dependencyCondition1 = new DependencyCondition();
        dependencyCondition1.setId(1L);
        DependencyCondition dependencyCondition2 = new DependencyCondition();
        dependencyCondition2.setId(dependencyCondition1.getId());
        assertThat(dependencyCondition1).isEqualTo(dependencyCondition2);
        dependencyCondition2.setId(2L);
        assertThat(dependencyCondition1).isNotEqualTo(dependencyCondition2);
        dependencyCondition1.setId(null);
        assertThat(dependencyCondition1).isNotEqualTo(dependencyCondition2);
    }
}
