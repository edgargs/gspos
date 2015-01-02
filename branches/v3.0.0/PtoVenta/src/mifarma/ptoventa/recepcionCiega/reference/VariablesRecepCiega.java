package mifarma.ptoventa.recepcionCiega.reference;

import java.util.ArrayList;

import mifarma.common.FarmaTableModel;

public class VariablesRecepCiega {
    public VariablesRecepCiega() {
    }
    
    /**
     * Variables Recepcion de Productos
     * @AUTHOR JCORTEZ
     * @SINCE 16.11.09
     * */
    public static ArrayList vArrayListaGuias = new ArrayList();
    
    public static String vNombreTrans = "";
    public static String vHoraTrans = "";
    public static String vPlacaUnidTrans = "";
    public static String vCantBultos = "";
    public static String vCantPrecintos = "";
    /* AAMPUERO 15.04.2014 */
    public static String vCantBandejas = "";    
    
    public static String vNumIngreso= "";
    public static String vFechIngreso= "";
    public static String vCantGuias= "";
    
    public static String vNumNotaEst= "";
    public static String vNumGuia= "";
    public static String vNumEntrega= "";
    public static String vFecCreaNota= "";
    public static String vCantItems= "";
    public static String vCantProds= "";
    public static String vEstado= "";
    public static String vSecGuia= "";
    
    public static String vColumna="";
    public static String vOrden="";
    
    public static int cPos=0;
    
    public static String vTipoNumEntrega="";//Nuevo, Ver
    public static String vTipoIngrEntrega="";//Nuevo, Ver
    
    public static String vNuSecUsu="";
    public static String vIdUsu="";
    public static String vNomUsu="";
    public static String vPatUsu="";
    public static String vMatUsu="";
         

    
    //JCHAVEZ
    
    public static String vNro_Recepcion =  ""; //vNumIngreso
    public static String vCod_Barra = "";
    public static String vDesc_Producto = "";
    public static String vUnidad = "";
    public static String vCantidadVerificaConteo;
    public static String vCodProd = "";
    public static String vSecConteo = "";
    public static String vMotivoTransferencia = "";    
    public static String vLote = "";
    public static String vFechaVcto = "";
    public static String vCantProdTransferir = "";
    public static boolean vIndModificarIngresoCantProdTranf = false;
    public static FarmaTableModel vTableModelProdTranf;
    public static boolean vIndBuscaProducto = false;
    public static String vTipoDestino_Transf = "";
    public static String vMotivo_Transf_Interno = "";
    public static String vDescMotivo_Transf = "";
    public static String vDescMotivo_Transf_Larga = ""; //JMIRANDA 16.02.10
    public static String vMotivo_Transf = "";                
    public static String vCodDestino_Transf = "";
    public static String vDestino_Transf = "";
    public static String vRucDestino_Transf = "";
    public static String vDirecDestino_Transf = "";
    public static String vTransportista_Transf = "";
    public static String vRucTransportista_Transf = "";
    public static String vDirecTransportista_Transf = "";
    public static String vPlacaTransportista_Transf = "";
    public static boolean vHistoricoLote = true;
    public static String vDirecOrigen_Transf = "";
    public static String vNumComp = "";
    public static String vValPrecVta = "";
    public static String vValFrac = "";  
    public static int  vCantIngreso=0;
    //Transferencia a Matriz
    public static boolean vTransfMatriz = false;
    
    public static String vIndTextFraccion = "" ;
    
    
    //JMIRANDA
    public static String vUsuarioConteo = "";
    
    public static ArrayList vArrayListCodBarraNoEncontrados = new ArrayList();
    public static ArrayList vOrdenarLista = new ArrayList();
    
    public static int contadorFila = 0;
    //JMIRANDA 16.11.2009
    //Variables para almacenar el último producto ingresado.
    public static String vLastCodBarra = "";  //Producto Ingresado
    public static String vLastDesProd = "";
    public static String vLastCodProd = "";
    public static String vLastCant = "";
    public static String vLastUsuario = "";
    public static String vLastSecProd = "";
    public static String vLastUndPresente = "";
    
    public static String vTempCodBarra = ""; //Temporal del CodBarra por si no existe producto
    
    public static boolean vIndIngresoCodBarra = false;
    
    public static String vIndDeteriorado = "N";
    public static String vIndFueraLote = "N";
    public static String vIndNoFound = "N";
    
    public static String vNroBloque = "";
       
    //Secuencial de la recepcion GUIA (NRO RECEPCION)   
    public static String vSecRecepGuia = "";
    
    public static boolean vIndAgregaConteo = true;
    public static boolean vIndFocoTablaConteo = false;
    
    //AUXILIARES
    public static String vTempCant = "";
    public static String vTempDescProd = "";
    public static String vTempAuxSecConteo = "";
    public static String vTempUndPresente = "";
    public static String vTempNroBloque = "";
    
    public static boolean vIndModificarCant = false;
    public static boolean vIndEliminaFila = false;
    
    public static String vDestEmailCodBarraNoFound = "";
    /**
     * @author ERIOS
     * @since 2.3.3
     */
    public static String vDestEmailIngresoTransportista = "";
    
    public static String vAuxCodBarra = "";
    public static String vAuxSecProd = "";
    
    //JMIRANDA 03.12.09
    public static boolean vIndModificoCantActivo = false; //verifica que solo modifique una vez
    public static String vTipoPedRep = "";
    
    //JMIRANDA 05.03.2010
    public static String vGlosa = "";
    
    //JMIRANDA 17.03.2010
    public static String vTran_NroRecepcion = "";
    public static String vTran_Fecha = "";
    public static String vTran_UsuCrea = "";
    public static String vTran_NomTransportista = "";
    public static String vTran_Estado = "";
    public static String vTran_Placa = "";
    public static String vTran_CodEstado = "";
    public static String vTran_Ordena = "";
    
    public static boolean vIndAsociaTransportista = false;
    
    public static String vCod_Estado = "";
    
    public static boolean vIndHabDatosTransp = false;
    
    //JQUISPE 05.05.2010 Variable de numero de impresiones
    public static int vNumImpresiones = 0;
    
    //ASOSA, 21.07.2010 
    public static String secRepStk="";
    
    //JMIRANDA 09.08.2011
    public static String vAfectaSobranteNuevo = "S";
    
}
