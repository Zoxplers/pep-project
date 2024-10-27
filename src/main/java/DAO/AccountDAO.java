package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO
{

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
            ResultSet resultSet;

            preparedStatement = connection.prepareStatement("insert into account (username, password) values (?, ?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next())
            {
                return new Account(resultSet.getInt(1), account.getUsername(), account.getPassword());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account findAccount(String username, String password)
    {
        Connection connection = ConnectionUtil.getConnection();
        if(connection == null)
        {
            return null;
        }
        try
        {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            
            preparedStatement = connection.prepareStatement("select account.account_id from account where account.username = ? and account.password = ?;");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return new Account(resultSet.getInt("account_id"), username, password);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account findAccount(int accountId)
    {
        Connection connection = ConnectionUtil.getConnection();
        if(connection == null)
        {
            return null;
        }
        try
        {
            PreparedStatement preparedStatement;
            ResultSet resultSet;

            preparedStatement = connection.prepareStatement("select * from account where account_id = ?;");
            preparedStatement.setInt(1, accountId);
            resultSet = preparedStatement.executeQuery();

            return new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
