package com.backend.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonProperty;

import com.backend.datavalidation.Validate;
import com.backend.datavalidation.Validator;

@Entity
@Table
public class Student extends User implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long studentId;
	
	@JsonProperty("first_name")
	private String firstname;
	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("mobile")
	private String mobile;
	@JsonProperty("department")
	private String department;
	@JsonProperty("id")
	private String id;
	@JsonProperty("score_gpa")
	private double score;
	@JsonProperty("date_birth")
	private Date dateOfBirth;
	@JsonProperty("date_admission")
	private Date dateOfAdmission;
	@JsonProperty("Admission_type")
	private String admissionType;
	@JsonProperty("fee_Amount")
	private float fee;
	
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(String userEmail, String password) {
		super(userEmail, password);
		// TODO Auto-generated constructor stub
	}
	public Student(User user) {
		super(user);
		// TODO Auto-generated constructor stub
	}
	public Student(String userEmail, String password, String firstname, String lastName, String mobile,
			String department, String id, double score, Date dateOfBirth, Date dateOfAdmission, String admissionType,
			float fee) {
		super(userEmail, password);
		this.firstname = firstname;
		this.lastName = lastName;
		this.mobile = mobile;
		this.department = department;
		this.id = id;
		this.score = score;
		this.dateOfBirth = dateOfBirth;
		this.dateOfAdmission = dateOfAdmission;
		this.admissionType = admissionType;
		this.fee = fee;
	}
	public Student(User user, String firstname, String lastName, String mobile,
			String department, String id, double score, Date dateOfBirth, Date dateOfAdmission, String admissionType,
			float fee) {
		super(user);
		this.firstname = firstname;
		this.lastName = lastName;
		this.mobile = mobile;
		this.department = department;
		this.id = id;
		this.score = score;
		this.dateOfBirth = dateOfBirth;
		this.dateOfAdmission = dateOfAdmission;
		this.admissionType = admissionType;
		this.fee = fee;
	}
	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		super.setPassword(password);
	}	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Date getDateOfAdmission() {
		return dateOfAdmission;
	}
	public void setDateOfAdmission(Date dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}
	public String getAdmissionType() {
		return admissionType;
	}
	public void setAdmissionType(String admissionType) {
		this.admissionType = admissionType;
	}
	public float getFee() {
		return fee;
	}
	public void setFee(float fee) {
		this.fee = fee;
	}
	
	
	public long getStudentId() {
		return studentId;
	}
	@Access(AccessType.PROPERTY)
	  @Column(name = "email")
	public String getUserEmail() {
		return super.getUserEmail();
	}
	@Access(AccessType.PROPERTY)
	  @Column(name = "password")
	public String getPassword() {
		return super.getPassword();
	}
	
	@Override
	public String toString() {
		
		return this.getUserEmail()+" "+ this.getStudentId()+" "+this.getPassword()+this.getFirstname()+" "+this.getLastName()+" "+this.getScore();
	}
	@Override
	public boolean equals(Object s) {
		Student student = (Student) s;
		// Add logic to check if they are equal
		
		if(
			this.getAdmissionType().equals(student.getAdmissionType()) &&
			this.getDateOfAdmission()==student.getDateOfAdmission() &&
			this.getDateOfBirth() == student.getDateOfBirth() &&
			this.getDepartment().equals(student.getDepartment()) &&
			this.getFee() == student.getFee() &&
			this.getFirstname().equals(student.getFirstname()) &&
			this.getId().equals(student.getId()) &&
			this.getLastName().equals(student.getLastName()) &&
			this.getMobile().equals(student.getMobile()) &&
			this.getUserEmail().equals(student.getUserEmail())
				)
			return true;
		return false;
	}
	public static Student validateFields(String[] fields)
	{
		if(fields.length==0)
			return null;
		SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
		Student student=null;
		String fname,lname,mobile,dept,id,adm,email,pwd;
		Double score;
		Float fee;
		Date dob,doa;
		boolean valid = true;
		try 
		{
			fname = fields[0];
			lname = fields[1];
			mobile = fields[2];
			dept=fields[3];
			id = fields[4];
			score = (Double) Validate.validate(fields[5], Validator.DOUBLE);
			if(score==null)
			{
				valid = false;
				System.out.print("SCORE error ");
			}
			dob = (Date) Validate.validate(fields[6],Validator.DATE);
			if(dob==null)
			{
				valid = false;
				System.out.print("DOB error ");
			}
			doa = (Date) Validate.validate(fields[7],Validator.DATE);
			if(doa==null)
			{
				valid = false;
				System.out.print("DOA error ");
			}
			adm = fields[8];
			fee = (Float) Validate.validate(fields[9], Validator.FLOAT);
			if(fee==null)
			{
				valid = false;
				System.out.print("Fee error ");
			}
			email = fields[10];
			pwd= fields[11];
		
			if(valid)
			{
				student = new Student(email,pwd,fname,lname,mobile,dept,id,score,dob,doa,adm,fee);
			}
		
		} catch(Exception e){
			e.printStackTrace();
		}
		return student;
	}

}
