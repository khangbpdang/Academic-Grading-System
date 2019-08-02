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
public class TAViewGradesGUI extends JFrame {
    Account ta; // TA Account object
    Course course; // current Course object
    Assignment assignment; // current Assignment object
    public static final int WIDTH = 300; // width and height of frame
    public static final int HEIGHT = 200;
    public static final int SMALL_WIDTH = 300;  //for add and edit grade window
    public static final int SMALL_HEIGHT = 200;
    JTable table;
    public TAViewGradesGUI(Account a, Course c, Assignment ass) {
        ta = a; // retains TA Account object, current Course and Assignment objects
        course = c;
        assignment = ass; 
        
        // settings for frame
        setTitle("TAViewGradesGUI");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(4,1));
        
        // first panel - title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("TAViewGradesGUI");
        titlePanel.add(prompt);
        add(titlePanel);
        
        // access list of grades of each student for current course and assignment and load data into table
        HashMap<String, Integer> gradeList = (new Storage()).proftaViewGrades(ta, course.getCourseID(), ass.getName());
        String[] columnNames = {"Student", "Grade"};
        Set set = gradeList.entrySet();
        Object[][] value = new Object[gradeList.entrySet().size()][columnNames.length];
        int index = 0;
        for (String key : gradeList.keySet())
        {
            Integer i = gradeList.get(key);
            value[index][0] = key;
            value[index][1] = i;
            // and so forth
            index++;
        }
        table = new JTable(new DefaultTableModel(value, columnNames));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        // second panel - scroll panel with table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        // third panel - button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());        
        JButton addGradeButton = new JButton("Add Grade");
        addGradeButton.addActionListener(new AddGradeListener());        
        buttonPanel.add(addGradeButton);

        // fourth panel - return button panel
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
           new TAAssignmentsGUIWindow(ta, course).setVisible(true);
           dispose();
        }
    }
    
    // add grade button listener
    private class AddGradeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           new AddGradeWindow().setVisible(true);
           dispose();
        }
    }
    
    // creates an add grade window to input info
    private class AddGradeWindow extends JFrame implements ActionListener {
       public static final int MAX_CHARS = 20; // max characters in text fields
       private JTextField studentText = new JTextField(MAX_CHARS); // student name field
       private JTextField gradeText = new JTextField(MAX_CHARS); // grade value fiend
       public AddGradeWindow() {
           //initialize frame
           super();
           setTitle("AddGradeWindow");
           setSize(SMALL_WIDTH, SMALL_HEIGHT);
           getContentPane().setBackground(Color.WHITE);
           setLayout(new GridLayout(4, 1));
           
           //create and add label
           JPanel titlePanel = new JPanel();
           JLabel titleLabel = new JLabel("Enter Grade Window");
           titleLabel.setBackground(Color.YELLOW);
           titlePanel.add(titleLabel);
           add(titlePanel);
           
           JPanel studentPanel = new JPanel();
           studentPanel.setLayout(new GridLayout(1, 2));
           studentPanel.setBackground(Color.WHITE);
           JLabel studentLabel = new JLabel("Enter student:");
           studentPanel.add(studentLabel);
           studentPanel.add(studentText);
           add(studentPanel);
           
           JPanel gradePanel = new JPanel();
           gradePanel.setLayout(new GridLayout(1, 2)); 
           gradePanel.setBackground(Color.WHITE);
           JLabel gradeLabel = new JLabel("Enter grade:");
           gradePanel.add(gradeLabel);
           gradePanel.add(gradeText);
           add(gradePanel);
           
           //adds button panel and buttons
           JPanel buttonPanel = new JPanel();
           buttonPanel.setBackground(Color.ORANGE);
           buttonPanel.setLayout(new FlowLayout());
           // accepts input field
           JButton OKButton = new JButton("OK");
           OKButton.addActionListener(this);
           buttonPanel.add(OKButton);
           // returns to previous view grade window
           JButton cancelButton = new JButton("Cancel");
           cancelButton.addActionListener(this);
           buttonPanel.add(cancelButton);
           
           add(buttonPanel);
        }
       
       // registers button click
       public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           if(actionCommand.equals("OK")){
               // accesses database to add grade to the right course, assignment and student
               Storage storage = new Storage();
               storage.addGrade(ta, course.getCourseID(), assignment.getName(), studentText.getText(), Integer.parseInt(gradeText.getText()));
               TAViewGradesGUI gui = new TAViewGradesGUI(ta, course, assignment);          
               gui.pack();
               gui.setVisible(true);
               dispose();
            }
           else 
                if (actionCommand.equals("Cancel")) {
                    TAViewGradesGUI gui = new TAViewGradesGUI(ta, course, assignment);
                    gui.pack();
                    gui.setVisible(true);
                    dispose(); 
                }
           else
               System.out.println("Unexpected Error in Add Grade Window.");
       }  
    } //end of inner class AddGradeWindow
}