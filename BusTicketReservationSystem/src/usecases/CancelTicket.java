package usecases;


import java.util.Scanner;

import dao.CustomerDaoImpl;
import exception.BusException;
import model.Customer;

public class CancelTicket {
	
	public  static void cancelTicket(Customer customer) {

		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Bus Name");
		String busName = sc.nextLine();
		
		CustomerDaoImpl dao = new CustomerDaoImpl();
		try {
			
			int cusId = customer.getCustomerId();
			String message = dao.cancelTicket(busName, cusId);
			
			if (message.equals("Ticket cancelled Successfully")) {
				System.out.println(message);
			}
			else {
				System.out.println(message);
			}
			
		} catch (BusException e) {
			System.out.println(e.getMessage());
		}
	
	}
}