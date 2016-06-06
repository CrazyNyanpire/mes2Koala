package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.ProcessInfo;

/**
 * Created by LCN on 2016/3/6.
 */
public interface ProcessInfoApplication {

    public void setIQC(ProcessInfo processInfo);

    public ProcessInfo getIQC(String id);


    public void createProcessInfo(ProcessInfo processInfo);

    public ProcessInfo getProceeInfo(Long id);

}