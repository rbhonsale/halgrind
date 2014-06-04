package org.valgrind.forhudson.call;

public interface ValgrindOption
{
	public String getName();
    public String getArgumentString(ValgrindVersion version) throws ValgrindOptionNotApplicableException;
}
