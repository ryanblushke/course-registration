import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
//        welcomePage.setVisible(false);
    }

    private void btnLoginMouseClicked(MouseEvent e) {
        new loginPage().setVisible(true);
        welcomePage.setVisible(false);
    }

    protected void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - John Smith
        welcomePage = new JFrame();
        txtWelcome = new JTextField();
        txtLogin = new JTextField();
        btnLogin = new JButton();
        txtSignup = new JTextField();
        btnSignUp = new JButton();

        //======== welcomePage ========
        {
            Container welcomePageContentPane = welcomePage.getContentPane();

            //---- txtWelcome ----
            txtWelcome.setText("Welcome To the Course Registration Tool");
            txtWelcome.setFont(new Font("Century Gothic", Font.PLAIN, 24));
            txtWelcome.setEditable(false);
            txtWelcome.setEnabled(false);
            txtWelcome.setBackground(Color.white);

            //---- txtLogin ----
            txtLogin.setText("Already a Member? Login");
            txtLogin.setFont(new Font("Century Gothic", Font.PLAIN, 14));
            txtLogin.setEditable(false);
            txtLogin.setBackground(Color.white);
            txtLogin.setEnabled(false);

            //---- btnLogin ----
            btnLogin.setText("Login");
            btnLogin.setFont(new Font("Century Gothic", Font.PLAIN, 16));
            btnLogin.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnLoginMouseClicked(e);
                }
            });

            //---- txtSignup ----
            txtSignup.setText("Sign Up Today!");
            txtSignup.setFont(new Font("Century Gothic", Font.PLAIN, 14));
            txtSignup.setEditable(false);
            txtSignup.setBackground(Color.white);
            txtSignup.setEnabled(false);

            //---- btnSignUp ----
            btnSignUp.setText("Sign Up");
            btnSignUp.setFont(new Font("Century Gothic", Font.PLAIN, 16));
            btnSignUp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnSignUpMouseClicked(e);
                }
            });

            GroupLayout welcomePageContentPaneLayout = new GroupLayout(welcomePageContentPane);
            welcomePageContentPane.setLayout(welcomePageContentPaneLayout);
            welcomePageContentPaneLayout.setHorizontalGroup(
                welcomePageContentPaneLayout.createParallelGroup()
                    .addGroup(welcomePageContentPaneLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(btnLogin)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                        .addComponent(btnSignUp)
                        .addGap(57, 57, 57))
                    .addGroup(welcomePageContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtLogin, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                        .addComponent(txtSignup, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addComponent(txtWelcome, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
            );
            welcomePageContentPaneLayout.setVerticalGroup(
                welcomePageContentPaneLayout.createParallelGroup()
                    .addGroup(welcomePageContentPaneLayout.createSequentialGroup()
                        .addComponent(txtWelcome, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 284, Short.MAX_VALUE)
                        .addGroup(welcomePageContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSignup)
                            .addComponent(txtLogin))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(welcomePageContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLogin)
                            .addComponent(btnSignUp))
                        .addContainerGap())
            );
            welcomePage.pack();
            welcomePage.setLocationRelativeTo(null);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - John Smith
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


