package emi;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;

public class CalculatorEMIFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("Loan Amount:");
	private JTextField Loan;
	private JTextField Interest;
	private JTextField Tenture;
	private JTextField Result;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorEMIFrame frame = new CalculatorEMIFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CalculatorEMIFrame() {
		setTitle("EMI Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel.setBounds(98, 36, 98, 31);
		contentPane.add(lblNewLabel);

		JLabel lblAnnualInterest = new JLabel("Annual Interest:");
		lblAnnualInterest.setBounds(84, 78, 98, 31);
		contentPane.add(lblAnnualInterest);

		JLabel lblNewLabel_2 = new JLabel("Loan Tenture: ");
		lblNewLabel_2.setBounds(94, 120, 85, 28);
		contentPane.add(lblNewLabel_2);

		Loan = new JTextField();
		Loan.setBounds(192, 42, 180, 20);
		contentPane.add(Loan);
		Loan.setColumns(10);

		Interest = new JTextField();
		Interest.setColumns(10);
		Interest.setBounds(192, 87, 180, 20);
		contentPane.add(Interest);

		Tenture = new JTextField();
		Tenture.setColumns(10);
		Tenture.setBounds(192, 128, 180, 20);
		contentPane.add(Tenture);

		JButton btnNewButton = new JButton("Calculate EMI");
		btnNewButton.setBounds(122, 167, 118, 23);
		contentPane.add(btnNewButton);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(302, 167, 89, 23);
		contentPane.add(btnClear);

		JLabel lblNewLabel_2_1 = new JLabel("Result will display here: ");
		lblNewLabel_2_1.setBounds(10, 233, 118, 28);
		contentPane.add(lblNewLabel_2_1);

		Result = new JTextField();
		Result.setBounds(126, 216, 265, 63);
		contentPane.add(Result);
		Result.setColumns(10);

		// === Calculate EMI Logic ===
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double loanAmount = Double.parseDouble(Loan.getText());
					double annualRate = Double.parseDouble(Interest.getText());
					int years = Integer.parseInt(Tenture.getText());

					if (loanAmount <= 0 || annualRate <= 0 || years <= 0) {
						JOptionPane.showMessageDialog(null, "Values must be greater than zero.");
						return;
					}

					double monthlyRate = annualRate / 12 / 100;
					int months = years * 12;
					double emi = (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, months))
							/ (Math.pow(1 + monthlyRate, months) - 1);

					String result = String.format("Your EMI is: %.2f", emi);
					Result.setText(result);

					// Save to file
					try (FileWriter writer = new FileWriter("emi_history.txt", true)) {
						writer.write("Loan: " + loanAmount + ", Rate: " + annualRate + "%, Years: " + years
								+ ", EMI: " + String.format("%.2f", emi) + "\n");
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null, "Error writing to file.");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please enter valid numeric values.");
				}
			}
		});

		// === Clear Button Logic ===
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Loan.setText("");
				Interest.setText("");
				Tenture.setText("");
			}
		});
	}
}
