package mifarma.ptoventa.reference;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;


public class ArrayTypeHandler implements TypeHandler {


    public Object valueOf(String arg0) {
        return null;
    }

    @Override
    public Object getResult(ResultSet arg0, String arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType arg3) throws SQLException {

        //Connection conn = ps.getConnection();
        // TODO Auto-generated method stub
        //	ArrayDescriptor desc = ArrayDescriptor.createDescriptor("VARCHAR2_TABLE", conn);
        //	parameter = new ARRAY(desc, conn, ((ArrayList) parameter).toArray());
        ps.setArray(i, (oracle.sql.ARRAY)parameter);
    }

    @Override
    public Object getResult(ResultSet resultSet, int i) {
        return null;
    }
}
