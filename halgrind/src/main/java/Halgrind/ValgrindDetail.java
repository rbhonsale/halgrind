package org.valgrind.forhudson;

import hudson.model.AbstractBuild;

import org.valgrind.forhudson.model.ValgrindError;
import org.valgrind.forhudson.model.ValgrindProcess;
import org.valgrind.forhudson.util.ValgrindSourceFile;

/**
 * 
 * @author Johannes Ohlemacher
 * 
 */
public class ValgrindDetail
{
	private ValgrindError error;
	private ValgrindProcess process;
	final private AbstractBuild<?, ?> owner;
	
	public ValgrindDetail( AbstractBuild<?, ?> owner, ValgrindProcess process, ValgrindError error, ValgrindSourceFile valgrindSourceFile )
	{
		this.owner = owner;
		this.error = error;	
		this.process = process;
		
		if ( error != null )
			error.setSourceCode( valgrindSourceFile );
	}

	public ValgrindError getError()
	{
		return error;
	}
	
	public ValgrindProcess getProcess()
	{
		return process;
	}

	public AbstractBuild<?, ?> getOwner()
	{
		return owner;
	}
}