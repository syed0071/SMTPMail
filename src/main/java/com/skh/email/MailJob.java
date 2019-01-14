package com.skh.email;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

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
	public MailGenerator getJiraMailGenerator() {
		return mailGenerator;
	}

	@PostConstruct
	public void init() {
		setJiraMailGenerator(mailGenerator);
		System.out.println("mailGenerator");
	}

	/**
	 * @param mailGenerator
	 */
	public void setJiraMailGenerator(MailGenerator mailGenerator) {
		this.mailGenerator = mailGenerator;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		getJiraMailGenerator().sendEmail();
	}

}
