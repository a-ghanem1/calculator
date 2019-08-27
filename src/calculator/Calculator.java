/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mint
 */
public class Calculator extends JFrame implements ActionListener {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/calc";
    Connection conn = null;
    
    Map<String, Double> holders = new HashMap<String, Double>();
    private int operationsCount = 0;
    private double result = 0;
    private char[] signHolder = new char[100];
    private boolean showTable = false;
    private final JPanel p;
    private final JTextField textField;
    private final JButton BC;
    private final JButton BDel;
    private final JButton BNegative;
    private final JButton BPercentage;
    private final JToggleButton BHistory;
    private final JButton BPower;
    private final JButton BSqrt;
    private final JButton BDivide;
    private final JButton B7;
    private final JButton B8;
    private final JButton B9;
    private final JButton BMultiply;
    private final JButton B4;
    private final JButton B5;
    private final JButton B6;
    private final JButton BSub;
    private final JButton B1;
    private final JButton B2;
    private final JButton B3;
    private final JButton BAdd;
    private final JButton B0;
    private final JButton BDot;
    private final JButton BEqual;
    private final JButton BClearHistory;
    JTable historyTable = new JTable(1000,1);
    JScrollPane tableScrollPane = new JScrollPane(historyTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);   
        
    public static void main(String[] args) {
        new Calculator();        
    }

    public Calculator() {
        
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        p = new JPanel();
        textField = new JTextField();
        BC = new JButton("C");
        BDel = new JButton("Del");
        BNegative = new JButton("-/+");
        BPercentage = new JButton("%");
        BHistory = new JToggleButton("H");
        BPower = new JButton("x\u00B2");
        BSqrt = new JButton("\u221A");
        BDivide = new JButton("÷");
        B7 = new JButton("7");
        B8 = new JButton("8");
        B9 = new JButton("9");
        BMultiply = new JButton("x");
        B4 = new JButton("4");
        B5 = new JButton("5");
        B6 = new JButton("6");
        BSub = new JButton("-");
        B1 = new JButton("1");
        B2 = new JButton("2");
        B3 = new JButton("3");
        BAdd = new JButton("+");
        B0 = new JButton("0");
        BDot = new JButton(".");
        BEqual = new JButton("=");
        BClearHistory = new JButton("Clear History");
        design();       
        addAction();
        textFieldHandle();
        readHistoryIntoTable();
    }
    private void dbConnection() {
        try {
        conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        System.out.println("connected");
        } catch (SQLException ex) {
        }
    }
    private void dbInsert(String record) throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            PreparedStatement post = conn.prepareStatement("INSERT INTO history (opreration) VALUES ('"+record+"')");
            post.executeUpdate();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    private void dbDelete() throws SQLException {
        try {
            Connection connection = DriverManager
            .getConnection(CONN_STRING, USERNAME, PASSWORD);
            Statement st = connection.createStatement();
            int result = st.executeUpdate("DELETE FROM history WHERE 1 = 1");
            System.out.println("deleted successfully");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void design() {    
        p.setLayout(null);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        settingBoundsAndBorders();
        settingFonts();
        settingColor();
        addingItems();
        this.setTitle("Calculator");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(280,404);
        this.setVisible(true);
    }
    private void settingBoundsAndBorders() {
        
        //bounds
        textField.setBounds(0, 0, 280, 70);
        BC.setBounds(0, 70, 70, 52);
        BDel.setBounds(70, 70, 70, 52);
        BNegative.setBounds(140, 70, 70, 52);
        BPercentage.setBounds(210, 70, 70, 52);
        BHistory.setBounds(0, 122, 70, 52);
        BPower.setBounds(70, 122, 70, 52);
        BSqrt.setBounds(140, 122, 70, 52);
        BDivide.setBounds(210, 122, 70, 52);
        B7.setBounds(0, 174, 70, 52);
        B8.setBounds(70, 174, 70, 52);
        B9.setBounds(140, 174, 70, 52);
        BMultiply.setBounds(210, 174, 70, 52);
        B4.setBounds(0, 226, 70, 52);
        B5.setBounds(70, 226, 70, 52);
        B6.setBounds(140, 226, 70, 52);
        BSub.setBounds(210, 226, 70, 52);
        B1.setBounds(0, 278, 70, 52);
        B2.setBounds(70, 278, 70, 52);
        B3.setBounds(140, 278, 70, 52);
        BAdd.setBounds(210, 278, 70, 52);
        B0.setBounds(0, 330, 140, 52);
        BDot.setBounds(140, 330, 70, 52);
        BEqual.setBounds(210, 330, 70, 52);
        tableScrollPane.setBounds(280, 0, 220, 330);
        BClearHistory.setBounds(280, 330, 220, 52);
        
        //borders
        
        Border border1 = BorderFactory.createLineBorder(new Color(66, 71, 76), 1, false);
        textField.setBorder(null);
        BC.setBorder(border1);
        BDel.setBorder(border1);
        BNegative.setBorder(border1);
        BPercentage.setBorder(border1);
        BHistory.setBorder(border1);
        BPower.setBorder(border1);
        BSqrt.setBorder(border1);
        BDivide.setBorder(border1);
        B7.setBorder(border1);
        B8.setBorder(border1);
        B9.setBorder(border1);
        BMultiply.setBorder(border1);
        B4.setBorder(border1);
        B5.setBorder(border1);
        B6.setBorder(border1);
        BSub.setBorder(border1);
        B1.setBorder(border1);
        B2.setBorder(border1);
        B3.setBorder(border1);
        BAdd.setBorder(border1);
        B0.setBorder(border1);
        BDot.setBorder(border1);
        BEqual.setBorder(border1);
        tableScrollPane.setBorder(border1);
        BClearHistory.setBorder(border1);
        
        // dotted border
        
        BC.setFocusPainted(false);
        BDel.setFocusPainted(false);
        BNegative.setFocusPainted(false);
        BPercentage.setFocusPainted(false);
        BHistory.setFocusPainted(false);
        BPower.setFocusPainted(false);
        BSqrt.setFocusPainted(false);
        BDivide.setFocusPainted(false);
        B7.setFocusPainted(false);
        B8.setFocusPainted(false);
        B9.setFocusPainted(false);
        BMultiply.setFocusPainted(false);
        B4.setFocusPainted(false);
        B5.setFocusPainted(false);
        B6.setFocusPainted(false);
        BSub.setFocusPainted(false);
        B1.setFocusPainted(false);
        B2.setFocusPainted(false);
        B3.setFocusPainted(false);
        BAdd.setFocusPainted(false);
        B0.setFocusPainted(false);
        BDot.setFocusPainted(false);
        BEqual.setFocusPainted(false);
        BClearHistory.setFocusPainted(false);
        
    }
    private void settingFonts() {
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        BC.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BDel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BNegative.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BPercentage.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BHistory.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BPower.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BSqrt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BDivide.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        B7.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        B8.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        B9.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BMultiply.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        B4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        B5.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        B6.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BSub.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        B1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        B2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        B3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BAdd.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        B0.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BDot.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BEqual.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        BClearHistory.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
    }
    private void settingColor() {
        //background
 
        textField.setBackground(new Color(66, 71, 76));
        BC.setBackground(new Color(83, 88, 92));
        BDel.setBackground(new Color(83, 88, 92));
        BNegative.setBackground(new Color(83, 88, 92));
        BPercentage.setBackground(new Color(255, 158, 11));
        BHistory.setBackground(new Color(116, 121, 125));
        BPower.setBackground(new Color(116, 121, 125));
        BSqrt.setBackground(new Color(116, 121, 125));
        BDivide.setBackground(new Color(255, 158, 11));
        B7.setBackground(new Color(116, 121, 125));
        B8.setBackground(new Color(116, 121, 125));
        B9.setBackground(new Color(116, 121, 125));
        BMultiply.setBackground(new Color(255, 158, 11));
        B4.setBackground(new Color(116, 121, 125));
        B5.setBackground(new Color(116, 121, 125));
        B6.setBackground(new Color(116, 121, 125));
        BSub.setBackground(new Color(255, 158, 11));
        B1.setBackground(new Color(116, 121, 125));
        B2.setBackground(new Color(116, 121, 125));
        B3.setBackground(new Color(116, 121, 125));
        BAdd.setBackground(new Color(255, 158, 11));
        B0.setBackground(new Color(116, 121, 125));
        BDot.setBackground(new Color(116, 121, 125));
        BEqual.setBackground(new Color(255, 158, 11));
        historyTable.setBackground(new Color(116, 121, 125));
        historyTable.getTableHeader().setBackground(new Color(55, 61, 66));
        BClearHistory.setBackground(new Color(83, 88, 92));
        
        
        //font color 
        
        textField.setForeground(Color.white);
        BC.setForeground(Color.white);
        BDel.setForeground(Color.white);
        BNegative.setForeground(Color.white);
        BPercentage.setForeground(Color.white);
        BHistory.setForeground(Color.white);
        BPower.setForeground(Color.white);
        BSqrt.setForeground(Color.white);
        BDivide.setForeground(Color.white);
        B7.setForeground(Color.white);
        B8.setForeground(Color.white);
        B9.setForeground(Color.white);
        BMultiply.setForeground(Color.white);
        B4.setForeground(Color.white);
        B5.setForeground(Color.white);
        B6.setForeground(Color.white);
        BSub.setForeground(Color.white);
        B1.setForeground(Color.white);
        B2.setForeground(Color.white);
        B3.setForeground(Color.white);
        BAdd.setForeground(Color.white);
        B0.setForeground(Color.white);
        BDot.setForeground(Color.white);
        BEqual.setForeground(Color.white);
        historyTable.setForeground(Color.WHITE);
        historyTable.getTableHeader().setForeground(Color.WHITE);
        BClearHistory.setForeground(Color.WHITE);
        
        
        historyTable.setGridColor(new Color(66, 71, 76));
        
    }
    private void addingItems() {
        p.add(textField);
        p.add(BC);
        p.add(BDel);
        p.add(BNegative);
        p.add(BPercentage);
        p.add(BHistory);
        p.add(BPower);
        p.add(BSqrt);
        p.add(BDivide);
        p.add(B7);
        p.add(B8);
        p.add(B9);
        p.add(BMultiply);
        p.add(B4);
        p.add(B5);
        p.add(B6);
        p.add(BSub);
        p.add(B1);
        p.add(B2);
        p.add(B3);
        p.add(BAdd);
        p.add(B0);
        p.add(BDot);
        p.add(BEqual);
        p.add(tableScrollPane);
        p.add(BClearHistory);
        
        this.add(p);
    }
    private void textFieldHandle(){
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkTextField();
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) || (c == 'c') || (c == '-') || (c == '.')){
                    if(c == '*'){
                        calculateMultiply();
                    }
                    else if (c == '+') {
                        calculateAdd();
                    }
                    else if (c == '-') {
                        if(textField.getText().equals("")) {
                            return;
                        }
                        else
                            calculateSub();
                    }
                    else if (c == '/') {
                        calculateDivision();
                    }
                    else if (c == '%') {
                        try {
                            calculatePercentage();
                        } catch (SQLException ex) {
                        }
                    }
                    else if (c == KeyEvent.VK_ENTER)
                    {
                        try {
                            Evaluate();
                        } catch (SQLException ex) {
                        }
                    }
                    else if (c == '.')
                    {
                        if(textField.getText().contains(".") || (textField.getText().equals("")))
                        {
                            e.consume();
                            java.awt.Toolkit.getDefaultToolkit().beep();   
                        }   
                        else { 
                            textField.setText(textField.getText() + ".");
                        }                        
                    }
                    else if (c == 'c')
                    {
                        textField.setText("");
                        operationsCount = 0;
                        result = 0;
                    }
                    e.consume();
                    java.awt.Toolkit.getDefaultToolkit().beep();   
                }
            }
        });
//        textField.addFocusListener(new FocusListener(){
//            @Override
//            public void focusGained(FocusEvent fe)
//            {
//                textField.setCaretPosition(textField.getDocument().getLength());
//            }
//            @Override
//            public void focusLost(FocusEvent e) {
//            }
//        });
    }
    private void addAction(){
        BC.addActionListener(this);
        BDel.addActionListener(this);
        BNegative.addActionListener(this);
        BPercentage.addActionListener(this);
        BHistory.addActionListener(this);
        BPower.addActionListener(this);
        BSqrt.addActionListener(this);
        BDivide.addActionListener(this);
        B7.addActionListener(this);
        B8.addActionListener(this);
        B9.addActionListener(this);
        BMultiply.addActionListener(this);
        B4.addActionListener(this);
        B5.addActionListener(this);
        B6.addActionListener(this);
        BSub.addActionListener(this);
        B1.addActionListener(this);
        B2.addActionListener(this);
        B3.addActionListener(this);
        BAdd.addActionListener(this);
        B0.addActionListener(this);
        BDot.addActionListener(this);
        BEqual.addActionListener(this);
        BClearHistory.addActionListener(this);
    }
    private void showHistory(){
        if(showTable)
        {
            this.setSize(280,404);
            showTable = false;
        }
        else
        {
            historyTable.getColumnModel().getColumn(0).setHeaderValue("History");
            this.setSize(500,404); 
            this.setVisible(true);
            showTable = true;  
        }
    }
    private void calculatePercentage() throws SQLException{
        String x;
        String fileName = "calc.txt";
        File f = new File(fileName);
        if(!textField.getText().equals("")){
            Evaluate();
            x = textField.getText();
            try {
                FileWriter output = new FileWriter(f, true);
                result = Double.parseDouble(String.format("%.7f", Double.parseDouble(x)/100));
                textField.setText(Double.toString(result));
                String record = x + " /100 = " + Double.toString(Double.parseDouble(x)/100) + ": \n";
                output.write(record);
                dbInsert(record);
                output.close();
            }
            catch(Exception ex)
            {      
            }
            readHistoryIntoTable();
        }
    }
    private void calculatePower() throws SQLException{
        String x;
        String fileName = "calc.txt";
        File f = new File(fileName);
        if(!textField.getText().equals("")){
            Evaluate();
            x = textField.getText();
            try {
                FileWriter output = new FileWriter(f, true);
                output.write(x + "\u00B2 = ");
                result = Double.parseDouble(String.format("%.7f", Math.pow(Double.parseDouble(x), 2)));
                String record = x + "\u00B2 = ";
                x = Double.toString(result);
                output.write(x + ": \n");
                output.close();
                record += x;
                dbInsert(record);
                if (x.endsWith(".0"))
                    textField.setText(x.replace(".0", ""));
                else
                    textField.setText(x);
            }
            catch(Exception ex)
            {           
            }  
            readHistoryIntoTable();
        }
    }
    private void calculateSqrt() throws SQLException{
        String x;
        String fileName = "calc.txt";
        File f = new File(fileName);
        if(!textField.getText().equals("")){
            Evaluate();
            x = textField.getText();
            if(x.contains("-"))
            {
                textField.setText("Math Error");
                return;
            }
            try {
                FileWriter output = new FileWriter(f, true);
                output.write("\u221A" + x);
                String record = "sqrt(" + x + ")";
                result = Double.parseDouble(String.format("%.7f", Math.sqrt(Double.parseDouble(x))));
                x = Double.toString(result);
                output.write(" = " + x + ": \n");
                record += " = " + x + ": \n";
                output.close();
                dbInsert(record);
                if (x.endsWith(".0"))
                    textField.setText(x.replace(".0", ""));
                else
                    textField.setText(x);
            }
            catch(Exception ex)
            {  
            }  
            readHistoryIntoTable();
        }
    }
    private void calculateDivision(){
        String x;
        if(!textField.getText().equals("")){
            x = textField.getText();
            holders.put("holder".concat(Integer.toString(1 + operationsCount)), Double.parseDouble(x));
            signHolder[operationsCount] = '/';
            operationsCount++;
            textField.setText("");
        }
    }
    private void calculateMultiply(){
        String x;
        if(!textField.getText().equals("")){
            x = textField.getText();
            holders.put("holder".concat(Integer.toString(1 + operationsCount)), Double.parseDouble(x));
            signHolder[operationsCount] = '*';
            operationsCount++;
            textField.setText("");
        }
    }
    private void calculateSub(){
        String x;
        if(!textField.getText().equals("")){
            x = textField.getText();
            holders.put("holder".concat(Integer.toString(1 + operationsCount)), Double.parseDouble(x));
            signHolder[operationsCount] = '-';
            operationsCount++;
            textField.setText("");                   
        }

    }
    private void calculateAdd(){
        String x;
        if(!textField.getText().equals("")){
            x = textField.getText();
            holders.put("holder".concat(Integer.toString(1 + operationsCount)), Double.parseDouble(x));
            signHolder[operationsCount] = '+';
            operationsCount++;
            textField.setText("");
        }
    }
    private void saveHistory() throws SQLException{
        if(operationsCount == 0)
            return;
        String fileName = "calc.txt";
        File f = new File(fileName);
        try {
            FileWriter output = new FileWriter(f, true);
            output.write(Double.toString(holders.get("holder1")));
            String record = Double.toString(holders.get("holder1"));
            for(int i = 1; i <= operationsCount; i++)
            {
                output.write(" " + signHolder[i-1] + " ");
                output.write(Double.toString(holders.get("holder".concat(Integer.toString(i + 1)))));
                record += " " + signHolder[i-1] + " ";
                record += Double.toString(holders.get("holder".concat(Integer.toString(i + 1))));
            }
            output.write(" = " + result+":");
            record += " = " + result;
            output.write("\n");
            output.close();    
            dbInsert(record);   

        } catch (Exception ex) {
        }
        readHistoryIntoTable();
    }
    private void readHistoryIntoTable(){
        DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
        model.setRowCount(0);
        model.setRowCount(1000);
        File f = new File("calc.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            DefaultTableModel mod = (DefaultTableModel) historyTable.getModel();
        
            
            Object[] tableLines = br.lines().toArray();
            
            for(int i = 0; i < tableLines.length; i++){
                String Line = tableLines[i].toString().trim();
                String[] dataRow = Line.split(":");
                mod.insertRow(0, dataRow);
            }
        } catch (Exception ex) {
        }     
    }
    private void Evaluate() throws SQLException {
        String x;
            x = textField.getText();
            holders.put("holder".concat(Integer.toString(1 + operationsCount)), Double.parseDouble(x));
            
            result = holders.get("holder1");
            for(int i = 1; i <= operationsCount; i++)
            {
                if(signHolder[i-1] == '+')
                    result += holders.get("holder".concat(Integer.toString(i + 1)));
                else if(signHolder[i-1] == '-')
                    result -= holders.get("holder".concat(Integer.toString(i + 1)));
                else if(signHolder[i-1] == '*')
                    result *= holders.get("holder".concat(Integer.toString(i + 1)));
                else if(signHolder[i-1] == '/')
                {
                    if(holders.get("holder".concat(Integer.toString(i + 1))) == 0) {
                        textField.setText("Math Error");
                        operationsCount = 0;
                        return;
                    }
                    else
                        result /= holders.get("holder".concat(Integer.toString(i + 1)));
                }
                
            }
            result = Double.parseDouble(String.format("%.7f", result));
            if (Double.toString(result).endsWith(".0")) {
                textField.setText(Double.toString(result).replace(".0", ""));
            } else {
                textField.setText(Double.toString(result));
            }
            saveHistory();
            operationsCount = 0;     
    }
    private void checkTextField(){
        if(textField.getText().equals("Math Error"))
            textField.setText("");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == BC) {
            textField.setText("");
            result = 0;
            operationsCount = 0;
            textField.requestFocus();
        }
        else if (e.getSource() == BDel) {
            checkTextField();
            int length = textField.getText().length();
            int number = length - 1;

            if (length > 0) {
                StringBuilder back = new StringBuilder(textField.getText());
                back.deleteCharAt(number);
                textField.setText(back.toString());
            }
            textField.requestFocus();
        }
        else if (e.getSource() == BNegative) {
            checkTextField();
            if(textField.getText().equals(""))
                textField.setText("-");
            textField.requestFocus();
        }
        else if (e.getSource() == BPercentage) {
            try {
                checkTextField();
                calculatePercentage();
                textField.requestFocus();
            } catch (SQLException ex) {
            }
        }
        else if (e.getSource() == BHistory) {
            checkTextField();
            showHistory();
            textField.requestFocus();
        }
        else if (e.getSource() == BPower) {
            try {
                checkTextField();
                calculatePower();
                textField.requestFocus();
            } catch (SQLException ex) {
            }
        }
        else if (e.getSource() == BSqrt) {
            try {
                checkTextField();
                calculateSqrt();
                textField.requestFocus();
            } catch (SQLException ex) {
            }
        }
        else if (e.getSource() == BDivide) {
            checkTextField();
            calculateDivision();
            textField.requestFocus();
        }
        else if (e.getSource() == B7) {
            checkTextField();
            textField.setText(textField.getText() + "7");
            textField.requestFocus();
        }
        else if (e.getSource() == B8) {
            checkTextField();
            textField.setText(textField.getText() + "8");
            textField.requestFocus();
        }
        else if (e.getSource() == B9) {
            checkTextField();
            textField.setText(textField.getText() + "9");
            textField.requestFocus();
        }
        else if (e.getSource() == BMultiply) {
            checkTextField();
            calculateMultiply();
            textField.requestFocus();
        }
        else if (e.getSource() == B4) {
            checkTextField();
            textField.setText(textField.getText() + "4");
            textField.requestFocus();
        }
        else if (e.getSource() == B5) {
            checkTextField();
            textField.setText(textField.getText() + "5");
            textField.requestFocus();
        }
        else if (e.getSource() == B6) {
            checkTextField();
            textField.setText(textField.getText() + "6");
            textField.requestFocus();
        }
        else if (e.getSource() == BSub) {
            checkTextField();
            calculateSub();
            textField.requestFocus();
        }
        else if (e.getSource() == B1) {
            checkTextField();
            textField.setText(textField.getText() + "1");
            textField.requestFocus();
        }
        else if (e.getSource() == B2) {
            checkTextField();
            textField.setText(textField.getText() + "2");
            textField.requestFocus();
        }
        else if (e.getSource() == B3) {
            checkTextField();
            textField.setText(textField.getText() + "3");
            textField.requestFocus();
        }
        else if (e.getSource() == BAdd) {
            checkTextField();
            calculateAdd();
            textField.requestFocus();
        }
        else if (e.getSource() == B0) {
            checkTextField();
            if (!textField.getText().equals("0")) {
                textField.setText(textField.getText() + "0");
            }
            textField.requestFocus();
        }
        else if (e.getSource() == BDot) {
            checkTextField();
            if (!textField.getText().contains(".")) {
                textField.setText(textField.getText() + ".");
            }
            textField.requestFocus();
        }
        else if (e.getSource() == BEqual) {
            try {
                Evaluate();
                textField.requestFocus();
            } catch (SQLException ex) {
            }
        }
        else if (e.getSource() == BClearHistory) {
            File f = new File("calc.txt");
            try{
                f.delete();
                DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
                model.setRowCount(0);
                model.setRowCount(1000);
                
            } catch(Exception ex){   
            }
            textField.requestFocus();
        }   
    }
}