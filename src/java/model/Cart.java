/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class Cart {

    private List<Item> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Cart(List<Item> items) {
        this.items = items;
    }

    private Item getItemById(int id) {
        for (Item i : items) {
            if (i.getConsignment().getId() == id) {
                return i;
            }
        }
        return null;
    }

    public int getQuantityById(int id) {
        return getItemById(id).getQuantity();
    }

    // add consignment in cart
    public void addItem(Item t) {
        // already in cart
        if (getItemById(t.getConsignment().getId()) != null) {
            Item i = getItemById(t.getConsignment().getId());
            // old quantity + new quantity
            i.setQuantity(i.getQuantity() + t.getQuantity());
        } else { // if item not in cart yet then add in to cart
            items.add(t);
        }
    }

    public void removeItem(int id) {
        if (getItemById(id) != null) {
            items.remove(getItemById(id));
        }
    }



    public int getTotalMoney() {
        int temp = 0;
        for (Item i : items) {
            temp += i.getQuantity() * i.getPrice();
        }

        return temp;
    }
    
    public List<Integer> getItemIds() {
    List<Integer> ids = new ArrayList<>();
    for (Item item : items) {
        ids.add(item.getConsignment().getId());
    }
    return ids;
}

}
