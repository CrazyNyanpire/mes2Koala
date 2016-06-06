package org.seu.acetec.mes2Koala.facade.quartz;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
@Named
@Transactional
public class TestTask {
	private static Logger LOGGER = Logger
			.getLogger(TestTask.class);
	
	//@Scheduled(cron = "30 * * * * ?")
	public void testNotice() {
		LOGGER.info("testNotice");
	}
}
