package epos;

import java.awt.Graphics;
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


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import javax.imageio.ImageIO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;



class AddProductForm extends JFrame {

    JLabel lblPrice = new JLabel();
    JLabel lblQuantity = new JLabel();

    JLabel lblProduct = new JLabel();
    JButton btnBrowse = new JButton();
    JButton btnRemove = new JButton();
    JButton btnCancel = new JButton();
    JButton btnOK = new JButton();
    JTextField txtPrice = new JTextField();
    JTextField txtQuantity = new JTextField();

    ImageIcon icnProduct = null;

    AddProductForm() {
        createGui();
    }

    void createGui() {
        setTitle(MainForm.appName+" - Add Product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(296,508);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        lblProduct.setForeground(Color.WHITE);
        lblProduct.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        lblProduct.setHorizontalAlignment(SwingConstants.CENTER);
        lblProduct.setVerticalAlignment(SwingConstants.CENTER);

        lblPrice.setText("Price");
        lblQuantity.setText("Quantity");

        btnBrowse.setText("Browse");
        btnRemove.setText("Remove");
        btnCancel.setText("Cancel");
        btnOK.setText("OK");

        lblPrice.setBounds(16,340,64,32);
        lblQuantity.setBounds(16,388,64,32);

        lblProduct.setBounds(16,16,256,256);
        btnBrowse.setBounds(16,288,96,32);
        btnRemove.setBounds(176,288,96,32);

        txtPrice.setBounds(96,340,176,32);
        txtPrice.setHorizontalAlignment(SwingConstants.RIGHT);

        txtQuantity.setBounds(96,388,176,32);
        txtQuantity.setHorizontalAlignment(SwingConstants.RIGHT);

        btnCancel.setBounds(16,436,96,32);
        btnOK.setBounds(176,436,96,32);

        btnBrowse.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                browseImage();
            }
        });

        btnRemove.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                removeProductImage();
            }
        });

        btnCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
               clearForm();
               MainForm.addProductForm.setVisible(false);
               MainForm.productForm.setVisible(true);
            }
        });

        btnOK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                addProduct();
            }
        });

        add(lblProduct);
        add(btnBrowse);
        add(btnRemove);
        add(lblPrice);
        add(lblQuantity);
        add(txtPrice);
        add(txtQuantity);
        add(btnCancel);
        add(btnOK);
    }

    void browseImage() {
        try {
            JFileChooser fc = new JFileChooser(".");
            fc.setFileFilter(new JpegImageFileFilter());
            int res = fc.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                icnProduct=new ImageIcon(fc.getSelectedFile().getAbsolutePath());
                lblProduct.setIcon(icnProduct);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainForm.addProductForm,ex.getClass().getName() + ": " + ex.getMessage(),"Message",JOptionPane.PLAIN_MESSAGE);
        }

    }

    void removeProductImage() {
        icnProduct=null;
        lblProduct.setIcon(null);
    }

    void addProduct() {

        try {
            int price=(int)(Double.valueOf(txtPrice.getText())*1000);
            int quantity = Integer.valueOf(txtQuantity.getText());
            String base64IcnProduct = new String(convertImageIconToBase64(icnProduct));

            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+MainForm.dbPath);

            String sql = "INSERT INTO PRODUCT (PRODUCT_PRICE,PRODUCT_QUANTITY,PRODUCT_IMAGE_BASE64) VALUES (?,?,?);";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,price);
            pstmt.setInt(2,quantity);
            pstmt.setString(3,base64IcnProduct);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            JOptionPane.showMessageDialog(MainForm.addProductForm,"Product Added","Message",JOptionPane.PLAIN_MESSAGE);

            clearForm();

            MainForm.addProductForm.setVisible(false);
            MainForm.productForm.setVisible(true);

        } catch ( Exception ex ) {

            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainForm.addProductForm,ex.getClass().getName() + ": " + ex.getMessage(),"Message",JOptionPane.PLAIN_MESSAGE);

        }


    }

    byte[] convertImageIconToBase64(ImageIcon icon) {
        try {

            BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.createGraphics();
            icon.paintIcon(null,g,0,0);
            g.dispose();

    		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    		ImageIO.write(bi,"jpg",bos);
    		byte[] data = bos.toByteArray();
    		byte[] encodedBytes = Base64.getEncoder().encode(data);

    		return encodedBytes;

	    } catch(Exception ex) {
	        ex.printStackTrace();
            JOptionPane.showMessageDialog(MainForm.registerForm,ex.getClass().getName() + ": " + ex.getMessage(),"Register Message",JOptionPane.PLAIN_MESSAGE);
       	}

       	return null;
    }

    void clearForm() {
        removeProductImage();
        txtPrice.setText("");
        txtQuantity.setText("");
    }

}

