/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author St10464247
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//  Interface Requirement 
interface IProductCalculations {
    double GetVat();
    double GetCommission();
    double GetTotal();
}

// Calculation Class Requirement
class ProductCalculations implements IProductCalculations {
    private double price;

    public ProductCalculations(double price) {
        this.price = price;
    }

    @Override
    public double GetVat() {
        return price * 0.15;
    }

    @Override
    public double GetCommission() {
        return price * 0.05;
    }

    @Override
    public double GetTotal() {
        return price + GetVat() + GetCommission();
    }
}

// Main GUI Application 
public class MattressSalesApp extends JFrame {
    private JTextField txtCustomerName, txtProductPrice;
    private JComboBox<String> comboProducts;
    private JButton btnProcess;

    public MattressSalesApp() {
        setTitle("Mattress Sales Management System");
        setSize(500, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Build Menu Bar 
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu menuFile = new JMenu("File");
        JMenuItem itemExit = new JMenuItem("Exit");
        menuFile.add(itemExit);

        // Tools Menu
        JMenu menuTools = new JMenu("Tools");
        JMenuItem itemProcess = new JMenuItem("Process");
        JMenuItem itemClear = new JMenuItem("Clear");
        menuTools.add(itemProcess);
        menuTools.add(itemClear);

        menuBar.add(menuFile);
        menuBar.add(menuTools);
        setJMenuBar(menuBar);

        // Main Layout 
        setLayout(new BorderLayout(10, 10));

        // Form Panel (Input Fields)
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 8, 8));
        panelForm.setBorder(BorderFactory.createTitledBorder("Enter Sale Details"));

        panelForm.add(new JLabel("Customer Name:"));
        txtCustomerName = new JTextField();
        panelForm.add(txtCustomerName);

        panelForm.add(new JLabel("Select Mattress Product:"));
        String[] products = {
            "Cozy Nights Mornarch",
            "Restonic Barbados",
            "Sealy Phuket"
        };
        comboProducts = new JComboBox<>(products);
        panelForm.add(comboProducts);

        panelForm.add(new JLabel("Product Price (R):"));
        txtProductPrice = new JTextField();
        panelForm.add(txtProductPrice);

        btnProcess = new JButton("PROCESS");
        panelForm.add(new JLabel("")); // Empty spacer
        panelForm.add(btnProcess);

        add(panelForm, BorderLayout.CENTER);

        // --- Button & Menu Action Listeners ---
        btnProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processAndSaveSale();
            }
        });

        // Menu Item Actions
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        itemProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processAndSaveSale();
            }
        });

        itemClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }

    private void processAndSaveSale() {
        String customerName = txtCustomerName.getText().trim();
        String productName = (String) comboProducts.getSelectedItem();
        String priceText = txtProductPrice.getText().trim();

        // Validate Inputs
        if (customerName.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Price cannot be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric value for the price.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Use the ProductCalculations class implementing IProductCalculations
        ProductCalculations calculations = new ProductCalculations(price);
        double vat = calculations.GetVat();
        double commission = calculations.GetCommission();
        double totalSale = calculations.GetTotal();

        // Format record string for file saving (using %% for literal percent signs)
        String record = String.format("Customer: %s | Product: %s | Price: R%.2f | VAT (15%%): R%.2f | Commission (5%%): R%.2f | Total: R%.2f",
                customerName, productName, price, vat, commission, totalSale);
        
        // Save to data.txt (append mode = true)
        try (PrintWriter writer = new PrintWriter(new FileWriter("data.txt", true))) {
            writer.println(record);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving to file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Build the message dialog showing Customer Name, VAT, Commission, and Total Sale
        String message = String.format(
            "Sale Processed & Saved Successfully!\n\n" +
            "Customer Name: %s\n" +
            "Product: %s\n" +
            "VAT Amount (15%%): R%.2f\n" +
            "Commission Amount (5%%): R%.2f\n" +
            "Total Sale Amount: R%.2f",
            customerName, productName, vat, commission, totalSale
        );

        // Clear Form Fields
        clearForm();

        // Show popup with requested details
        JOptionPane.showMessageDialog(this, message, "Sale Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        txtCustomerName.setText("");
        comboProducts.setSelectedIndex(0);
        txtProductPrice.setText("");
        txtCustomerName.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MattressSalesApp().setVisible(true);
            }
        });
    }
}