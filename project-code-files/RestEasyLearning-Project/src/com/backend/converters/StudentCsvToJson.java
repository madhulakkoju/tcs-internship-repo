package com.backend.converters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.backend.dao.StudentDao;
import com.backend.model.Student;

public class StudentCsvToJson 
{
	public static void csvToJson(String csvFilePath) throws IOException
	{
		Pattern pattern = Pattern.compile(",");

		BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
		String fields[] ;
		String line = null;
		
		Student student = null;
		String[] fieldNames = pattern.split(reader.readLine());
		
		long startTime ,endTime;
		startTime = System.currentTimeMillis();
		
		List<Student> studentsList = new ArrayList<Student>(10000);
		int i=0;
		System.out.println("Start time : "+startTime);
		
		StudentDao studentDb = new StudentDao();
		
		while((line = reader.readLine())!=null)
		{
			fields = pattern.split(line);
			if(fields.length==0)
				break;
			// validate
			student = Student.validateFields(fields);
			//System.out.println("validated");
			//System.out.println(student.toString());
			// add to database
			if(student != null)
			{
				studentsList.add(student);
				student = null;
				i++;
			}
			else
			{
				System.out.println("Error at Student Record : "+fields[4]);
			}
			
			if(i==10000)
			{
				i=0;
				studentDb.addStudents(studentsList);
				System.out.println("added");
				studentsList.clear();
			}
		}
		if(i>0)
		{
			studentDb.addStudents(studentsList);
		}
		endTime = System.currentTimeMillis();
		System.out.println("Time Elapsed in milli seconds : "+(endTime-startTime));
	}
	
	public static void main(String args[])
	{
		String csvFile="C:\\Users\\madhu\\Desktop\\studentsTestFile.csv";
		
		try {
			StudentCsvToJson.csvToJson(csvFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
