package epos;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


class MainForm extends JFrame {

    static String db_path="./epos.db";
    static int user_id = 0;
    static String user_name = null;

    static MainForm mainForm = new MainForm();
    static LoginForm loginForm = new LoginForm();
    static RegisterForm registerForm = new RegisterForm();
    static ProductForm productForm = new ProductForm();

    JButton btnLogout = new JButton();
    JButton btnProduct = new JButton();

    MainForm() {
        createGui();
    }

    void createGui() {
        setTitle("EPOS");
        setSize(640,480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        btnLogout.setText("Logout");
        btnLogout.setBounds(492,10,128,30);

        btnProduct.setText("Product");
        btnProduct.setBounds(10,10,128,30);

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                loginForm.setVisible(true);
                mainForm.setVisible(false);
            }
        });

        btnProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                mainForm.setVisible(false);
                productForm.setVisible(true);
            }
        });

        add(btnLogout);
        add(btnProduct);
    }

    public static void main(String[] args) {
        loginForm.setVisible(true);
    }

}
