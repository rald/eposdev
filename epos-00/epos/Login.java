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

class Login extends JFrame {

    JLabel lblUsername = new JLabel();
    JLabel lblPassword = new JLabel();
    JTextField txtUsername = new JTextField();
    JPasswordField txtPassword = new JPasswordField();
    JButton btnOK = new JButton();
    JButton btnRegister = new JButton();


    Login() {
        createGUI();
    }

    void createGUI() {

        setTitle("EPOS - Login");

        setSize(390,260);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        lblUsername.setText("Username");
        lblPassword.setText("Password");
        btnOK.setText("OK");
        btnRegister.setText("Register");

        lblUsername.setBounds(20,20,80,30);
        lblPassword.setBounds(20,70,80,30);
        txtUsername.setBounds(120,20,240,30);
        txtPassword.setBounds(120,70,240,30);
        btnOK.setBounds(20,120,340,30);
        btnRegister.setBounds(20,170,340,30);

        btnOK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                String username=txtUsername.getText();
                String password=MD5.getMd5(new String(txtPassword.getPassword()));
                boolean valid=false;

                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                try {
                    Class.forName("org.sqlite.JDBC");
                    conn = DriverManager.getConnection("jdbc:sqlite:"+Main.db_path);

                    String sql = "SELECT ID,USER_NAME,USER_PASS FROM USER WHERE USER_NAME=? AND USER_PASS=?;";
                    pstmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
                    pstmt.setString(1,username);
                    pstmt.setString(2,password);
                    rs=pstmt.executeQuery();

                    if(rs.next()) {
                        Main.user_id=rs.getInt("ID");
                        Main.user_name=rs.getString("USER_NAME");
                        Main.main.setTitle("EPOS - Welcome "+Main.user_name);
                        valid=true;
                    }

                    rs.close();
                    pstmt.close();
                    conn.close();
                } catch ( Exception ex ) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Main.register,ex.getClass().getName() + ": " + ex.getMessage(),"Register Message",JOptionPane.PLAIN_MESSAGE);
                }


                if(valid) {
                    JOptionPane.showMessageDialog(Main.login,"Access Granted","Login Message",JOptionPane.PLAIN_MESSAGE);
                    Main.login.setVisible(false);
                    Main.main.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Main.login,"Access Denied","Login Message",JOptionPane.PLAIN_MESSAGE);
                }


                txtUsername.setText("");
                txtPassword.setText("");


            }
        });

        btnRegister.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Main.login.setVisible(false);
                Main.register.setVisible(true);
            }
        });

        add(lblUsername);
        add(lblPassword);
        add(txtUsername);
        add(txtPassword);
        add(btnOK);
        add(btnRegister);
    }

}
