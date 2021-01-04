/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ilkomunila.oop;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author didik
 */
public class Account {
    private IntegerProperty accNumber;
    private DoubleProperty balance;

    public Account(int accNumber, double balance) {
        this.accNumber=new SimpleIntegerProperty(accNumber);
        this.balance=new SimpleDoubleProperty(balance);
    }

    public Double getBalance() {
        return balance.get();
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    public Integer getAccNumber() {
        return accNumber.get();
    }

    public void setAccNumber(int accNumber) {
        this.accNumber.set(accNumber);
    }
    
    public void deposite(double amt){
        this.balance.set(this.balance.get()+amt);
    }
    public void withdraw(double amt){
        this.balance.set(this.balance.get()-amt);
    }
    public IntegerProperty accNumberProperty(){
        return accNumber;
    }
    public DoubleProperty balanceProperty(){
        return balance;
    }
}
