package com.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.backend.dao.StudentDao;
import com.backend.model.Student;

@Path(value="students")
public class StudentService
{
	
	@GET
	@Path(value="/")
	@Produces(MediaType.APPLICATION_JSON )
	public Response getStudents() throws JsonParseException, JsonMappingException, IOException 
	{
		ObjectMapper mapper = new ObjectMapper();
		
		StudentDao db = new StudentDao();
		//Mongo Document List is returned
		List<Student> students = db.getAllStudents();
		
		ArrayList<String> allStudents = new ArrayList<String>(students.size());
		
		Iterator<Student> it = students.iterator(); 
		while(it.hasNext()) 
		{
			allStudents.add(mapper.writeValueAsString(it.next()));
		}
		 
		System.out.println("Sent List\n\n"+allStudents);
		return Response.ok(allStudents).build();
	}
	
	@GET
	@Path(value="/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent(@PathParam("studentId") long studentId) throws JsonGenerationException, JsonMappingException, IOException 
	{
		ObjectMapper mapper = new ObjectMapper();
		
		StudentDao db = new StudentDao();
		Student student = db.getStudent(studentId);

		return Response.ok(mapper.writeValueAsString(student)).build();
	}
	
	
	
	@POST
	@Path(value="/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON )
	public Response addStudent(Student student) throws JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		
		StudentDao db = new StudentDao();
		boolean added =  db.addStudent(student);
		if(added)
		return Response.accepted(mapper.writeValueAsString(student)).build();
		return Response.accepted(null).build();
	}
	
	@DELETE
	@Path(value="/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStudent(@PathParam("studentId") long studentId ) throws JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		
		StudentDao db = new StudentDao();
		System.out.println("delete ");
		Student studentDoc = db.deleteStudent(studentId);
		
		return Response.accepted(mapper.writeValueAsString(studentDoc)).build();
	}
	
	@PUT
	@Path(value="/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStudent(@PathParam("studentId") long studentId, Student student) throws JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		
		StudentDao db = new StudentDao();
		
		if(! student.getId().equals(student.getId()))
			return Response.notModified("The ID in URL and ID in STUDENT body donot Match !!").build();
		
		Student studentDoc = db.updateStudent(studentId,student);		
		
		return Response.accepted(mapper.writeValueAsString(studentDoc)).build();
	}
	
}

