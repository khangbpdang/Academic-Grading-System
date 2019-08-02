
/**
 * Demo program for building a login window
 */

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
import javax.swing.JPasswordField;
import java.util.*;

public class LoginWindow extends JFrame implements ActionListener {
   public static final int WIDTH = 300; // Width of main frame
   public static final int HEIGHT = 200; // Height of main frame
   
   public static final int MAX_CHARS = 15; // Maximum of characters for text field
   
   // Initializes password, text fields and update label
   private JPasswordField passwordText = new JPasswordField(MAX_CHARS);
   private JTextField userText = new JTextField(MAX_CHARS);
   private JLabel update = new JLabel();
   
   public LoginWindow() {
       // Initializes frame
       setTitle("Login Window");
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setSize(WIDTH, HEIGHT);
       setLayout(new GridLayout(5,1));

       // Construct top panel (instruction label)
       JPanel topPanel = new JPanel();
       topPanel.setBackground(Color.BLUE);
       JLabel prompt = new JLabel("LOGIN");
       topPanel.add(prompt);
       
       add(topPanel);
              
       // Second panel (user name / entry)
       JPanel userPanel = new JPanel();
       userPanel.setLayout(new GridLayout(1, 2));
       userPanel.setBackground(Color.WHITE);
       JLabel userLabel = new JLabel("Username:");
       userPanel.add(userLabel);
       userPanel.add(userText);
       add(userPanel);
       
       // Third panel (password / entry)
       JPanel passwordPanel = new JPanel();
       passwordPanel.setLayout(new GridLayout(1, 2));
       passwordPanel.setBackground(Color.YELLOW);
       JLabel passwordLabel = new JLabel("Password:");
       passwordPanel.add(passwordLabel);       
       passwordPanel.add(passwordText);
       add(passwordPanel);
       
       // Fourth panel (update to report wrong username and password)
       JPanel updatePanel = new JPanel();
       updatePanel.setLayout(new FlowLayout());
       updatePanel.setBackground(Color.WHITE);
       update.setForeground(Color.RED);
       updatePanel.add(update);
       add(updatePanel);
       
       // Fifth panel (button panel)
       JPanel buttonPanel = new JPanel();
       buttonPanel.setLayout(new FlowLayout());
       buttonPanel.setBackground(Color.WHITE);
       
       // Button to log in
       JButton okButton = new JButton("Log In");
       okButton.addActionListener(this);
       buttonPanel.add(okButton);
       
       // Button to close log in window
       JButton cancelButton = new JButton("Cancel");
       cancelButton.addActionListener(this);
       buttonPanel.add(cancelButton);
       
       // adds fifth panel to frame
       add(buttonPanel);       
    }
   
   // ActionListener to register Button press
   public void actionPerformed(ActionEvent e) {
       String actionCommand = e.getActionCommand();           
       if(actionCommand.equals("Cancel"))
          System.exit(0);
       else 
            if (actionCommand.equals("Log In")){
                Storage storage = new Storage(); // Initializes Storage object to access database
                
                // accesses Account database
                HashSet<Account> accountList = storage.readAccountStorage(); 
                Iterator<Account> accountListIterator = accountList.iterator(); // 
                // loops to access list of Account objects
                while (accountListIterator.hasNext()) {
                    Account a = accountListIterator.next(); // Gets each Account object in the list of Account object in database
                    // Checks if username matches username text field
                    if (a.getUsername().equals(userText.getText())) {
                        // Checks if password matches password text field
                        if (a.getPassword().equals(passwordText.getText())) {
                            // Role in Account object comparison with role charactoers and spawn the correct main GUI window for each role
                            if (a.getRole() == 's') {
                                dispose();
                                new StudentGUIWindow(a).setVisible(true);
                            }
                            if (a.getRole() == 'p') {
                                dispose();
                                new ProfessorGUIWindow(a).setVisible(true);
                            }
                            if (a.getRole() == 't') {
                                dispose();
                                new TAGUIWindow(a).setVisible(true);
                            }
                        }
                    }                                           
                }
                // Reports to user wrong input if all checks are wrong
                update.setText("Invalid username or password. Please try again.");
                passwordText.setText(""); // reset password field  
                }         
            else
               System.out.println("Unexpected Error in Confirm Window.");
   }      
}
