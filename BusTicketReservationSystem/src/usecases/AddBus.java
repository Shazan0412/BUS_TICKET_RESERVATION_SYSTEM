package usecases;


import java.util.InputMismatchException;
import java.util.Scanner;
import dao.AdminDaoImpl;
import model.Bus;

public class AddBus{

	public static void AddBus() {

		Scanner sc = new Scanner(System.in);
		
		try {
			
			System.out.println("Enter Bus number");
			int busNo = sc.nextInt();
			
			System.out.println("Enter Bus name");
			String busName = sc.next();
			
			System.out.println("Enter Route from");
			String routeFrom = sc.next();
			
			System.out.println("Enter Route To");
			String routeTo = sc.next();
			
			System.out.println("Enter Bus Type - AC / NonAC");
			String busType = sc.next();
			
			sc.nextLine();
			System.out.println("Enter Departure date and time in format (YYYY-MM-DD HH:MI:SS)");
			String departure = sc.nextLine();
			
			System.out.println("Enter Arrival date and time in format (YYYY-MM-DD HH:MI:SS)");
			String arrival = sc.nextLine();
			
			System.out.println("Enter Total Seats");
			int totalSeats = sc.nextInt();
			
			System.out.println("Enter Available Seats");
			int availSeats = sc.nextInt();
			
			System.out.println("Enter fare");
			int fare = sc.nextInt();
			
			Bus bus = new Bus(busNo, busName, routeFrom, routeTo, busType, departure, arrival, totalSeats, availSeats, fare);
			
			AdminDaoImpl dao = new AdminDaoImpl();
			
			String result = dao.addBus(busNo, busName, routeFrom, routeTo, busType, arrival, departure, totalSeats, availSeats, fare);
			
			if (result.equals("Bus added Successfully")) {
				System.out.println(result);
			}
			else {
				System.out.println(result);
			}
		}
		catch (InputMismatchException e) {
			System.out.println("Invalid input");
		}
	}
}