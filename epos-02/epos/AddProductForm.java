package epos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import java.io.File;


class AddProductForm extends JFrame {

    JLabel lblPrice = new JLabel();
    JLabel lblQuantity = new JLabel();

    JLabel lblProductImage = new JLabel();
    JButton btnBrowse = new JButton();
    JButton btnRemove = new JButton();
    JButton btnCancel = new JButton();
    JButton btnOK = new JButton();
    JTextField txtPrice = new JTextField();
    JTextField txtQuantity = new JTextField();

    AddProductForm() {
        createGui();
    }

    void createGui() {
        setTitle("Add Product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(296,508);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        lblProductImage.setForeground(Color.WHITE);
        lblProductImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        lblProductImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblProductImage.setVerticalAlignment(SwingConstants.CENTER);

        lblPrice.setText("Price");
        lblQuantity.setText("Quantity");

        btnBrowse.setText("Browse");
        btnRemove.setText("Remove");
        btnCancel.setText("Cancel");
        btnOK.setText("OK");

        lblPrice.setBounds(16,340,64,32);
        lblQuantity.setBounds(16,388,64,32);

        lblProductImage.setBounds(16,16,256,256);
        btnBrowse.setBounds(32,288,96,32);
        btnRemove.setBounds(160,288,96,32);

        txtPrice.setBounds(96,340,176,32);
        txtPrice.setHorizontalAlignment(SwingConstants.RIGHT);

        txtQuantity.setBounds(96,388,176,32);
        txtQuantity.setHorizontalAlignment(SwingConstants.RIGHT);

        btnCancel.setBounds(32,436,96,32);
        btnOK.setBounds(160,436,96,32);

        btnBrowse.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                browseImage(ae);
            }
        });

        btnRemove.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                removeImage(ae);
            }
        });

        btnCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                MainForm.productForm.addProductForm.setVisible(false);
                MainForm.productForm.setVisible(true);
            }
        });

        btnOK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
            }
        });

        add(lblProductImage);
        add(btnBrowse);
        add(btnRemove);
        add(lblPrice);
        add(lblQuantity);
        add(txtPrice);
        add(txtQuantity);
        add(btnCancel);
        add(btnOK);
    }

    void browseImage(ActionEvent ae) {
        try {
            JFileChooser fc = new JFileChooser(".");
            fc.setFileFilter(new JpegImageFileFilter());
            int res = fc.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                String path=fc.getSelectedFile().getAbsolutePath();
                lblProductImage.setIcon(new ImageIcon(path));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainForm.registerForm,ex.getClass().getName() + ": " + ex.getMessage(),"Register Message",JOptionPane.PLAIN_MESSAGE);
        }

    }

    void removeImage(ActionEvent ae) {
        lblProductImage.setIcon(null);
    }


}

