package testbase;

import java.util.*;  
import java.io.*;  
public class Properties_read {

	public static void main(String[] args)throws Exception{ 
		
		
		String FilePath = System.getProperty("user.dir") +"//src//main//resources//db.properties";
		System.out.println(FilePath);
			    FileReader reader=new FileReader(FilePath);  
	      
		
		  Properties p=new Properties();
		 	    p.load(reader);  
	      
	    System.out.println(p.getProperty("user"));  
	    System.out.println(p.getProperty("password"));  
	}  
	}  


