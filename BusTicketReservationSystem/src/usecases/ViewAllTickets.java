package usecases;

import dao.AdminDaoImpl;

public class ViewAllTickets {

public static void viewAllTicket() {
		
		AdminDaoImpl dao = new AdminDaoImpl();
		dao.viewAllTickets();
	}
}