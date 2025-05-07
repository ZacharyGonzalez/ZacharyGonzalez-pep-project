package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    /**
    * Takes an account object, attempts to insert it, checks for a generated key, 
    * and returns inserted account object.
    *
    * @param account
    * @return Account Object or null
    */
    public Account RegisterAccount(Account account){


        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username,password) VALUES(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2,account.getPassword());

            ps.executeUpdate();
            ResultSet rsKey = ps.getGeneratedKeys(); 

            if(rsKey.next()){ 
                int account_id=rsKey.getInt("account_id");
                return new Account(account_id,account.getUsername(),account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    return null;
    }

    /**
     * Takes an account object, attempts to locate it by username & password,
     * returning found record.
     * 
     * @param account
     * @return Account Object or null
     */
    public Account loginAccount(Account account){

        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2,account.getPassword());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    return null;
    }
    
    /**
     * Queries account table on account_id field.
     * @param account_id
     * @return Account Object or null
     */
    public Account getAccountByID(int account_id){

        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE account_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Account(rs.getString("username"),rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    return null;
    }

    /**
     * Queries account table on username field.
     * 
     * @param 
     * @return found Account record
     */
    public Account getAccountByUsername(String username){

        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Account(rs.getString("username"),rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    return null;
    }

}
