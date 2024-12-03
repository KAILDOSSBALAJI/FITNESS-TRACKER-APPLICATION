import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

// Workout class to store workout details
class Workout {
    private String type;
    private int duration; // in minutes
    private double caloriesBurned;

    public Workout(String type, int duration, double caloriesBurned) {
        this.type = type;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

    @Override
    public String toString() {
        return String.format("Type: %s, Duration: %d mins, Calories Burned: %.2f", type, duration, caloriesBurned);
    }
}

// Main Application
public class FitnessTrackerApp extends JFrame {

    private final ArrayList<Workout> workoutList = new ArrayList<>();
    private final DefaultListModel<String> workoutListModel = new DefaultListModel<>();

    private double userWeight = 70.0; // Default weight in kg
    private double userHeight = 1.75; // Default height in meters

    private final JLabel lblBMI = new JLabel("BMI: ");
    private final JLabel lblMotivation = new JLabel("Stay fit, stay strong!");

    private final String[] motivationalMessages = {
            "Keep pushing yourself!",
            "You're doing great, keep it up!",
            "Every step counts!",
            "Stay consistent, and success will follow!",
            "Believe in yourself, you've got this!"
    };

    public FitnessTrackerApp() {
        // Set up the main frame
        setTitle("Fitness Tracker");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // North Panel: Form Inputs
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Log Workout"));

        JLabel lblType = new JLabel("Workout Type:");
        JTextField txtType = new JTextField();

        JLabel lblDuration = new JLabel("Duration (mins):");
        JTextField txtDuration = new JTextField();

        JLabel lblCalories = new JLabel("Calories Burned:");
        JTextField txtCalories = new JTextField();

        JButton btnAdd = new JButton("Add Workout");

        inputPanel.add(lblType);
        inputPanel.add(txtType);
        inputPanel.add(lblDuration);
        inputPanel.add(txtDuration);
        inputPanel.add(lblCalories);
        inputPanel.add(txtCalories);
        inputPanel.add(new JLabel()); // Empty cell
        inputPanel.add(btnAdd);

        // Center Panel: Workout List
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Workout Log"));

        JList<String> workoutJList = new JList<>(workoutListModel);
        JScrollPane scrollPane = new JScrollPane(workoutJList);

        listPanel.add(scrollPane, BorderLayout.CENTER);

        // South Panel: Health Monitoring
        JPanel healthPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        healthPanel.setBorder(BorderFactory.createTitledBorder("Health Monitoring"));

        JLabel lblWeight = new JLabel("Weight (kg):");
        JTextField txtWeight = new JTextField(String.valueOf(userWeight));
        JButton btnUpdateHealth = new JButton("Update Health");

        healthPanel.add(lblBMI);
        healthPanel.add(lblWeight);
        healthPanel.add(txtWeight);
        healthPanel.add(btnUpdateHealth);

        // East Panel: Motivation
        JPanel motivationPanel = new JPanel();
        motivationPanel.setBorder(BorderFactory.createTitledBorder("Motivation"));
        lblMotivation.setHorizontalAlignment(SwingConstants.CENTER);
        motivationPanel.add(lblMotivation);

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(healthPanel, BorderLayout.WEST);
        add(motivationPanel, BorderLayout.SOUTH);

        // Button Action Listeners
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String type = txtType.getText();
                    int duration = Integer.parseInt(txtDuration.getText());
                    double calories = Double.parseDouble(txtCalories.getText());

                    // Create and add a new workout
                    Workout workout = new Workout(type, duration, calories);
                    workoutList.add(workout);
                    workoutListModel.addElement(workout.toString());

                    // Clear input fields
                    txtType.setText("");
                    txtDuration.setText("");
                    txtCalories.setText("");

                    // Display a motivational message
                    Random random = new Random();
                    String motivation = motivationalMessages[random.nextInt(motivationalMessages.length)];
                    lblMotivation.setText(motivation);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FitnessTrackerApp.this,
                            "Please enter valid numeric values for duration and calories.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnUpdateHealth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userWeight = Double.parseDouble(txtWeight.getText());

                    // Calculate BMI
                    double bmi = userWeight / (userHeight * userHeight);
                    lblBMI.setText(String.format("BMI: %.2f (%s)", bmi, getBMICategory(bmi)));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FitnessTrackerApp.this,
                            "Please enter a valid numeric value for weight.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Calculate BMI Category
    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 24.9) {
            return "Normal weight";
        } else if (bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> {
            FitnessTrackerApp app = new FitnessTrackerApp();
            app.setVisible(true);
        });
    }
}