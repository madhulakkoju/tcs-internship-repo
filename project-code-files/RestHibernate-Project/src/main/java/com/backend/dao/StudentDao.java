package com.backend.dao;

import java.util.List;

import org.hibernate.*;

import com.backend.hibernateutil.HibernateUtil;
import com.backend.model.Student;

public class StudentDao {

	Session session;
	
	public StudentDao()
	{
		this.session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public boolean addStudent(Student student)
	{
		session.beginTransaction();
		boolean added = true;
		if(session.save(student)==null)
			added = false;
		session.getTransaction().commit();
		return added;
	}
	
	public void addStudents(List<Student> studentsList)
	{
		session.beginTransaction();
		
		for(Student student: studentsList)
		{
			if(session.save(student) == null) {
				System.out.println(student+"  Not added to database");
			}
		}
		session.getTransaction().commit();
	}

	public List<Student> getAllStudents()
	{
		List<Student> allStudents = session.createQuery("From Student").list();
		return allStudents;
	}
	
	public List<Student> getStudentsByDepartment(String department)
	{
		List<Student> allStudents = session.createQuery("From Student s where s.department = \'"+department+"\'").list();
		return allStudents;
	}
	
	public Student getStudent(long studentId)
	{
		Student student = session.get(Student.class, studentId);
		return student;
	}

	public Student updateStudent(long studentId, Student student)
	{
		session.beginTransaction();
		Student studentOld = session.get(Student.class, studentId);
		if(studentOld != null) {
		studentOld.setScore(student.getScore());
		studentOld.setMobile(student.getMobile());
		studentOld.setPassword(student.getPassword());
		
		session.update(studentOld);
		}
		session.getTransaction().commit();
		return student;
	}
	public Student deleteStudent(long studentId)
	{
		session.beginTransaction();
		Student student = session.get(Student.class, studentId);
		if(student != null)
			session.delete(student);
		session.getTransaction().commit();
		System.out.println("delete ---------------------------------");
		return student;
	}
	
	
}



