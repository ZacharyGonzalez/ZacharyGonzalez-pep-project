package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO=accountDAO;
    }
    /**
     * Criteria To register an account, is not fail-safe but rather fail-open for DB convenience
     */
    private boolean registerAccountCriteria(Account account){

        String usernameExists = account.getUsername();

        if(account.getUsername().contentEquals("") || account.getPassword().length()<4){
            return false;
        }
        return accountDAO.getAccountByUsername(usernameExists) == null;
    }

    /**
     * Login account only needs username & password
     */
    public Account LoginAccount(Account account){
        return accountDAO.loginAccount(account);
    }

    /**
     * Register account checks if username is blank, and if password is less than 4 characters.
     * Also checks for any existing account username in database.
     */
    public Account RegisterAccount(Account account){
        if(registerAccountCriteria(account)==false){
            return null;
        }
        return accountDAO.RegisterAccount(account);
    }
}
