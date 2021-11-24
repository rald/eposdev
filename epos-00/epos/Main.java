package epos;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


class Main extends JFrame {


    static String db_path="./epos.db";
    static int user_id = 0;
    static String user_name = null;

    static Main main = new Main();
    static Login login = new Login();
    static Register register = new Register();


    JButton btnLogout = new JButton();

    Main() {
        createGUI();
    }

    void createGUI() {
        setTitle("EPOS");
        setSize(640,480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        btnLogout.setText("Logout");
        btnLogout.setBounds(492,10,128,30);

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                login.setVisible(true);
                main.setVisible(false);
            }
        });

        add(btnLogout);
    }

    public static void main(String[] args) {
        login.setVisible(true);
    }

}
