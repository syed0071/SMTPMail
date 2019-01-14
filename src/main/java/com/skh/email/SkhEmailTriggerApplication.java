package com.skh.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author 
 *
 */
@SpringBootApplication
@EnableJpaAuditing
@ImportResource( "classpath:spring-batch-quartz-scheduler.xml" )
public class SkhEmailTriggerApplication
{

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        SpringApplication.run( SkhEmailTriggerApplication.class, args );
    }
}
