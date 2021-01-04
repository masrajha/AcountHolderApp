/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ilkomunila.oop;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author didik
 */
public abstract class AccountHolder {
    private IntegerProperty holderID;
    private StringProperty name;
    private StringProperty address;
    private IntegerProperty numAccounts;
    private ArrayList<Account> accounts;

    public AccountHolder(Integer holderID, String name, String address,
            ArrayList<Account> accounts) {
        this.holderID=new SimpleIntegerProperty(holderID);
        this.name= new SimpleStringProperty(name);
        this.address=new SimpleStringProperty(address);
        this.accounts = accounts;
        this.numAccounts=new SimpleIntegerProperty(accounts.size());
    }
    public AccountHolder(Integer holderID, String name, String address, Account account) {
        accounts = new ArrayList<>();
        this.holderID=new SimpleIntegerProperty(holderID);
        this.name=new SimpleStringProperty(name);
        this.address=new SimpleStringProperty(address);
        this.accounts.add(account);
        this.numAccounts=new SimpleIntegerProperty(accounts.size());
    }

    public Integer getHolderID() {
        return holderID.get();
    }

    public void setHolderID(Integer holderID) {
        this.holderID.set(holderID);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public Integer getNumAccounts() {
        return numAccounts.get();
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    
    public IntegerProperty holderIDProperty(){
        return holderID;
    }
    public StringProperty nameProperty(){
        return name;
    }
    public StringProperty addressProperty(){
        return address;
    }
    public IntegerProperty numAccountsProperty(){
        return numAccounts;
    }
}

