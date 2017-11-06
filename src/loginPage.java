import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/*
 * Created by JFormDesigner on Mon Oct 02 20:23:40 CST 2017
 */



/**
 * @author Dexter Gordon-Dirks
 */
public class loginPage extends JFrame {
    public loginPage() {
        initComponents();
    }

    private void txtNSIDActionPerformed(ActionEvent e) {
        if (! txtNSID.getText().isEmpty() && passFieldPassword.getPassword().length == 0){
            JOptionPane.showMessageDialog(this,"You didn't enter an initial  Password","Missing Input",0);
        }
        //TODO Proper verification of Logins
        else if (!txtNSID.getText().isEmpty() && passFieldPassword.getPassword().length > 0){

        }
        else{
            JOptionPane.showMessageDialog(this,"You didn't enter an initial  NSID","Missing Input",0);
        }
    }


    private void passFieldPasswordActionPerformed(ActionEvent e) {
        if (! txtNSID.getText().isEmpty() && passFieldPassword.getPassword().length == 0){
            JOptionPane.showMessageDialog(this,"You didn't enter an initial  Password","Missing Input",0);
        }
        //TODO Proper verification of Logins
        else if (!txtNSID.getText().isEmpty() && passFieldPassword.getPassword().length > 0){
            Driver mySQL = new Driver();
            mySQL.connectToDatabase();
            if( mySQL.authenticateUser(txtNSID.getText(), String.valueOf(passFieldPassword.getPassword())) ){
                new registrationToolWindow(txtNSID.getText(), String.valueOf(passFieldPassword.getPassword())).setVisible(true);
                this.setVisible(false);
            }
            else{
                JOptionPane.showMessageDialog(this,"Wrong username or password.","Invalid Credentials",0);
            }
            mySQL.closeConnection();
        }
        else{
            JOptionPane.showMessageDialog(this,"You didn't enter an initial  NSID","Missing Input",0);
        }
    }




    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Kevin Baker
        lblNSID = new JLabel();
        lblPass = new JLabel();
        txtNSID = new JTextField();
        passFieldPassword = new JPasswordField();

        //======== this ========
        Container contentPane = getContentPane();

        //---- lblNSID ----
        lblNSID.setText("Enter your NSID:");

        //---- lblPass ----
        lblPass.setText("Password:");

        //---- txtNSID ----
        txtNSID.addActionListener(e -> txtNSIDActionPerformed(e));

        //---- passFieldPassword ----
        passFieldPassword.addActionListener(e -> passFieldPasswordActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(lblPass, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                            .addGap(43, 43, 43))
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addComponent(lblNSID, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)))
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(txtNSID, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                        .addComponent(passFieldPassword, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(82, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(83, 83, 83)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(txtNSID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNSID))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(lblPass)
                        .addComponent(passFieldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(43, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        this.setLocationRelativeTo(null);

    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Kevin Baker
    private JLabel lblNSID;
    private JLabel lblPass;
    private JTextField txtNSID;
    private JPasswordField passFieldPassword;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
