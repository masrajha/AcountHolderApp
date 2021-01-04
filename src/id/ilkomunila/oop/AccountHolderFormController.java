/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ilkomunila.oop;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author didik
 */
public class AccountHolderFormController implements Initializable {

    @FXML
    private TextField tfHolderID;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAddress;

    @FXML
    private ComboBox<String> cbGender;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private TextField tfAccNumber;

    @FXML
    private TextField tfAccBalance;

    @FXML
    private Button btnAddAccountHolder;

    @FXML
    private Button btnReload;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<IndividualHolder> tblAccHolder;

    @FXML
    private TableColumn<IndividualHolder, Integer> colHolderID;

    @FXML
    private TableColumn<IndividualHolder, String> colName;

    @FXML
    private TableColumn<IndividualHolder, String> colAddress;

    @FXML
    private TableColumn<IndividualHolder, String> colGender;

    @FXML
    private TableColumn<IndividualHolder, String> colBirthdate;

    @FXML
    private TableColumn<IndividualHolder, Integer> colNumAccounts;

    @FXML
    private TableView<Account> tblAccount;

    @FXML
    private TableColumn<Account, Integer> colAccNumber;

    @FXML
    private TableColumn<Account, Double> colBalance;

    @FXML
    private TextField tfNewHolderID;

    @FXML
    private TextField tfNewAccNumber;

    @FXML
    private TextField tfNewAccBalance;

    @FXML
    private Button btnAddAccount;

    @FXML
    private Label lblDBStatus;
    
    @FXML
    private Label lblSaveStatus;
    
    private AccountHolderDataModel ahdm;

    @FXML
    void handleAddAccountButton(ActionEvent event) {
     
        try {
            Account acc =  new Account(Integer.parseInt(tfNewAccNumber.getText()),
                            Double.parseDouble(tfNewAccBalance.getText()));
            
            ahdm.addAccount(Integer.parseInt(tfNewHolderID.getText()),acc);          
            viewDataAccount(Integer.parseInt(tfNewHolderID.getText()));
            btnReload.fire();
            tfNewAccBalance.setText("");
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountHolderFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleAddHolderButton(ActionEvent event) {
        LocalDate ld = dpBirthdate.getValue();
        String birthdate = String.format("%d-%02d-%02d", ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
        IndividualHolder holder = new IndividualHolder(Integer.parseInt(tfHolderID.getText()),
                tfName.getText(),
                tfAddress.getText(),
                cbGender.getSelectionModel().getSelectedItem(),
                birthdate,
                new Account(Integer.parseInt(tfAccNumber.getText()), Double.parseDouble(tfAccBalance.getText())));
        try {
            ahdm.addAccountHolder(holder);
            lblSaveStatus.setText("Account berhasil dibuat");
            btnReload.fire();
            btnClear.fire();
        } catch (SQLException ex) {
            lblSaveStatus.setText("Account gagal dibuat");
            Logger.getLogger(AccountHolderFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    void handleClearButton(ActionEvent event) {
        try {
            tfHolderID.setText("" + ahdm.nextAccountHolderID());
            tfAccNumber.setText(tfHolderID.getText() + "01");
            tfName.setText("");
            tfAddress.setText("");
            cbGender.getSelectionModel().clearSelection();
            tfAccBalance.setText("");
            dpBirthdate.setValue(LocalDate.of(LocalDate.now().getYear() - 17, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));
        } catch (SQLException ex) {
            Logger.getLogger(AccountHolderFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @FXML
    void handleReloadButton(ActionEvent event) {
        ObservableList<IndividualHolder> data = ahdm.getIndividualHolders();
        colHolderID.setCellValueFactory(new PropertyValueFactory<>("holderID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        colNumAccounts.setCellValueFactory(new PropertyValueFactory<>("numAccounts"));
        tblAccHolder.setItems(null);
        tblAccHolder.setItems(data);
        btnAddAccount.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");
        cbGender.setItems(gender);
        try {
            ahdm = new AccountHolderDataModel("SQLITE");
            lblDBStatus.setText(ahdm.conn == null ? "Not Connected" : "Connected");
            btnClear.fire();
            btnReload.fire();
        } catch (SQLException ex) {
            Logger.getLogger(AccountHolderFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tblAccHolder.getSelectionModel().selectedIndexProperty().addListener(listener->{
            if (tblAccHolder.getSelectionModel().getSelectedItem()!=null){
                IndividualHolder holder =  tblAccHolder.getSelectionModel().getSelectedItem();
                viewDataAccount(holder.getHolderID());
                btnAddAccount.setDisable(false);
                tfNewHolderID.setText(""+holder.getHolderID());
                try {
                    tfNewAccNumber.setText(""+ahdm.nextAccountNumber(holder.getHolderID()));
                } catch (SQLException ex) {
                    Logger.getLogger(AccountHolderFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
    
    public void viewDataAccount(int holderID){
        ObservableList<Account> data = ahdm.getAccounts(holderID);
        colAccNumber.setCellValueFactory(new PropertyValueFactory<>("accNumber"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        tblAccount.setItems(null);
        tblAccount.setItems(data);
    }

}
