import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import com.intellij.uiDesigner.core.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Mon Oct 02 20:05:53 CST 2017
 */



/**
 * @author unknown
 */
public class welcomePage extends JFrame {
    public welcomePage() {

        initComponents();
    }

    private void btnSignUpMouseClicked(MouseEvent e) {
        new signUpWindow().setVisible(true);
        this.setVisible(false);
    }

    private void btnLoginMouseClicked(MouseEvent e) {
        new loginPage().setVisible(true);
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
            welcomePageContentPane.add(txtWelcome);

            //---- txtLogin ----
            txtLogin.setText("Already a Member? Login");
            txtLogin.setFont(new Font("Century Gothic", Font.PLAIN, 14));
            txtLogin.setEditable(false);
            txtLogin.setBackground(Color.white);
            txtLogin.setEnabled(false);
            welcomePageContentPane.add(txtLogin);

            //---- btnLogin ----
            btnLogin.setText("Login");
            btnLogin.setFont(new Font("Century Gothic", Font.PLAIN, 16));
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
            welcomePageContentPane.add(txtSignup);

            //---- btnSignUp ----
            btnSignUp.setText("Sign Up");
            btnSignUp.setFont(new Font("Century Gothic", Font.PLAIN, 16));
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


