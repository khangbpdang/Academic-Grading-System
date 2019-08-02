import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.io.Serializable;
import java.util.*;

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
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class ProfRosterWindow extends JFrame
{
    public static final int WIDTH = 300; // width and height for frame
    public static final int HEIGHT = 200;
    private Account p; // prof Account object and current Course object
    private Course course;

    JTable table;

    

    public ProfRosterWindow(Account a, Course c) {
        p = a; // retains prof Account object and current Course object
        course = c;

        // setting for frame
        setTitle("ProfRosterWindow");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(3,1));

        // first panel - title panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        JLabel prompt = new JLabel("Roster List");
        topPanel.add(prompt);
        add(topPanel);

        // access student roster list within the current Course in database and load onto table
        String[] columns = {"Student"};
        Storage storage = new Storage(); 
        HashSet<String> roster = storage.getStudentRoster(a, course);
        Object[][] values = new Object[roster.size()][columns.length]; 
        int index = 0;
        Iterator<String> rosterIterator = roster.iterator();
        while (rosterIterator.hasNext()) {
            values[index][0]= rosterIterator.next();
            index++;
        }
        table = new JTable(new DefaultTableModel(values, columns));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        // second panel - scroll panel with table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        
        // third panel - return button panel
        JPanel returnPanel = new JPanel();
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
           new ProfessorOptionsGUIWindow(p, course).setVisible(true);
        }
    }
}