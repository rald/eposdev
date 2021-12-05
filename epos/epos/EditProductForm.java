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



class EditProductForm extends JFrame {

    JLabel lblCode = new JLabel();
    JLabel lblName = new JLabel();
    JLabel lblPrice = new JLabel();
    JLabel lblQuantity = new JLabel();

    JLabel lblProduct = new JLabel();
    JButton btnBrowse = new JButton();
    JButton btnRemove = new JButton();
    JButton btnCancel = new JButton();
    JButton btnOK = new JButton();
    JTextField txtCode = new JTextField();
    JTextField txtName = new JTextField();
    JTextField txtPrice = new JTextField();
    JTextField txtQuantity = new JTextField();

    ImageIcon icnNoImage = null;

    int selectedProductId=-1;

    EditProductForm() {
        createGui();
    }

    void createGui() {
        setTitle(MainForm.appName+" - Edit Product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(296,608);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        icnNoImage = new ImageIcon(getClass().getResource("/epos/images/no_image.png"));

        lblProduct.setForeground(Color.WHITE);
        lblProduct.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        lblProduct.setHorizontalAlignment(SwingConstants.CENTER);
        lblProduct.setVerticalAlignment(SwingConstants.CENTER);
        lblProduct.setIcon(icnNoImage);

        lblCode.setText("Code");
        lblName.setText("Name");
        lblPrice.setText("Price");
        lblQuantity.setText("Quantity");

        btnBrowse.setText("Browse");
        btnRemove.setText("Remove");
        btnCancel.setText("Cancel");
        btnOK.setText("OK");

        lblCode.setBounds(16,340,64,32);
        lblName.setBounds(16,388,64,32);
        lblPrice.setBounds(16,436,64,32);
        lblQuantity.setBounds(16,484,64,32);

        lblProduct.setBounds(16,16,256,256);
        btnBrowse.setBounds(16,288,96,32);
        btnRemove.setBounds(176,288,96,32);

        txtCode.setBounds(96,340,176,32);
        txtName.setBounds(96,388,176,32);

        txtPrice.setBounds(96,436,176,32);
        txtPrice.setHorizontalAlignment(SwingConstants.RIGHT);

        txtQuantity.setBounds(96,484,176,32);
        txtQuantity.setHorizontalAlignment(SwingConstants.RIGHT);

        btnCancel.setBounds(16,532,96,32);
        btnOK.setBounds(176,532,96,32);

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
               MainForm.editProductForm.setVisible(false);
               MainForm.productForm.setVisible(true);
            }
        });

        btnOK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                updateProduct();
            }
        });

        add(lblProduct);
        add(btnBrowse);
        add(btnRemove);
        add(lblCode);
        add(lblName);
        add(lblPrice);
        add(lblQuantity);
        add(txtCode);
        add(txtName);
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
                lblProduct.setIcon(new ImageIcon(fc.getSelectedFile().getAbsolutePath()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainForm.addProductForm,ex.getClass().getName() + ": " + ex.getMessage(),"Message",JOptionPane.PLAIN_MESSAGE);
        }

    }

    void removeProductImage() {
        lblProduct.setIcon(icnNoImage);
    }

    void updateProduct() {

        try {
            String code=txtCode.getText();
            String name=txtName.getText();
            int price=(int)(Double.valueOf(txtPrice.getText())*1000);
            int quantity = Integer.valueOf(txtQuantity.getText());
            String icnProductBase64=null;
            if(lblProduct.getIcon()!=null) {
                icnProductBase64 = new String(convertImageIconToBase64((ImageIcon)lblProduct.getIcon()));
            }
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+MainForm.dbPath);

            String sql = "UPDATE PRODUCT SET PRODUCT_CODE=?,PRODUCT_NAME=?,PRODUCT_PRICE=?,PRODUCT_QUANTITY=?,PRODUCT_IMAGE_BASE64=? WHERE ID=?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,code);
            pstmt.setString(2,name);
            pstmt.setInt(3,price);
            pstmt.setInt(4,quantity);
            pstmt.setString(5,icnProductBase64);
            pstmt.setInt(6,selectedProductId);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            JOptionPane.showMessageDialog(MainForm.editProductForm,"Product Updated","Message",JOptionPane.PLAIN_MESSAGE);

            clearForm();

            MainForm.editProductForm.setVisible(false);
            MainForm.productForm.setVisible(true);

        } catch ( Exception ex ) {

            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainForm.editProductForm,ex.getClass().getName() + ": " + ex.getMessage(),"Message",JOptionPane.PLAIN_MESSAGE);

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
            JOptionPane.showMessageDialog(MainForm.addProductForm,ex.getClass().getName() + ": " + ex.getMessage(),"Register Message",JOptionPane.PLAIN_MESSAGE);
       	}

       	return null;
    }

    void clearForm() {
        removeProductImage();
        txtCode.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }


    void loadProduct(int selectedProductId) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+MainForm.dbPath);

            String sql = "SELECT ID,PRODUCT_CODE,PRODUCT_NAME,PRODUCT_PRICE,PRODUCT_QUANTITY,PRODUCT_IMAGE_BASE64 FROM PRODUCT WHERE ID=?;";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,selectedProductId);
            rs=pstmt.executeQuery();

            if(rs.next()) {
                this.selectedProductId=rs.getInt("ID");
                String code=rs.getString("PRODUCT_CODE");
                String name=rs.getString("PRODUCT_NAME");
                double price=rs.getInt("PRODUCT_PRICE")/1000.0;
                int quantity=rs.getInt("PRODUCT_QUANTITY");
                String imageBase64=rs.getString("PRODUCT_IMAGE_BASE64");

                ImageIcon icon = new ImageIcon("products/No Image.png");

                if(imageBase64!=null) {

            		byte[] decodedBytes = Base64.getDecoder().decode(imageBase64);

                    ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
                    BufferedImage bi = ImageIO.read(bais);
                    icon = new ImageIcon(bi);

                }

                lblProduct.setIcon(icon);
                txtCode.setText(code);
                txtName.setText(name);
                txtPrice.setText(String.format("%.2f",price));
                txtQuantity.setText(String.valueOf(quantity));

            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch ( Exception ex ) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainForm.editProductForm,ex.getClass().getName()+": "+ex.getMessage(),"Message",JOptionPane.PLAIN_MESSAGE);
        }

    }


}

