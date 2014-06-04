package org.valgrind.forhudson.parser;

import hudson.FilePath;
import hudson.Util;
import hudson.remoting.VirtualChannel;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.FileSet;
import org.valgrind.forhudson.model.ValgrindReport;
import org.valgrind.forhudson.util.ValgrindLogger;


public class ValgrindParserResult implements FilePath.FileCallable<ValgrindReport>
{
	private static final long serialVersionUID = -5475538646374717099L;
	private String pattern;
	
	public ValgrindParserResult( String pattern )
	{
		this.pattern = pattern;
	}

	public ValgrindReport invoke(File basedir, VirtualChannel channel) throws IOException, InterruptedException
	{
		ValgrindLogger.logFine("looking for valgrind files in '" + basedir.getAbsolutePath() + "' with pattern '" + pattern + "'");
		
		ValgrindReport valgrindReport = new ValgrindReport();
		
		for ( String fileName : findValgrindsReports( basedir ) )
		{
			ValgrindLogger.logFine("parsing " + fileName + "...");
			try
			{
				ValgrindReport report = new ValgrindSaxParser().parse( new File(basedir, fileName) );
				valgrindReport.integrate( report );
			} 
			catch (Exception e)
			{
				ValgrindLogger.logFine("failed to parse " + fileName + ": " + e.getMessage());
			}
		}

		return valgrindReport;
	}
	
	private String[] findValgrindsReports(File parentPath)
	{
		FileSet fs = Util.createFileSet(parentPath, this.pattern);
		DirectoryScanner ds = fs.getDirectoryScanner();
		return ds.getIncludedFiles();
	}
}
