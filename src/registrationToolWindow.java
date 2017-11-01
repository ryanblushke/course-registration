import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/*
 * Created by JFormDesigner on Thu Oct 12 16:56:24 CST 2017
 */



/**
 * @author Dexter Gordon-Dirks, Kevin Baker
 */
public class registrationToolWindow extends JFrame {

    private String nsid, password;
    protected Driver BDSM;

    public registrationToolWindow(String nsid, String password) {
        this.nsid = nsid;
        this.password = password;
        this.BDSM = new Driver();
        BDSM.connectToDatabase();
        initComponents();
    }

    private void viewButton_MouseClicked(MouseEvent e) {

        String courseToView;
        String[] courseInfo;
        courseToView = (String) classList.getSelectedValue();

        courseInfo = BDSM.getCourseInformation(courseToView);
        viewList.setListData(courseInfo);
    }

    private void refreshButton_MouseClicked(MouseEvent e) {

        String[] classes;
        classes = BDSM.getClassList(nsid);
        classList.setListData(classes);

    }

    private void Add_Button_MouseClicked(MouseEvent e) {

        int indexInList = viewList.getSelectedIndex();
        if( BDSM.addToSchedule(indexInList) ){
            String[] updatedRegisterList = BDSM.getScheduleAsStringArray();
            registerList.setListData( updatedRegisterList );
        }
        else{
            JOptionPane.showMessageDialog(this,"Class wasn't added to schedule","Error: Add",0);
        }
    }

    private void Remove_Button_MouseClicked(MouseEvent e) {

        String course = (String) registerList.getSelectedValue();
        BDSM.removeFromSchedule(course);
        String[] updatedRegisterList = BDSM.getScheduleAsStringArray();
        registerList.setListData( updatedRegisterList );

    }

    private void register_MouseClicked(MouseEvent e) {
        // TODO add your code here

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
        registerList = new JList();
        button1 = new JButton();
        Add_Button = new JButton();
        button3 = new JButton();
        register = new JButton();
        Remove_Button = new JButton();
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
                    scrollPane1.setViewportView(classList);
                }

                //======== scrollPane2 ========
                {

                    //---- viewList ----
                    viewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    scrollPane2.setViewportView(viewList);
                }

                //======== scrollPane3 ========
                {
                    scrollPane3.setViewportView(registerList);
                }

                //---- button1 ----
                button1.setText("View ->");
                button1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        viewButton_MouseClicked(e);
                    }
                });

                //---- Add_Button ----
                Add_Button.setText("Add ->");
                Add_Button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Add_Button_MouseClicked(e);
                    }
                });

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
                register.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        register_MouseClicked(e);
                    }
                });

                //---- Remove_Button ----
                Remove_Button.setText("Remove");
                Remove_Button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Remove_Button_MouseClicked(e);
                    }
                });

                GroupLayout pnlAddClassLayout = new GroupLayout(pnlAddClass);
                pnlAddClass.setLayout(pnlAddClassLayout);
                pnlAddClassLayout.setHorizontalGroup(
                    pnlAddClassLayout.createParallelGroup()
                        .addGroup(pnlAddClassLayout.createSequentialGroup()
                            .addContainerGap(12, Short.MAX_VALUE)
                            .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(button3, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(button1, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlAddClassLayout.createParallelGroup()
                                .addComponent(Remove_Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Add_Button, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(13, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, pnlAddClassLayout.createSequentialGroup()
                            .addContainerGap(658, Short.MAX_VALUE)
                            .addComponent(register, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                            .addGap(53, 53, 53))
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
                                        .addComponent(scrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                        .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)))
                                .addGroup(pnlAddClassLayout.createSequentialGroup()
                                    .addGap(105, 105, 105)
                                    .addComponent(Add_Button, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                    .addGap(43, 43, 43)
                                    .addComponent(Remove_Button, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlAddClassLayout.createSequentialGroup()
                                    .addGap(143, 143, 143)
                                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(register)
                            .addContainerGap(39, Short.MAX_VALUE))
                );
            }
            tbdPaneRegistration.addTab("Add Class", pnlAddClass);

            //======== pnlDropClass ========
            {

                GroupLayout pnlDropClassLayout = new GroupLayout(pnlDropClass);
                pnlDropClass.setLayout(pnlDropClassLayout);
                pnlDropClassLayout.setHorizontalGroup(
                    pnlDropClassLayout.createParallelGroup()
                        .addGap(0, 871, Short.MAX_VALUE)
                );
                pnlDropClassLayout.setVerticalGroup(
                    pnlDropClassLayout.createParallelGroup()
                        .addGap(0, 335, Short.MAX_VALUE)
                );
            }
            tbdPaneRegistration.addTab("Drop Class", pnlDropClass);

            //======== pnlDegProg ========
            {

                GroupLayout pnlDegProgLayout = new GroupLayout(pnlDegProg);
                pnlDegProg.setLayout(pnlDegProgLayout);
                pnlDegProgLayout.setHorizontalGroup(
                    pnlDegProgLayout.createParallelGroup()
                        .addGap(0, 871, Short.MAX_VALUE)
                );
                pnlDegProgLayout.setVerticalGroup(
                    pnlDegProgLayout.createParallelGroup()
                        .addGap(0, 335, Short.MAX_VALUE)
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
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(tbdPaneRegistration, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 207, Short.MAX_VALUE))
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
    private JList registerList;
    private JButton button1;
    private JButton Add_Button;
    private JButton button3;
    private JButton register;
    private JButton Remove_Button;
    private JPanel pnlDropClass;
    private JPanel pnlDegProg;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
