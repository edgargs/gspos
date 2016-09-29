package mifarma.ptoventa.fidelizacion.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.cliente.reference.VariablesCliente;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FMFidelizacion implements DAOFidelizacion {
    
    private static final Logger log = LoggerFactory.getLogger(MBFidelizacion.class);

    private SqlSession sqlSession = null;
    private MapperFidelizacion mapper = null;
    private UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
    
    public FMFidelizacion() {
        super();
    }
    
    @Override
    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

    @Override
    public void rollback() {
        sqlSession.rollback(true);
        sqlSession.close();
    }

    @Override
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperFidelizacion.class);
    }
    
    public List getListaTipoDocumento() throws Exception{
        return null;
    }
    
    public String getCodigoTipoDocumentoAfiliado(String nroDocumentoIdentidad) throws Exception{        
        return null;
    }
    
    public void grabarClienteNaturalRAC() throws Exception {
        //FALTA CODIGO
    }
}
