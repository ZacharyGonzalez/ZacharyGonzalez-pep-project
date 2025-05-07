package Controller;

import java.util.ArrayList;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::accountRegistrationHandler);
        app.post("/login", this::accountLoginHandler);
        app.post("/messages", this::MessageCreateHandler);
       
        app.get("/messages", this::MessageAllHandler);
        app.get("/messages/{message_id}", this::MessageIDHandler);
        app.get("/accounts/{account_id}/messages", this::AccountMessagesHandler);

        app.delete("/messages/{message_id}", this::MessageDeleteIDHandler);
        app.patch("/messages/{message_id}", this::MessagePatchIDHandler);

        return app;
    }
  
    private void accountRegistrationHandler(Context context){
        Account account = context.bodyAsClass(Account.class);
        Account addedAccount = accountService.RegisterAccount(account);
        if(addedAccount!=null){
            context.json(addedAccount).status(200);
        } else{
            context.status(400);
        }
    } 

    private void accountLoginHandler(Context context){
        Account account = context.bodyAsClass(Account.class);
        Account loginAccount = accountService.LoginAccount(account);
        if(loginAccount!=null){
            context.json(loginAccount).status(200);
        } 
        else{
            context.status(401);
        }
    }    
    private void MessageCreateHandler(Context context){
        Message message = context.bodyAsClass(Message.class);
        Message createdMessage = messageService.CreateMessage(message);
        if(createdMessage!=null){
            context.json(createdMessage).status(200);
        } 
        else{
            context.status(400);
        }
    } 
    private void MessageAllHandler(Context context) {
        ArrayList<Message> messages = messageService.getAllMessages();
        context.json(messages).status(200);
    }
        
    private void MessageIDHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.GetMessageByID(id);
        if(message != null){
            context.json(message);
        }
        context.status(200);
    }    

    private void MessageDeleteIDHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.DeleteMessage(id);
        if(deletedMessage!=null){
            context.json(deletedMessage);
        }
        context.status(200);
    }  
    private void MessagePatchIDHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message incomingMessage = context.bodyAsClass(Message.class);
        Message message = messageService.UpdateMessage(id, incomingMessage.getMessage_text());
        if(message != null){
            context.json(message).status(200);
        }else{
            context.status(400);
        }
       }    
    private void AccountMessagesHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("account_id"));
        ArrayList<Message> message = messageService.getAllMessagesByID(id);
        context.json(message).status(200);
    }
   




}