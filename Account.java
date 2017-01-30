package chapter_labs.chapter_nine;

import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Account extends JFrame {
	private static final long serialVersionUID = 1L;

	private Withdraw w;
	private Deposit d;
	private InterestRate i;

	private int id;
	private double balance;
	private double annualInterestRate = 0;
	private Date dateCreated;

	public static void main(String[] args) {
		new Account();
	}

	Account() {
		this(1122, 20000);
	}

	Account(int id, double balance) {
		super("Account");

		this.setSize(480, 102);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.add(new Dashboard());

		this.dateCreated = new Date();
		this.id = id;
		this.balance = balance;

		this.setVisible(true);
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	/*
	 * public int getId() { return id; }
	 * 
	 * public void setId(int id) { this.id = id; }
	 */

	public double getAnnualInterestRate() {
		return this.annualInterestRate;
	}

	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public double getBalance() {
		return this.balance;
	}

	public double getMonthlyInterest() {
		return this.balance * this.getMonthlyInterestRate();
	}

	public double getMonthlyInterestRate() {
		return this.annualInterestRate / 12;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	private interface Button {

		public void update();
	}

	private class Invoice extends JFrame {
		private static final long serialVersionUID = 1L;

		Invoice() {
			super("Invoice");

			this.setSize(240, 102);
			this.setResizable(false);

			try {
				JPanel p = new JPanel(new GridLayout(3, 1));
				this.setContentPane(p);

				JLabel b = new JLabel("Balance: " + Double.toString(getBalance()));
				p.add(b);

				JLabel m = new JLabel("Monthly Interest: " + Double.toString(getMonthlyInterest()));
				p.add(m);

				JLabel d = new JLabel("Account Created On " + dateCreated.toString());
				p.add(d);
			} catch (NumberFormatException e) {
			}

			this.setVisible(true);
		}
	}

	private class Withdraw extends JButton implements Button {
		private static final long serialVersionUID = 1L;

		Withdraw() {
			super("Withdraw");
		}

		public void update() {
			try {
				String resp = JOptionPane.showInputDialog("Withdraw Amount");
				double amnt = Double.parseDouble(resp);
				setBalance(getBalance() - amnt);
				new Invoice();
			} catch (NumberFormatException e) {

			}

		}
	}

	private class Deposit extends JButton implements Button {
		private static final long serialVersionUID = 1L;

		Deposit() {
			super("Deposit");
		}

		public void update() {
			try {
				String resp = JOptionPane.showInputDialog("Deposit Amount");
				double amnt = Double.parseDouble(resp);
				setBalance(getBalance() + amnt);
				new Invoice();
			} catch (NumberFormatException e) {

			}

		}
	}

	private class InterestRate extends JButton implements Button {
		private static final long serialVersionUID = 1L;
		private String resp;
		private double amnt;

		InterestRate() {
			super("Interest Rate");
		}

		public void update() {
			try {
				resp = JOptionPane.showInputDialog("Add an Interest Rate");
				amnt = Double.parseDouble(resp);
				setAnnualInterestRate(amnt);
				new Invoice();
			} catch (NumberFormatException e) {

			}

		}
	}

	private class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == w)
				w.update();
			else if (e.getSource() == d)
				d.update();
			else if (e.getSource() == i)
				i.update();
		}
	}

	private class Dashboard extends JPanel {
		private static final long serialVersionUID = 1L;

		Dashboard() {
			super();

			w = new Withdraw();
			d = new Deposit();
			i = new InterestRate();

			Handler h = new Handler();

			w.addActionListener(h);
			d.addActionListener(h);
			i.addActionListener(h);

			this.add(w);
			this.add(d);
			this.add(i);

			setLayout(new GridLayout(1, 2));
		}
	}
}