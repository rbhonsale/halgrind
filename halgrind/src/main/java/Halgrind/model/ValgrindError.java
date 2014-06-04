package org.valgrind.forhudson.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.valgrind.forhudson.util.ValgrindSourceFile;
import org.valgrind.forhudson.util.ValgrindUtil;


/**
 * 
 * @author Johannes Ohlemacher
 * 
 */
public class ValgrindError implements Serializable
{
	private static final long serialVersionUID = 6470943829358084900L;
	
	@Deprecated
	private String					executable;
	@SuppressWarnings("unused")
	@Deprecated
	private String                 pid;
	@SuppressWarnings("unused")
	@Deprecated
	private String                 ppid;
	
	private String					uniqueId;
	private ValgrindErrorKind		kind;
	private ValgrindStacktrace		stacktrace;
	private String					description;
	private Integer					leakedBytes;
	private Integer					leakedBlocks;
	private List<ValgrindAuxiliary>	auxiliaryData;

	public String toString()
	{
		return 
		"kind: " + kind + "\n" +
		"text: " + description + "\n" +
		"stack: " + stacktrace.toString();
	}	
	
	public void setSourceCode( ValgrindSourceFile sourceFile )
	{
		if ( stacktrace != null )
			stacktrace.setSourceCode( sourceFile );
		
		if ( auxiliaryData != null )
		{
			for( ValgrindAuxiliary aux : auxiliaryData )
			{
				if ( aux == null || aux.getStacktrace() == null )
					continue;
				
				aux.getStacktrace().setSourceCode( sourceFile);					
			}
		}
	}
	
	public ValgrindStacktrace getStacktrace()
	{
		return stacktrace;
	}
	
	public void setStacktrace(ValgrindStacktrace stacktrace)
	{
		this.stacktrace = stacktrace;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = ValgrindUtil.trimToNull( description );		
	}
	
	public ValgrindErrorKind getKind()
	{
		return kind;
	}
	
	public void setKind(ValgrindErrorKind kind)
	{
		this.kind = kind;
	}

	public String getUniqueId()
	{
		return uniqueId;
	}

	public void setUniqueId(String uniqueId)
	{
		this.uniqueId = ValgrindUtil.trimToNull( uniqueId );
	}

	@Deprecated
	public String getExecutable()
	{
		return executable;
	}
	
	public Integer getLeakedBytes()
	{
		return leakedBytes;
	}

	public void setLeakedBytes( Integer leakedBytes )
	{
		this.leakedBytes = leakedBytes;
	}

	public Integer getLeakedBlocks()
	{
		return leakedBlocks;
	}

	public void setLeakedBlocks( Integer leakedBlocks )
	{
		this.leakedBlocks = leakedBlocks;
	}
	
	public List<ValgrindAuxiliary> getAuxiliaryData()
	{
		return auxiliaryData;
	}

	public void setAuxiliaryData( List<ValgrindAuxiliary> auxiliaryData )
	{
		this.auxiliaryData = auxiliaryData;
	}
	
	public void addAuxiliaryData( ValgrindAuxiliary auxiliaryData )
	{
		if ( this.auxiliaryData == null )
			this.auxiliaryData = new ArrayList<ValgrindAuxiliary>();
		
		this.auxiliaryData.add(auxiliaryData);
	}
}
