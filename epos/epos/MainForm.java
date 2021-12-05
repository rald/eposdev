package epos;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


class MainForm extends JFrame {

    static String appName="EPOS";
    static String dbPath="./epos.db";
    static int userId = 0;
    static String userRole = null;
    static String userName = null;

    static MainForm mainForm = new MainForm();
    static LoginForm loginForm = new LoginForm();
    static AdminForm adminForm = new AdminForm();
    static RegisterForm registerForm = new RegisterForm();
    static ProductForm productForm = new ProductForm();
    static AddProductForm addProductForm = new AddProductForm();
    static EditProductForm editProductForm = new EditProductForm();

    JButton btnLogout = new JButton();

    MainForm() {
        createGui();
    }

    void createGui() {
        setTitle(MainForm.appName);
        setSize(640,480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        btnLogout.setText("Logout");
        btnLogout.setBounds(492,16,128,32);

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                loginForm.setVisible(true);
                mainForm.setVisible(false);
            }
        });

        add(btnLogout);
    }

    public static void main(String[] args) {
        loginForm.setVisible(true);
    }

}
