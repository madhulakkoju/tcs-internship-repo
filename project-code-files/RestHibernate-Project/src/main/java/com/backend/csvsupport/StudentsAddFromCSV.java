package com.backend.csvsupport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.backend.dao.StudentDao;
import com.backend.model.Student;


public class StudentsAddFromCSV {

	public void csvToDatabase(String csvFilePath) throws IOException {
		StudentDao studentDatabase = new StudentDao();
		Pattern pattern = Pattern.compile(",");

		BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
		String fields[];
		String line = null;

		Student student;

		String[] fieldNames = pattern.split(reader.readLine());

		long startTime, endTime;
		startTime = System.currentTimeMillis();
		List<Student> studentsList = new ArrayList<Student>(10000);
		int i = 0;
		System.out.println("Start Time : " + startTime);

		while ((line = reader.readLine()) != null) {
			fields = pattern.split(line);
			if (fields.length == 0)
				break;
			student = Student.validateFields(fields);
			if (student == null)
				System.out.println("\nError in the Record of Teacher ID : " + fields[2]);
			else {
				// TeacherDatabase.addTeacher(teacher);
				studentsList.add(student);
				i++;
			}

			if (i == 1000) {
				studentDatabase.addStudents(studentsList);
				i = 0;
				System.out.println(fields[2]);
				System.out.println("Time elapsed : "
						+ TimeUnit.SECONDS.convert(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS)
						+ " seconds");

				studentsList.clear();
				break;
			}
		}

		if (i > 0)
			studentDatabase.addStudents(studentsList);

		endTime = System.currentTimeMillis();
		System.out.println("End Time : " + endTime);

		System.out.println("Time elapsed : " + TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.MILLISECONDS)
				+ " milliseconds");

		System.out.println(
				"Time elapsed : " + TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.MILLISECONDS) + " seconds");

		reader.close();
		//System.out.println(studentDatabase.getAllStudents());
		System.out.println(studentDatabase.getStudent(1));
		studentDatabase.deleteStudent(1);
		System.out.println(studentDatabase.getStudent(1));
		System.out.println(studentDatabase.getStudentsByDepartment("CSE"));
	}

	public static void main(String args[]) throws IOException {
		String path = "C:\\Users\\madhu\\Desktop\\studentsTestFile.csv";
		StudentsAddFromCSV cs = new StudentsAddFromCSV();
		cs.csvToDatabase(path);
	}
}
