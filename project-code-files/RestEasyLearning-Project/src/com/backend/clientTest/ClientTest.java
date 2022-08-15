package com.backend.clientTest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.backend.model.Student;



public class ClientTest 
{
	String baseUrl = "http://localhost:8080/RestEasyLearning-Project/app/students/";
	public Student getStudentFromServer(String id)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(baseUrl+id);
		Response response = target.request().get();
		
		Student student =  response.readEntity(Student.class);
		System.out.println( "Student with Given Id - "+id+"  : "+ student);
		response.close();
		return student;
	}

	public List<Student> getAllStudents() throws JsonParseException, JsonMappingException, IOException
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(baseUrl);
		
		Response response = target.request().get();

		String studentsArray = response.readEntity(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
		
		List<Student> studentsList= mapper.readValue(studentsArray, new TypeReference<List<Student>>(){});
		
		System.out.println(studentsList);
		return studentsList;
	}
	
	public Student deleteStudent(String id)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(baseUrl+id);
		
		Response response = target.request().delete();
		Student str = response.readEntity(Student.class);
		//System.out.println("Deleted Student Object : "+str);
		return str;
	}
	
	public Student addStudent(Student st)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(baseUrl);
		
		Response response = target.request().post(Entity.entity(st, "application/json"));
		//System.out.println("Student Add Status to Response Object : "+response.getStatus());
		Student str = response.readEntity(Student.class);
		return str;
	}
	
	public Student updateStudent(String id,Student st)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(baseUrl+st.getId());
		
		Response response = target.request().put(Entity.entity(st, "application/json"));
		//System.out.println("Response Status to update a student id : "+response.getStatus());
		Student str = response.readEntity(Student.class);
		//System.out.println("Student Object Updated as  : "+str);
		return str;
	}
	
	
	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException
	{
		System.out.println("TESTER");
		ClientTest tester = new ClientTest();
		//Student with Id 2 returned.
		tester.getStudentFromServer("2");
		System.out.println();
		
		System.out.println("ALL STUDENTS");
		tester.getAllStudents();
		System.out.println("Tested");
		
		tester.deleteStudent("21");
		
	}
}
