package modules;

import com.google.inject.AbstractModule;

import dao.AccountDao;
import dao.AccountDaoImpl;
import dao.H2DbConnection;
import play.Logger;
import service.AccountService;
import service.AccountServiceImpl;
 


public class AccountModule extends AbstractModule{
    private static final Logger.ALogger logger = Logger.of(AccountModule.class);
    
    /*
	 *  eagerly configuring AccountDao, AccountService and in memory database at the time 
	 *  of application startup
	 *  Bind dao, service and h2 db as an eager singleton
	 */	
    @Override
    protected void configure() {
        
        bind(AccountDao.class).to(AccountDaoImpl.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
        H2DbConnection.populateTestData();
    }
}
