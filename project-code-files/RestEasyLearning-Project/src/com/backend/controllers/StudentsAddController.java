package com.backend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.converters.StudentCsvToJson;

@WebServlet("/addStudentsFromCsv")
public class StudentsAddController extends HttpServlet
{
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		// Call StudentsCSV 
		System.out.println(request.getParameter("filePath"));
		
		//Accept the file and save it to server and start processing
		
		//StudentCsvToJson.csvToJson(request.getParameter("filePath"));
	}
}