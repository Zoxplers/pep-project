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
        return accountDAO.insertAccount(account);
    }
}
