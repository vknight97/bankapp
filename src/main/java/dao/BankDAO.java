package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exceptions.ItemNotFoundException;
import model.Bank;
import util.DBConnection;

//Persistence Layer
public class BankDAO {

	
	private static final Logger logger = LogManager.getLogger(BankDAO.class);
	
	public ArrayList<Bank> findAll() throws Exception {
		
		ArrayList<Bank> bank = new ArrayList<Bank>();
		
		try {
			Connection con = DBConnection.getInstance().getConnection();
			String sql = "SELECT bankid, new_user, account_email, account_password FROM bank";
			
			logger.debug("fetching data", sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int bankid = rs.getInt("bankid");
				String nuser = rs.getString("new_user");
				String bemail = rs.getString("account_email");
				String bpass = rs.getString("account_password");
				
				bank.add(new Bank(bankid, nuser, bemail, bpass));
			}
					
		} catch (SQLException e) {
			logger.error("Unable to perform DB operation", e);
			throw e;
		}
		
		logger.debug("Returning results", bank);
		return bank;
	}
	
	public Bank findById(int bankid) throws ItemNotFoundException, Exception {
		
		Bank bank = null;
		
		Connection con = DBConnection.getInstance().getConnection();
		
		logger.debug("fetching data for bankid: " + bankid);
		
		String sql = "SELECT bankid, new_user, account_email, account_password FROM bank WHERE bankid = ?";
		
		logger.debug(sql);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, bankid);
		
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next() ) {
			String nuser = rs.getString("new_user");
			String bemail = rs.getString("account_email");
			String bpass = rs.getString("account_password");
			
			bank = new Bank(bankid, nuser, bemail, bpass);
		} else {
			throw new ItemNotFoundException();
		}
		
		logger.debug("Returning bank", bank);
		
		return bank;				
	}
	
	public boolean save(Bank bank) throws Exception {
		int inserted = 0;
		
		logger.debug("Received bank data to save: " + bank);
		
		Connection con = DBConnection.getInstance().getConnection();
		
		String sql = "INSERT INTO bank (account_email, account_password) VALUES (?,?)";
		
		logger.debug(sql);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, bank.getEmail());
		pstmt.setString(2, bank.getPassword());
						
		inserted = pstmt.executeUpdate();
		
		logger.debug("Inserted bank: " + inserted);
		
		return inserted != 0;
	}
	
	public boolean updateStatus(int id, boolean newUser) throws Exception {
		
		logger.debug("Received bank new user update: " + newUser);
		
		Connection con = DBConnection.getInstance().getConnection();
		
		String sql = "UPDATE bank SET new_user = ? WHERE id = ?";
		
		logger.debug(sql);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, newUser ? "Y" : "N");
		pstmt.setInt(2, id);
		
		int updated = pstmt.executeUpdate();
		
		logger.debug("Updated todo: " + updated);
		
		return updated != 0;
	}
}
