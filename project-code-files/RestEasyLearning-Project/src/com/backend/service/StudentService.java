package com.backend.service;


import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.backend.dao.StudentDao;
import com.backend.model.Student;



@Path(value="students")
public class StudentService 
{
	
	@GET
	@Path(value="/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudents()
	{
		StudentDao db = new StudentDao();
		System.out.println("Sending data");
		return Response.ok(db.getAllStudents()).build();
	}
	
	@GET
	@Path(value="/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent(@PathParam("studentId") String studentId)
	{
		StudentDao db = new StudentDao();
		Document st = db.getStudentById(studentId);
		st.remove("_id");
		return Response.ok(st).build();
	}
	
	@POST
	@Path(value="/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON )
	public Response addStudent(Student student) throws JsonGenerationException, JsonMappingException, IOException
	{
		StudentDao db = new StudentDao();
		Document doc = db.addStudent(student);
		doc.remove("_id");
		return Response.accepted(doc).build();
	}
	
	@DELETE
	@Path(value="/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStudent(@PathParam("studentId") String studentId )
	{
		StudentDao db = new StudentDao();
		Document studentDoc = db.deleteStudentById(studentId);
		studentDoc.remove("_id");
		return Response.accepted(studentDoc).build();
	}
	
	@PUT
	@Path(value="/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStudent(@PathParam("studentId") String studentId, Student student) throws JsonGenerationException, JsonMappingException, IOException
	{
		if(! student.getId().equals(student.getId()))
			return Response.notModified("The ID in URL and ID in STUDENT body donot Match !!").build();
		StudentDao db = new StudentDao();
		Document studentDoc = db.updateStudent(student);		
		studentDoc.remove("_id");
		return Response.accepted(studentDoc).build();
	}
	
}
