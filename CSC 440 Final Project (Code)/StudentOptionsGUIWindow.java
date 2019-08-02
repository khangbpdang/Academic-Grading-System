import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GradientPaint;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
public class StudentOptionsGUIWindow extends JFrame {
    public static final int WIDTH = 400; // width of frame
    public static final int HEIGHT = 300; // height of frame
    Account s; // student Account object
    Course course; // chosen Course object
    public StudentOptionsGUIWindow(Account a, Course c) {
        s = a; // retains Account object
        course = c; // retain Course object
        
        // set settings for frame
        setTitle("StudentOptionsGUIWindow"); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(3,1));
        
        // first panel - title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("StudentOptionsGUIWindow");
        titlePanel.add(prompt);
        add(titlePanel);
        
        // second panel - button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        add(buttonPanel);
        // view grades Button
        JButton viewGradesButton = new JButton("View Grades");
        viewGradesButton.addActionListener(new ViewGradesListener());
        buttonPanel.add(viewGradesButton);
        // drop out of course Button
        JButton deleteCourseButton = new JButton("Drop Out");
        deleteCourseButton.addActionListener(new DropOutListener());
        buttonPanel.add(deleteCourseButton);
        
        // third panel - return button panel
        JPanel returnPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
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
           new StudentGUIWindow(s).setVisible(true);
        }
    }
    
    // view grade button listener
    private class ViewGradesListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           StudentViewGradesGUI gui = new StudentViewGradesGUI(s, course);          
           gui.pack();
           gui.setVisible(true);
           dispose();
        }
    }
    
    // drop out button listener
    private class DropOutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();          
            Storage storage = new Storage();
            s = storage.studentDropOut(s, course);
            (new StudentGUIWindow(s)).setVisible(true);
            dispose();
        }
    }
}
