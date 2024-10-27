package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController
{
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController()
    {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI()
    {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("accounts/{account_id}/messages", this::getAccountMessagesHandler);
        return app;
    }

    private void registerHandler(Context context) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = accountService.addAccount(mapper.readValue(context.body(), Account.class));
        if(account != null)
        {
            context.json(mapper.writeValueAsString(account));
        }
        else
        {
            context.status(HttpStatus.BAD_REQUEST);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        account = accountService.attemptLogin(account.getUsername(), account.getPassword());

        if(account != null)
        {
            context.json(mapper.writeValueAsString(account));
        }
        else
        {
            context.status(HttpStatus.UNAUTHORIZED);
        }
    }

    private void postMessagesHandler(Context context) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = messageService.addMessage(mapper.readValue(context.body(), Message.class));
        if(message != null)
        {
            context.json(mapper.writeValueAsString(message));
        }
        else
        {
            context.status(HttpStatus.BAD_REQUEST);
        }
    }

    private void getMessagesHandler(Context context)
    {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageHandler(Context context)
    {
        Message message = messageService.getMessage(Integer.parseInt(context.pathParam("message_id")));
        if(message == null)
        {
            context.status(HttpStatus.OK);
        }
        else
        {
            context.json(message);
        }
    }

    private void deleteMessageHandler(Context context)
    {
        Message message = messageService.deleteMessage(Integer.parseInt(context.pathParam("message_id")));
        if(message == null)
        {
            context.status(HttpStatus.OK);
        }
        else
        {
            context.json(message);
        }
    }

    private void patchMessageHandler(Context context) throws JsonProcessingException
    {
        /*
        ObjectMapper mapper = new ObjectMapper();
        if(messageService.deleteMessage(Integer.parseInt(context.pathParam("message_id"))) == null)
        {
            context.status(HttpStatus.BAD_REQUEST);
            return;
        }
        Message message = messageService.addMessage(mapper.readValue(context.body(), Message.class));
        */
    }

    private void getAccountMessagesHandler(Context context)
    {
    }
}