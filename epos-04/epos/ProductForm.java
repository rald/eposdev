package epos;

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
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;
import java.io.File;

class ProductForm extends JFrame {

    JPanel      pnlSearch = new JPanel();
    JPanel      pnlOperations = new JPanel();

    JTextField  txtSearch = new JTextField();
    JButton     btnSearch = new JButton();

    JButton     btnAdd = new JButton();
    JButton     btnEdit = new JButton();
    JButton     btnRemove = new JButton();
    JButton     btnCancel = new JButton();

    ProductForm() {
        createGui();
    }

    void createGui() {
        setTitle(MainForm.appName+" - Product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640,480);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        btnSearch.setText("Search");
        btnAdd.setText("Add");
        btnEdit.setText("Edit");
        btnRemove.setText("Remove");
        btnCancel.setText("Cancel");

        pnlSearch.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.insets=new Insets(8,8,8,4);
        c.fill=GridBagConstraints.BOTH;
        c.weightx=11.0/12.0; c.weighty=1.0;
        c.gridx=0; c.gridy=0;
        pnlSearch.add(txtSearch,c);

        c.insets=new Insets(8,4,8,8);
        c.fill=GridBagConstraints.BOTH;
        c.weightx=1.0/12.0; c.weighty=1.0;
        c.gridx=1; c.gridy=0;
        pnlSearch.add(btnSearch,c);

        GridLayout grdOperations=new GridLayout(0,4);

        grdOperations.setHgap(16);
        grdOperations.setVgap(16);

        pnlOperations.setLayout(grdOperations);

        pnlOperations.add(btnAdd);
        pnlOperations.add(btnEdit);
        pnlOperations.add(btnRemove);
        pnlOperations.add(btnCancel);

        pnlOperations.setBorder(new EmptyBorder(8,8,8,8));

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                searchProductForm();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addProductForm();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                editProductForm();
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                removeProductForm();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                MainForm.productForm.setVisible(false);
                MainForm.mainForm.setVisible(true);
            }
        });

        add(pnlSearch,BorderLayout.PAGE_START);
        add(pnlOperations,BorderLayout.PAGE_END);

    }


    void searchProductForm() {
        JOptionPane.showMessageDialog(MainForm.productForm,"Search Product","Message",JOptionPane.PLAIN_MESSAGE);
    }

    void addProductForm() {
        MainForm.productForm.setVisible(false);
        MainForm.addProductForm.setVisible(true);
    }

    void editProductForm() {
        JOptionPane.showMessageDialog(MainForm.productForm,"Edit Product","Message",JOptionPane.PLAIN_MESSAGE);
    }

    void removeProductForm() {
        JOptionPane.showMessageDialog(MainForm.productForm,"Remove Product","Message",JOptionPane.PLAIN_MESSAGE);
    }

}
