package dao;

import model.Bus;

public interface AdminDao {
	
	public final String username = "Admin";
	
	public final String password = "1111";

	public String adminLogin(String username, String password);

	public String addBus(int busNo, String busName, String routeFrom, String routeTo, String busType, String arrival, String departure,
			int totalSeats, int availSeats, int fare);
	
	public String addBus(Bus bus);
	
	public String updateStatus(int customerId);
	
	public void viewAllTickets();

	String addBuses(Bus bus);
}