/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import core.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Gokhan
 */
public class MenuItemDAO implements DAO<MenuItem>
{
    public MenuItemDAO() {
        
    }
    List<MenuItem> menuitems;
    /**
     * Get a single contact entity as a contact object
     * @param id
     * @return 
     */
    @Override
    public Optional<MenuItem> get(int id) {
        DB db = DB.getInstance();
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Contact WHERE id = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            MenuItem menuitem = null;
            while (rs.next()) {
                menuitem = new MenuItem(rs.getInt("id"), rs.getString("itemname"), rs.getString("itemdescription"), rs.getDouble("price"));
            }
            return Optional.ofNullable(menuitem);
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
    public List<MenuItem> getAll() {
        DB db = DB.getInstance();
        ResultSet rs = null;
        menuitems = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Contact ORDER BY id";
            rs = db.executeQuery(sql);
            MenuItem menuitem = null;
            while (rs.next()) {
                menuitem = new MenuItem(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("phonenumber"));
                menuitems.add(menuitem);
            }
            return menuitems;
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Insert a contact object into contact table
     * @param menuitem
     */
    @Override
    public void insert(MenuItem menuitem)
    {
        DB db = DB.getInstance();
        try {
            String sql = "INSERT INTO MenuItem(ID, itemname, itemdescription, price) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, menuitem.getID());
            stmt.setString(2, menuitem.getItemname());
            stmt.setString(3, menuitem.getItemDescription());
            stmt.setDouble(4, menuitem.getPrice());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("A new menu item was inserted successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Update a contact entity in database if it exists using a contact object
     * @param menuitem
     */
    @Override
    public void update(MenuItem menuitem) {
        DB db = DB.getInstance();
        try {
            String sql = "UPDATE MenuItem SET itemname=?, itemdescription=?, price=? WHERE id=?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setString(1, menuitem.getItemname());
            stmt.setString(2, menuitem.getItemDescription());
            stmt.setDouble(3, menuitem.getPrice());
            stmt.setInt(4, menuitem.getID());
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
     * @param menuitem
     */
    @Override
    public void delete(MenuItem menuitem) {
        DB db = DB.getInstance();
        try {
            String sql = "DELETE FROM MenuItem WHERE ID = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, menuitem.getID());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A menu item was deleted successfully!");
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
