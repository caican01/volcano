package utils;

import java.sql.*;

/**
 * mysql数据库操作工具
 */
public class JdbcUtil {

    private static String url = "jdbc:mysql://volcano01.cc.com:3306/MovieRecommendSystem?characterEncoding=utf-8";
    private static String user = "root";
    private static String pwd = "123456";

    private JdbcUtil() {}

    /**
     *注册驱动oracle.jdbc.driver.OracleDriver
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库驱动加载失败！");
        }
    }

    /**
     *建立一个连接
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pwd);
    }

    /**
     * 这个main方法主要是用于测试这个工具类的功能的
     * 正式上线到生产环境中时需要将它注释掉
     */
//    public static void main(String[] args){
//        try{
//            Connection conn=getConnection();
//            String sql = "insert into textFile (value) values(?)";
//            PreparedStatement pstmt;
//            pstmt = (PreparedStatement) conn.prepareStatement(sql);
//            pstmt.setString(1,"test");
//            pstmt.executeUpdate();
//            free(pstmt,conn);
//            System.out.println("******"+conn);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    /**
     * 读取mysql数据
     * @param query
     * @return
     */
    public static ResultSet queryFromMysql(String tableName,String condition,Integer query) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement queryPreparedStatement = conn.prepareStatement("SELECT * from " + tableName + " where "+condition+" = "+query);
        ResultSet resultSet = queryPreparedStatement.executeQuery();
        return resultSet;
    }

    /**
     * 更新数据库
     * @param tableName
     * @param conditionKey
     * @param conditionValue
     * @param key
     * @param value
     */
    public static void updateToMysql(String tableName,String conditionKey,Integer conditionValue,String key,String value) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement updatePreparedStatement = conn.prepareStatement("UPDATE " + tableName + " set " + key + " = " + value + " WHERE " + conditionKey + " = " + conditionValue);
        updatePreparedStatement.executeUpdate();
        free(updatePreparedStatement,conn);
    }

    /**
     * 更新数据库
     * @param tableName
     * @param conditionValue
     * @param value
     */
    public static void updateToMysql(String tableName,Integer conditionValue,String value) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement updatePreparedStatement = conn.prepareStatement("UPDATE " + tableName + " set  movie_ids = ? WHERE user_id = ?");
        updatePreparedStatement.setString(1,value);
        updatePreparedStatement.setInt(2,conditionValue);
        updatePreparedStatement.executeUpdate();
        free(updatePreparedStatement,conn);
    }

    /**
     * 更新数据库
     * @param tableName
     * @param conditionKey
     * @param conditionValue
     * @param key
     * @param value
     */
    public static void updateToMysql(String tableName,String conditionKey,Integer conditionValue,String key,double value) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement updatePreparedStatement = conn.prepareStatement("UPDATE " + tableName + " set " + key + " = " + value + " WHERE " + conditionKey + " = " + conditionValue);
        updatePreparedStatement.executeUpdate();
        free(updatePreparedStatement,conn);
    }

    /**
     * 更新数据库
     * @param tableName
     * @param conditionKey
     * @param conditionValue
     * @param key
     * @param value
     */
    public static void updateToMysql(String tableName,String conditionKey,Integer conditionValue,String key,int value) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement updatePreparedStatement = conn.prepareStatement("UPDATE " + tableName + " set " + key + " = " + value + " WHERE " + conditionKey + " = " + conditionValue);
        updatePreparedStatement.executeUpdate();
        free(updatePreparedStatement,conn);
    }

    /**
     * 删除指定表的所有的记录
     * @param tableName
     */
    public static void deleteAllDataFromTable(String tableName) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement deletePreparedStatement = conn.prepareStatement("DELETE FROM " + tableName);
        deletePreparedStatement.executeUpdate();
        free(deletePreparedStatement,conn);
    }

    /**
     * 添加数据到mysql数据库
     * @param tableName
     * @param Id
     * @param data
     * @throws SQLException
     */
    public static void insert2ParametersIntoMysql(String tableName,int Id,String data) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement insertPreparedStatement = conn.prepareStatement("INSERT INTO "+tableName+" VALUES (?,?)");
        insertPreparedStatement.setInt(1,Id);
        insertPreparedStatement.setString(2,data);
        insertPreparedStatement.executeUpdate();
        free(insertPreparedStatement,conn);
    }

    /**
     * 添加数据到mysql数据库
     * @param tableName
     * @param movieId
     * @param data
     * @throws SQLException
     */
    public static void insert2ParametersIntoMysql(String tableName,int movieId,Integer data) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement insertPreparedStatement = conn.prepareStatement("INSERT INTO "+tableName+" VALUES (?,?)");
        insertPreparedStatement.setInt(1,movieId);
        insertPreparedStatement.setInt(2,data);
        insertPreparedStatement.executeUpdate();
        free(insertPreparedStatement,conn);
    }

    /**
     * 添加数据到mysql数据库
     * @param tableName
     * @param movieId1
     * @param movieId2
     * @param data
     * @throws SQLException
     */
    public static void insert3ParametersIntoMysql(String tableName,int movieId1,int movieId2,String data) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement insertPreparedStatement = conn.prepareStatement("INSERT INTO "+tableName+" VALUES (?,?,?)");
        insertPreparedStatement.setInt(1,movieId1);
        insertPreparedStatement.setInt(2,movieId2);
        insertPreparedStatement.setString(3,data);
        insertPreparedStatement.executeUpdate();
        free(insertPreparedStatement,conn);
    }

    /**
     * 关闭资源
     */
    public static void free(Statement stmt, Connection conn) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
