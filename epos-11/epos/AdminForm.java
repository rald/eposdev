package epos;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


class AdminForm extends JFrame {

    JButton btnLogout = new JButton();
    JButton btnProduct = new JButton();

    AdminForm() {
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

        btnProduct.setText("Product");
        btnProduct.setBounds(16,16,128,32);

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                MainForm.adminForm.setVisible(false);
                MainForm.loginForm.setVisible(true);
            }
        });

        btnProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                MainForm.adminForm.setVisible(false);
                MainForm.productForm.setVisible(true);
            }
        });

        add(btnLogout);
        add(btnProduct);
    }


}
