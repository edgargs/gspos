package mifarma.factelec.daemon.service;


import cl.paperless.core.asp.online.ws.Online;
import cl.paperless.core.asp.online.ws.OnlinePortType;

import java.io.ByteArrayInputStream;

import java.net.URL;

import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mifarma.factelec.daemon.bean.MonVtaCompPagoE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class MonitorFacturacion implements Runnable {
    
    private static final Logger log = LoggerFactory.getLogger(MonitorFacturacion.class);
    
    private List<MonVtaCompPagoE> lstDocumentos;
    private FacadeDaemon facade;
    private Properties farmadaemon;
    
    int pTipoRespuesta = 3; //3 = Estado en la SUNAT 
    String rucCia;
    
    public MonitorFacturacion(List<MonVtaCompPagoE> lstDocumentos, FacadeDaemon facade, Properties farmadaemon, String rucCia) {
        super();
        
        this.lstDocumentos = lstDocumentos;
        this.facade = facade;
        this.farmadaemon = farmadaemon;
        this.rucCia =  rucCia;
    }

    @Override
    public void run() {
        
        try {
            //1. Verificar el estado
            consultaEstado(lstDocumentos);
            //2. Actualizar documento
            facade.grabaEnvio(lstDocumentos);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void consultaEstado(List<MonVtaCompPagoE> lstDocumentos) throws Exception {
        for(MonVtaCompPagoE documento:lstDocumentos){
            llamada(documento);
        }
    }
    
    private void llamada(MonVtaCompPagoE documento) throws Exception {
        
        String pServidor = farmadaemon.getProperty("ServidorEPOS");
        String pUsuario = farmadaemon.getProperty("UsuarioEPOS");
        String pClave = farmadaemon.getProperty("ClaveEPOS");
        
        
        URL url = new URL(pServidor+"/axis2/services/Online?wsdl");
        Online service = new Online(url);
        
        OnlinePortType port = service.getOnlineSOAP11PortHttp();
        
        String codLocal = documento.getCod_local();
        Integer pTipoDocumento = new Integer(documento.getTip_doc_sunat());
        String pDocumento = documento.getNum_comp_pago_e();
        log.info(String.format("Documento a consultar: [%s|%s|%s]",codLocal,pTipoDocumento,pDocumento));
        String response = port.onlineRecovery(rucCia, pUsuario, pClave, pTipoDocumento, pDocumento, pTipoRespuesta);
        
        DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document parse = newDocumentBuilder.parse(new ByteArrayInputStream(response.getBytes()));
        
        Node node = parse.getFirstChild();
        NodeList childNodes = node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
          Node cNode = childNodes.item(j);

          //Identifying the child tag of employee encountered. 
          if (cNode instanceof Element) {
            String content = cNode.getLastChild().getTextContent().trim();
            switch (cNode.getNodeName()) {
              case "Codigo":
                documento.setCodigo(content);
                break;
              case "Mensaje":
                documento.setMensaje(content);
                break;
            }
            
          }
        }
    }
}
