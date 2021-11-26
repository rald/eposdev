import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;

import epos.MD5;

public class CreateProductTable {

    public static void main(String[] args) {


        String path="./epos.db";
        String userrole="admin";
        String username="admin";
        String userpass="1234";

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+path);

            String sql = "DROP TABLE IF EXISTS PRODUCT;";
            stmt = conn.createStatement();
            stmt.execute(sql);

            stmt.close();
            conn.close();

            System.out.println("OLD PRODUCT TABLE DROPPED");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }


        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+path);

            String sql = "CREATE TABLE PRODUCT (ID INTEGER PRIMARY KEY AUTOINCREMENT,PRODUCT_NAME TEXT,PRODUCT_PRICE INTEGER, PRODUCT_QUANTITY INTEGER,PRODUCT_IMAGE_BASE64 TEXT);";
            stmt = conn.createStatement();
            stmt.execute(sql);

            stmt.close();
            conn.close();

            System.out.println("NEW PRODUCT TABLE CREATED");

        } catch ( Exception ex ) {
            ex.printStackTrace();
            System.exit(1);
        }


    }

}



