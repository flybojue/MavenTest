package data;

import org.testng.annotations.DataProvider;
import util.ReadProperties;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestSql {

    static Connection conn;
    private static ResultSet rs;

    private static String sql = "SELECT name, date, remarks FROM test_case where isUsed = 1";

    // 方法一：先将sql查询出来的ResultSet转换成list；再调用转换成object
//    @DataProvider(name = "selectSQL")
//    public static Object[][] dataTest() throws SQLException, IOException {
//        conn = DriverManager.getConnection(ReadProperties.getProp("mySQL", "mySQL", "ip"), ReadProperties.getProp("mySQL", "mySQL", "username"),
//                ReadProperties.getProp("mySQL", "mySQL", "pwd"));
//        List<List<Object>> alist = querySQL();
//        Object[][] obj = new Object[alist.size()][alist.get(0).size()];
//
//        for (int i = 0; i < alist.size(); i++) {
//            List<Object> list = alist.get(i);
//            for (int j = 0; j < list.size(); j++) {
//                obj[i][j] = list.get(j);
//            }
//        }
//        return obj;
//    }
//
//    // ResultSet转list
//    public static List<List<Object>> querySQL() throws SQLException {
//        rs = conn.prepareStatement(sql).executeQuery();
//        ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
//        int columnCount = md.getColumnCount(); //获得列数
//
//        List results = new ArrayList();
//        while (rs.next()) {
//            List content = new ArrayList();
//            for (int i = 1; i <= columnCount; i++) {
//                content.add(rs.getObject(i));
//            }
//            results.add(content);
//        }
//        return results;
//    }

    // 方法二：直接将sql查询出来的ResultSet转换成object
    @DataProvider(name = "selectSQL")
    public static Object[][] dataTest() throws SQLException, IOException {
        conn = DriverManager.getConnection(ReadProperties.getProp("mySQL", "mySQL", "ip"), ReadProperties.getProp("mySQL", "mySQL", "username"),
                ReadProperties.getProp("mySQL", "mySQL", "pwd"));
        rs = conn.prepareStatement(sql).executeQuery();
        ResultSetMetaData md = rs.getMetaData(); // 获得结果集结构信息,元数据
        int columnCount = md.getColumnCount(); // 获得列数
        rs.last();// 将指针移至最后一行，再通过rs.getRow()来获得行数
        Object[][] obj = new Object[rs.getRow()][columnCount];

        rs.beforeFirst();// 获取行数后，再将指针还原至第一行，用以rs.next()的循环
        while (rs.next()) {
            for (int i = 0; i < columnCount; i++) {
                obj[rs.getRow() - 1][i] = rs.getObject((i + 1));
            }
        }
        return obj;
    }

}
