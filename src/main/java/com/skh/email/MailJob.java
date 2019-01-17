package com.skh.email;

import java.util.logging.Logger;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author
 *
 */
@Component
public class MailJob extends QuartzJobBean {

	private static final Logger logger = Logger.getLogger(MailJob.class.getName());

	@Autowired
	private MailGenerator mailGenerator;

	/**
	 * @return MailGenerator
	 */
	public MailGenerator getMailGenerator() {
		return mailGenerator;
	}


	/**
	 * @param mailGenerator
	 */
	public void setMailGenerator(MailGenerator mailGenerator) {
		this.mailGenerator = mailGenerator;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println(mailGenerator.hashCode());
		mailGenerator.sendEmail();
	}

}
