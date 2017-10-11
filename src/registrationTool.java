import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Mon Oct 09 13:15:35 CST 2017
 */



/**
 * @author Dexter Gordon-Dirks
 */
public class registrationTool extends JFrame {
    public registrationTool() {
        initComponents();
    }

    private void btnClassSearchMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void btnDropClassMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Dexter Gordon-Dirks
        tabbedPane1 = new JTabbedPane();
        pnlWelcome = new JPanel();
        lblWelcome = new JLabel();
        pnlAdd = new JPanel();
        btnClassSearch = new JButton();
        pnlDrop = new JPanel();
        btnDropClass = new JButton();
        pnlDegProg = new JPanel();
        progBarDegree = new JProgressBar();
        pnlSchedule = new JPanel();

        //======== this ========
        Container contentPane = getContentPane();

        //======== tabbedPane1 ========
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
            tabbedPane1.addTab("Welcome", pnlWelcome);

            //======== pnlAdd ========
            {
                pnlAdd.setLayout(new FlowLayout());

                //---- btnClassSearch ----
                btnClassSearch.setText("Search For Classes");
                btnClassSearch.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnClassSearchMouseClicked(e);
                    }
                });
                pnlAdd.add(btnClassSearch);
            }
            tabbedPane1.addTab("Add Class", pnlAdd);

            //======== pnlDrop ========
            {
                pnlDrop.setLayout(new FlowLayout());

                //---- btnDropClass ----
                btnDropClass.setText("Drop Class");
                btnDropClass.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnDropClassMouseClicked(e);
                    }
                });
                pnlDrop.add(btnDropClass);
            }
            tabbedPane1.addTab("Drop Class", pnlDrop);

            //======== pnlDegProg ========
            {
                pnlDegProg.setLayout(new FlowLayout());
                pnlDegProg.add(progBarDegree);
            }
            tabbedPane1.addTab("Degree Progress", pnlDegProg);

            //======== pnlSchedule ========
            {
                pnlSchedule.setLayout(new FlowLayout());
            }
            tabbedPane1.addTab("Schedule", pnlSchedule);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tabbedPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tabbedPane1, GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Dexter Gordon-Dirks
    private JTabbedPane tabbedPane1;
    private JPanel pnlWelcome;
    private JLabel lblWelcome;
    private JPanel pnlAdd;
    private JButton btnClassSearch;
    private JPanel pnlDrop;
    private JButton btnDropClass;
    private JPanel pnlDegProg;
    private JProgressBar progBarDegree;
    private JPanel pnlSchedule;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
