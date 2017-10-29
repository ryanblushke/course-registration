import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    private void list1MouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void viewButton_MouseClicked(MouseEvent e) {
        // TODO add your code here
        String courseToView;
        String[] courseInfo;
        courseToView = (String) classList.getSelectedValue();

        Driver BDSM = new Driver();
        BDSM.connectToDatabase();
        courseInfo = BDSM.getCourseTimes(courseToView);
        viewList.setListData(courseInfo);
        BDSM.closeConnection();
    }

    private void refreshButton_MouseClicked(MouseEvent e) {
        // TODO add your code here
        String[] classes;
        Driver BDSM = new Driver();
        BDSM.connectToDatabase();
        classes = BDSM.getDegreeRequirements(nsid);
        classList.setListData(classes);
        BDSM.closeConnection();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Kevin Baker
        tbdPaneRegistration = new JTabbedPane();
        pnlWelcome = new JPanel();
        lblWelcome = new JLabel();
        pnlAddClass = new JPanel();
        scrollPane1 = new JScrollPane();
        classList = new JList();
        scrollPane2 = new JScrollPane();
        viewList = new JList();
        scrollPane3 = new JScrollPane();
        list3 = new JList();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        register = new JButton();
        pnlDropClass = new JPanel();
        pnlDegProg = new JPanel();

        //======== this ========
        Container contentPane = getContentPane();

        //======== tbdPaneRegistration ========
        {

            //======== pnlWelcome ========
            {

                // JFormDesigner evaluation mark
                pnlWelcome.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                        java.awt.Color.red), pnlWelcome.getBorder())); pnlWelcome.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

                pnlWelcome.setLayout(new FlowLayout());

                //---- lblWelcome ----
                lblWelcome.setText("Welcome To The Course Registration Tool");
                pnlWelcome.add(lblWelcome);
            }
            tbdPaneRegistration.addTab("Welcome", pnlWelcome);

            //======== pnlAddClass ========
            {

                //======== scrollPane1 ========
                {

                    //---- classList ----
                    classList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    classList.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            list1MouseClicked(e);
                        }
                    });
                    scrollPane1.setViewportView(classList);
                }

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(viewList);
                }

                //======== scrollPane3 ========
                {
                    scrollPane3.setViewportView(list3);
                }

                //---- button1 ----
                button1.setText("View");
                button1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        viewButton_MouseClicked(e);
                    }
                });

                //---- button2 ----
                button2.setText("Add");

                //---- button3 ----
                button3.setText("Refresh");
                button3.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        refreshButton_MouseClicked(e);
                    }
                });

                //---- register ----
                register.setText("Register");

                GroupLayout pnlAddClassLayout = new GroupLayout(pnlAddClass);
                pnlAddClass.setLayout(pnlAddClassLayout);
                pnlAddClassLayout.setHorizontalGroup(
                    pnlAddClassLayout.createParallelGroup()
                        .addGroup(pnlAddClassLayout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(button3, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(button1)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(button2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(register, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                                .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                            .addContainerGap(17, Short.MAX_VALUE))
                );
                pnlAddClassLayout.setVerticalGroup(
                    pnlAddClassLayout.createParallelGroup()
                        .addGroup(pnlAddClassLayout.createSequentialGroup()
                            .addGroup(pnlAddClassLayout.createParallelGroup()
                                .addGroup(pnlAddClassLayout.createSequentialGroup()
                                    .addGap(41, 41, 41)
                                    .addComponent(button3)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(scrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                        .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                        .addComponent(scrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)))
                                .addGroup(pnlAddClassLayout.createSequentialGroup()
                                    .addGap(143, 143, 143)
                                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlAddClassLayout.createSequentialGroup()
                                    .addGap(145, 145, 145)
                                    .addComponent(button2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(register)
                            .addContainerGap(139, Short.MAX_VALUE))
                );
            }
            tbdPaneRegistration.addTab("Add Class", pnlAddClass);

            //======== pnlDropClass ========
            {

                GroupLayout pnlDropClassLayout = new GroupLayout(pnlDropClass);
                pnlDropClass.setLayout(pnlDropClassLayout);
                pnlDropClassLayout.setHorizontalGroup(
                    pnlDropClassLayout.createParallelGroup()
                        .addGap(0, 590, Short.MAX_VALUE)
                );
                pnlDropClassLayout.setVerticalGroup(
                    pnlDropClassLayout.createParallelGroup()
                        .addGap(0, 435, Short.MAX_VALUE)
                );
            }
            tbdPaneRegistration.addTab("Drop Class", pnlDropClass);

            //======== pnlDegProg ========
            {

                GroupLayout pnlDegProgLayout = new GroupLayout(pnlDegProg);
                pnlDegProg.setLayout(pnlDegProgLayout);
                pnlDegProgLayout.setHorizontalGroup(
                    pnlDegProgLayout.createParallelGroup()
                        .addGap(0, 590, Short.MAX_VALUE)
                );
                pnlDegProgLayout.setVerticalGroup(
                    pnlDegProgLayout.createParallelGroup()
                        .addGap(0, 435, Short.MAX_VALUE)
                );
            }
            tbdPaneRegistration.addTab("Degree Progress", pnlDegProg);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(tbdPaneRegistration)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(tbdPaneRegistration, GroupLayout.PREFERRED_SIZE, 461, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 107, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Kevin Baker
    private JTabbedPane tbdPaneRegistration;
    private JPanel pnlWelcome;
    private JLabel lblWelcome;
    private JPanel pnlAddClass;
    private JScrollPane scrollPane1;
    private JList classList;
    private JScrollPane scrollPane2;
    private JList viewList;
    private JScrollPane scrollPane3;
    private JList list3;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton register;
    private JPanel pnlDropClass;
    private JPanel pnlDegProg;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
