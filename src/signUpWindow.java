import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
/*
 * Created by JFormDesigner on Tue Oct 03 12:13:49 CST 2017
 */


/**
 * @author Dexter Gordon-Dirks
 */
public class signUpWindow extends JFrame {

    public signUpWindow() {
        initComponents();
    }

    private void verifyLogin(){
        if (!txtNSID.getText().isEmpty() && passFieldInitial.getPassword().length == 0 || !txtNSID.getText().isEmpty() && passFieldConfirm.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "You are missing a password field", "Missing Input", 0);
        }
        //TODO Proper verification of Logins
        else if (!txtNSID.getText().isEmpty() && Arrays.equals(passFieldInitial.getPassword(), passFieldConfirm.getPassword())) {
            this.verifyRegistration();
        } else if (!txtNSID.getText().isEmpty() && !Arrays.equals(passFieldInitial.getPassword(), passFieldConfirm.getPassword())) {
            JOptionPane.showMessageDialog(this, "Passwords didn't match", "Missing Input", 0);
        } else {
            JOptionPane.showMessageDialog(this, "You didn't enter an initial  NSID", "Missing Input", 0);
        }
    }

    private void txtNSIDActionPerformed(ActionEvent e) {
        verifyLogin();
    }

    private void passFieldInitialActionPerformed(ActionEvent e) {
        verifyLogin();
    }

    private void passFieldConfirmActionPerformed(ActionEvent e) {
        verifyLogin();
    }

    public void verifyRegistration () {
        Driver registrationDriver = new Driver();
        registrationDriver.connectToDatabase();
        if (!registrationDriver.userExists(txtNSID.getText())) {
            registrationDriver.addUser(txtNSID.getText(), String.valueOf(passFieldConfirm.getPassword()));
            JOptionPane.showMessageDialog(this, "User Registered Successfully", "Great Success!", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "User Already Exists", "Terrible Failure!", 0);
        }
        registrationDriver.closeConnection();
    }

    private void btnEnterMouseClicked(MouseEvent e) {
        verifyLogin();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - John Smith
        lblEmail = new JLabel();
        lblPass1 = new JLabel();
        lblPass2 = new JLabel();
        txtNSID = new JTextField();
        passFieldInitial = new JPasswordField();
        passFieldConfirm = new JPasswordField();
        btnEnter = new JButton();

        //======== this ========
        setTitle("Sign Up");
        setFont(new Font("Century Gothic", Font.PLAIN, 12));
        Container contentPane = getContentPane();

        //---- lblEmail ----
        lblEmail.setText("NSID:");

        //---- lblPass1 ----
        lblPass1.setText("Password:");
        lblPass1.setFont(new Font("Century Gothic", Font.PLAIN, 12));

        //---- lblPass2 ----
        lblPass2.setText("Confirm Password:");
        lblPass2.setFont(new Font("Century Gothic", Font.PLAIN, 12));

        //---- txtNSID ----
        txtNSID.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        txtNSID.addActionListener(e -> txtNSIDActionPerformed(e));

        //---- passFieldInitial ----
        passFieldInitial.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        passFieldInitial.addActionListener(e -> passFieldInitialActionPerformed(e));

        //---- passFieldConfirm ----
        passFieldConfirm.addActionListener(e -> passFieldConfirmActionPerformed(e));

        //---- btnEnter ----
        btnEnter.setText("Enter");
        btnEnter.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        btnEnter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnEnterMouseClicked(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblEmail, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                        .addComponent(lblPass1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                        .addComponent(lblPass2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                    .addGap(27, 27, 27)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNSID, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .addComponent(passFieldInitial, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .addComponent(passFieldConfirm, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                    .addContainerGap(41, Short.MAX_VALUE))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(285, 285, 285)
                    .addComponent(btnEnter, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(76, 76, 76)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(lblEmail)
                        .addComponent(txtNSID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(29, 29, 29)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPass1)
                        .addComponent(passFieldInitial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(lblPass2)
                        .addComponent(passFieldConfirm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                    .addComponent(btnEnter, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - John Smith
    private JLabel lblEmail;
    private JLabel lblPass1;
    private JLabel lblPass2;
    private JTextField txtNSID;
    private JPasswordField passFieldInitial;
    private JPasswordField passFieldConfirm;
    private JButton btnEnter;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
