package usecases;

import java.util.InputMismatchException;
import java.util.Scanner;

import dao.CustomerDaoImpl;
import exception.CustomerException;
import model.Customer;

public class CutomerLogin {

	public static Customer CusLogin() {
		
		Customer customer = null;
		
		try {
			
			Scanner sc = new Scanner (System.in);
			
			System.out.println("Enter Username");
			String username = sc.next();
			
			System.out.println("Enter Password");
			String password = sc.next();
			
			CustomerDaoImpl dao = new CustomerDaoImpl();
			
			try {
				customer = dao.cusLogin(username, password);
				
				System.out.println("Welcome " + customer.getFirstName() + " " + customer.getLastName());
			} catch (CustomerException e) {
				
				System.out.println(e.getMessage());
			}
		}
		catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
		
		return customer;

	}

}