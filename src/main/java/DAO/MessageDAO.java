package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO
{
    public Message insertMessage(Message message)
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

            preparedStatement = connection.prepareStatement("insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next())
            {
                return new Message(resultSet.getInt(1), message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        if(connection == null)
        {
            return null;
        }
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from message");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Message message = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessage(int messageId)
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

            preparedStatement = connection.prepareStatement("select * from message where message_id = ?;");
            preparedStatement.setInt(1, messageId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int messageId)
    {
        Connection connection = ConnectionUtil.getConnection();
        if(connection == null)
        {
            return null;
        }
        try
        { 
            PreparedStatement preparedStatement;
            Message message = getMessage(messageId);
            int result;

            preparedStatement = connection.prepareStatement("delete from message where message_id = ?;");
            preparedStatement.setInt(1, messageId);
            result = preparedStatement.executeUpdate();

            if (result != 0)
            {
                return message;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(int messageId, String messageText)
    {
        Connection connection = ConnectionUtil.getConnection();
        if(connection == null)
        {
            return null;
        }
        try
        {
            PreparedStatement preparedStatement;
            int result;

            preparedStatement = connection.prepareStatement("update message set message_text = ? where message_id = ?;");
            preparedStatement.setString(1, messageText);
            preparedStatement.setInt(2, messageId);
            result = preparedStatement.executeUpdate();

            if (result != 0)
            {
                return getMessage(messageId);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAccountMessages(int accountId)
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        if(connection == null)
        {
            return null;
        }
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from message where posted_by = ?;");
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Message message = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}