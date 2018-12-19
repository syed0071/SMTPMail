package com.cerner.cts.jira;

/**
 * @author sh030348
 *
 */
public class JiraRow
{

    private String assigneeId;
    private String logUserId;
    private String jiraId;
    private String dateLogged;
    private String logUserName;
    private String logManagerName;
    private String component;
    private String loggedHours;
    private String jiraSummary;

    public String getJiraSummary()
    {
        if ( jiraSummary != null )
        {
            return jiraSummary;
        }
        return "";

    }

    public void setJiraSummary( String jiraSummary )
    {
        this.jiraSummary = jiraSummary;
    }

    public String getLoggedHours()
    {
        if ( loggedHours != null )
        {
            return loggedHours;
        }
        return "";

    }

    public void setLoggedHours( String loggedHours )
    {
        this.loggedHours = loggedHours;
    }

    public String getAssigneeId()
    {
        if ( assigneeId != null )
        {
            return assigneeId;
        }
        return "";

    }

    public void setAssigneeId( String assigneeId )
    {
        this.assigneeId = assigneeId;
    }

    public String getLogUserId()
    {
        if ( logUserId != null )
        {
            return logUserId;
        }
        return "";

    }

    public void setLogUserId( String logUserId )
    {
        this.logUserId = logUserId;
    }

    public String getJiraId()
    {
        return jiraId;
    }

    public void setJiraId( String jiraId )
    {
        this.jiraId = jiraId;
    }

    public String getDateLogged()
    {
        if ( dateLogged != null )
        {
            return dateLogged;
        }
        return "";

    }

    public void setDateLogged( String dateLogged )
    {
        this.dateLogged = dateLogged;
    }

    public String getLogUserName()
    {
        if ( logUserName != null )
        {
            return logUserName;
        }
        return "";
    }

    public void setLogUserName( String logUserName )
    {
        this.logUserName = logUserName;
    }

    public String getLogManagerName()
    {
        if ( logManagerName != null )
        {
            return logManagerName;
        }
        return "";
    }

    public void setLogManagerName( String logManagerName )
    {
        this.logManagerName = logManagerName;
    }

    public String getComponent()
    {
        if ( component != null )
        {
            return component;
        }
        return "";

    }

    public void setComponent( String component )
    {
        this.component = component;
    }

    @Override
    public String toString()
    {

        return getJiraId() + JiraMailGenerator.DELIMETER + getJiraSummary().replace( ",", " " )
                        + JiraMailGenerator.DELIMETER + getAssigneeId() + JiraMailGenerator.DELIMETER + getLogUserId()
                        + JiraMailGenerator.DELIMETER + getLogUserName().replace( ",", " " )
                        + JiraMailGenerator.DELIMETER + getDateLogged() + JiraMailGenerator.DELIMETER + getLoggedHours()
                        + JiraMailGenerator.DELIMETER + getLogManagerName().replace( ",", " " )
                        + JiraMailGenerator.DELIMETER + getComponent();
    }

}
