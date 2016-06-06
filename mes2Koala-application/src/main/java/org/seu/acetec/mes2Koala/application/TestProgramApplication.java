package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.TestProgram;

import java.util.List;
import java.util.Set;

/**
 * @author yuxiangque
 * @version 2016/3/23
 */
public interface TestProgramApplication {

    TestProgram getTestProgram(Long id);

    void createTestProgram(TestProgram testProgram);

    void updateTestProgram(TestProgram testProgram);

    void removeTestProgram(TestProgram testProgram);

    void removeTestPrograms(Set<TestProgram> testPrograms);

    List<TestProgram> findAllTestProgram();

    void createTestPrograms(List<TestProgram> testPrograms);
}
