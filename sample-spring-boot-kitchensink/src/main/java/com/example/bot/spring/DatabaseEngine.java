/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.bot.spring;

import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j

/**
 *
 * @author Clara Christina
 */
public class DatabaseEngine {

    Connection c;

    public DatabaseEngine() throws URISyntaxException, SQLException {
        this.c = getConnection();
    }

    private Connection getConnection() throws URISyntaxException, SQLException {

        Connection connection;

        //URI dbUri = new URI(System.getenv("hayvnwccqwqsyz:2260beab8711e5eed8273e34d034e0e701cd078b6c85765708b8a05d93112fc2@ec2-23-23-225-12.compute-1.amazonaws.com:5432/d7edocubgj0rb7"));
//        String username = dbUri.getUserInfo().split(":")[0];
//        String password = dbUri.getUserInfo().split(":")[1];
        String username = "hayvnwccqwqsyz";
        String password = "2260beab8711e5eed8273e34d034e0e701cd078b6c85765708b8a05d93112fc2";
        String dbUrl = "jdbc:postgresql://ec2-23-23-225-12.compute-1.amazonaws.com:5432/d7edocubgj0rb7?user=hayvnwccqwqsyz&password=2260beab8711e5eed8273e34d034e0e701cd078b6c85765708b8a05d93112fc2&?ssl=true";//&sslfactory=org.postgresql.ssl.NonValidatingFactory";

        log.info("Username: {} Password: {}", username, password);
        log.info("dbUrl: {}", dbUrl);

//        connection = DriverManager.getConnection(dbUrl,username, password);
        connection = DriverManager.getConnection(dbUrl);

        return connection;
    }

    String savePersonal(String userId, String key, String value) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            c.setAutoCommit(false);
            Statement stmt = null;
            stmt = c.createStatement();
            String sql = "INSERT INTO pm (user_id,key,value) " + "VALUES ('" + userId + "','" + key
                    + "','" + value + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
            return "Tersimpan";

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    String saveGroup(String userId, String key, String value) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            c.setAutoCommit(false);
            Statement stmt = null;
            stmt = c.createStatement();
            String sql = "INSERT INTO grup (grup_id,key,value) " + "VALUES ('" + userId + "','" + key
                    + "','" + value + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
            return "Tersimpan";

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    String saveMulti(String userId, String key, String value) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            c.setAutoCommit(false);
            Statement stmt = null;
            stmt = c.createStatement();
            String sql = "INSERT INTO multi (room_id,key,value) " + "VALUES ('" + userId + "','" + key
                    + "','" + value + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
            return "Tersimpan";

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    String load(String userid, String key) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT user_id, key, value FROM pm WHERE user_id='" + userid + "' AND key='" + key + "';");
            String res_value = "";
            while (rs.next()) {
//                String res_id = rs.getString("user_id");
//                String res_key = rs.getString("key");
                res_value = rs.getString("value");
//                String res_status = rs.getString("status");
            }
            rs.close();
            stmt.close();
            c.close();
            if(res_value.equals("")){
                return "Data tidak ditemukan";
            }else{
                return res_value;
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    String loadGroup(String grupid, String key) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT grup_id, key, value FROM grup WHERE grup_id='" + grupid + "' AND key='" + key + "';");
            String res_value = "";
            while (rs.next()) {
//                String res_id = rs.getString("user_id");
//                String res_key = rs.getString("key");
                res_value = rs.getString("value");
//                String res_status = rs.getString("status");
            }
            rs.close();
            stmt.close();
            c.close();
            if(res_value.equals("")){
                return "Data tidak ditemukan";
            }else{
                return res_value;
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    String loadMulti(String roomid, String key) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT room_id, key, value FROM multi WHERE room_id='" + roomid + "' AND key='" + key + "';");
            String res_value = "";
            while (rs.next()) {
//                String res_id = rs.getString("user_id");
//                String res_key = rs.getString("key");
                res_value = rs.getString("value");
//                String res_status = rs.getString("status");
            }
            rs.close();
            stmt.close();
            c.close();
            if(res_value.equals("")){
                return "Data tidak ditemukan";
            }else{
                return res_value;
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    String getStatusPersonal(String userid) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT user_id, status FROM statuspersonal WHERE user_id='" + userid + "';");
            String res_status = "";
            while (rs.next()) {
//                String res_id = rs.getString("user_id");
//                String res_key = rs.getString("key");
                res_status = rs.getString("status");
//                String res_status = rs.getString("status");
            }
            rs.close();
            stmt.close();
            c.close();
            return res_status;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    String getStatusGroup(String grupid) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT grup_id, status FROM statusgrup WHERE grup_id='" + grupid + "';");
            String res_status = "";
            while (rs.next()) {
//                String res_id = rs.getString("user_id");
//                String res_key = rs.getString("key");
                res_status = rs.getString("status");
//                String res_status = rs.getString("status");
            }
            rs.close();
            stmt.close();
            c.close();
            return res_status;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    String getStatusMulti(String roomid) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT room_id, status FROM statusmulti WHERE room_id='" + roomid + "';");
            String res_status = "";
            while (rs.next()) {
//                String res_id = rs.getString("user_id");
//                String res_key = rs.getString("key");
                res_status = rs.getString("status");
//                String res_status = rs.getString("status");
            }
            rs.close();
            stmt.close();
            c.close();
            return res_status;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public boolean changeStatusPersonal(String userId, String status) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("UPDATE statuspersonal SET status='" + status + "' WHERE user_id ='" + userId + "';");
//            String res_value = "";
//            while (rs.next()) {
////                String res_id = rs.getString("user_id");
////                String res_key = rs.getString("key");
//                res_value = rs.getString("value");
////                String res_status = rs.getString("status");
//            }
            rs.close();
            stmt.close();
            c.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean changeStatusGroup(String grupId, String status) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("UPDATE statusgrup SET status='" + status + "' WHERE grup_id ='" + grupId + "';");
//            String res_value = "";
//            while (rs.next()) {
////                String res_id = rs.getString("user_id");
////                String res_key = rs.getString("key");
//                res_value = rs.getString("value");
////                String res_status = rs.getString("status");
//            }
            rs.close();
            stmt.close();
            c.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
     public boolean changeStatusMulti(String roomId, String status) throws Exception {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("UPDATE statusmulti SET status='" + status + "' WHERE room_id ='" + roomId + "';");
//            String res_value = "";
//            while (rs.next()) {
////                String res_id = rs.getString("user_id");
////                String res_key = rs.getString("key");
//                res_value = rs.getString("value");
////                String res_status = rs.getString("status");
//            }
            rs.close();
            stmt.close();
            c.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String insertStatusPersonal(String userid, String status) {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            c.setAutoCommit(false);
            Statement stmt = null;
            stmt = c.createStatement();
            String sql = "INSERT INTO statuspersonal (user_id,status) " + "VALUES ('" + userid + "','" + status+ "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
            return "Ok";

        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String insertStatusGroup(String grupid, String status) {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            c.setAutoCommit(false);
            Statement stmt = null;
            stmt = c.createStatement();
            String sql = "INSERT INTO statusgrup (grup_id,status) " + "VALUES ('" + grupid + "','" + status+ "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
            return "Ok";

        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String insertStatusMulti(String roomid, String status) {
        try {
            this.c = getConnection();
//            String s = "";
//            if (c != null) {
//                s = "connected";
//            }
            c.setAutoCommit(false);
            Statement stmt = null;
            stmt = c.createStatement();
            String sql = "INSERT INTO statusmulti (room_id,status) " + "VALUES ('" + roomid + "','" + status+ "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
            return "Ok";

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    //  String search(String text) throws Exception {
    // 	String result = null;
    // 	BufferedReader br = null;
    // 	InputStreamReader isr = null;
    // 	try {
    // 		isr = new InputStreamReader(
    //                 this.getClass().getResourceAsStream(FILENAME));
    // 		br = new BufferedReader(isr);
    // 		String sCurrentLine;
    // 		while (result != null && (sCurrentLine = br.readLine()) != null) {
    // 			String[] parts = sCurrentLine.split(":");
    // 			if (text.toLowerCase().equals(parts[0].toLowerCase())) {
    // 				result = parts[1];
    // 			}
    // 		}
    // 	} catch (IOException e) {
    // 		log.info("IOException while reading file: {}", e.toString());
    // 	} finally {
    // 		try {
    // 			if (br != null)
    // 				br.close();
    // 			if (isr != null)
    // 				isr.close();
    // 		} catch (IOException ex) {
    // 			log.info("IOException while closing file: {}", ex.toString());
    // 		}
    // 	}
    // 	if (result != null)
    // 		return result;
    // 	throw new Exception("NOT FOUND");
    // }
}
