package com.cerner.cts.jira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author sh030348
 *
 */
@SpringBootApplication
@EnableJpaAuditing
@ImportResource( "classpath:spring-batch-quartz-scheduler.xml" )
public class CtsMalvernJiraApplication
{

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        SpringApplication.run( CtsMalvernJiraApplication.class, args );
    }
}
