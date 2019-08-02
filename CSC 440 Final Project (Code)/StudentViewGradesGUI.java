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
public class StudentViewGradesGUI extends JFrame {    
    Account s; // student Account object
    Course course; // current Course object
    public static final int WIDTH = 300; // width of frame
    public static final int HEIGHT = 200; // height of frame
    JTable table; // table to display student grades for each assignment in the course
    public StudentViewGradesGUI(Account a, Course c) {
        s = a; // retains student Account object
        course = c; // retains current Course object
        
        // set settings for frame
        setTitle("StudentViewGradesGUI");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(4,1));
        
        // first panel - title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("StudentViewGradesGUI");
        titlePanel.add(prompt);
        add(titlePanel);
        
        // accesses student grade list within current course and load data on to table
        HashMap<Assignment, Integer> gradeList = (new Storage()).studentViewGrades(s, course.getCourseID());
        String[] columnNames = {"Assignment", "Description", "Grade"};
        Set set = gradeList.entrySet();        
        Object[][] value = new Object[gradeList.entrySet().size()][columnNames.length];
        int index = 0; // index number for row access
        for (Assignment key : gradeList.keySet())
        {
            Integer i = gradeList.get(key);
            value[index][0] = key.getName();
            value[index][1] = key.getDesc();
            value[index][2] = i;
            // and so forth
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
           new StudentOptionsGUIWindow(s, course).setVisible(true);
        }
    }
}
