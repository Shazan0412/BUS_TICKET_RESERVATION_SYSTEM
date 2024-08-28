package usecases;

import dao.CustomerDaoImpl;
import model.Customer;

public class ViewTicket {

	public static void viewTicket(Customer customer) {
		
		CustomerDaoImpl dao = new CustomerDaoImpl();
		
		dao.viewTicket(customer.getCustomerId());
	}
}