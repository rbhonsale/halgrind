package  org.valgrind.forhudson.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.valgrind.forhudson.util.ValgrindErrorList;


public class ValgrindProcess implements Serializable
{
	private static final long	serialVersionUID	= -7073482135992069077L;
	
	private String executable;
	private List<String> arguments;
	private String pid;
	private String ppid;
	private List<ValgrindError> errors;
	
	private transient ValgrindProcess parent;
	private transient List<ValgrindProcess> childs;
	
	public ValgrindProcess()
	{
	}
	
	public ValgrindProcess getParent()
	{
		return parent;
	}
	
	public List<ValgrindProcess> getChilds()
	{
		return childs;
	}
	
	public String getExecutable()
	{
		return executable;
	}
	
	public void setExecutable(String executable)
	{
		this.executable = executable;
	}
	
	public List<String> getArguments()
	{
		return arguments;
	}
	
	public void setArguments(List<String> arguments)
	{
		this.arguments = arguments;
	}
	
	public void addArgument(String arg)
	{
		if ( arguments == null )
			arguments = new ArrayList<String>();
		
		arguments.add(arg);
	}
	
	public String getPid()
	{
		return pid;
	}
	
	public void setPid(String pid)
	{
		this.pid = pid;
	}
	
	public String getPpid()
	{
		return ppid;
	}
	
	public void setPpid(String ppid)
	{
		this.ppid = ppid;
	}

	public List<ValgrindError> getErrors()
	{
		return errors;
	}

	public void setErrors(List<ValgrindError> errors)
	{
		this.errors = errors;
	}

	public void addError(ValgrindError error)
	{
		if ( errors == null )
			errors = new ArrayList<ValgrindError>();
		
		errors.add(error);
	}
	
	public ValgrindError findErrorByUniqueId(String id)
	{
		if ( errors == null )
			return null;
		
		for( ValgrindError error : errors )
			if ( error.getUniqueId().equals(id) )
				return error;
		
		return null;
	}
	
	public ValgrindErrorList getErrorList()
	{
		return new ValgrindErrorList(errors);
	}
	
	public void setupParentChilds( List<ValgrindProcess> processes )
	{
		if ( parent != null || childs != null || processes == null )
			return;
		
		for ( ValgrindProcess p : processes )
		{
			if ( parent == null && ppid != null && ppid.equals(p.pid) )
				parent = p;
			
			if ( pid != null && pid.equals(p.ppid) )
			{
				if ( childs == null )
					childs = new ArrayList<ValgrindProcess>();
				
				childs.add(p);
			}
		}
	}
	
	public String getArgumentsString()
	{
		String s = "";
		
		if ( arguments != null )
		{
			for( String a : arguments )
				s += "\"" + a + "\" ";
		}
		
		return s.trim();
	}
}
