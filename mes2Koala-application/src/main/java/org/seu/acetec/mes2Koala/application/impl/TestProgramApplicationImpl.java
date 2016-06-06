package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.TestProgramApplication;
import org.seu.acetec.mes2Koala.core.domain.TestProgram;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;
import java.util.Set;

/**
 * @author yuxiangque
 * @version 2016/3/23
 */
@Named
@Transactional
public class TestProgramApplicationImpl implements TestProgramApplication {

    @Override
    public TestProgram getTestProgram(Long id) {
        return TestProgram.get(TestProgram.class, id);
    }

    @Override
    public void createTestProgram(TestProgram testProgram) {
        testProgram.save();
    }

    @Override
    public void updateTestProgram(TestProgram testProgram) {
        testProgram.save();
    }

    @Override
    public void removeTestProgram(TestProgram testProgram) {
        if (testProgram != null) {
            testProgram.remove();
        }
    }

    @Override
    public void removeTestPrograms(Set<TestProgram> testPrograms) {
        for (TestProgram testProgram : testPrograms) {
            testProgram.remove();
        }
    }

    @Override
    public List<TestProgram> findAllTestProgram() {
        return TestProgram.findAll(TestProgram.class);
    }

    @Override
    public void createTestPrograms(List<TestProgram> testPrograms) {
        for (TestProgram testProgram : testPrograms) {
            testProgram.save();
        }
    }
}
