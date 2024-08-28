package usecases;


import java.util.Scanner;

import dao.AdminDaoImpl;

public class Admin {

	public static boolean AdminLogin() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter username");
		String username = sc.next();
		
		System.out.println("Enter password");
		String password = sc.next();
		
		AdminDaoImpl dao = new AdminDaoImpl();
		String result =  dao.adminLogin(username, password);
		
		if (result.equals("Login Successfull")){
			System.out.println(result);
			return true;
		}
		else {
			System.out.println(result);
			return false;
		}

	}

}