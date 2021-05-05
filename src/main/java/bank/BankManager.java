package bank;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import customer.BankUI;
import dao.BankDAO;
import exceptions.ItemNotFoundException;
import model.Bank;



//Business Layer
public class BankManager {

	private static final Logger logger = LogManager.getLogger(BankManager.class);
	
	//DAO for persisting data
	private BankDAO dao = new BankDAO();
	
	public void setDAO(BankDAO dao) {
		this.dao = dao;
	}
	
	//returns all items from system @return list of bank objects @throws exception
	
	public ArrayList<Bank> findAll() throws Exception {
		System.out.println("Received findAll request");
		
		//Call DAO
		ArrayList<Bank> list = dao.findAll();
		
		logger.debug("Received data from DB");
		return list;
	}
	
	//returns a bank item for given bank id
	public Bank findById(int bankid) throws ItemNotFoundException, Exception {
		logger.debug("Received findByBankId request: " + bankid);
		
		return dao.findById(bankid);
	}
	
	//saves bank object with data populated in system
	
	public boolean save(Bank bank) throws Exception {
		
		logger.debug("Received save request: " + bank);
		//delegating call to DAO
		return dao.save(bank);							
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
}
