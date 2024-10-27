package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO
{
    public Account getAccountByID(int accountId)
    {
        Connection connection = ConnectionUtil.getConnection();
        if(connection == null)
        {
            return null;
        }
        try
        {
            PreparedStatement preparedStatement;
            ResultSet rs;

            preparedStatement = connection.prepareStatement("select * from account where account_id = ?;");
            preparedStatement.setInt(1, accountId);
            rs = preparedStatement.executeQuery();

            return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        if(connection == null)
        {
            return null;
        }
        try
        {
            PreparedStatement preparedStatement;
            ResultSet rs;

            preparedStatement = connection.prepareStatement("insert into account (?, ?) values (?, ?);");
            preparedStatement.setString(1, "username");
            preparedStatement.setString(2, "password");
            preparedStatement.setString(3, account.getUsername());
            preparedStatement.setString(4, account.getPassword());
            preparedStatement.executeUpdate();

            rs = preparedStatement.getGeneratedKeys();

            return new Account(rs.getInt(1), account.getUsername(), account.getPassword());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
