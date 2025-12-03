/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import core.*;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 *
 * @author Gokhan
 */
public class ContactDAO implements DAO<Contact>
{   
    public ContactDAO() {
        
    }
    List<Contact> contacts;
    /**
     * Get a single contact entity as a contact object
     * @param id
     * @return 
     */
    @Override
    public Optional<Contact> get(int id) {
        DB db = DB.getInstance();
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Contact WHERE id = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            Contact contact = null;
            while (rs.next()) {
                contact = new Contact(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("phonenumber"));
            }
            return Optional.ofNullable(contact);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Get all contact entities as a List
     * @return 
     */
    @Override
    public List<Contact> getAll() {
        DB db = DB.getInstance();
        ResultSet rs = null;
        contacts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Contact ORDER BY id";
            rs = db.executeQuery(sql);
            Contact contact = null;
            while (rs.next()) {
                contact = new Contact(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("phonenumber"));
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Insert a contact object into contact table
     * @param contact 
     */
    @Override
    public void insert(Contact contact)
    {
        DB db = DB.getInstance();
        try {
            String sql = "INSERT INTO Contact(ID, firstName, lastName, phoneNumber) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, contact.getID());
            stmt.setString(2, contact.getFirstName());
            stmt.setString(3, contact.getLastName());
            stmt.setString(4, contact.getPhoneNumber());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("A new contact was inserted successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Update a contact entity in database if it exists using a contact object
     * @param contact
     */
    @Override
    public void update(Contact contact) {
        DB db = DB.getInstance();
        try {
            String sql = "UPDATE Contact SET firstName=?, lastName=?, phoneNumber=? WHERE id=?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setString(1, contact.getFirstName());
            stmt.setString(2, contact.getLastName());
            stmt.setString(3, contact.getPhoneNumber());
            stmt.setInt(4, contact.getID());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing contact was updated successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Delete a contact from contact table if the entity exists
     * @param contact 
     */
    @Override
    public void delete(Contact contact) {
        DB db = DB.getInstance();
        try {
            String sql = "DELETE FROM Contact WHERE ID = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, contact.getID());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A contact was deleted successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Get all column names in a list array
     * @return 
     */
    @Override
    public List<String> getColumnNames() {
        DB db = DB.getInstance();
        ResultSet rs = null;
        List<String> headers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Contact WHERE ID = -1";//We just need this sql query to get the column headers
            rs = db.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            //Get number of columns in the result set
            int numberCols = rsmd.getColumnCount();
            for (int i = 1; i <= numberCols; i++) {
                headers.add(rsmd.getColumnLabel(i));//Add column headers to the list
            }
            return headers;
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        } 
    }
}
