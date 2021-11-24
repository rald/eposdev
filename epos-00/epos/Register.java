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


class Register extends JFrame {

    JLabel lblUsername = new JLabel();
    JLabel lblPassword1 = new JLabel();
    JLabel lblPassword2 = new JLabel();
    JTextField txtUsername = new JTextField();
    JPasswordField txtPassword1 = new JPasswordField();
    JPasswordField txtPassword2 = new JPasswordField();
    JButton btnOK = new JButton();
    JButton btnCancel = new JButton();

    Register() {
        createGUI();
    }

    void createGUI() {

        setTitle("EPOS - Register");

        setSize(390,310);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        lblUsername.setText("Username");
        lblPassword1.setText("Password1");
        lblPassword2.setText("Password2");
        btnOK.setText("OK");
        btnCancel.setText("Cancel");

        lblUsername.setBounds(20,20,80,30);
        lblPassword1.setBounds(20,70,80,30);
        lblPassword2.setBounds(20,120,80,30);
        txtUsername.setBounds(120,20,240,30);
        txtPassword1.setBounds(120,70,240,30);
        txtPassword2.setBounds(120,120,240,30);
        btnOK.setBounds(20,170,340,30);
        btnCancel.setBounds(20,220,340,30);

        btnOK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                String userrole="user";
                String username=txtUsername.getText();
                char[] password1=txtPassword1.getPassword();
                char[] password2=txtPassword2.getPassword();

                if(password1.length==0 || password2.length==0) {

                    JOptionPane.showMessageDialog(Main.register,"Password may not be blank","Register Message",JOptionPane.PLAIN_MESSAGE);

                } else if(  password1.length==password2.length &&
                            Arrays.equals(password1,password2)) {

                    Connection conn = null;
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;

                    boolean valid=true;

                    try {
                        Class.forName("org.sqlite.JDBC");
                        conn = DriverManager.getConnection("jdbc:sqlite:"+Main.db_path);

                        String sql = "SELECT USER_NAME FROM USER WHERE USER_NAME=?;";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1,username);
                        rs=pstmt.executeQuery();

                        if(rs.next()) {
                            valid=false;
                            JOptionPane.showMessageDialog(Main.register,"Username already exists","Register Message",JOptionPane.PLAIN_MESSAGE);
                        }

                        pstmt.close();
                        conn.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Main.register,ex.getClass().getName() + ": " + ex.getMessage(),"Register Message",JOptionPane.PLAIN_MESSAGE);
                    }


                    if(valid) {

                        try {
                            Class.forName("org.sqlite.JDBC");
                            conn = DriverManager.getConnection("jdbc:sqlite:"+Main.db_path);

                            String sql = "INSERT INTO USER (USER_ROLE,USER_NAME,USER_PASS) VALUES (?,?,?);";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1,userrole);
                            pstmt.setString(2,username);
                            pstmt.setString(3,MD5.getMd5(new String(password1)));
                            pstmt.executeUpdate();

                            pstmt.close();
                            conn.close();

                            JOptionPane.showMessageDialog(Main.register,"Account Registered","Register Message",JOptionPane.PLAIN_MESSAGE);
                            txtUsername.setText("");

                            Main.register.setVisible(false);
                            Main.login.setVisible(true);
                        } catch ( Exception ex ) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(Main.register,ex.getClass().getName() + ": " + ex.getMessage(),"Register Message",JOptionPane.PLAIN_MESSAGE);
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(Main.register,"Password does not match","Register Message",JOptionPane.PLAIN_MESSAGE);
                }

                txtPassword1.setText("");
                txtPassword2.setText("");

            }
        });


        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.register.setVisible(false);
                Main.login.setVisible(true);
            }
        });

        add(lblUsername);
        add(lblPassword1);
        add(lblPassword2);
        add(txtUsername);
        add(txtPassword1);
        add(txtPassword2);
        add(btnOK);
        add(btnCancel);
    }

}

