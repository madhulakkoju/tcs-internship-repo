package com.backend.application;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.backend.service.StudentService;

@ApplicationPath("/app")
public class SchoolApplication extends Application
{
	// this singletons set contains all the Resources created in the aplication
	// we need to add objects of all the resources 
	private Set<Object> singletons = new HashSet<>();
	
	public SchoolApplication()
	{
		// This is initial creation od application. 
		// So, add all the reources to the set.
		singletons.add(new StudentService());
	}
	
	@Override
	public Set<Object> getSingletons()
	{
		return this.singletons;
	}
	
}
