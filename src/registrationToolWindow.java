

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        BDSM.updateSchedulesFromDB(nsid);
        initComponents();
        updateTables();
    }

    /**
     * Updates term tables in add class tab with classes in schedules.
     */
    public void updateTables(){
        Object[][] times = { {"830"},
                             {"900"},
                             {"930"},
                             {"1000"},
                             {"1030"},
                             {"1100"},
                             {"1130"},
                             {"1200"},
                             {"1230"},
                             {"1300"},
                             {"1330"},
                             {"1400"},
                             {"1430"},
                             {"1500"},
                             {"1530"},
                             {"1600"},
                             {"1630"},
                             {"1700"},
                             {"1730"},
                             {"1800"},
                             {"1830"},
                             {"1900"},
                             {"1930"},
                             {"2000"},
                             {"2030"},
                             {"2100"},
                             {"2130"},
                             {"2200"} };

        String[] columnNames = { "Time", "Monday", "Tuesday", "Wednesday","Thursday","Friday" };

        // TABLE TERM 1
        DefaultTableModel t1Model = new DefaultTableModel(columnNames,0);
        for( Object[] i : times ){
            t1Model.addRow(i);
        }
        for( Course c : BDSM.T1_Schedule.getCoursesInSchedule() ){
            int[] columns;
            if( c.getDays().equals("MWF") ){ columns = new int[]{1, 3, 5}; }
            else if( c.getDays().equals("TR") ){ columns = new int[]{2, 4}; }
            else if( c.getDays().equals("M") ){ columns = new int[]{1}; }
            else if( c.getDays().equals("T") ){ columns = new int[]{2}; }
            else if( c.getDays().equals("W") ){ columns = new int[]{3}; }
            else if( c.getDays().equals("R") ){ columns = new int[]{4}; }
            else if( c.getDays().equals("F") ){ columns = new int[]{5}; }
            else{ columns = null; }


            if( columns != null ) {
                int row = getRowInTableFromTime(c);
                for (int col : columns) {
                    t1Model.setValueAt(c.getName(), row, col);
                }
            }
        }
        tableTermOneSchedule.setModel(t1Model);

        // TABLE TERM 2
        DefaultTableModel t2Model = new DefaultTableModel(columnNames, 0);
        for( Object[] i : times ){
            t2Model.addRow(i);
        }
        for( Course c : BDSM.T2_Schedule.getCoursesInSchedule() ){
            int[] columns;
            if( c.getDays().equals("MWF") ){ columns = new int[]{1, 3, 5}; }
            else if( c.getDays().equals("TR") ){ columns = new int[]{2, 4}; }
            else if( c.getDays().equals("M") ){ columns = new int[]{1}; }
            else if( c.getDays().equals("T") ){ columns = new int[]{2}; }
            else if( c.getDays().equals("W") ){ columns = new int[]{3}; }
            else if( c.getDays().equals("R") ){ columns = new int[]{4}; }
            else if( c.getDays().equals("F") ){ columns = new int[]{5}; }
            else{ columns = null; }


            if( columns != null ) {
                int row = getRowInTableFromTime(c);
                for (int col : columns) {
                    t2Model.setValueAt(c.getName(), row, col);
                }
            }
        }
        tableTermTwoSchedule.setModel(t2Model);
    }


    private int getRowInTableFromTime(Course c){
        switch( c.getStartTime() ){
            case 830: return 0;
            case 900: return 1;
            case 930: return 2;
            case 1000: return 3;
            case 1030: return 4;
            case 1100: return 5;
            case 1130: return 6;
            case 1200: return 7;
            case 1230: return 8;
            case 1300: return 9;
            case 1330: return 10;
            case 1400: return 11;
            case 1430: return 12;
            case 1500: return 13;
            case 1530: return 14;
            case 1600: return 15;
            case 1630: return 16;
            case 1700: return 17;
            case 1730: return 18;
            case 1800: return 19;
            case 1830: return 20;
            case 1900: return 21;
            case 1930: return 22;
            case 2000: return 23;
            case 2030: return 24;
            case 2100: return 25;
            case 2130: return 26;
            case 2200: return 27;
            default: return -1;
        }
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
                updateTables();
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
        updateTables();
    }

    private void btnRegisterMouseClicked(MouseEvent e) {
        String error;
        if( (error = BDSM.addRegisterListToDatabase(nsid)) == null ){
            listRegisterAdd.setListData( new String[0] );
            listInitialAdd.setListData( BDSM.populateCourseList(nsid) );
            JOptionPane.showMessageDialog(this, "Registration Successful.","Registration Tool",JOptionPane.INFORMATION_MESSAGE);
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
            listViewAdd.setListData(courseInfo);
        }
    }

    private void btnRefreshDropMouseClicked(MouseEvent e) {
        // TODO add your code here
        String[] classes;
        classes = BDSM.getDroppableCourses(nsid);
        listInitialDrop.setListData(classes);
    }

    private void btnInitialAddDropMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void listInitialDropMouseClicked(MouseEvent e) {
        // TODO add your code here
        String courseToView;
        String[] courseInfo;

        if( !listInitialDrop.isSelectionEmpty() ) {
            courseToView = (String) listInitialDrop.getSelectedValue();
            courseInfo = BDSM.getCourseInformation(courseToView);
            listViewAdd2.setListData(courseInfo);
        }
    }

    private void btnRefreshDegProgMouseClicked(MouseEvent e) {
        String[] incomplete = BDSM.populateIncomplete(nsid);
        String[] complete = BDSM.populateComplete(nsid);
        listDegProgIncomplete.setListData(incomplete);
        listDegProgComplete.setListData(complete);
        degreeProgressBar.setValue((int) BDSM.updateProgressBar(nsid));
        degreeProgressBar.setStringPainted(true);
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Lane Larochelle
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
        scrollPaneForTermOneSchedule = new JScrollPane();
        tableTermOneSchedule = new JTable();
        scrollPaneForTermTwoSchedule = new JScrollPane();
        tableTermTwoSchedule = new JTable();
        label2 = new JLabel();
        label3 = new JLabel();
        pnlDropClass = new JPanel();
        scrlInitialDrop = new JScrollPane();
        listInitialDrop = new JList();
        scrlViewDrop = new JScrollPane();
        listViewAdd2 = new JList();
        btnRefreshDrop = new JButton();
        btnInitialDrop = new JButton();
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
        label_incomplete = new JLabel();
        label1 = new JLabel();
        degreeProgressBar = new JProgressBar();

        //======== this ========
        setTitle("Course Registation Tool");
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

                //======== scrollPaneForTermOneSchedule ========
                {
                    scrollPaneForTermOneSchedule.setViewportView(tableTermOneSchedule);
                }

                //======== scrollPaneForTermTwoSchedule ========
                {
                    scrollPaneForTermTwoSchedule.setViewportView(tableTermTwoSchedule);
                }

                //---- label2 ----
                label2.setText("Term 1 Schedule");

                //---- label3 ----
                label3.setText("Term 2 Schedule");

                GroupLayout pnlAddClassLayout = new GroupLayout(pnlAddClass);
                pnlAddClass.setLayout(pnlAddClassLayout);
                pnlAddClassLayout.setHorizontalGroup(
                    pnlAddClassLayout.createParallelGroup()
                        .addGroup(pnlAddClassLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(pnlAddClassLayout.createParallelGroup()
                                .addGroup(pnlAddClassLayout.createSequentialGroup()
                                    .addGroup(pnlAddClassLayout.createParallelGroup()
                                        .addComponent(scrollPaneForTermOneSchedule, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label2))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(pnlAddClassLayout.createParallelGroup()
                                        .addComponent(scrollPaneForTermTwoSchedule, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(pnlAddClassLayout.createSequentialGroup()
                                            .addComponent(label3)
                                            .addGap(0, 0, Short.MAX_VALUE))))
                                .addGroup(pnlAddClassLayout.createSequentialGroup()
                                    .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnRefreshAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(scrlPaneInitialAdd, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(scrlPaneViewAdd, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlAddClassLayout.createParallelGroup()
                                        .addComponent(btnInitialAdd, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnInitialRemove, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pnlAddClassLayout.createParallelGroup()
                                        .addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(scrlPaneConfirmAdd, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))))
                            .addGap(10, 10, 10))
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
                                    .addGap(45, 45, 45)
                                    .addComponent(btnInitialRemove, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlAddClassLayout.createParallelGroup()
                                .addComponent(btnRefreshAdd)
                                .addComponent(btnRegister))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(pnlAddClassLayout.createSequentialGroup()
                                    .addGroup(pnlAddClassLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label2)
                                        .addComponent(label3))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(scrollPaneForTermOneSchedule, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
                                .addComponent(scrollPaneForTermTwoSchedule, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap())
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

                //---- btnInitialDrop ----
                btnInitialDrop.setText(" Drop ->");
                btnInitialDrop.addMouseListener(new MouseAdapter() {
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
                                    .addGroup(pnlDropClassLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnInitialDrop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnInitialRemove2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlDropClassLayout.createSequentialGroup()
                                    .addComponent(btnRefreshDrop, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnConfirmDrop, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(8, Short.MAX_VALUE))
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
                                    .addComponent(btnInitialDrop, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                    .addGap(46, 46, 46)
                                    .addComponent(btnInitialRemove2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlDropClassLayout.createParallelGroup()
                                .addComponent(btnRefreshDrop)
                                .addComponent(btnConfirmDrop))
                            .addContainerGap(314, Short.MAX_VALUE))
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

                //---- label_incomplete ----
                label_incomplete.setText("Incomplete");
                label_incomplete.setHorizontalAlignment(SwingConstants.CENTER);

                //---- label1 ----
                label1.setText("Complete");
                label1.setHorizontalAlignment(SwingConstants.CENTER);

                GroupLayout pnlDegProgLayout = new GroupLayout(pnlDegProg);
                pnlDegProg.setLayout(pnlDegProgLayout);
                pnlDegProgLayout.setHorizontalGroup(
                    pnlDegProgLayout.createParallelGroup()
                        .addGroup(pnlDegProgLayout.createSequentialGroup()
                            .addGap(131, 131, 131)
                            .addGroup(pnlDegProgLayout.createParallelGroup()
                                .addComponent(scrlDegProgIncomplete, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_incomplete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                            .addGroup(pnlDegProgLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, pnlDegProgLayout.createSequentialGroup()
                                    .addComponent(btnRefreshDegProg, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18))
                                .addGroup(GroupLayout.Alignment.TRAILING, pnlDegProgLayout.createSequentialGroup()
                                    .addComponent(degreeProgressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(40, 40, 40)))
                            .addGroup(pnlDegProgLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(label1, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                .addComponent(scrlDegProgComplete, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                            .addContainerGap(174, Short.MAX_VALUE))
                );
                pnlDegProgLayout.setVerticalGroup(
                    pnlDegProgLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, pnlDegProgLayout.createSequentialGroup()
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlDegProgLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(scrlDegProgIncomplete, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addComponent(scrlDegProgComplete, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addComponent(btnRefreshDegProg, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlDegProgLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(pnlDegProgLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label_incomplete)
                                    .addComponent(label1))
                                .addComponent(degreeProgressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(55, 55, 55))
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
                    .addComponent(tbdPaneRegistration)
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
    // Generated using JFormDesigner Evaluation license - Lane Larochelle
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
    private JScrollPane scrollPaneForTermOneSchedule;
    private JTable tableTermOneSchedule;
    private JScrollPane scrollPaneForTermTwoSchedule;
    private JTable tableTermTwoSchedule;
    private JLabel label2;
    private JLabel label3;
    private JPanel pnlDropClass;
    private JScrollPane scrlInitialDrop;
    private JList listInitialDrop;
    private JScrollPane scrlViewDrop;
    private JList listViewAdd2;
    private JButton btnRefreshDrop;
    private JButton btnInitialDrop;
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
    private JLabel label_incomplete;
    private JLabel label1;
    private JProgressBar degreeProgressBar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
