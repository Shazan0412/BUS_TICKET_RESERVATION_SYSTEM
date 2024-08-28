package usecases;


import java.util.InputMismatchException;
import java.util.Scanner;

import dao.CustomerDaoImpl;
import exception.BusException;
import model.Customer;

public class BookTicket {

	public static void BookTicket(Customer customer) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Bus Name");
		String busName = sc.next();
		
		CustomerDaoImpl dao = new CustomerDaoImpl();
		try {
			System.out.println("Enter no. of Tickets to Book");
			int num = sc.nextInt();
			
			int customerId = customer.getCustomerId();
			String message = dao.bookTicket(busName, customerId, num);
			
			if (message.equals("Ticket Booked Successfully")) {
				System.out.println(message);
			}
			else {
				System.out.println(message);
			}
			
		} catch (BusException e) {
			System.out.println(e.getMessage());
		}
		catch (InputMismatchException e) {
			System.out.println("Invalid input");
		}
		
	}
}