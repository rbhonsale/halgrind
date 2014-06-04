package org.valgrind.forhudson.config;

import java.io.Serializable;

import org.kohsuke.stapler.DataBoundConstructor;

public class ValgrindPublisherConfig implements Serializable
{
	private static final long serialVersionUID = 1335068144678253494L;
	
	private String pattern = "*.memcheck";
	private String failThresholdInvalidReadWrite;
	private String failThresholdDefinitelyLost;
	private String failThresholdTotal;
	private String unstableThresholdInvalidReadWrite;
	private String unstableThresholdDefinitelyLost;
	private String unstableThresholdTotal;	
	private boolean publishResultsForAbortedBuilds;
	private boolean publishResultsForFailedBuilds;
	
	@DataBoundConstructor
	public ValgrindPublisherConfig( String pattern, 
			String failThresholdInvalidReadWrite, 
			String failThresholdDefinitelyLost, 
			String failThresholdTotal,
			String unstableThresholdInvalidReadWrite, 
			String unstableThresholdDefinitelyLost, 
			String unstableThresholdTotal,
			boolean publishResultsForAbortedBuilds,
			boolean publishResultsForFailedBuilds)
	{
		this.pattern = pattern.trim();
		this.failThresholdInvalidReadWrite = failThresholdInvalidReadWrite.trim();
		this.failThresholdDefinitelyLost = failThresholdDefinitelyLost.trim();
		this.failThresholdTotal = failThresholdTotal.trim();		
		this.unstableThresholdInvalidReadWrite = unstableThresholdInvalidReadWrite.trim();
		this.unstableThresholdDefinitelyLost = unstableThresholdDefinitelyLost.trim();
		this.unstableThresholdTotal = unstableThresholdTotal.trim();	
		this.publishResultsForAbortedBuilds = publishResultsForAbortedBuilds;
		this.publishResultsForFailedBuilds = publishResultsForFailedBuilds;
	}

	public ValgrindPublisherConfig()
	{
	}

	public String getPattern()
	{
		return pattern;
	}

	public String getFailThresholdInvalidReadWrite()
	{
		return failThresholdInvalidReadWrite;
	}

	public String getFailThresholdDefinitelyLost()
	{
		return failThresholdDefinitelyLost;
	}

	public String getFailThresholdTotal()
	{
		return failThresholdTotal;
	}

	public String getUnstableThresholdInvalidReadWrite()
	{
		return unstableThresholdInvalidReadWrite;
	}

	public String getUnstableThresholdDefinitelyLost()
	{
		return unstableThresholdDefinitelyLost;
	}

	public String getUnstableThresholdTotal()
	{
		return unstableThresholdTotal;
	}

	public boolean isPublishResultsForAbortedBuilds()
	{
		return publishResultsForAbortedBuilds;
	}

	public boolean isPublishResultsForFailedBuilds()
	{
		return publishResultsForFailedBuilds;
	}

}
