package sms;

import java.awt.Frame;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/*
 * The main Function of this API file is to communicate with database
 */

//For connection with database
//Constructor is a function which is called when object of that class is created
//The name of constructor should be same as that of class.
public class dbCon {
	private Connection conn = null; //Connection is a class defined in jar file which is added(mysql-connector.... .jar)
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	final private String user = "root";
	final private String passwd = "";
	
	public dbCon(){
		getConnection();
	}
	
	/*
	 * This function is used to establish connection with database.
	 * It sets the conn variable to be used in future
	 **/
	public void getConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sms_db",user,passwd);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	//---------------------------------------------LOGIN------------------------------------//
	
	/**
	 * This function is used to verify username and password.
	 * 
	 * @param username, password
	 * @return true/false
	 * */
	public boolean verifyCredentials(String username, String password) {
		try {
			statement = conn.createStatement();
			//username must be unique
			resultSet = statement.executeQuery("select password from login where name='" + username+"'");
			while(resultSet.next()){
				if (resultSet.getString(1).equals(password)){
					return true;
				}else {
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkUsername(String username) {
		try {
			int count = 0;
			statement = conn.createStatement();
			resultSet = statement.executeQuery("select COUNT(*) from login where name='" + username+"'");
			while(resultSet.next()){
				count++;
				if (count > 1)
					return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean addCredentials(String username, String password) {
		try {
			String query = "insert into login(name,password) " + " values (?, ?)";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.execute();
			System.out.println("User details added");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//------------------------------------ITEMS-----------------------------------------//
	
	public ArrayList<String> getAllItemDetails() {
		ArrayList<String> items = new ArrayList<>();
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery("select * from items");
			
			while(resultSet.next()){
				items.add(resultSet.getInt(1)+"");
				items.add(resultSet.getString(2));
				items.add(resultSet.getInt(3)+"");
				items.add(resultSet.getInt(4)+"");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public boolean checkItemName(String name) {
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery("Select * from items where name='"+name+"'");
			if (resultSet.next()){
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void addItem(String name, String quantity, String price){
		try{
			String query = "insert into items(name,quantity, price) " + " values (?, ?, ?)";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, quantity);
			preparedStatement.setString(3, price);
			preparedStatement.execute();
			System.out.println("Item insert done");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateItem(int index, int value){
		try{
			statement = conn.createStatement();
			statement.executeUpdate("Update items set quantity="+ value + " where id="+index);
			System.out.println("Item update done");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//-------------------------------------SELL--------------------------------------
	public void addToSellT(String date, String name, int quan, int price ){
		try{
			String query = "insert into sell(date, name,quantity, price) " + " values (?, ?, ?, ?)";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, date);
			preparedStatement.setString(2, name);
			preparedStatement.setInt(3, quan);
			preparedStatement.setInt(4, price);
			preparedStatement.execute();
			System.out.println("sell insert done");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getAllSellData(){
		ArrayList<String> data = new ArrayList<>();
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery("select * from sell");
			
			while(resultSet.next()){
				data.add(resultSet.getInt(1)+"");
				data.add(resultSet.getString(2));
				data.add(resultSet.getString(3));
				data.add(resultSet.getInt(4)+"");
				data.add(resultSet.getInt(5)+"");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;	
	}
	
	//-------------------------------------PURCHASE--------------------------------------
		public void addToPurT(String date, String name, int quan, int price ){
			try{
				String query = "insert into purchase(date, name,quantity, price) " + " values (?, ?, ?, ?)";
				preparedStatement = conn.prepareStatement(query);
				preparedStatement.setString(1, date);
				preparedStatement.setString(2, name);
				preparedStatement.setInt(3, quan);
				preparedStatement.setInt(4, price);
				preparedStatement.execute();
				System.out.println("sell insert done");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public ArrayList<String> getAllPurData(){
			ArrayList<String> data = new ArrayList<>();
			try {
				statement = conn.createStatement();
				resultSet = statement.executeQuery("select * from purchase");
				
				while(resultSet.next()){
					data.add(resultSet.getInt(1)+"");
					data.add(resultSet.getString(2));
					data.add(resultSet.getString(3));
					data.add(resultSet.getInt(4)+"");
					data.add(resultSet.getInt(5)+"");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return data;	
		}
}
