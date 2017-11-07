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
        BDSM.getSchedulesFromDB(nsid);
        initComponents();
    }

    private void btnRefreshAddMouseClicked(MouseEvent e) {
        String[] classes;
        classes = BDSM.populateCourseList(nsid);
        listInitialAdd.setListData(classes);
    }

    private void btnInitialAddMouseClicked(MouseEvent e) {
        int indexInList = listViewAdd.getSelectedIndex();
        String error;
        if( !listViewAdd.isSelectionEmpty() ) {
            if ((error = BDSM.addToSchedule(indexInList)) == null) {
                String[] updatedRegisterList = BDSM.getScheduleAsStringArray();
                listRegisterAdd.setListData(updatedRegisterList);
            } else {
                JOptionPane.showMessageDialog(this, error, "Error: Add", 0);
            }
        }
    }

    private void btnInitialRemoveMouseClicked(MouseEvent e) {
        String course = (String) listRegisterAdd.getSelectedValue();
        BDSM.removeFromSchedule(course);
        String[] updatedRegisterList = BDSM.getScheduleAsStringArray();
        listRegisterAdd.setListData( updatedRegisterList );

    }

    private void btnRegisterMouseClicked(MouseEvent e) {
        String error;
        if( (error = BDSM.addRegisterListToDatabase(nsid)) == null ){
            listRegisterAdd.setListData( new String[0] );
        } else {
            JOptionPane.showMessageDialog(this, error,"Error: Registering",0);
        }
    }

    private void listInitialAddMouseClicked(MouseEvent e) {
        String courseToView;
        String[] courseInfo;

        if( !listInitialAdd.isSelectionEmpty() ) {
            courseToView = (String) listInitialAdd.getSelectedValue();
            courseInfo = BDSM.getCourseInformation(courseToView);
            listInitialAdd.setListData(courseInfo);
        }
    }

    private void btnRefreshDropMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void btnInitialAddDropMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void listInitialDropMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void btnRefreshDegProgMouseClicked(MouseEvent e) {
        // TODO add your code here
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Dexter Gordon-Dirks
        tbdPaneRegistration = new JTabbedPane();
        pnlWelcome = new JPanel();
        lblWelcome = new JLabel();
        pnlAddClass = new JPanel();
        scrlPaneInitialAdd = new JScrollPane();
        listInitialAdd = new JList();
        scrlPaneViewAdd = new JScrollPane();
        listViewAdd = new JList();
        scrlPaneConfirmAdd = new JScrollPane();
        listRegisterAdd = new JList();
        btnInitialAdd = new JButton();
        btnRefreshAdd = new JButton();
        btnRegister = new JButton();
        btnInitialRemove = new JButton();
        pnlDropClass = new JPanel();
        scrlInitialDrop = new JScrollPane();
        listInitialDrop = new JList();
        scrlViewDrop = new JScrollPane();
        listViewAdd2 = new JList();
        btnRefreshDrop = new JButton();
        btnInitialAddDrop = new JButton();
        btnInitialRemove2 = new JButton();
        scrollPane1 = new JScrollPane();
        listRegisterDrop = new JList();
        btnConfirmDrop = new JButton();
        pnlDegProg = new JPanel();
        scrlDegProgIncomplete = new JScrollPane();
        listDegProgIncomplete = new JList();
        scrlDegProgComplete = new JScrollPane();
        listDegProgComplete = new JList();
        btnRefreshDegProg = new JButton();

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

                //======== scrlPaneInitialAdd ========
                {

                    //---- listInitialAdd ----
                    listInitialAdd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listInitialAdd.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            listInitialAddMouseClicked(e);
                        }
                    });
                    scrlPaneInitialAdd.setViewportView(listInitialAdd);
                }

                //======== scrlPaneViewAdd ========
                {

                    //---- listViewAdd ----
                    listViewAdd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    scrlPaneViewAdd.setViewportView(listViewAdd);
                }

                //======== scrlPaneConfirmAdd ========
                {
                    scrlPaneConfirmAdd.setViewportView(listRegisterAdd);
                }

                //---- btnInitialAdd ----
                btnInitialAdd.setText("Add ->");
                btnInitialAdd.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnInitialAddMouseClicked(e);
                    }
                });

                //---- btnRefreshAdd ----
                btnRefreshAdd.setText("Refresh");
                btnRefreshAdd.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnRefreshAddMouseClicked(e);
                    }
                });

                //---- btnRegister ----
                btnRegister.setText("Register");
                btnRegister.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnRegisterMouseClicked(e);
                    }
                });

                //---- btnInitialRemove ----
                btnInitialRemove.setText("Remove");
                btnInitialRemove.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnInitialRemoveMouseClicked(e);
                    }
                });

                GroupLayout pnlAddClassLayout = new GroupLayout(pnlAddClass);
                pnlAddClass.setLayout(pnlAddClassLayout);
                pnlAddClassLayout.setHorizontalGroup(
                    pnlAddClassLayout.createParallelGroup()
                        .addGroup(pnlAddClassLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnRefreshAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scrlPaneInitialAdd, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(scrlPaneViewAdd, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlAddClassLayout.createParallelGroup()
                                .addComponent(btnInitialRemove, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlAddClassLayout.createSequentialGroup()
                                    .addComponent(btnInitialAdd, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(pnlAddClassLayout.createParallelGroup()
                                .addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
                                .addComponent(scrlPaneConfirmAdd, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
                            .addGap(16, 16, 16))
                );
                pnlAddClassLayout.setVerticalGroup(
                    pnlAddClassLayout.createParallelGroup()
                        .addGroup(pnlAddClassLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(pnlAddClassLayout.createParallelGroup()
                                .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(scrlPaneInitialAdd)
                                    .addComponent(scrlPaneViewAdd, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scrlPaneConfirmAdd, GroupLayout.Alignment.TRAILING))
                                .addGroup(pnlAddClassLayout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addComponent(btnInitialAdd, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                    .addGap(46, 46, 46)
                                    .addComponent(btnInitialRemove, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlAddClassLayout.createParallelGroup()
                                .addComponent(btnRefreshAdd)
                                .addComponent(btnRegister))
                            .addContainerGap(331, Short.MAX_VALUE))
                );
            }
            tbdPaneRegistration.addTab("Add Class", pnlAddClass);

            //======== pnlDropClass ========
            {

                //======== scrlInitialDrop ========
                {

                    //---- listInitialDrop ----
                    listInitialDrop.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listInitialDrop.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            listInitialDropMouseClicked(e);
                        }
                    });
                    scrlInitialDrop.setViewportView(listInitialDrop);
                }

                //======== scrlViewDrop ========
                {

                    //---- listViewAdd2 ----
                    listViewAdd2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    scrlViewDrop.setViewportView(listViewAdd2);
                }

                //---- btnRefreshDrop ----
                btnRefreshDrop.setText("Refresh");
                btnRefreshDrop.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnRefreshDropMouseClicked(e);
                    }
                });

                //---- btnInitialAddDrop ----
                btnInitialAddDrop.setText("Add ->");
                btnInitialAddDrop.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnInitialAddDropMouseClicked(e);
                    }
                });

                //---- btnInitialRemove2 ----
                btnInitialRemove2.setText("Remove");
                btnInitialRemove2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnInitialRemoveMouseClicked(e);
                    }
                });

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(listRegisterDrop);
                }

                //---- btnConfirmDrop ----
                btnConfirmDrop.setText("Confirm Drop Class");

                GroupLayout pnlDropClassLayout = new GroupLayout(pnlDropClass);
                pnlDropClass.setLayout(pnlDropClassLayout);
                pnlDropClassLayout.setHorizontalGroup(
                    pnlDropClassLayout.createParallelGroup()
                        .addGroup(pnlDropClassLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(pnlDropClassLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(pnlDropClassLayout.createSequentialGroup()
                                    .addComponent(scrlInitialDrop, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(scrlViewDrop, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlDropClassLayout.createParallelGroup()
                                        .addComponent(btnInitialAddDrop, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnInitialRemove2, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlDropClassLayout.createSequentialGroup()
                                    .addComponent(btnRefreshDrop, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnConfirmDrop, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(13, Short.MAX_VALUE))
                );
                pnlDropClassLayout.setVerticalGroup(
                    pnlDropClassLayout.createParallelGroup()
                        .addGroup(pnlDropClassLayout.createSequentialGroup()
                            .addGroup(pnlDropClassLayout.createParallelGroup()
                                .addGroup(pnlDropClassLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(pnlDropClassLayout.createParallelGroup()
                                        .addComponent(scrlInitialDrop, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(scrlViewDrop, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnlDropClassLayout.createSequentialGroup()
                                    .addGap(38, 38, 38)
                                    .addComponent(btnInitialAddDrop, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                    .addGap(46, 46, 46)
                                    .addComponent(btnInitialRemove2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlDropClassLayout.createParallelGroup()
                                .addComponent(btnRefreshDrop)
                                .addComponent(btnConfirmDrop))
                            .addContainerGap(331, Short.MAX_VALUE))
                );
            }
            tbdPaneRegistration.addTab("Drop Class", pnlDropClass);

            //======== pnlDegProg ========
            {

                //======== scrlDegProgIncomplete ========
                {
                    scrlDegProgIncomplete.setViewportView(listDegProgIncomplete);
                }

                //======== scrlDegProgComplete ========
                {
                    scrlDegProgComplete.setViewportView(listDegProgComplete);
                }

                //---- btnRefreshDegProg ----
                btnRefreshDegProg.setText("Refresh Degree Progress");
                btnRefreshDegProg.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnRefreshDegProgMouseClicked(e);
                    }
                });

                GroupLayout pnlDegProgLayout = new GroupLayout(pnlDegProg);
                pnlDegProg.setLayout(pnlDegProgLayout);
                pnlDegProgLayout.setHorizontalGroup(
                    pnlDegProgLayout.createParallelGroup()
                        .addGroup(pnlDegProgLayout.createSequentialGroup()
                            .addGap(131, 131, 131)
                            .addComponent(scrlDegProgIncomplete, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                            .addComponent(btnRefreshDegProg, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(scrlDegProgComplete, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(180, Short.MAX_VALUE))
                );
                pnlDegProgLayout.setVerticalGroup(
                    pnlDegProgLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, pnlDegProgLayout.createSequentialGroup()
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlDegProgLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(scrlDegProgIncomplete, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addComponent(scrlDegProgComplete, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addComponent(btnRefreshDegProg, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                            .addGap(88, 88, 88))
                );
            }
            tbdPaneRegistration.addTab("Degree Progress", pnlDegProg);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tbdPaneRegistration, GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tbdPaneRegistration, GroupLayout.Alignment.TRAILING)
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
    private JScrollPane scrlPaneInitialAdd;
    private JList listInitialAdd;
    private JScrollPane scrlPaneViewAdd;
    private JList listViewAdd;
    private JScrollPane scrlPaneConfirmAdd;
    private JList listRegisterAdd;
    private JButton btnInitialAdd;
    private JButton btnRefreshAdd;
    private JButton btnRegister;
    private JButton btnInitialRemove;
    private JPanel pnlDropClass;
    private JScrollPane scrlInitialDrop;
    private JList listInitialDrop;
    private JScrollPane scrlViewDrop;
    private JList listViewAdd2;
    private JButton btnRefreshDrop;
    private JButton btnInitialAddDrop;
    private JButton btnInitialRemove2;
    private JScrollPane scrollPane1;
    private JList listRegisterDrop;
    private JButton btnConfirmDrop;
    private JPanel pnlDegProg;
    private JScrollPane scrlDegProgIncomplete;
    private JList listDegProgIncomplete;
    private JScrollPane scrlDegProgComplete;
    private JList listDegProgComplete;
    private JButton btnRefreshDegProg;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
