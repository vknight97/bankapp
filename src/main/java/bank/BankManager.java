package bank;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import customer.BankUI;
import dao.BankDAO;
import dao.CustomerDAO;
import exceptions.ItemNotFoundException;
import model.Bank;
import model.Customer;



//Business Layer
public class BankManager {

	private static final Logger logger = LogManager.getLogger(BankManager.class);
	
	//bank DAO for persisting data
	private BankDAO dao = new BankDAO();
	
	public void setDAO(BankDAO dao) {
		this.dao = dao;
	}
	
	
	//customer dao
	private CustomerDAO daotoo = new CustomerDAO();
	
	public void setDAO(CustomerDAO daotoo) {
		this.daotoo = daotoo;
	}
	
	//returns all items from system @return list of bank objects @throws exception
	
	public ArrayList<Bank> findAll() throws Exception {
		System.out.println("Received findAll request");
		
		//Call DAO
		ArrayList<Bank> list = dao.findAll();
		
		logger.debug("Received data from DB");
		return list;
	}
	
	//returns all items from customer
	
	public ArrayList<Customer> findCustomer() throws Exception {
		System.out.println("Received findCustomer request");
		
		//call dao
		ArrayList<Customer> list2 = daotoo.findCustomer();
		
		logger.debug("Received data from DB");
		return list2;
	}
	
	
	
	//returns a bank item for given bank id
	public Bank findById(int bankid) throws ItemNotFoundException, Exception {
		logger.debug("Received findByBankId request: " + bankid);
		
		return dao.findById(bankid);
	}
	
	//returns a customer item for a given customer id
	public Customer findByCustomerId(int customerid) throws ItemNotFoundException, Exception {
		logger.debug("Received findByCustomerId request: " +customerid);
		
		return daotoo.findByCustomerId(customerid);
	}
	
	//saves bank object with data populated in system
	
	public boolean save(Bank bank) throws Exception {
		
		logger.debug("Received save request: " + bank);
		//delegating call to DAO
		return dao.save(bank);							
	}
	
	//saves customer object with data populated in system
	
	public boolean save(Customer customer) throws Exception {
		
		logger.debug("Received save request: " + customer);
		return daotoo.save(customer);
	}
	
	//updates bank item status for given id
	
	public boolean update(int bankid, boolean nuser) throws ItemNotFoundException, Exception {
		
		logger.debug("Received update request");
		
		try {
			dao.findById(bankid);		
		} catch (ItemNotFoundException e) {
			logger.warn("Item entered is not correct", e);
			throw e;
		}
		
		dao.updateStatus(bankid, nuser);
		
		return false;
	}
	
	//updates customer checking for given id
	
	public boolean updateChecking(int customerid, int checking) throws Exception {
		
		logger.debug("Received checking update request ");
		
		try {
			daotoo.findByCustomerId(customerid);
		} catch (ItemNotFoundException e) {
			logger.warn("Item entered is not correct", e);
			throw e;
		}
		
		daotoo.updateChecking(customerid, checking);
		
		return false;
	}
	
	//updates customer saving for given id
	
	public boolean updateSaving(int customerid, int saving) throws Exception {
		
		logger.debug("Received checking update request ");
		
		try {
			daotoo.findByCustomerId(customerid);
		} catch (ItemNotFoundException e) {
			logger.warn("Item entered is not correct", e);
			throw e;
		}
		
		daotoo.updateChecking(customerid, saving);
		
		return false;
	}
}
