package SQLConnUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLServerConn {
    private Connection conn;
    //创建连接
    public SQLServerConn()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn=DriverManager.getConnection("jdbc:sqlserver://rm-bp1i79232adq9114vco.sqlserver.rds.aliyuncs.com:" +
                    "3433;databaseName=software_architecture","tlj","Tljzh85594");

        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }
    //关闭连接
    public void close()
    {
        try {
            conn.close();
        }
        catch(Exception e)
        {
            System.out.println("Close the conn failed!");
        }
    }
    //sql语句执行并返回结果
    public ResultSet executeSelectQuery(String sql)
    {
        ResultSet resultSet=null;
        try {
            Statement statement = conn.createStatement();
            resultSet=statement.executeQuery(sql);
        }
        catch(Exception e)
        {
            System.out.println("execute failed!");
        }
        return resultSet;
    }
}
