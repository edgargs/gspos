package mifarma.factelec.daemon.dao;

import java.util.List;
import java.util.Map;

import mifarma.factelec.daemon.bean.BeanLocal;
import mifarma.factelec.daemon.bean.MonVtaCompPagoE;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;


public interface MapperDaemon {

    @SelectProvider(type = SQLDaemonProvider.class, method = "selectMonVtaCompPagoE")
    public List<MonVtaCompPagoE> getDocumentosPendientes(Map<String, Object> object);

    @UpdateProvider(type = SQLDaemonProvider.class, method = "updateMonVtaCompPagoE")
    public void actualizaDocumentoE(MonVtaCompPagoE pDocumento);

    @SelectProvider(type = SQLDaemonProvider.class, method = "selectRucCia")
    public String getRucCia(Map<String, Object> object);

    @SelectProvider(type = SQLDaemonProvider.class, method = "selectLocales")
    public List<BeanLocal> getLocales(Map<String, Object> object);

    @DeleteProvider(type = SQLDaemonProvider.class, method = "deleteMonVtaCompPagoE")
    public void borraDocumentoE(Map<String, Object> object);

    @InsertProvider(type = SQLDaemonProvider.class, method = "insertMonVtaCompPagoE")
    public void insertaDocumentoE(MonVtaCompPagoE monVtaCompPagoE);

    @SelectProvider(type = SQLDaemonProvider.class, method = "selectMonVtaCompPagoELocal")
    public List<MonVtaCompPagoE> getDocsPendientes(Map<String, Object> object);
}
