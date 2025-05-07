package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    public Message createMessage(Message message){

        Connection conn = ConnectionUtil.getConnection();
        try{     
            String sql = "INSERT INTO message (posted_by, message_text,time_posted_epoch) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, message.getPosted_by());
            ps.setString(2,message.getMessage_text());
            ps.setLong(3,message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet rsKey = ps.getGeneratedKeys();
            if(rsKey.next()){
                int message_id=rsKey.getInt(1);
                return new Message(message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Message> getAllMessages(){
        Connection conn = ConnectionUtil.getConnection();
        ArrayList<Message> messages = new ArrayList();
        try{     
            String sql = "SELECT * FROM message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageByID(int id){
        Connection conn = ConnectionUtil.getConnection();
        try{     
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs= ps.executeQuery();
            if(rs.next()){
                return new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getInt("time_posted_epoch"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }
    public int deleteMessageByID(int id){
        Connection conn = ConnectionUtil.getConnection();
        try{     
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            return ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }
        return 0;
    }
    public Message updateMessageByID(int id, String updateMessageText){
        Connection conn = ConnectionUtil.getConnection();
        try{     
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,updateMessageText);
            ps.setInt(2,id);
            int rowsAffected= ps.executeUpdate();
            if(rowsAffected>0){
                return getMessageByID(id);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public ArrayList<Message> getAllMessagesByAccountID(int accountId){
        Connection conn = ConnectionUtil.getConnection();
        ArrayList<Message> messages = new ArrayList();
        try{     
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,accountId);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
