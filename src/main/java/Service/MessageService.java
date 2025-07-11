package Service;

import java.util.ArrayList;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;


public class MessageService {
    private final MessageDAO messageDAO;
    private final AccountDAO accountDAO; //A message depends on an account

    public MessageService(){
        this.accountDAO = new AccountDAO();
        this.messageDAO = new MessageDAO();
    }

    private Boolean validMessageText(int id, String message_text){
        if(message_text.length()<1 || message_text.length()>255){
            return false;
        }
        Account userExists = accountDAO.getAccountByID(id);
        return userExists != null; //User has to exist for a message to be sent
    }
    public Message CreateMessage(Message message){
        if(validMessageText(message.getPosted_by(), message.getMessage_text())){
            return messageDAO.createMessage(message);
        }
        return null;
    }

    public ArrayList<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message GetMessageByID(int id){
        return messageDAO.getMessageByID(id);
    }

    public Message DeleteMessage(int id){
        Message message = messageDAO.getMessageByID(id);
        if(message == null){
            return message;
        }
        int deletedRowCount=messageDAO.deleteMessageByID(id);
        if(deletedRowCount!=1){
            //Here is where we would rollback the SQL process since we only want to delete 1 entry
            System.err.println("Deleted too many entries");}
        return message;
    }

    public Message UpdateMessage(int id,String updateMessageText){
        if(validMessageText(id, updateMessageText)){
            return messageDAO.updateMessageByID(id, updateMessageText);
        }
        return null;
    }

    public ArrayList<Message> getAllMessagesByID(int accountId){
        return messageDAO.getAllMessagesByAccountID(accountId);
    }

}
