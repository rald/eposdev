package epos;


import java.awt.Image;
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
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import javax.imageio.ImageIO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ListCellRenderer;

class ProductForm extends JFrame {

    JPanel      pnlSearch = new JPanel();
    JPanel      pnlOperations = new JPanel();

    JTextField  txtSearch = new JTextField();
    JButton     btnSearch = new JButton();

    JButton     btnAdd = new JButton();
    JButton     btnEdit = new JButton();
    JButton     btnRemove = new JButton();
    JButton     btnCancel = new JButton();


    DefaultListModel dlmProduct = new DefaultListModel();
    JList       lstProduct = new JList(dlmProduct);
    JScrollPane spnProduct = new JScrollPane(lstProduct);
    ListCellRenderer renderer = new ListCellRenderer();

    Map<String,ImageIcon> mapProduct=null;

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

        lstProduct.setCellRenderer(renderer);


        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                searchProduct(txtSearch.getText());
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
        add(spnProduct,BorderLayout.CENTER);

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

    void searchProduct(String searchText) {

        System.out.printf("Search: %s\n",searchText);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+MainForm.dbPath);

            String sql = "SELECT ID,PRODUCT_CODE,PRODUCT_NAME,PRODUCT_PRICE,PRODUCT_QUANTITY,PRODUCT_IMAGE_BASE64,'%'||?||'%' AS SEARCH_TEXT FROM PRODUCT WHERE ID LIKE SEARCH_TEXT OR PRODUCT_CODE LIKE SEARCH_TEXT OR PRODUCT_NAME LIKE SEARCH_TEXT OR PRODUCT_PRICE LIKE SEARCH_TEXT OR PRODUCT_QUANTITY LIKE SEARCH_TEXT;";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,searchText);
            rs=pstmt.executeQuery();


            mapProduct=new HashMap();

            while(rs.next()) {
                int id=rs.getInt("ID");
                String name=rs.getString("PRODUCT_NAME");
                int price=rs.getInt("PRODUCT_PRICE");
                int quantity=rs.getInt("PRODUCT_QUANTITY");
                String imageBase64=rs.getString("PRODUCT_IMAGE_BASE64");

        		byte[] decodedBytes = Base64.getDecoder().decode(imageBase64);

                ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
                BufferedImage bi = ImageIO.read(bais);
                ImageIcon icon = new ImageIcon(bi);

                mapProduct.put(name,icon);

                System.out.printf("%04d %32s %.2f %4d\n",id,name,price/1000.0,quantity);
            }

            dlmProduct.removeAllElements();
            for(String key:mapProduct.keySet()) {
                dlmProduct.addElement(key);
            }
            MainForm.productForm.revalidate();
            MainForm.productForm.repaint();


            rs.close();
            pstmt.close();
            conn.close();
        } catch ( Exception ex ) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MainForm.productForm,ex.getClass().getName()+": "+ex.getMessage(),"Message",JOptionPane.PLAIN_MESSAGE);
        }
    }

    public class ListCellRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvetica", Font.BOLD, 24);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(mapProduct.get(value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }
}
