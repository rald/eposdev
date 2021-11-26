import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;

import epos.MD5;

public class CreateUserTable {

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

            String sql = "DROP TABLE IF EXISTS USER;";
            stmt = conn.createStatement();
            stmt.execute(sql);

            stmt.close();
            conn.close();

            System.out.println("OLD USER TABLE DROPPED");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }


        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+path);

            String sql = "CREATE TABLE USER (ID INTEGER PRIMARY KEY AUTOINCREMENT,USER_ROLE TEXT, USER_NAME TEXT NOT NULL,USER_PASS TEXT NOT NULL);";
            stmt = conn.createStatement();
            stmt.execute(sql);

            stmt.close();
            conn.close();

            System.out.println("NEW USER TABLE CREATED");

        } catch ( Exception ex ) {
            ex.printStackTrace();
            System.exit(1);
        }

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+path);

            String sql = "INSERT INTO USER(USER_ROLE,USER_NAME,USER_PASS) VALUES(?,?,?);";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,userrole);
            pstmt.setString(2,username);
            pstmt.setString(3,MD5.getMd5(userpass));
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            System.out.println("NEW ADMIN ACCOUNT CREATED");

        } catch ( Exception ex ) {
            ex.printStackTrace();
            System.exit(1);
        }



    }

}



