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
        pnlDegProg = new JPanel();

        //======== this ========
        Container contentPane = getContentPane();

        //======== tbdPaneRegistration ========
        {

            //======== pnlWelcome ========
            {

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
                                .addComponent(btnInitialRemove, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                .addComponent(btnInitialAdd, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
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
                                    .addGap(35, 35, 35)
                                    .addComponent(btnInitialAdd, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                    .addGap(43, 43, 43)
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

                GroupLayout pnlDropClassLayout = new GroupLayout(pnlDropClass);
                pnlDropClass.setLayout(pnlDropClassLayout);
                pnlDropClassLayout.setHorizontalGroup(
                    pnlDropClassLayout.createParallelGroup()
                        .addGap(0, 871, Short.MAX_VALUE)
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
                        .addGap(0, 871, Short.MAX_VALUE)
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
    private JPanel pnlDegProg;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
