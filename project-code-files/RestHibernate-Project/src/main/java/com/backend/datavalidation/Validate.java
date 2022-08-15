package com.backend.datavalidation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Validate {
	private static Integer validateInteger(String number)
	{
		try {return Integer.parseInt(number);}
		catch(Exception e){ }
		return null;
	}
	
	private static Float validateFloat(String number)
	{
		try {return Float.parseFloat(number);}
		catch(Exception e) { }
		return null;
	}
	
	private static Double validateDouble(String number)
	{
		try {return Double.parseDouble(number);}
		catch(Exception e) { }
		return null;
	}
	
	private static Date validateDate(String dt)
	{
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
			return format.parse(dt);
		}
		catch(Exception e) { }
		return null;
	}
	
	public static Object validate(String field, Validator val)
	{
		switch(val)
		{
		case INTEGER:
			return validateInteger(field);
			
		case FLOAT:
			return validateFloat(field);
			
		case DOUBLE:
			return validateDouble(field);
			
		case DATE:
			return validateDate(field);
			
		}
		return null;
	}
}
