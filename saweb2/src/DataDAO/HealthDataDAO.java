package DataDAO;

import DataVO.DataVO;
import SQLConnUtils.SQLServerConn;

import java.sql.ResultSet;
import java.util.Vector;

public class HealthDataDAO {
    private SQLServerConn conn=new SQLServerConn();
    public Vector<DataVO> getAllData()
    {
        String sql="select [Premium Adult Individual Age 21],[Premium Adult Individual Age 27]," +
                "[Premium Adult Individual Age 30],[Premium Adult Individual Age 40]," +
                "[Premium Adult Individual Age 50],[Premium Adult Individual Age 60] from software_test_tabel";
        ResultSet resultSet=conn.executeSelectQuery(sql);
        Vector<DataVO> result=new Vector<DataVO>();
        try {
            while (resultSet.next()) {
                DataVO dataVO = new DataVO();
                float v1 = (float) resultSet.getDouble(1);
                dataVO.setPremiumAdultIdi21(v1);
                float v2 = (float) resultSet.getDouble(2);
                dataVO.setPremiumAdultIdi27(v2);
                float v3 = (float) resultSet.getDouble(3);
                dataVO.setPremiumAdultIdi30(v3);
                float v4 = (float) resultSet.getDouble(4);
                dataVO.setPremiumAdultIdi40(v4);
                float v5 = (float) resultSet.getDouble(5);
                dataVO.setPremiumAdultIdi50(v5);
                float v6 = (float) resultSet.getDouble(6);
                dataVO.setPremiumAdultIdi60(v6);

                result.add(dataVO);
            }
        }
        catch(Exception e)
        {

        }
        return result;

    }
    public float[] getMin()
    {
        float[] result=new float[6];
        try {
            String sql="select Min([Premium Adult Individual Age 21]) from software_test_tabel";
            ResultSet resultSet=conn.executeSelectQuery(sql);
            resultSet.next();
            result[0]=(float) resultSet.getDouble(1);

            resultSet=conn.executeSelectQuery("select Min([Premium Adult Individual Age 27]) from software_test_tabel");
            resultSet.next();
            result[1]=(float) resultSet.getDouble(1);

            resultSet=conn.executeSelectQuery("select Min([Premium Adult Individual Age 30]) from software_test_tabel");
            resultSet.next();
            result[2]=(float) resultSet.getDouble(1);

            resultSet=conn.executeSelectQuery("select Min([Premium Adult Individual Age 40]) from software_test_tabel");
            resultSet.next();
            result[3]=(float) resultSet.getDouble(1);

            resultSet=conn.executeSelectQuery("select Min([Premium Adult Individual Age 50]) from software_test_tabel");
            resultSet.next();
            result[4]=(float) resultSet.getDouble(1);

            resultSet=conn.executeSelectQuery("select Min([Premium Adult Individual Age 60]) from software_test_tabel");
            resultSet.next();
            result[5]=(float) resultSet.getDouble(1);
        }
        catch (Exception e)
        {

        }
        return result;
    }
    public float[] getMax()
    {
        float[] result=new float[6];
        try {
            String sql="select Max([Premium Adult Individual Age 21]) from software_test_tabel";
            ResultSet resultSet=conn.executeSelectQuery(sql);
            resultSet.next();
            result[0]=(float) resultSet.getDouble(1);

            resultSet=conn.executeSelectQuery("select Max([Premium Adult Individual Age 27]) from software_test_tabel");
            resultSet.next();
            result[1]=(float) resultSet.getDouble(1);

            resultSet=conn.executeSelectQuery("select Max([Premium Adult Individual Age 30]) from software_test_tabel");
            resultSet.next();
            result[2]=(float) resultSet.getDouble(1);

            resultSet=conn.executeSelectQuery("select Max([Premium Adult Individual Age 40]) from software_test_tabel");
            resultSet.next();
            result[3]=(float) resultSet.getDouble(1);

            resultSet=conn.executeSelectQuery("select Max([Premium Adult Individual Age 50]) from software_test_tabel");
            resultSet.next();
            result[4]=(float) resultSet.getDouble(1);

            resultSet=conn.executeSelectQuery("select Max([Premium Adult Individual Age 60]) from software_test_tabel");
            resultSet.next();
            result[5]=(float) resultSet.getDouble(1);
        }
        catch (Exception e)
        {

        }
        return result;
    }
}
