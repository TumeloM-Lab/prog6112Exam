/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mattaresssalesreport1;

/**
 *
 * @author St10464247
 */
public class MattaressSalesReport1 {
    public static void main(String[] args) {
        // 1. Single-dimensional arrays for product names and months
        String[] products = { " Cozy Nights Monarch mattress", "Restonic Barbados mattress", "Sealy Phuket mattress" };
        String[] months = { "Month 1", "Month 2", "Month 3" };

        // 2. Two-dimensional array for sales data 
        // Rows = Products (0 to 2), Columns = Months (0 to 2)
        int[][] salesData = {
            { 25, 18, 20 }, // Sales for Cozy Nights Monarch mattress over 3 months
            { 15, 10, 12 },  // Sales for Restonic Barbados mattress over 3 months
            { 18, 15, 20 }  // Sales for Sealy Phuket mattress over 3 months
        };

        // Single-dimensional array to store the sum of sales per product
        int[] totalSalesPerProduct = new int[products.length];

        int grandTotalSales = 0;
        int maxSales = -1;
        int mostPopularIndex = 0;

        // Processing the 2D array using nested loops
        for (int i = 0; i < products.length; i++) {
            int productTotal = 0;
            for (int j = 0; j < months.length; j++) {
                productTotal += salesData[i][j]; // Accumulate monthly sales for product i
            }
            
            // Store product total in single-dimensional array
            totalSalesPerProduct[i] = productTotal;
            grandTotalSales += productTotal;

            // Determine the most popular product dynamically
            if (productTotal > maxSales) {
                maxSales = productTotal;
                mostPopularIndex = i;
            }
        }

        // Calculate the average number of products sold per product
        double averagePerProduct = (double) grandTotalSales / products.length;

        
        for (int i = 0; i < products.length; i++) {
            for (int j = 0; j < months.length; j++) {
                System.out.println("  Enter number of sales for " + months[j] + " for " + products[i] + ": " + salesData[i][j]);
            }
            System.out.println("");
         
        }
        
        
        System.out.println("PRODUCT SALES REPORT - 2026");
        System.out.println("");

        // Displaying the Required Summary Statistics
        System.out.println("************************************************");
        System.out.println("TOTAL PRODUCT SALES: " + grandTotalSales );
        System.out.println("AVERAGE PRODUCT SALES: " + String.format("%.2f", averagePerProduct)  );
        System.out.println("MOST POPULAR PRODUCT: " + products[mostPopularIndex] + " (" + maxSales + " total units)");
       
    }
}  
