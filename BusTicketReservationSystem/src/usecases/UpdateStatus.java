package usecases;

import java.util.Scanner;
import dao.AdminDaoImpl;


public class UpdateStatus {
	
	public static void updateStatus() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter customer Id");
		int customerId = sc.nextInt();
		
		AdminDaoImpl dao = new AdminDaoImpl();
		
		String result = dao.updateStatus(customerId);
		boolean flag = true;
		
		for (int i = 0; i < result.length(); i++) {
			if (result.charAt(i) == 'n') flag = false;
		}
	
		if (flag) System.out.println(result);
		else System.out.println(result);
		
	}

}