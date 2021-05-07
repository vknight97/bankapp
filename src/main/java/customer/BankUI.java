package customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bank.BankManager;
import exceptions.ItemNotFoundException;
import model.Bank;
import model.Customer;
import util.ConfigReader;

//User Interface for Bank App
// NEEDS LOGIC AND ADD FUNCTIONS FOR CHECKING AND SAVINGS
//Presentation Layer
public class BankUI {

	private static final Logger logger = LogManager.getLogger(BankUI.class);

	public static void main(String[] args) {

		logger.info("Application Started");

		System.out.println("Welcome to Bank of BigPiggy");

		Scanner input = new Scanner(System.in);

		try {
			run(input);
		} catch (Exception e) {
			if (e instanceof SQLException) {
				System.out.println("ERROR: " + e.getLocalizedMessage());
				System.exit(0);
			}
			logger.error("Unexpected Error", e);
		} finally {
			input.close();
		}

		logger.info("Application Stopped");
	}

	private static void run(Scanner input) throws Exception, ItemNotFoundException {

		// load all app configs
		ConfigReader.getInstance();

		BankManager manager = new BankManager();

		int menuAction = 0;
		do {
			showMainMenu();

			System.out.println("Chose the action: ");
			menuAction = input.nextInt();

			switch (menuAction) {

			case 1: // List Items
				displayBank(manager);
				break;

			case 2: // Add Item to new user Table
				System.out.println("New User? (y/n): ");
				String confirmAdd = input.next();
				
				if ("y".equalsIgnoreCase(confirmAdd)) {

					String nuser = readNuser(input);
					String bemail = readBemail(input);

					// saving data
					manager.save(new Bank(nuser, bemail));
					System.out.println("Item Saved");
				}
				break;
				
			case 3: // Log In
				System.out.println("Log IN (y/n)");
				String confirmLog = input.next();
				displayCustomer(manager);
				if ("y".equalsIgnoreCase(confirmLog)) {
					
					String email = readEmail(input);
					
//					manager.save(new Customer(email));
				}	
				break;
//			case 4: //Make a Deposit
//				System.out.println("Make a Deposit");
//				String 
			case 5: // Update New User Status
				System.out.println("Update User Status: Y/N");
				String confirmUpdate = input.next();
				
				if ("y".equalsIgnoreCase(confirmUpdate)) {
					
					displayBank(manager);
					
					int bankid = readId(input, manager);
					String doneInput = readNuser(input);
					
					manager.update(bankid, doneInput.equalsIgnoreCase("y") ? true : false);
					System.out.println("User Status Changed");
				}
				break;
			case 6: // Exit
				System.out.println("Application Stopped");
				System.exit(0);
				break;
			default:
				break;
			}
		} while (menuAction > 0 && menuAction < 5);
	}

	private static String readNuser(Scanner input) {
		String nuserInput;
		do {
			System.out.println("New User (y/n):");
			nuserInput = input.next();

			if (!("y".equalsIgnoreCase(nuserInput) || "n".equalsIgnoreCase(nuserInput))) {
				nuserInput = null;
				System.out.println("Enter valid input");
			}
		} while (nuserInput == null);
		return nuserInput;
	}
//for update
	private static int readId(Scanner input, BankManager manager) throws Exception {
		int bankid;
		do {
			System.out.println("Enter ID:");
			bankid = input.nextInt();

			if (!isValidId(manager, bankid)) {
				bankid = 0;
				System.out.println("ID not valid");
			}
		} while (bankid == 0);
		return bankid;
	}

//needs regex for email input
	private static String readBemail(Scanner input) {
		String bemailInput;
		do {
			System.out.println("Email Address:");
			bemailInput = input.next();

			if (bemailInput.matches("anything")) {
				bemailInput = null;
				System.out.println("not a valid email address");
			}
		} while (bemailInput == null);
		return bemailInput;
	}
	
	private static String readEmail(Scanner input) {
		String emailInput;
		do {
			System.out.println("Email Address:");
			emailInput = input.next();

			if (emailInput.matches("anything")) {
				emailInput = null;
				System.out.println("not a valid email address");
			}
		} while (emailInput == null);
		return emailInput;
	}
//PASSWORD LOGIC	
	/*
	 * private static String readBpass(Scanner input) { String bpassInput; do {
	 * System.out.println("Password: MUST HAVE AT LEAST 8 Characters"); bpassInput =
	 * input.next();
	 * 
	 * if (bpassInput.matches("%") && bpassInput.length() > 8) { bpassInput = null;
	 * System.out.println("not a valid password"); } } while (bpassInput == null);
	 * return bpassInput; }
	 */

	private static boolean isValidId(BankManager manager, int bankid) throws Exception {
		try {
			manager.findById(bankid);
		} catch (ItemNotFoundException e) {
			return false;
		}
		return true;
	}

	private static void displayBank(BankManager manager) throws Exception {
		List<Bank> bank = manager.findAll();
		printTableFormat(bank);
	}
	
	private static void displayCustomer(BankManager manager) throws Exception {
		List<Customer> customer = manager.findCustomer();
		printTableFormat2(customer);
	}

	private static void showMainMenu() {
		System.out.println();
		System.out.println("1. List Items");
		System.out.println("2. Create a New Account");
		System.out.println("3. Log IN");
		System.out.println("4. Make a Deposit");
		System.out.println("5. Update New User Status");
		System.out.println("6. Exit");
	}

	static void printTableFormat(List<Bank> list) {
		for (Bank bank : list) {
			System.out.println(" | Bank ID: " + bank.getbankid() + " | ");
			System.out.println(" | New User?: " + bank.getNewUser() + " | ");
			System.out.println(" | Email Address: " + bank.getEmail() + " | ");
			System.out.println("--------------------" + "\n");
		}
	}	
	static void printTableFormat2(List<Customer> list2) {
		for (Customer customer : list2) {
			System.out.println(" | Customer ID: " + customer.getCustomerId() + " | ");
			System.out.println(" | Email Address: " + customer.getEmail() + " | ");
			System.out.println(" | Password: " + customer.getPass() + " | ");
			System.out.println(" | Checking: " + customer.getChecking() + " | ");
			System.out.println(" | Saving: " + customer.getSaving() + " | ");
			System.out.println("--------------------" + "\n");
			}
	}

}
