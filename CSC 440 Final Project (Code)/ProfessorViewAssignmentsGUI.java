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
public class ProfessorViewAssignmentsGUI extends JFrame {    
    Account p; // prof Account object
    Course course; // current Course object
    public static final int WIDTH = 300; // height and width for frame
    public static final int HEIGHT = 200;
    public static final int SMALL_WIDTH = 300;  //for add and edit assignment window
    public static final int SMALL_HEIGHT = 200;
    JTable table;
    public ProfessorViewAssignmentsGUI(Account a, Course c) {
        p = a; // retain prof Account object and current Course object
        course = c; 
        
        // settings for frame
        setTitle("ProfessorViewAssignmentsGUI");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(4,1));
        
        // first panel - title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.YELLOW);
        JLabel prompt = new JLabel("ProfessorViewAssignmentsGUI");
        titlePanel.add(prompt);
        add(titlePanel);
        
        // assignment list access within Storage and load data onto table
        HashSet<Assignment> assignmentList = (new Storage()).getAssignmentList(a, c);
        String[] columnNames = {"Assignment", "Description"};
        Iterator<Assignment> assignmentListIterator = assignmentList.iterator();
        Object[][] value = new Object[assignmentList.size()][columnNames.length];
        int index = 0;
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
        
        // third panel - button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        // add assignment button
        JButton addAssignmentButton = new JButton("Add Assignment");
        addAssignmentButton.addActionListener(new AddAssignmentListener());        
        buttonPanel.add(addAssignmentButton);
        // remove assignment button
        JButton removeAssignmentButton = new JButton("Remove Assignment");
        removeAssignmentButton.addActionListener(new RemoveAssignmentListener());        
        buttonPanel.add(removeAssignmentButton);        
        add(buttonPanel);
        
        // fourth panel - return button panel
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ReturnListener());        
        returnPanel.add(returnButton);
        add(returnPanel);
    }
    
    // return button panel
    private class ReturnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           dispose();
           new ProfessorOptionsGUIWindow(p, course).setVisible(true);
        }
    }
    
    // add assignment button listener
    private class AddAssignmentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           new AddAssignmentWindow().setVisible(true);
           dispose();
        }
    }
    
    // remove assignment button listener
    private class RemoveAssignmentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           String actionCommand = e.getActionCommand();
           new RemoveAssignmentWindow().setVisible(true);
           dispose();
        }
    }
    
    // add assignment window
    private class AddAssignmentWindow extends JFrame implements ActionListener {
       public static final int MAX_CHARS = 80; // max char in text field
       private JTextField assignmentText = new JTextField(MAX_CHARS); // assignment name and description fields
       private JTextField descText = new JTextField(MAX_CHARS);
       private JLabel update = new JLabel();
       public AddAssignmentWindow() {
           //initialize frame
           super();
           
           // settings for frame
           setTitle("AddAssignmentWindow");
           setSize(SMALL_WIDTH, SMALL_HEIGHT);
           getContentPane().setBackground(Color.WHITE);
           setLayout(new GridLayout(5, 1));
           
           // first panel - title panel
           JPanel titlePanel = new JPanel();
           JLabel titleLabel = new JLabel("Add Assignment Window");
           titleLabel.setBackground(Color.YELLOW);
           titlePanel.add(titleLabel);
           add(titlePanel);
           
           // second panel - assignment name label and field
           JPanel assignmentPanel = new JPanel();
           assignmentPanel.setLayout(new GridLayout(1, 2));
           assignmentPanel.setBackground(Color.WHITE);
           JLabel assignmentLabel = new JLabel("Enter assignment name:");
           assignmentPanel.add(assignmentLabel);
           assignmentPanel.add(assignmentText);
           add(assignmentPanel);
           
           // third panel - description label and field
           JPanel descPanel = new JPanel();
           descPanel.setLayout(new GridLayout(1, 2));
           descPanel.setBackground(Color.WHITE);
           JLabel descLabel = new JLabel("Enter description:");
           descPanel.add(descLabel);
           descPanel.add(descText);
           add(descPanel);
           
           // fourth label - update status
           JPanel updatePanel = new JPanel();
           updatePanel.setBackground(Color.RED);
           updatePanel.add(update);
           add(updatePanel);
           
           // fifth panel - button panel
           JPanel buttonPanel = new JPanel();
           buttonPanel.setBackground(Color.ORANGE);
           buttonPanel.setLayout(new FlowLayout());
           // accept input button
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
               update.setText("Assignment name is already used.");
               Storage storage = new Storage();
               storage.addAssignment(p, course.getCourseID(), assignmentText.getText(), descText.getText());
               ProfessorViewAssignmentsGUI gui = new ProfessorViewAssignmentsGUI(p, storage.getCourse(course.getCourseID()));          
               gui.pack();
               gui.setVisible(true);
               dispose();               
            }
           else 
                if (actionCommand.equals("Cancel")) {
                    ProfessorViewAssignmentsGUI gui = new ProfessorViewAssignmentsGUI(p, course);
                    gui.pack();
                    gui.setVisible(true);
                    dispose();
                }
           else
               System.out.println("Unexpected Error in Add Grade Window.");
       }  
    } //end of inner class AddAssignmentWindow
    private class RemoveAssignmentWindow extends JFrame implements ActionListener {
       public static final int MAX_CHARS = 80; // max char in text field
       private JTextField assignmentText = new JTextField(MAX_CHARS); // assignment name and description fields
       
       private JLabel update = new JLabel();
       public RemoveAssignmentWindow() {
           //initialize frame
           super();
           
           // settings for frame
           setTitle("RemoveAssignmentWindow");
           setSize(SMALL_WIDTH, SMALL_HEIGHT);
           getContentPane().setBackground(Color.WHITE);
           setLayout(new GridLayout(4, 1));
           
           // first panel - title panel
           JPanel titlePanel = new JPanel();
           JLabel titleLabel = new JLabel("Remove Assignment Window");
           titleLabel.setBackground(Color.YELLOW);
           titlePanel.add(titleLabel);
           add(titlePanel);
           
           // second panel - assignment name label and field
           JPanel assignmentPanel = new JPanel();
           assignmentPanel.setLayout(new GridLayout(1, 2));
           assignmentPanel.setBackground(Color.WHITE);
           JLabel assignmentLabel = new JLabel("Enter assignment name:");
           assignmentPanel.add(assignmentLabel);
           assignmentPanel.add(assignmentText);
           add(assignmentPanel);
                      
           // third label - update status
           JPanel updatePanel = new JPanel();
           updatePanel.setBackground(Color.RED);
           updatePanel.add(update);
           add(updatePanel);

           // fourth panel - button panel
           JPanel buttonPanel = new JPanel();
           buttonPanel.setBackground(Color.ORANGE);
           buttonPanel.setLayout(new FlowLayout());
           // accept input button
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
               update.setText("Assignment" + assignmentText.getText() + " is not elegible for removal.");
               Storage storage = new Storage();
               storage.removeAssignment(p, course.getCourseID(), assignmentText.getText());
               ProfessorViewAssignmentsGUI gui = new ProfessorViewAssignmentsGUI(p, storage.getCourse(course.getCourseID()));          
               gui.pack();
               gui.setVisible(true);
               dispose();
            }
           else 
                if (actionCommand.equals("Cancel")) {
                    ProfessorViewAssignmentsGUI gui = new ProfessorViewAssignmentsGUI(p, course);
                    gui.pack();
                    gui.setVisible(true);
                    dispose();
                }
           else
               System.out.println("Unexpected Error in Add Grade Window.");
       }  
    } //end of inner class RemoveAssignmentWindow
}
