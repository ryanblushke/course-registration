import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/*
 * Created by JFormDesigner on Mon Oct 02 20:05:53 CST 2017
 */

/**
 * Author: Dexter Gordon-Dirks
 * NSID: dsg841
 * Course: CMPT 370
 * Class Functionality: welcomePage class contains the JFrame and UI elements for the welcome page window. A user can
 * choose to log in or sign up from this window by clicking on the corresponding JButton.
 */
public class welcomePage extends JFrame {
    public welcomePage() {

        initComponents();
    }

    private void btnSignUpMouseClicked(MouseEvent e) {
        new signUpWindow().setVisible(true);
//        welcomePage.setVisible(false);
    }

    private void btnLoginMouseClicked(MouseEvent e) {
        new loginPage().setVisible(true);
        welcomePage.setVisible(false);
    }

    protected void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Dexter Gordon-Dirks
        welcomePage = new JFrame();
        txtWelcome = new JTextField();
        txtLogin = new JTextField();
        btnLogin = new JButton();
        txtSignup = new JTextField();
        btnSignUp = new JButton();

        //======== welcomePage ========
        {
            Container welcomePageContentPane = welcomePage.getContentPane();
            welcomePageContentPane.setLayout(new BoxLayout(welcomePageContentPane, BoxLayout.Y_AXIS));

            //---- txtWelcome ----
            txtWelcome.setText("Welcome To the Course Registration Tool");
            txtWelcome.setFont(new Font("Century Gothic", Font.PLAIN, 24));
            txtWelcome.setEditable(false);
            txtWelcome.setEnabled(false);
            txtWelcome.setBackground(Color.white);
            txtWelcome.setHorizontalAlignment(JTextField.CENTER);
            welcomePageContentPane.add(txtWelcome);

            //---- txtLogin ----
            txtLogin.setText("Already a Member? Login");
            txtLogin.setFont(new Font("Century Gothic", Font.PLAIN, 14));
            txtLogin.setEditable(false);
            txtLogin.setBackground(Color.white);
            txtLogin.setEnabled(false);
            txtLogin.setHorizontalAlignment(JTextField.CENTER);
            welcomePageContentPane.add(txtLogin);

            //---- btnLogin ----
            btnLogin.setText("Login");
            btnLogin.setFont(new Font("Century Gothic", Font.PLAIN, 16));
            btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnLogin.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnLoginMouseClicked(e);
                }
            });
            welcomePageContentPane.add(btnLogin);

            //---- txtSignup ----
            txtSignup.setText("Sign Up Today!");
            txtSignup.setFont(new Font("Century Gothic", Font.PLAIN, 14));
            txtSignup.setEditable(false);
            txtSignup.setBackground(Color.white);
            txtSignup.setEnabled(false);
            txtSignup.setHorizontalAlignment(JTextField.CENTER);
            welcomePageContentPane.add(txtSignup);

            //---- btnSignUp ----
            btnSignUp.setText("Sign Up");
            btnSignUp.setFont(new Font("Century Gothic", Font.PLAIN, 16));
            btnSignUp.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnSignUp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnSignUpMouseClicked(e);
                }
            });
            welcomePageContentPane.add(btnSignUp);
            welcomePage.pack();
            welcomePage.setLocationRelativeTo(null);
            welcomePage.setSize(600,600);
            welcomePage.setVisible(true);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Dexter Gordon-Dirks
    private JFrame welcomePage;
    private JTextField txtWelcome;
    private JTextField txtLogin;
    private JButton btnLogin;
    private JTextField txtSignup;
    private JButton btnSignUp;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                welcomePage welcome = new welcomePage();
                welcome.setVisible(false);

            }
        });
    }
}


