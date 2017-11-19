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
        // Generated using JFormDesigner Evaluation license - John Smith
        lblNSID = new JLabel();
        lblPass = new JLabel();
        txtNSID = new JTextField();
        passFieldPassword = new JPasswordField();
        btnBack = new JButton();
        btnContinue = new JButton();
        lblCourseWelcome = new JLabel();

        //======== this ========
        setTitle("Login Page");
        setBackground(new Color(204, 204, 204));
        Container contentPane = getContentPane();

        //---- lblNSID ----
        lblNSID.setText("Enter your NSID:");
        lblNSID.setFont(new Font("Century Gothic", Font.PLAIN, 12));

        //---- lblPass ----
        lblPass.setText("Password:");
        lblPass.setFont(new Font("Century Gothic", Font.PLAIN, 12));

        //---- txtNSID ----
        txtNSID.addActionListener(e -> txtNSIDActionPerformed(e));

        //---- passFieldPassword ----
        passFieldPassword.addActionListener(e -> passFieldPasswordActionPerformed(e));

        //---- btnBack ----
        btnBack.setText("Back");
        btnBack.setFont(new Font("Century Gothic", Font.PLAIN, 12));

        //---- btnContinue ----
        btnContinue.setText("Enter");
        btnContinue.setFont(new Font("Century Gothic", Font.PLAIN, 12));

        //---- lblCourseWelcome ----
        lblCourseWelcome.setText("Big Dynamic Software Machine");
        lblCourseWelcome.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                            .addComponent(btnContinue, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(76, 76, 76)
                                    .addComponent(lblCourseWelcome))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(58, 58, 58)
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(lblNSID, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblPass, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                                    .addGap(50, 50, 50)
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(txtNSID, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passFieldPassword, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
                            .addGap(0, 59, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblCourseWelcome)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNSID)
                        .addComponent(txtNSID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(33, 33, 33)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPass)
                        .addComponent(passFieldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(59, 59, 59)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnContinue, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        this.setLocationRelativeTo(null);

    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - John Smith
    private JLabel lblNSID;
    private JLabel lblPass;
    private JTextField txtNSID;
    private JPasswordField passFieldPassword;
    private JButton btnBack;
    private JButton btnContinue;
    private JLabel lblCourseWelcome;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
