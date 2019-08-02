import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.util.*;
public class TAViewAssignmentsGUI extends JFrame {
    Account ta; // TA Account object
    Course course; // current Course object
    public static final int WIDTH = 300; // width and height of frame
    public static final int HEIGHT = 200;
    JTable table; // table of assignments in current Course
    public TAViewAssignmentsGUI(Account a, Course c) {
        ta = a; // retains TA Account object and current Course object
        course = c;
        
        // settings for frame
        setTitle("TAViewAssignmentsGUI");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(3,1));
        
        // first panel - title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("TAViewAssignmentsGUI");
        titlePanel.add(prompt);
        add(titlePanel);
        
        
        
        // Storage access to gain list of Assignment objects in the current course and loads data onto table
        HashSet<Assignment> assignmentList = (new Storage()).getAssignmentList(a, c);
        String[] columnNames = {"Assignment", "Description"};
        Iterator<Assignment> assignmentListIterator = assignmentList.iterator();
        Object[][] value = new Object[assignmentList.size()][columnNames.length];
        int index = 0; // index number for row access
        while (assignmentListIterator.hasNext()) {
            Assignment newAss = assignmentListIterator.next();
            value[index][0] = newAss.getName();
            value[index][1] = newAss.getDesc();
            index++;
        }
        table = new JTable(new DefaultTableModel(value, columnNames));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        // second panel - scroll panel with table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        JPanel returnPanel = new JPanel();
        
        // third panel - return button panel
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ReturnListener());        
        returnPanel.add(returnButton);
        add(returnPanel);
    }
    
    // return button listener
    private class ReturnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           dispose();
           new TAOptionsGUIWindow(ta, course).setVisible(true);
        }
    }    
}
