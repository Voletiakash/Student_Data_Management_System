package com.dsa.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {
    private static final String URL = "jdbc:mysql://localhost:3306/sms";
    private static final String USER = "root";
    private static final String PASSKEY = "root";
    
    private static Connection getConnection() throws SQLException {
    	return DriverManager.getConnection(URL,USER,PASSKEY);
    }
    private static void readData(Connection con) throws SQLException {
    	String query="select * from sms.smsystem";
    	try(PreparedStatement ps=con.prepareStatement(query);ResultSet rs=ps.executeQuery();){
    		 System.out.println("\n--- Student List ---");
             while (rs.next()) {
                 System.out.println("Name : " + rs.getString("name") + ", Age : " + rs.getInt("age") +
                         ", Skill : " + rs.getString("skills")+",ID : " + rs.getInt("id"));
             }
    	}	
    }
    //to add data
    private static void addData(Connection con) throws SQLException {
    	@SuppressWarnings("resource")
		Scanner input=new Scanner(System.in);
    	System.out.println("enter the name to be added :");
    	String name=input.nextLine();
    	System.out.println("enter age of the candidate :");
    	int age=input.nextInt();
    	input.nextLine();
    	System.out.println("enter the candidate skill :");
    	String skills=input.nextLine();
    	System.out.println("enter the id :");
    	int id=input.nextInt();
    	String queryAdd="INSERT INTO sms.smsystem(name,age,skills,id) VALUES(?,?,?,?)";
    	try(PreparedStatement ps=con.prepareStatement(queryAdd)){
    		ps.setString(1,name);
    		ps.setInt(2,age);
    		ps.setString(3,skills);
    		ps.setInt(4, id);
    		ps.executeUpdate();
    		System.out.println("Data added sucessfully...");	
    	}
    }
    //update the data
    private static void updateData(Connection con) throws SQLException {
    	try (Scanner input = new Scanner(System.in)) {
			System.out.println("enter the updated name :");
			String name=input.nextLine();
			System.out.println("enter the updated age :");
			int age =input.nextInt();
			input.nextLine();
			System.out.println("enter the updated skills :");
			String skills=input.nextLine();
			System.out.println("enter the id :");
			int id=input.nextInt();
			String query="update sms.smsystem set name=?, age=?, skills=? where id=?";
			try(PreparedStatement ps=con.prepareStatement(query)){
				ps.setString(1,name);
				ps.setInt(2, age);
				ps.setString(3, skills);
				ps.setInt(4, id);
				ps.executeUpdate();
				System.out.println("Data updated sucessfully...");
				}
		}
    	}
    //to add a new column
    private static void deleteData(Connection con) throws SQLException {
    	try (Scanner input = new Scanner(System.in)) {
			System.out.println("enter id details to be deleted :");
			int id=input.nextInt();
			String query="delete from sms.smsystem where id=?";
			try(PreparedStatement ps=con.prepareStatement(query)){
				ps.setInt(1,id);
				ps.executeUpdate();
				System.out.println("Data deleted sucessfully...");			
			}
	}
    }
    public static void main(String[] args) throws SQLException {
    	
    	try(Connection con=getConnection();
				Scanner input = new Scanner(System.in)){
    		while(true) {
    			System.out.println("|| STUDENT DATA MANAGEMENT ||");
    			System.out.println("1. Read the Data :");
    			System.out.println("2. Add the Data :");
    			System.out.println("3. Update the Data :");
    			System.out.println("4. Delete the Data :");
    			System.out.println("5. Close");
    			int choose=input.nextInt();
    			switch(choose) {
    			case 1->readData(con);
    			case 2->addData(con);
    			case 3->updateData(con);
    			case 4->deleteData(con);
    			case 5->{
    				System.out.println("Closeing...");
    				return ;
    			}
				default -> System.out.println("choose the correct path!...");
    			}			
    		}
    	}
    }
}
