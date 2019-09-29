package modules;

import com.google.inject.AbstractModule;
import dao.AccountDao;
import dao.AccountDaoImpl;
 
import play.Logger;
import service.AccountService;
import service.AccountServiceImpl;
 


public class AccountModule extends AbstractModule{
    private static final Logger.ALogger logger = Logger.of(AccountModule.class);
    @Override
    protected void configure() {
        
        bind(AccountDao.class).to(AccountDaoImpl.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
    }
}
