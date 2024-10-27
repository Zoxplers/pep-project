package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService
{
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService()
    {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }


    public Message addMessage(Message message)
    {
        if(message.message_text.isEmpty() ||  accountDAO.findAccount(message.getPosted_by()) != null)
        {
            return null;
        }
        return messageDAO.insertMessage(message);
    }
}
