package dao;

import exception.BusException;
import exception.CustomerException;
import model.Customer;

public interface CustomerDao {

	public String cusSignUp (String username, String password, String firstName, String lastName, String address, String mobile) ;
	
	public String cusSignUp (Customer customer);
	
	public Customer cusLogin (String username, String password) throws CustomerException;
	
	public String bookTicket (String busName, int cusId, int num) throws BusException;
	
	public String cancelTicket(String busName, int cusId) throws BusException;
	
	public void viewTicket(int cusId);
}