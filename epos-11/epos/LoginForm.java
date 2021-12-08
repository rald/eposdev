package epos;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Arrays;

import epos.MD5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

class LoginForm extends JFrame {

    JLabel lblUsername = new JLabel();
    JLabel lblPassword = new JLabel();
    JTextField txtUsername = new JTextField();
    JPasswordField txtPassword = new JPasswordField();
    JButton btnOK = new JButton();
    JButton btnRegister = new JButton();


    LoginForm() {
        createGui();
    }

    void createGui() {

        setTitle(MainForm.appName+" - Login");

        setSize(408,184);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        lblUsername.setText("Username");
        lblPassword.setText("Password");
        btnOK.setText("OK");
        btnRegister.setText("Register");

        lblUsername.setBounds(16,16,96,32);
        lblPassword.setBounds(16,64,96,32);
        txtUsername.setBounds(128,16,256,32);
        txtPassword.setBounds(128,64,256,32);
        btnRegister.setBounds(16,112,128,32);
        btnOK.setBounds(256,112,128,32);

        btnOK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                login(ae);
            }
        });

        btnRegister.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                MainForm.loginForm.setVisible(false);
                MainForm.registerForm.setVisible(true);
            }
        });

        add(lblUsername);
        add(lblPassword);
        add(txtUsername);
        add(txtPassword);
        add(btnOK);
        add(btnRegister);
    }

    void login(ActionEvent ae) {

        String username=txtUsername.getText();
        String password=MD5.getMd5(new String(txtPassword.getPassword()));
        boolean valid=false;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+MainForm.dbPath);

            String sql = "SELECT ID,USER_ROLE,USER_NAME,USER_PASS FROM USER WHERE USER_NAME=? AND USER_PASS=?;";
            pstmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            rs=pstmt.executeQuery();

            if(rs.next()) {
                MainForm.userId=rs.getInt("ID");
                MainForm.userRole=rs.getString("USER_ROLE");
                MainForm.userName=rs.getString("USER_NAME");
                valid=true;
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch ( Exception ex ) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainForm.registerForm,ex.getClass().getName() + ": " + ex.getMessage(),"Register Message",JOptionPane.PLAIN_MESSAGE);
        }


        if(valid) {
            JOptionPane.showMessageDialog(MainForm.loginForm,"Access Granted","Message",JOptionPane.PLAIN_MESSAGE);
            MainForm.loginForm.setVisible(false);
            if(MainForm.userRole.equals("admin")) {
                MainForm.adminForm.setTitle(MainForm.appName+" - Welcome "+MainForm.userName);
                MainForm.adminForm.setVisible(true);
            } else if(MainForm.userRole.equals("user")) {
                MainForm.mainForm.setTitle(MainForm.appName+" - Welcome "+MainForm.userName);
                MainForm.mainForm.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(MainForm.loginForm,"Access Denied","Message",JOptionPane.PLAIN_MESSAGE);
        }


        txtUsername.setText("");
        txtPassword.setText("");

    }

}
