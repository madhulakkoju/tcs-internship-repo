package com.backend.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.backend.model.Student;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;

public class StudentDao 
{
	private final String databaseName = "school";
	private final String collectionName = "students";
	private MongoClient client;
	private MongoDatabase db;
	private MongoCollection<Document> students;
	private ObjectMapper mapper;
	
	public StudentDao() {
		//Constructor
		// Connecting to localhost:27017 port
		this.client = new MongoClient("localhost",27017);
		//fetching MongoDatabase from connected Client
		this.db = client.getDatabase(databaseName);
		//fetching concerned collection from db
		this.students = this.db.getCollection(collectionName);
		List<String> fieldNames = new ArrayList<String>();
		fieldNames.add("id");
		fieldNames.add("department");
		this.mapper = new ObjectMapper();
		mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
		this.students.createIndex(Indexes.ascending(fieldNames));
	}

	// Add a Student by taking Student Object as Parameter
	public Document addStudent(Student student) throws JsonGenerationException, JsonMappingException, IOException {
		//if empty student object sent, return null
		if(student == null)
			return null;
		//check if the student object is already in database
		Document checkStudent = students.find( Filters.eq("id",student.getId())).first();
		if(checkStudent != null)
			return checkStudent;
		//as the student object is not in db already, create a Document 
		Document studentDoc = Document.parse(mapper.writeValueAsString(student));
		//Insert the student document into collection
		students.insertOne(studentDoc);
		studentDoc.remove("_id");
		return studentDoc;
	}
	
	public void addStudents(List<Student> studentsList) throws JsonGenerationException, JsonMappingException, IOException
	{
		for(Student s : studentsList)
		{
			//Check for repetition by find(Filters.eq())
			Document doc = Document.parse(mapper.writeValueAsString(s));
			students.insertOne(doc);
		}
	}
	
	
	//get Student object from Database using given field
	public Document getStudent(String field,Object value)
	{
		Document st = students.find(Filters.eq(field,value)).first();
		st.remove("_id");
		return st;
	}
	//Fetching Student on given Id
	public Document getStudentById(String id)
	{
		Document st = students.find(Filters.eq("id",id)).first();
		st.remove("_id");
		return st;
	}
	//Fetching Student on the User Email
	public Document getStudentByEmail(String email)
	{
		Document st = students.find(Filters.eq("User_Email",email)).first();
		st.remove("_id");
		return st;
	}
	
	//Fetching all Students
	public List<Document> getAllStudents()
	{
		List<Document> allStudents = new ArrayList<Document>();
		//fetch all students into iterator
		MongoCursor<Document> cursor = students.find().cursor();
		Document d;
		while(cursor.hasNext())
		{
			d = cursor.next();
			d.remove("_id");
			allStudents.add(d);
		}
		return allStudents;
	}
	
	//Fetch students on the Department
	public List<Document> getStudentsByDept(String dept)
	{ 
		if(dept == null || dept.equals(""))
			return this.getAllStudents();
		List<Document> allStudents = new ArrayList<Document>();
		//fetch all students into iterator
		MongoCursor<Document> cursor = students.find(Filters.eq("department",dept.toUpperCase())).cursor();
		Document d;
		while(cursor.hasNext())
		{
			d = cursor.next();
			d.remove("_id");
			allStudents.add(d);
		}
		return allStudents;
	}
	
	public Document deleteStudentById(String studentId)
	{
		return students.findOneAndDelete(Filters.eq("id",studentId));
	}
	
	public Document updateStudent(Student student) throws JsonGenerationException, JsonMappingException, IOException
	{
		students.findOneAndDelete(Filters.eq("id",student.getId()));
		//Update Logic within the Document for better results
		Document studentDoc = Document.parse(mapper.writeValueAsString(student));
		students.insertOne(studentDoc);
		return studentDoc;
	}
	
}
