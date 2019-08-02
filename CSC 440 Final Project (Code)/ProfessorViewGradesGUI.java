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
public class ProfessorViewGradesGUI extends JFrame {
    
    Account p; // prof Account object, current Course and Assignment objects
    Course course;
    Assignment assignment;
    public static final int WIDTH = 300; // width and height for frame
    public static final int HEIGHT = 200;
    public static final int SMALL_WIDTH = 300;  //for add and edit grade window
    public static final int SMALL_HEIGHT = 200;
    JTable table;
    public ProfessorViewGradesGUI(Account a, Course c, Assignment ass) {
        p = a; // retains prof Account object, current Course and Assignment objects
        course = c;
        assignment = ass; 
        
        // settings for frame
        setTitle("ProfessorViewGradesGUI");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(4,1));
        
        // first panel - title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("ProfessorViewGradesGUI");
        titlePanel.add(prompt);
        add(titlePanel);
       
        // access grade list of current course and assignment in Storage
        HashMap<String, Integer> gradeList = (new Storage()).proftaViewGrades(p, course.getCourseID(), ass.getName());
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
        // add grade button
        JButton addGradeButton = new JButton("Add Grade");
        addGradeButton.addActionListener(new AddGradeListener());        
        buttonPanel.add(addGradeButton);
        // edit grade button
        JButton editGradeButton = new JButton("Edit Grade");
        editGradeButton.addActionListener(new EditGradeListener());        
        buttonPanel.add(editGradeButton);
        add(buttonPanel);
        
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
           new ProfessorAssignmentsGUIWindow(p, course).setVisible(true);
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
    
    // edit grade button listener
    private class EditGradeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           new EditGradeWindow().setVisible(true);
           dispose();
        }
    }
       //inner private class for add grade window frame and action listener
    private class AddGradeWindow extends JFrame implements ActionListener {
       public static final int MAX_CHARS = 20; // max char in student and grade fields
       private JTextField studentText = new JTextField(MAX_CHARS); // student name and grade fields
       private JTextField gradeText = new JTextField(MAX_CHARS);
       public AddGradeWindow() {
           //initialize frame
           super();
           
           // settings for frame
           setTitle("AddGradeWindow");
           setSize(SMALL_WIDTH, SMALL_HEIGHT);
           getContentPane().setBackground(Color.WHITE);
           setLayout(new GridLayout(4, 1));
           
           // first panel - title panel
           JPanel titlePanel = new JPanel();
           JLabel titleLabel = new JLabel("Enter Grade Window");
           titleLabel.setBackground(Color.YELLOW);
           titlePanel.add(titleLabel);
           add(titlePanel);
           
           // second panel - student name label and field
           JPanel studentPanel = new JPanel();
           studentPanel.setLayout(new GridLayout(1, 2));
           studentPanel.setBackground(Color.WHITE);
           JLabel studentLabel = new JLabel("Enter student:");
           studentPanel.add(studentLabel);
           studentPanel.add(studentText);
           add(studentPanel);
           
           // third panel - grade label and field
           JPanel gradePanel = new JPanel();
           gradePanel.setLayout(new GridLayout(1, 2)); 
           gradePanel.setBackground(Color.WHITE);
           JLabel gradeLabel = new JLabel("Enter grade:");
           gradePanel.add(gradeLabel);
           gradePanel.add(gradeText);
           add(gradePanel);
           
           // fourth panel - button panel
           JPanel buttonPanel = new JPanel();
           buttonPanel.setBackground(Color.ORANGE);
           buttonPanel.setLayout(new FlowLayout());
           // confirm button
           JButton exitButton = new JButton("OK");
           exitButton.addActionListener(this);
           buttonPanel.add(exitButton);
           // cancel button
           JButton cancelButton = new JButton("Cancel");
           cancelButton.addActionListener(this);
           buttonPanel.add(cancelButton);    
           add(buttonPanel);
        }
       
       // register button click
       public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();           
           if(actionCommand.equals("OK")){
               Storage storage = new Storage();
               storage.addGrade(p, course.getCourseID(), assignment.getName(), studentText.getText(), Integer.parseInt(gradeText.getText()));
               ProfessorViewGradesGUI gui = new ProfessorViewGradesGUI(p, course, assignment);          
               gui.pack();
               gui.setVisible(true);
               dispose();
            }
           else 
                if (actionCommand.equals("Cancel")) {
                    ProfessorViewGradesGUI gui = new ProfessorViewGradesGUI(p, course, assignment);
                    gui.pack();
                    gui.setVisible(true);
                    dispose(); 
                }
           else
               System.out.println("Unexpected Error in Add Grade Window.");
       }  
    } //end of inner class AddGradeWindow
    private class EditGradeWindow extends JFrame implements ActionListener {
       public static final int MAX_CHARS = 20; // max char in student and grade fields
       private JTextField studentText = new JTextField(MAX_CHARS); // student name and grade fields
       private JTextField gradeText = new JTextField(MAX_CHARS);
       public EditGradeWindow() {
           //initialize frame
           super();
           
           // settings for frame
           setTitle("EditGradeWindow");
           setSize(SMALL_WIDTH, SMALL_HEIGHT);
           getContentPane().setBackground(Color.WHITE);
           setLayout(new GridLayout(4, 1));
           
           // first panel - title panel
           JPanel titlePanel = new JPanel();
           JLabel titleLabel = new JLabel("Edit Grade Window");
           titleLabel.setBackground(Color.YELLOW);
           titlePanel.add(titleLabel);
           add(titlePanel);
           
           // second panel - student name label and field
           JPanel studentPanel = new JPanel();
           studentPanel.setLayout(new GridLayout(1, 2));
           studentPanel.setBackground(Color.WHITE);
           JLabel studentLabel = new JLabel("Enter student:");
           studentPanel.add(studentLabel);
           studentPanel.add(studentText);
           add(studentPanel);
           
           // third panel - grade label and field
           JPanel gradePanel = new JPanel();
           gradePanel.setLayout(new GridLayout(1, 2)); 
           gradePanel.setBackground(Color.WHITE);
           JLabel gradeLabel = new JLabel("Enter grade:");
           gradePanel.add(gradeLabel);
           gradePanel.add(gradeText);
           add(gradePanel);
           
           // fourth panel - button panel
           JPanel buttonPanel = new JPanel();
           buttonPanel.setBackground(Color.ORANGE);
           buttonPanel.setLayout(new FlowLayout());
           // confirm button
           JButton exitButton = new JButton("OK");
           exitButton.addActionListener(this);
           buttonPanel.add(exitButton);
           // cancel button
           JButton cancelButton = new JButton("Cancel");
           cancelButton.addActionListener(this);
           buttonPanel.add(cancelButton);    
           add(buttonPanel);
        }
       
       // register button click
       public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();          
           if(actionCommand.equals("OK")){
               Storage storage = new Storage();
               storage.editGrade(p, course.getCourseID(), assignment.getName(), studentText.getText(), Integer.parseInt(gradeText.getText()));
               ProfessorViewGradesGUI gui = new ProfessorViewGradesGUI(p, course, assignment);          
               gui.pack();
               gui.setVisible(true);
               dispose();
            }
           else 
                if (actionCommand.equals("Cancel")) {
                    ProfessorViewGradesGUI gui = new ProfessorViewGradesGUI(p, course, assignment);
                    gui.pack();
                    gui.setVisible(true);
                    dispose(); 
                }
           else
               System.out.println("Unexpected Error in Add Grade Window.");
       }  
    } //end of inner class EditGradeWindow 
}
