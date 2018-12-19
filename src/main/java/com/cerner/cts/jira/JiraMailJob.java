package com.cerner.cts.jira;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author sh030348
 *
 */
@Component
public class JiraMailJob extends QuartzJobBean
{

    private static final Logger logger = Logger.getLogger( JiraMailJob.class.getName() );

    @Autowired
    private JiraMailGenerator   jiraMailGenerator;

    /**
     * @return JiraMailGenerator
     */
    public JiraMailGenerator getJiraMailGenerator()
    {
        return jiraMailGenerator;
    }

    /**
     * @param jiraMailGenerator
     */
    public void setJiraMailGenerator( JiraMailGenerator jiraMailGenerator )
    {
        this.jiraMailGenerator = jiraMailGenerator;
    }

    @Override
    protected void executeInternal( JobExecutionContext arg0 ) throws JobExecutionException
    {
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        List<JiraRow> list = new ArrayList<JiraRow>();
        try
        {
            logger.info( "Triggered Jira Mail Job" );

            jiraMailGenerator.processMalvernJira( list );
            endTime = System.currentTimeMillis();
            logger.info( "Malvern jira report  has completed and has taken : " + ((endTime - startTime) / 1000)
                            + " seconds" );

        }
        catch ( Exception e )
        {

        }
    }

}
