package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.BusException;
import exception.CustomerException;
import model.Customer;
import utility.DatabaseConnection;


public class CustomerDaoImpl implements CustomerDao{

	@Override
	public String cusSignUp(String username, String password, String firstName, String lastName, String address,
			String mobile) {
		
		String message = "Sign up Failed";
		
		try(Connection conn = DatabaseConnection.provideConnection()){
			
			PreparedStatement ps =  conn.prepareStatement("insert into customer(username, password, firstName, lastName, address, mobile) values (?,?,?,?,?,?)");
			
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3,  firstName);
			ps.setString(4,  lastName);
			ps.setString(5,  address);
			ps.setString(6,  mobile);
			
			int x = ps.executeUpdate();
			
			if (x > 0) message = "Sign up Successfull";
			
		}
		catch (SQLException e) {
			message = e.getMessage();
		}
		
		return message;
	}

	public String cusSignUp1(Customer customer) {
		
		String message = "Sign up Failed";
		
		try(Connection conn = DatabaseConnection.provideConnection()){
			
			PreparedStatement ps =  conn.prepareStatement("insert into customer(username, password, firstName, lastName, address, mobile) values (?,?,?,?,?,?)");
			
			ps.setString(1, customer.getUsername());
			ps.setString(2, customer.getPassword());
			ps.setString(3, customer.getFirstName());
			ps.setString(4, customer.getLastName());
			ps.setString(5, customer.getAddress());
			ps.setString(6, customer.getMobile());
			
			int x = ps.executeUpdate();
			
			if (x > 0) message = "Sign up Successfull";
			
		}
		catch (SQLException e) {
			message = e.getMessage();
		}
		
		return message;
		
	}

	@Override
	public Customer cusLogin(String username, String password) throws CustomerException {
		
		Customer customer = null;
		
		try (Connection conn = DatabaseConnection.provideConnection()){
			
			PreparedStatement ps = conn.prepareStatement("select * from customer where username = ? and password = ?");
			
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs =  ps.executeQuery();
			
			if (rs.next()) {
				int customerId = rs.getInt("customerId");
				String usernamee =  rs.getString("username");
				String passwordd = rs.getString("password");
				String firstName = rs.getString("firstName");		
				String lastName = rs.getString("lastName");			
				String address = rs.getString("address");			
				String mobile = rs.getString("mobile");
				
				customer = new Customer(customerId,usernamee, passwordd, firstName, lastName, address, mobile);
				
			}
			else {
				throw new CustomerException("Invalid username or password");
				
			}
			
		}
		catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}
		
		return customer;
	}

	@Override
	public String bookTicket(String busName, int customerId, int num) throws BusException {
	
		String message = "Ticket Booking fail";
		
		try (Connection conn = DatabaseConnection.provideConnection()){
			
			PreparedStatement ps = conn.prepareStatement("select * from bus where busName = ?");
			ps.setString(1, busName);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				
				int busNo = rs.getInt("busNo");
				int totalSeats = rs.getInt("totalSeats");
				int availSeats = rs.getInt("availSeats");
				Date departure = rs.getDate("departure");
				int fare = rs.getInt("fare");
				
				PreparedStatement stmt = conn.prepareStatement("select datediff(?,current_date()) as date");
				stmt.setDate(1, departure);
				
				ResultSet rs1 = stmt.executeQuery();
				int days = 0;
				if (rs1.next()) {
					days = rs1.getInt("date");
				}
				
				if (days <= 0) {
					throw new BusException("Booking is not available for this date");
				}
				else if (availSeats >= num) {
					int seatFrom = totalSeats - availSeats + 1;
					int seatTo = seatFrom + num -1;
					fare = fare * num;
					
					PreparedStatement ps2 = conn.prepareStatement("insert into booking(customerId, busNo, seatFrom, seatTo) values (?, ?, ?, ?)");
					ps2.setInt(1, customerId);
					ps2.setInt(2, busNo);
					ps2.setInt(3, seatFrom);
					ps2.setInt(4, seatTo);
					
					int x = ps2.executeUpdate();

					if (x > 0) {
						
						PreparedStatement ps3 = conn.prepareStatement("update bus set availseats = ? where busNo = ?");
						availSeats = availSeats - num;
						ps3.setInt(1, availSeats);
						ps3.setInt(2, busNo);
						int y = ps3.executeUpdate();
						
						if (y <= 0) throw new BusException("Available Seat is not updated");
						
						
						System.out.println("--------------------------------------------" + "\n"
																   + "Customer Id is : " + customerId + "\n"
																   + "Bus No is : " + busNo + "\n"
																   + "Seat No is from : " + seatFrom + " to " + seatTo + "\n"
																   + "Bus fare is : " + fare + "\n"
																   + "Booking yet to be confirm by Admin" + "\n" 
																   + "---------------------------------------------");
						
						 message = "Ticket Booked Successfully";
					}
				
				}
	
			}
			else {
				throw new BusException("Bus with name " + busName + " is not available");
			}
			
		}
		catch (SQLException e) {
			throw new BusException(e.getMessage());
		}
		
		return message;
	}

	@Override
	public String cancelTicket(String busName, int customerId) throws BusException {
		String message = "Ticket cancellation failed";
		
		try (Connection conn = DatabaseConnection.provideConnection()){
				
				PreparedStatement ps = conn.prepareStatement("select * from bus where busName = ?");
				ps.setString(1, busName);
				
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
					
					int busNo = rs.getInt("busNo");
					int availSeats = rs.getInt("availSeats");
					
					PreparedStatement ps1 = conn.prepareStatement("select * from booking where busNo = ? and customerId = ?");
					ps1.setInt(1, busNo);
					ps1.setInt(2, customerId);
					
					ResultSet rs1 = ps1.executeQuery();
					boolean flag = false;
					int count = 0;
					
					while (rs1.next()) {
						flag = true;
						int seatFrom = rs1.getInt("seatFrom");
						int seatTo = rs1.getInt("seatTo");
						count += seatTo - seatFrom + 1;
					}
					
				    if (flag) {
						
						PreparedStatement ps2 = conn.prepareStatement("delete from booking where busNo = ? and customerId = ?");
						ps2.setInt(1, busNo);
						ps2.setInt(2, customerId);
						
						int x = ps2.executeUpdate();
						if (x > 0) {
							
							PreparedStatement ps3 = conn.prepareStatement("update bus set availseats = ? where busNo = ?");
							availSeats = availSeats + count;
							ps3.setInt(1, availSeats);
							ps3.setInt(2, busNo);
							int y = ps3.executeUpdate();
							
							if (y <= 0) throw new BusException("Available Seat is not updated");
							
							 message = "Ticket cancelled Successfully";
						}
					
					}
				    else message = "No booking found";
		
				}
				else {
					throw new BusException("Bus with name " + busName + " is not available");
				}
				
			}
			catch (SQLException e) {
				throw new BusException(e.getMessage());
			}
		
		return message;
	
	}

	@Override
	public void viewTicket(int customerId) {
		boolean flag = false;
		
		try(Connection conn = DatabaseConnection.provideConnection()){
			PreparedStatement ps1 = conn.prepareStatement("select * from booking where customerId = ?");
			ps1.setInt(1, customerId);
			
			ResultSet rs1 = ps1.executeQuery();
			
			while (rs1.next()) {
				flag = true;
				System.out.println("---------------------------------------------");
				System.out.println("Bus Id : " + rs1.getInt("bId"));
				System.out.println("Bus No : " + rs1.getInt("busNo"));
				System.out.println("Total tickets : " + (rs1.getByte("seatTo") - rs1.getInt("seatFrom") + 1));
				if (rs1.getBoolean("status")) System.out.println("Status : Booked");
				else System.out.println("Status : Pending");
				
				System.out.println("----------------------------------------------" );
			}
			
			if (flag == false) System.out.println("No tickets found");
		}
		catch (SQLException s){
			System.out.println(s.getMessage());
		}
		
	}

	@Override
	public String cusSignUp(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}