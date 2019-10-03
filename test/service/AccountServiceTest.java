package service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.AccountDao;
import dao.H2DbConnection;
import model.Account;
import model.Transaction;
import play.libs.Json;
import service.AccountService;
import service.AccountServiceImpl;

public class AccountServiceTest  {
	
	
	private AccountService accountService;
    private AccountDao accountDao;
    
    @Before
    public void init() {
        accountDao = mock(AccountDao.class);         
        accountService = new AccountServiceImpl(accountDao);

    }
    
    @BeforeClass
	public static void setup() {
		 
    	H2DbConnection.populateTestData();
	}
    
    @Test
    public void testTransferAmount() throws Exception {
    	
    	String jsonString = "{\n" + 
    			" \"fromAccountId\": 102,\n" + 
    			" \"toAccountId\": 103,  \n" + 
    			" \"amount\": 100.00\n" + 
    			"}";
    	 
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(jsonString);
        
        System.out.println("json="+json);
     
    	Optional<Account> accountData = accountService.transferAmount(Json.fromJson(json, Transaction.class));
    	
    	System.out.println("acc="+accountData.map(row -> row));
    	assertTrue(accountData.isPresent());
    	
    	
        
         
    }


}
