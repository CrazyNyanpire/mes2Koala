package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.ProcessInfoApplication;
import org.seu.acetec.mes2Koala.core.domain.ProcessInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;


/**
 * Created by LCN on 2016/3/6.
 */
@Named
@Transactional
public class ProcessInfoApplicationImpl implements ProcessInfoApplication {
    @Override
    public void setIQC(ProcessInfo processInfo) {
    }

    @Override
    public ProcessInfo getIQC(String id) {
        return ProcessInfo.get(ProcessInfo.class, id);
    }

    @Override
    public void createProcessInfo(ProcessInfo processInfo) {
        processInfo.save();
    }

    @Override
    public ProcessInfo getProceeInfo(Long id) {
        return ProcessInfo.get(ProcessInfo.class, id);
    }
}