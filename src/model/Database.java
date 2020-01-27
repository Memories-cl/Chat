package model;

/**
 * ���������������ݿ�
 * 
 */


import java.sql.*;

public class Database {

	private String url = "jdbc:mysql://localhost:3306/chat_1.0?serverTimezone=UTC&useSSL=FALSE";
    private final static String driver = "com.mysql.cj.jdbc.Driver";
    private final static String userName = "cyy_lmh";
    private final static String password = "123456";
    private Connection connection;
//    private Statement statement;//��̬��ѯ
//    ������ֻʹ�ö�̬��ѯ
    private PreparedStatement preparedStatement;//��̬��ѯ
    public Database() {
 
    } 
    /*
    �������ݿ�
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
     * �÷�������ִ��Sql��䲢���ؽ���� �ʺ���Ҫ���ؽ�����Ĳ�ѯ��� ����   execResult("select*from user where id = ? and name = ?","1","jack");
     * ���ʺ�ռλ Ȼ�����String�������Ҫ�ʺŵ�ֵ �÷������ظ������ �� ResultSet
     *
     * @param Sql
     * @param data
     * @return
     * @throws SQLException
     */
    public  ResultSet execResult(String Sql, String... data) throws SQLException {//String����java5�¼���Ĺ��ܣ���ʾ����һ���ɱ䳤�ȵĲ����б�
        preparedStatement = connection.prepareStatement(Sql);
        for (int i = 1; i <= data.length; i++) {
            preparedStatement.setString(i, data[i - 1]);
        }
        return preparedStatement.executeQuery(); 
    }
    /**
     * �ж�ĳ���������ݿ��Ƿ����
     */
    public  boolean exist(String tableName, String data) throws SQLException {//String����java5�¼���Ĺ��ܣ���ʾ����һ���ɱ䳤�ȵĲ����б�
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
     * ִ��Sql��� �������κζ��� ����exec("update user set password = ? where account = ?","password","name");
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
     * ִ�о�̬SQL���  ����exec("delete from user");
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
     * �÷������������  ����insert(����,Ҫ���������(String�������ʽ))
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
     * �÷���ɾ�������� ����delete(����,ɾ��ʱ������(����"id = ? AND name = ?"),�����ʺŴ����ֵ)
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
     * ��������Щһ��
     *
     * @param tableName
     * @param target
     * @param condition
     * @param data
     * @throws SQLException
     */
    public void update(String tableName, String target, String condition, String data[]) throws SQLException {//string data[]��string...���
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
     * �õ���̬��ѯ����
     * @return
     */
    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
 
    /**
     * �õ����ݿ����Ӷ���
     * @return
     */
    public Connection getConnection() {
        return connection;
    }
 
    /**
     * ���ݿ�����
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
