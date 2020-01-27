package model;

/**
 * 这类用来操作数据库
 * 
 */


import java.sql.*;

public class Database {

	private String url = "jdbc:mysql://localhost:3306/chat_1.0?serverTimezone=UTC&useSSL=FALSE";
    private final static String driver = "com.mysql.cj.jdbc.Driver";
    private final static String userName = "cyy_lmh";
    private final static String password = "123456";
    private Connection connection;
//    private Statement statement;//静态查询
//    这里我只使用动态查询
    private PreparedStatement preparedStatement;//动态查询
    public Database() {
 
    } 
    /*
    链接数据库
     */
    public void connect(){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     *
     *
     * 该方法用来执行Sql语句并返回结果集 适合需要返回结果集的查询语句 例如   execResult("select*from user where id = ? and name = ?","1","jack");
     * 用问号占位 然后传入个String数组代表要问号的值 该方法返回个结果集 即 ResultSet
     *
     * @param Sql
     * @param data
     * @return
     * @throws SQLException
     */
    public  ResultSet execResult(String Sql, String... data) throws SQLException {//String…是java5新加入的功能，表示的是一个可变长度的参数列表
        preparedStatement = connection.prepareStatement(Sql);
        for (int i = 1; i <= data.length; i++) {
            preparedStatement.setString(i, data[i - 1]);
        }
        return preparedStatement.executeQuery(); 
    }
    /**
     * 判断某数据在数据库是否存在
     */
    public  boolean exist(String tableName, String data) throws SQLException {//String…是java5新加入的功能，表示的是一个可变长度的参数列表
        String sql = "SELECT COUNT(*) FROM "+tableName +" WHERE UserName="+"'"+data+"'";
    	
        PreparedStatement ps= connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
        	if(rs.getInt(1)==0) {
        		return false;
        	}
        	else {
        		return true;
        	}
        	
        }
        return false;
    }
    /**
     * 
     *
     * 执行Sql语句 不返回任何东西 例如exec("update user set password = ? where account = ?","password","name");
     * exec("delete from user where name = ? and account = ?","name","account");
     * exec("insert into user values(?,?,?,?,?,?,?,?,?)",1,2,3,4,5,6,7,8,9);
     * @param Sql
     * @param data
     * @throws SQLException
     */
    public void exec(String Sql, String...data) throws SQLException {
 
        preparedStatement = connection.prepareStatement(Sql);
        for (int i = 1; i <= data.length; i++) {
            preparedStatement.setString(i, data[i - 1]);
        }
        preparedStatement.executeUpdate();
    }
 
    /**
     * 执行静态SQL语句  例如exec("delete from user");
     * @param Sql
     */
    public void exec(String Sql) {
        try
        {
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.executeUpdate();
        }catch (Exception e){
        }
    }
    /**
     * 该方法插入个数据  例如insert(表名,要插入的数据(String数组的形式))
     *
     * @param tableName
     * @param data
     * @throws SQLException
     */
    public void insert(String tableName, String... data) throws SQLException {
 
        String pre = "";
        for (int i = 0; i < data.length; i++) {
 
            if (i != data.length - 1)
                pre += "?,";
            else
                pre += "?";
 
        }
 
        String Sql = "INSERT INTO " + tableName + " VALUES(" + pre + ")";
        preparedStatement = connection.prepareStatement(Sql);
        for (int i = 1; i <= data.length; i++) {
 
            preparedStatement.setString(i, data[i - 1]);
 
        }
        preparedStatement.executeUpdate();
 
    }
 
    /**
     * 该方法删除表数据 例如delete(表名,删除时的条件(例如"id = ? AND name = ?"),传入问号代表的值)
     *
     * @param tableName
     * @param condition
     * @param data
     * @throws SQLException
     */
    public void delete(String tableName, String condition, String... data) throws SQLException {
 
 
        String Sql = "DELETE FROM " + tableName + " WHERE " + condition;
 
 
        preparedStatement = connection.prepareStatement(Sql);
        for (int i = 1; i <= data.length; i++) {
 
            preparedStatement.setString(i, data[i - 1]);
 
 
        }
        preparedStatement.executeUpdate();
 
 
    }
 
    /**
     * 跟上面那些一样
     *
     * @param tableName
     * @param target
     * @param condition
     * @param data
     * @throws SQLException
     */
    public void update(String tableName, String target, String condition, String data[]) throws SQLException {//string data[]和string...差不多
        String Sql = "UPDATE " + tableName + " SET " + target + " WHERE " + condition;
        preparedStatement = connection.prepareStatement(Sql);
 
        for (int i = 1; i <= data.length; i++) {
 
            preparedStatement.setString(i, data[i - 1]);
 
        }
        preparedStatement.executeUpdate();
 
    }
 

 
    /**
     * @param Sql
     * @param data
     * @return
     * @throws SQLException
     */
    public ResultSet select(String Sql, String... data) throws SQLException {
 
 
        preparedStatement = connection.prepareStatement(Sql);
        for (int i = 1; i <= data.length; i++) {
 
            preparedStatement.setString(i, data[i - 1]);
        }
        return preparedStatement.executeQuery();
 
    }
    
 
 
 
    /**
     * 得到动态查询对象
     * @return
     */
    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
 
    /**
     * 得到数据库链接对象
     * @return
     */
    public Connection getConnection() {
        return connection;
    }
 
    /**
     * 数据库重连
     * @param Url
     * @param UserName
     * @param Password
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void reConnection(String Url, String UserName, String Password) throws ClassNotFoundException, SQLException {
 
        Class.forName(driver);
        connection = DriverManager.getConnection(Url, UserName, Password);
 
    }
}
