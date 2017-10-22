import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Thu Oct 12 16:56:24 CST 2017
 */



/**
 * @author Dexter Gordon-Dirks
 */
public class registrationToolWindow extends JFrame {

    private String nsid, password;

    public registrationToolWindow(String nsid, String password) {
        initComponents();
    }

    private void emptyLabels(){
        chkBoxClass1.setText("");
        chkBoxClass2.setText("");
        chkBoxClass3.setText("");
        chkBoxClass4.setText("");
        chkBoxClass5.setText("");
        chkBoxClass6.setText("");
        chkBoxClass7.setText("");
        chkBoxClass8.setText("");
        chkBoxClass9.setText("");
        chkBoxClass10.setText("");
    }

    private void btnSearchClassesMouseClicked(MouseEvent e) {
        // TODO add your code here
        if(chkBoxClass1.isSelected()){
            chkBoxClass1.getText();
        }
        if(chkBoxClass2.isSelected()){
            chkBoxClass2.getText();
        }
        if(chkBoxClass3.isSelected()){
            chkBoxClass3.getText();
        }
        if(chkBoxClass4.isSelected()){
            chkBoxClass4.getText();
        }
        if(chkBoxClass5.isSelected()){
            chkBoxClass5.getText();
        }
        if(chkBoxClass6.isSelected()){
            chkBoxClass6.getText();
        }
        if(chkBoxClass7.isSelected()){
            chkBoxClass7.getText();
        }
        if(chkBoxClass8.isSelected()){
            chkBoxClass8.getText();
        }
        if(chkBoxClass9.isSelected()){
            chkBoxClass9.getText();
        }
        if(chkBoxClass10.isSelected()){
            chkBoxClass10.getText();
        }



    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Dexter Gordon-Dirks
        tbdPaneRegistration = new JTabbedPane();
        pnlWelcome = new JPanel();
        lblWelcome = new JLabel();
        pnlAddClass = new JPanel();
        btnSearchClasses = new JButton();
        chkBoxClass1 = new JCheckBox();
        chkBoxClass2 = new JCheckBox();
        chkBoxClass3 = new JCheckBox();
        chkBoxClass4 = new JCheckBox();
        chkBoxClass5 = new JCheckBox();
        chkBoxClass6 = new JCheckBox();
        chkBoxClass7 = new JCheckBox();
        chkBoxClass8 = new JCheckBox();
        chkBoxClass9 = new JCheckBox();
        chkBoxClass10 = new JCheckBox();
        pnlDropClass = new JPanel();
        pnlDegProg = new JPanel();

        //======== this ========
        Container contentPane = getContentPane();

        //======== tbdPaneRegistration ========
        {

            //======== pnlWelcome ========
            {

                // JFormDesigner evaluation mark
              
                pnlWelcome.setLayout(new FlowLayout());

                //---- lblWelcome ----
                lblWelcome.setText("Welcome To The Course Registration Tool");
                pnlWelcome.add(lblWelcome);
            }
            tbdPaneRegistration.addTab("Welcome", pnlWelcome);

            //======== pnlAddClass ========
            {

                //---- btnSearchClasses ----
                btnSearchClasses.setText("Add Courses");
                btnSearchClasses.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnSearchClassesMouseClicked(e);
                        //TODO: Actually implement this functionality
                        JOptionPane.showMessageDialog(tbdPaneRegistration,"You have been registered!","Registration",JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                //---- chkBoxClass1 ----
                chkBoxClass1.setText("MATH 123");

                //---- chkBoxClass2 ----
                chkBoxClass2.setText("GE 101");

                //---- chkBoxClass3 ----
                chkBoxClass3.setText("GE 111");

                //---- chkBoxClass4 ----
                chkBoxClass4.setText("GE 124");

                //---- chkBoxClass5 ----
                chkBoxClass5.setText("CHEM 114");

                //---- chkBoxClass6 ----
                chkBoxClass6.setText("COMM 102");

                GroupLayout pnlAddClassLayout = new GroupLayout(pnlAddClass);
                pnlAddClass.setLayout(pnlAddClassLayout);
                pnlAddClassLayout.setHorizontalGroup(
                    pnlAddClassLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, pnlAddClassLayout.createSequentialGroup()
                            .addContainerGap(231, Short.MAX_VALUE)
                            .addComponent(btnSearchClasses, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                            .addGap(215, 215, 215))
                        .addGroup(pnlAddClassLayout.createSequentialGroup()
                            .addGap(149, 149, 149)
                            .addGroup(pnlAddClassLayout.createParallelGroup()
                                .addComponent(chkBoxClass1)
                                .addComponent(chkBoxClass5)
                                .addComponent(chkBoxClass4)
                                .addComponent(chkBoxClass3)
                                .addComponent(chkBoxClass2))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 216, Short.MAX_VALUE)
                            .addGroup(pnlAddClassLayout.createParallelGroup()
                                .addComponent(chkBoxClass6)
                                .addComponent(chkBoxClass7)
                                .addComponent(chkBoxClass8)
                                .addComponent(chkBoxClass9)
                                .addComponent(chkBoxClass10))
                            .addGap(141, 141, 141))
                );
                pnlAddClassLayout.setVerticalGroup(
                    pnlAddClassLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, pnlAddClassLayout.createSequentialGroup()
                            .addGap(99, 99, 99)
                            .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(chkBoxClass1)
                                .addComponent(chkBoxClass6))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(chkBoxClass2)
                                .addComponent(chkBoxClass7))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(chkBoxClass3)
                                .addComponent(chkBoxClass8))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(chkBoxClass4)
                                .addComponent(chkBoxClass9))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(chkBoxClass5)
                                .addComponent(chkBoxClass10))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                            .addComponent(btnSearchClasses, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                            .addGap(141, 141, 141))
                );
            }
            tbdPaneRegistration.addTab("Add Class", pnlAddClass);

            //======== pnlDropClass ========
            {

                GroupLayout pnlDropClassLayout = new GroupLayout(pnlDropClass);
                pnlDropClass.setLayout(pnlDropClassLayout);
                pnlDropClassLayout.setHorizontalGroup(
                    pnlDropClassLayout.createParallelGroup()
                        .addGap(0, 596, Short.MAX_VALUE)
                );
                pnlDropClassLayout.setVerticalGroup(
                    pnlDropClassLayout.createParallelGroup()
                        .addGap(0, 544, Short.MAX_VALUE)
                );
            }
            tbdPaneRegistration.addTab("Drop Class", pnlDropClass);

            //======== pnlDegProg ========
            {

                GroupLayout pnlDegProgLayout = new GroupLayout(pnlDegProg);
                pnlDegProg.setLayout(pnlDegProgLayout);
                pnlDegProgLayout.setHorizontalGroup(
                    pnlDegProgLayout.createParallelGroup()
                        .addGap(0, 596, Short.MAX_VALUE)
                );
                pnlDegProgLayout.setVerticalGroup(
                    pnlDegProgLayout.createParallelGroup()
                        .addGap(0, 544, Short.MAX_VALUE)
                );
            }
            tbdPaneRegistration.addTab("Degree Progress", pnlDegProg);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tbdPaneRegistration)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tbdPaneRegistration)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Dexter Gordon-Dirks
    private JTabbedPane tbdPaneRegistration;
    private JPanel pnlWelcome;
    private JLabel lblWelcome;
    private JPanel pnlAddClass;
    private JButton btnSearchClasses;
    private JCheckBox chkBoxClass1;
    private JCheckBox chkBoxClass2;
    private JCheckBox chkBoxClass3;
    private JCheckBox chkBoxClass4;
    private JCheckBox chkBoxClass5;
    private JCheckBox chkBoxClass6;
    private JCheckBox chkBoxClass7;
    private JCheckBox chkBoxClass8;
    private JCheckBox chkBoxClass9;
    private JCheckBox chkBoxClass10;
    private JPanel pnlDropClass;
    private JPanel pnlDegProg;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
