/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
/**
 *
 * @author Gokhan
 */
public class MenuItem
{
    private int ID;
    private String itemname;
    private String itemdescription;
    private double price;

    public MenuItem(int ID, String itemname, String itemdescription, double price)
    {
        this.ID = ID;
        this.itemname = itemname;
        this.itemdescription = itemdescription;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public String getItemname() {
        return itemname;
    }

    public String getItemDescription() {
        return itemdescription;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Contact{" + "ID=" + ID + ", itemname=" + itemname + ", itemdecription=" + itemdescription + ", price=" + price + '}';
    }
}
