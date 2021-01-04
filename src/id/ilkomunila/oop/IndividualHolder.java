/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ilkomunila.oop;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author didik
 */
public class IndividualHolder extends AccountHolder {

    StringProperty gender;
    StringProperty birthdate;

    public IndividualHolder(Integer holderID, String name,
            String address, String gender, String birthdate, ArrayList<Account> accounts) {
        super(holderID, name, address, accounts);
        this.gender = new SimpleStringProperty(gender);
        this.birthdate = new SimpleStringProperty(birthdate);
    }

    public IndividualHolder(Integer holderID, String name, String address,
            String gender, String birthdate, Account account) {
        super(holderID, name, address, account);
        this.gender = new SimpleStringProperty(gender);
        this.birthdate = new SimpleStringProperty(birthdate);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getBirthdate() {
        return birthdate.get();
    }

    public void setBirthdate(String birthdate) {
        this.birthdate.set(birthdate);
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public StringProperty birthdateProperty() {
        return birthdate;
    }
}
