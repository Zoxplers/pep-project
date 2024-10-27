package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService
{
    AccountDAO accountDAO;

    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account)
    {
        if(account.getPassword().length() < 4 || account.getUsername().isEmpty())
        {
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    public Account attemptLogin(String username, String password)
    {
        return accountDAO.findAccount(username, password);
    }
}
