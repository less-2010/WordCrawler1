package org.crawler.db;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


/**
 * 模型层 获取数据库连接
 *
 * @author BING
 * @date 2020/8/29 11:48
 */
public class DBUtil {
    private static String URL = null;
    private static  String USER = null;
    private static  String PASSWORD = null;
    private static String Driver = null;
    private static Connection conn = null;
    static {
        //创建资源容器
        Properties properties = new Properties();
        //获取配置资源
        InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("DBWordsInfo.properties");
        try {
            //获取配置属性
            properties.load(inputStream);
            URL = properties.getProperty("URL");
            USER  = properties.getProperty("User");
            PASSWORD = properties.getProperty("PassWord");
            Driver = properties.getProperty("Driver");
            //加载驱动程序
            Class.forName(Driver);
            //获取连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * 关闭连接
     *
     * @param conn
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn, Statement st, ResultSet rs) {
        close(st);
        close(rs);
        close(conn);
    }

    public static void close(PreparedStatement ptm) {
        try {
            ptm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement ptm, Connection conn) {
        close(ptm);
        close(conn);
    }

}