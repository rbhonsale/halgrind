package org.valgrind.forhudson;

import hudson.model.AbstractBuild;
import hudson.FilePath;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.valgrind.forhudson.config.ValgrindPublisherConfig;
import org.valgrind.forhudson.model.ValgrindError;
import org.valgrind.forhudson.model.ValgrindProcess;
import org.valgrind.forhudson.model.ValgrindReport;
import org.valgrind.forhudson.parser.ValgrindParserResult;
import org.valgrind.forhudson.util.ValgrindSourceFile;
import org.valgrind.forhudson.util.ValgrindSummary;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;



public class ValgrindResult implements Serializable
{	
	private static final long serialVersionUID = -5347879997716170059L;
	private static final String PID_TOKEN = "pid=";

    private ValgrindParserResult parser;
    private ValgrindReport report;
    private AbstractBuild<?, ?> owner;
    private Map<String, String> sourceFiles;
     

    public ValgrindResult( AbstractBuild<?, ?> build, ValgrindParserResult parser)
    {
    	this.owner = build;
        this.parser = parser;
        this.report = null; //for results serialized through this plugin version and later
    }
    
	public AbstractBuild<?, ?> getOwner()
	{
		return owner;
	}
	
	public ValgrindPublisherConfig getConfig()
	{
		ValgrindBuildAction action = (ValgrindBuildAction)owner.getAction(ValgrindBuildAction.class);
    	if ( action == null )
    		return null;
    	
		return action.getConfig();    			
	}

	/**
	 * @return a ValgrindReport
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public ValgrindReport getReport() throws IOException, InterruptedException
	{
	    if(report != null) {
	        return report; //for results serialized through older plugin version(s)
	    }
	    else {
	        FilePath file = new FilePath(owner.getRootDir());
		return file.act(parser);
	    }
	}

	public Map<String, String> getSourceFiles()
	{
		return sourceFiles;
	}
	
	public void setSourceFiles(Map<String, String> sourceFiles)
	{
		this.sourceFiles = sourceFiles;
	}	

	/**
	 * Renders the summary Valgrind report for the build result.
	 * 
	 * @return the HTML fragment of the summary Valgrind report
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String getSummary() throws IOException, InterruptedException
	{
		return ValgrindSummary.createReportSummary(this);
	}
	
	/**
	 * 
	 * @param link expected to be in format "id=<executable name>,<unique error id>"
	 * @param request
	 * @param response
	 * @return valgrind detail(s)
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Object getDynamic(final String l, final StaplerRequest request, final StaplerResponse response)
			throws IOException, InterruptedException
	{	
		final String[] s = l.split("/");
		final String data = s[s.length -1];
		
		if ( !data.startsWith(PID_TOKEN) )
			return null;
		
		int sep = data.indexOf(",");
		
		ValgrindReport report = getReport();
		if ( sep > PID_TOKEN.length() )
		{
			String pid = data.substring(PID_TOKEN.length(), sep);
			String uniqueId = data.substring( sep + 1 );

			ValgrindError error = report.findError(pid, uniqueId);
			if ( error == null )
				return null;		

			ValgrindSourceFile sourceFile = new ValgrindSourceFile( ValgrindPublisher.DESCRIPTOR.getLinesBefore(), ValgrindPublisher.DESCRIPTOR.getLinesAfter(), sourceFiles, owner );
	 
			return new ValgrindDetail( owner, report.findProcess(pid), error, sourceFile );			
		}
		else
		{
			String pid = data.substring(PID_TOKEN.length());
			ValgrindProcess process = report.findProcess(pid);
			
			return new ValgrindProcessDetails(owner, process);			
		}
	}

}
