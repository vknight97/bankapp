package professional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import bank.BankManager;
import dao.BankDAO;
import model.Bank;

public class BankManagerTest {

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void findAll() {
		BankManager manager = new BankManager();
		
		BankDAO daoMock = mock(BankDAO.class);
		
		ArrayList<Bank> mockReturnObjs = new ArrayList<Bank>();
		try {
			when(daoMock.findAll()).thenReturn(mockReturnObjs);
			
			manager.setDAO(daoMock);
			
			ArrayList<Bank> bank = manager.findAll();
			
			assertNotNull(bank);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
