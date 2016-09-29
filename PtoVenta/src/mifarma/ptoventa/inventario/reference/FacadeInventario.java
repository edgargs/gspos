package mifarma.ptoventa.inventario.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.DlgListadoGuias;
import mifarma.ptoventa.inventario.dao.DAOInventario;
import mifarma.ptoventa.inventario.dao.FactoryInventario;
import mifarma.ptoventa.inventario.dto.NotaEsCabDTO;
import mifarma.ptoventa.inventario.dto.NotaEsCabDetDTO;
import mifarma.ptoventa.inventario.dto.OrdenCompraCabDTO;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : FacadeRecetario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LRUIZ      17.05.2013   Creación<br>
 * <br>
 * @author Luis Ruiz Peralta<br>
 * @version 1.0<br>
 *
 */
public class FacadeInventario {

    private static final Logger log = LoggerFactory.getLogger(FacadeInventario.class);

    private DAOInventario daoInventario;
    private static String nroOrdenCompra = "";
    private static String numNota = "";

    public FacadeInventario() {
        super();
        daoInventario = FactoryInventario.getDAOInventario(FactoryInventario.Tipo.MYBATIS);
    }

    /**
     * Metodo que obtiene las Ords. Comps por rango de Fechas(dd/mm/yyyy)
     * @return: ArrayList
     * @param: pIniFecha
     * @param: pFinFecha
     * @author: Cesar Huanes
     */

    public ArrayList<ArrayList<String>> listaOrdenCompraByFecha(String pFinFecha, String pIniFecha) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = daoInventario.listaOrdenDeCompraByFecha(pFinFecha, pIniFecha);
        } catch (Exception ex) {
            log.error("Ocurrio una Incidencia", ex);
        }
        return lstListado;
    }

    /**
     * Metodo que obtine las Cabeceras de las Ords. Comps. segun Ord. Comp. ('##########')
     * @return:  ArrayList<ArrayList<String>>
     * @param:   pOrdCompra
     **/
    public ArrayList<ArrayList<String>> listarCabOrdenCompra(String pOrdCompra) {
        ArrayList<ArrayList<String>> lstCabOrdComp = null;
        try {
            lstCabOrdComp = daoInventario.listarCabOrdComp(pOrdCompra);
        } catch (Exception ex) {
            log.error("", ex);
        } finally {
            return lstCabOrdComp;
        }
    }

    /**
     * Metodo que obtine los productos de la Ord. Comp. segun OrdComp ('##########')
     * @return:  ArrayList<ArrayList<String>>
     * @param:   pCodOrdCompra
     **/
    public ArrayList<ArrayList<String>> listarProdOrdenCompra(String pCodOrdCompra) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = daoInventario.listarProdOrdenCompra(pCodOrdCompra);
        } catch (Exception ex) {
            log.error("", ex);
        }
        return lstListado;

    }


    /**
     * Listar detalle de orden de compra de la cual se espera devolver algunos productos por haber ingresado previamente sobrantes.
     * @author ASOSA
     * @since 19/08/2014
     * @param pCodOrdCompra
     * @return
     */
    public ArrayList<ArrayList<String>> listarProdOrdenCompraSobrante(String pCodOrdCompra) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = daoInventario.listarProdOrdenCompraSobrante(pCodOrdCompra);
        } catch (Exception ex) {
            log.error("", ex);
        }
        return lstListado;

    }

    /**
     * Metodo que obtine los productos de la Recepcion de laOrd. Comp. ('##########', '###', '#######', '##')
     * @return:  ArrayList<ArrayList<String>>
     * @param:   pNumOrd, pSeriDoc, pNumDoc, pIdDoc
     **/

    public ArrayList<ArrayList<String>> listarProdDetRecep(String pNumOrd, String pSecRecep) {
        ArrayList<ArrayList<String>> lstListado = null;
        daoInventario.openConnection();
        try {
            lstListado = daoInventario.listarProdDetRecep(pNumOrd, pSecRecep);
            daoInventario.commit();
        } catch (Exception ex) {
            daoInventario.rollback();
            log.error("", ex);
        }
        return lstListado;

    }

    /**
     * Metodo que obtine los productos de la Ord. Comp.('##########', DTO)
     * @return:  ArrayList<ArrayList<String>>
     * @param:   pCodOrdCompra, ordenCompraCabDTO
     **/

    public ArrayList<ArrayList<String>> listarProductosPorOrdenCompra(String pNumeroOC,
                                                                      NotaEsCabDetDTO ordenCompraCabDTO) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = daoInventario.listarProductosPorOrdenCompra(pNumeroOC, ordenCompraCabDTO);

        } catch (SQLException ex) {
            log.error("", ex);
        } finally {
            return lstListado;
        }
    }

    /**
     * Metodo que obtine las devoluciones segun Ord. Comp.(DTO)
     * @return:  ArrayList<ArrayList<String>>
     * @param:   notaEsCabDTO
     **/

    public ArrayList<ArrayList<String>> listarDevoluciones(NotaEsCabDTO notaEsCabDTO) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            notaEsCabDTO.setCodGrupoCia(FarmaVariables.vCodGrupoCia);
            notaEsCabDTO.setCodCia(FarmaVariables.vCodCia);
            notaEsCabDTO.setCodLocal(FarmaVariables.vCodLocal);
            notaEsCabDTO.setFiltro(notaEsCabDTO.getFiltro() == null ? "%" : notaEsCabDTO.getFiltro());
            notaEsCabDTO.setTipoOrigenNotaEs(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR);
            lstListado = daoInventario.listarDevoluciones(notaEsCabDTO);
        } catch (SQLException e) {
            log.error("", e);
        } finally {
            return lstListado;
        }
    }

    public ArrayList<ArrayList<String>> cargaDetalleTransferencia(String pNumeroNota) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = daoInventario.cargaDetalleTransferencia(pNumeroNota);
        } catch (SQLException e) {
            log.error("", e);
        }
        return lstListado;
    }

    public ArrayList<ArrayList<String>> listarOrdenesCompra(OrdenCompraCabDTO ordenCompraCabDTO) {
        ArrayList<ArrayList<String>> lstListado = null;
        daoInventario.openConnection();
        try {
            ordenCompraCabDTO.setCodGrupoCia(FarmaVariables.vCodGrupoCia);
            //ordenCompraCabDTO.setCodCia(FarmaVariables.vCodCia);
            ordenCompraCabDTO.setCodLocal(FarmaVariables.vCodLocal);
            ordenCompraCabDTO.setCodProv(VariablesPtoVenta.vIdProv);

            lstListado = daoInventario.listarOrdenesCompra(ordenCompraCabDTO);

        } catch (SQLException ex) {
            log.error("Ocurrio un error en el momento de obtener los datos", ex);
        } finally {
            return lstListado;
        }
    }

    public String grabarDevolucionOC(NotaEsCabDTO notaEsCabDTO,
                                     ArrayList<NotaEsCabDetDTO> lstNotaEsCabDetDTO) throws Exception {
        String vRetorno = "";
        daoInventario.openConnection();
        try {

            String oc = "";

            for (NotaEsCabDetDTO notaEsCabDetDTO : lstNotaEsCabDetDTO) {
                oc = notaEsCabDetDTO.getCodigoOc();
            }

            String numera = daoInventario.grabarCabeceraNotaSalida(notaEsCabDTO, oc);


            for (NotaEsCabDetDTO notaEsCabDetDTO : lstNotaEsCabDetDTO) {
                daoInventario.grabarDetalleNotaSalida(numera, notaEsCabDTO, notaEsCabDetDTO);
            }


            daoInventario.generarGuiaSalida(numera, VariablesInventario.vTipoFormatoImpresion,
                                            notaEsCabDTO.getCantItem());

            daoInventario.commit();
            vRetorno = numera;
        } catch (Exception e) {
            daoInventario.rollback();
            log.info("ERROR GRABAR DETALLE:" + e.getMessage());
            throw e;
        }
        return vRetorno;
    }

    public ArrayList<ArrayList<String>> getCabOrdenCompraRecep(String pCodOrdComp, String pSecRecepcion) {
        ArrayList<ArrayList<String>> lstListado = null;
        daoInventario.openConnection();
        try {
            lstListado = daoInventario.getCabOrdenCompraRecep(pCodOrdComp.trim(), pSecRecepcion);
            daoInventario.commit();
        } catch (Exception ex) {
            log.error("Ocurrio un error en: ", ex);
            daoInventario.rollback();
        }
        return lstListado;
    }

    public boolean guardarRecepcionOC(ArrayList CabRecepcion, ArrayList DetRecepcion, 
                                      ArrayList DetRecepcionLotes) throws Exception {
        boolean vRecepcion = false;
        String vNumGuia = null;
        String vSecOCRecep = "";
        daoInventario.openConnection();
        try {
            //1. Grabar Detalle OC
            //grabarDetalleOC
            vSecOCRecep =
                    daoInventario.grabarCabDetRecep(CabRecepcion.get(0).toString(), CabRecepcion.get(1).toString(),
                                                    CabRecepcion.get(2).toString(), CabRecepcion.get(3).toString(),
                                                    CabRecepcion.get(4).toString(), CabRecepcion.get(5).toString(),
                                                    CabRecepcion.get(6).toString(), CabRecepcion.get(7).toString(),
                                                    CabRecepcion.get(8).toString(), CabRecepcion.get(9).toString(),
                                                    CabRecepcion.get(10).toString(), CabRecepcion.get(11).toString(),
                                                    CabRecepcion.get(12).toString(), CabRecepcion.get(13).toString());
            //3. Grabar Nota Ingreso
            if (!vSecOCRecep.trim().equals("FALSE")) {
                vNumGuia =
                        daoInventario.agregaCabGuiaIngreso(CabRecepcion.get(0).toString(), CabRecepcion.get(2).toString(),
                                                           CabRecepcion.get(5).toString(),
                                                           CabRecepcion.get(6).toString(),
                            //CabRecepcion.get(7).toString()+//FC: concatena comprobante
                            CabRecepcion.get(8).toString(), CabRecepcion.get(6).toString(),
                            CabRecepcion.get(10).toString(), CabRecepcion.get(9).toString().trim(),
                            CabRecepcion.get(12).toString().trim(), "", "", "", CabRecepcion.get(3).toString());

                log.info("GRABO CABECERA 01");             
                String pCtdEntero_LOTE;
                String pDescLote_LOTE;
                String pFechVenc_LOTE;
                for (int i = 0; i < DetRecepcion.size(); i++) {                    
                    log.info("ENTRO A FOR");
                    //2. Grabar Cabecera
                    //grabarCabecera
                    String nroOrdenCompra = CabRecepcion.get(4).toString();
                    String codProd = ((ArrayList)DetRecepcion.get(i)).get(0).toString();
                    String cantRecib = ((ArrayList)DetRecepcion.get(i)).get(2).toString();
                    //ERIOS 2.4.3 Se graba valor de fraccion
                    int valFrac = Integer.parseInt(((ArrayList)DetRecepcion.get(i)).get(7).toString());
                    int cantFracLocal = Integer.parseInt(cantRecib) * valFrac;
                    log.info("INICIO grabarDetRecep");
                    daoInventario.grabarDetRecep(CabRecepcion.get(0).toString(), CabRecepcion.get(1).toString(),
                                                 CabRecepcion.get(2).toString(), CabRecepcion.get(3).toString(),
                                                 nroOrdenCompra, codProd,
                                                 ((ArrayList)DetRecepcion.get(i)).get(1).toString(), cantRecib,
                                                 ((ArrayList)DetRecepcion.get(i)).get(3).toString(),
                                                 ((ArrayList)DetRecepcion.get(i)).get(4).toString(),
                                                 ((ArrayList)DetRecepcion.get(i)).get(5).toString(),
                                                 ((ArrayList)DetRecepcion.get(i)).get(6).toString(),
                                                 CabRecepcion.get(6).toString(), CabRecepcion.get(7).toString(),
                                                 CabRecepcion.get(8).toString(), vSecOCRecep,
                                                 ((ArrayList)DetRecepcion.get(i)).get(8).toString(), 
                                                 ((ArrayList)DetRecepcion.get(i)).get(9).toString());
                    log.info("FIN grabarDetRecep");
                    //4. Grabar Nota Ingreso Detalle
                    log.info("INICIO agregaDetGuiaIngreso");
                    // FORRRR    LOTE CANTIDAD   ////
                    // dubilluz 08.06.2016
                    for(int v=0;v<DetRecepcionLotes.size();v++){
                        
                        if(codProd.trim().equalsIgnoreCase(FarmaUtility.getValueFieldArrayList(DetRecepcionLotes,
                                                                                               v,
                                                                                               4)))
                        {
                          pCtdEntero_LOTE = FarmaUtility.getValueFieldArrayList(DetRecepcionLotes,v,0);
                          pDescLote_LOTE = FarmaUtility.getValueFieldArrayList(DetRecepcionLotes,v,1);
                          pFechVenc_LOTE = FarmaUtility.getValueFieldArrayList(DetRecepcionLotes,v,2);
                        
                        daoInventario.agregaDetGuiaIngreso(CabRecepcion.get(0).toString(), //cCodGrupoCia_in
                                                           CabRecepcion.get(2).toString(), //cCodLocal_in
                                                           vNumGuia, //cNumNota_in
                                                           "", //cTipOrigen_in
                                                           codProd, //cCodProd_in
                                                           ((ArrayList)DetRecepcion.get(i)).get(3).toString(), //nValPrecUnit_in
                                                           ((ArrayList)DetRecepcion.get(i)).get(5).toString(), //nValPrecTotal_in
                                                           pCtdEntero_LOTE,
                                                           // cantFracLocal + "", //nCantMov_in
                                                           CabRecepcion.get(5).toString(), //vFecNota_in
                                                           pFechVenc_LOTE,//((ArrayList)DetRecepcion.get(i)).get(9).toString(), //vFecVecProd_in
                                                           pDescLote_LOTE,//((ArrayList)DetRecepcion.get(i)).get(8).toString(), //vNumLote_in
                                                           "", //cCodMotKardex_in
                                                           "", //cTipDocKardex_in
                                                           valFrac + "", //vValFrac_in
                                                           CabRecepcion.get(3).toString(), //vUsu_in
                                                           ((ArrayList)DetRecepcion.get(i)).get(1).toString(), //nCantSolicitado
                                                           nroOrdenCompra) //nOrdenCompra  ASOSA - 24/09/2014                        
                        ;   
                        }
                    }
                    // dubilluz 08.06.2016
                    // FORRRR    LOTE CANTIDAD   ////
                }
                log.info("FIN agregaDetGuiaIngreso");
                daoInventario.actualizaNumNota(vNumGuia, CabRecepcion.get(4).toString(), vSecOCRecep);
                daoInventario.commit();
                //ERIOS 27.08.2015 Se envia por hilo
                actualizarOrdenCompraComis(CabRecepcion.get(4).toString(), vNumGuia);
                vRecepcion = true;
            } else {
                vRecepcion = false;
                daoInventario.rollback();
            }

        } catch (Exception ex) {
            log.error("Error al momneto de guardar los productos.:" + ex.getMessage());
            vRecepcion = false;
            daoInventario.rollback();
            throw ex;
        }

        return vRecepcion;
    }

    private void actualizarOrdenCompraComis(String nroOrdenCompra, String numNota){
        this.nroOrdenCompra = nroOrdenCompra;
        this.numNota = numNota;
        //ERIOS 28.08.2015 Envia por hilo
        new Thread() {
            public void run() {
                ArrayList<ArrayList<String>> listaProductos;
                String salida = "";
                try {
                    daoInventario.openConnection();
                    listaProductos = daoInventario.listarProductosPorNota(FacadeInventario.nroOrdenCompra, FacadeInventario.numNota);
                    daoInventario.commit();
                    
                    salida = daoInventario.actualizarOrdenCompra(FacadeInventario.nroOrdenCompra, listaProductos);
                            
                } catch (Exception e) {
                    daoInventario.rollback();
                    log.error("", e);   
                    //TODO enviar correo
                }
            }
        }.start();
    }

    /**
     * Metodo que anula la guia y documento de recepcion segun Ord. Comp.
     * @return:  boolean
     * @param:   7 String's
     **/

    public boolean anularGuiaIngreso(String vCodGrupoCia, String vCodCia, String vCodLocal, String vCodOC,
                                     String vNumerGuia, String vIdeDocumento, String vNumeroDocument, String vUser,
                                     String vSerieDocument, String pSecRecepcion) {

        boolean valor = false;
        String flag = "";
        daoInventario.openConnection();
        try {
            flag =
daoInventario.anularRecepGuiaIngreso(vCodGrupoCia.trim(), vCodLocal.trim(), vNumerGuia.trim(), vIdeDocumento.trim(),
                                     vNumeroDocument.trim());

            if (flag.trim().equals("TRUE")) {
                daoInventario.anulaDocumentoRecep(vCodGrupoCia.trim(), vCodLocal.trim(), vNumerGuia.trim(),
                                                  ConstantsPtoVenta.MOT_KARDEX_RECEPCION_PROVEEDOR,
                                                  ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES, vUser.trim());

                daoInventario.cambiaEstado(vCodGrupoCia, vCodCia, vCodLocal, vCodOC, pSecRecepcion);
                valor = true;
            } else {
                valor = false;
            }
            daoInventario.commit();
        } catch (Exception e) {
            daoInventario.rollback();
            log.error("Ocurrio un Error en: ", e);
            valor = false;
        }
        return valor;
    }

    /**
     * Metodo que obtine los productos de recepcion de las Ord. comp.
     * @return:  ArrayList<ArrayList<String>>
     * @param:   2 String's
     **/

    public ArrayList<ArrayList<String>> listarDocumRecep(String cCodGrupoCia, String cCodCia, String cCodLocal,
                                                         String vNumOrdCompr, String vCodProv) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = daoInventario.getListaDocumtRecep(cCodGrupoCia, cCodCia, cCodLocal, vNumOrdCompr, vCodProv);

        } catch (Exception ex) {
            log.error("Ocurrio un Error en: ", ex);
        }
        return lstListado;
    }

    public boolean cierreOC(String CodGrupoCia, String CodLocal, String NumOrdComp) {
        boolean flag = false;
        String valor = "";
        daoInventario.openConnection();
        try {
            valor = daoInventario.cierreOC(CodGrupoCia, CodLocal, NumOrdComp);
            if (valor.trim().equals("TRUE")) {
                daoInventario.commit();
                flag = true;
            } else {
                flag = false;
            }

        } catch (Exception ex) {
            daoInventario.rollback();
            log.error("Ocurrio un error al cierre de la Ord. Compra: " + NumOrdComp, ex);
            flag = false;
        }

        return flag;

    }

    /**
     * Confirma la devolucion
     * @author ERIOS
     * @since 22.07.2013
     * @param pNumeroNota
     * @throws Exception
     */
    public void confirmarDevolucion(String pNumeroNota) throws Exception {
        daoInventario.openConnection();
        try {
            daoInventario.confirmarDevolucion(pNumeroNota);
            daoInventario.commit();
        } catch (Exception e) {
            daoInventario.rollback();
            log.error("", e);
            throw e;
        }
    }

    /**
     * Confirma la devolucion
     * @author ERIOS
     * @since 22.07.2013
     * @param pNumeroNota
     * @throws Exception
     */
    public void confirmarDevolucionMercaderia(String pNumeroNota) throws Exception {
        //1.Actualiza legacy
        //1.1.Recuperar guias no enviadas legacy
        ArrayList<ArrayList<String>> vGuias = daoInventario.getGuiaNoEnvioLegacy(pNumeroNota);
        String vNomProve = VariablesInventario.vNomProve;
        String salida = "";

        for (ArrayList<String> guia : vGuias) {
            String vSecGuiaRem = guia.get(1);
            String vNumGuiaRem = guia.get(2);
            ArrayList<ArrayList<String>> detalleGuia = daoInventario.getGuiaDetGuiaLegacy(pNumeroNota, vSecGuiaRem);
            salida = actualizaOrdenDevolucion(vNumGuiaRem, vNomProve, detalleGuia);
            if (salida.equals("OK")) {
                daoInventario.openConnection();
                daoInventario.actualizaIndicador(pNumeroNota, vSecGuiaRem);
                daoInventario.commit();
            }
        }

        //2.Confirma guia
        confirmarDevolucion(pNumeroNota);
    }

    /**
     * Obtiene el InnerPack de un producto (numero de unidades por paquete)
     * @autor GFONSECA
     * @since 10.12.2013
     * */
    public int obtenerProdInnerPack(String strCodProd) {
        int valInnerPack = 0;
        daoInventario.openConnection();
        try {
            valInnerPack = daoInventario.obtenerProdInnerPack(FarmaVariables.vCodGrupoCia.trim(), strCodProd);
        } catch (Exception e) {
            log.error("", e);
        }
        return valInnerPack;
    }


    /**
     *
     * @author CHUANES
     * @since 16.12.13
     * @throws Exception
     */
    public ArrayList<ArrayList<String>> listaDatosLocal(String cCodGrupoCia, String cCodLocal) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = daoInventario.obtieneDatosLocal(cCodGrupoCia, cCodLocal);

        } catch (Exception ex) {
            log.error("Ocurrio un Error en: ", ex);
        }
        return lstListado;
    }

    /**
     *
     * @author CHUANES
     * @since 16.12.13
     * @throws Exception
     */

    public boolean grabarGuiaRemision(JDialog pJDialog) throws Exception {
        boolean flag = false;
        DlgListadoGuias dlglistadoGuias = new DlgListadoGuias();

        daoInventario.openConnection();
        try {
            NotaEsCabDTO notaEsCabDTO = new NotaEsCabDTO();
            notaEsCabDTO.setTipoOrigenNotaEs("06");
            notaEsCabDTO.setCodDestinoNotaEs("000");
            notaEsCabDTO.setTipoMotiNotaEs("");

            notaEsCabDTO.setDescEmpresa(VariablesInventario.vNomDesctina);
            notaEsCabDTO.setRucEmpresa(VariablesInventario.vRucDestina);
            notaEsCabDTO.setDireEmpresa(VariablesInventario.vDireDestina);

            notaEsCabDTO.setDescTransp(VariablesInventario.vNomTransp);
            notaEsCabDTO.setRucTransp(VariablesInventario.vRucTransp);
            notaEsCabDTO.setDirTransp(VariablesInventario.vDireTransp);
            notaEsCabDTO.setPlacaTransp(VariablesInventario.vPlacTransp);

            notaEsCabDTO.setCantItem(1);
            notaEsCabDTO.setValorTotalNotaEsCab(0);

            //1.Grabar cabecera
            String strNumNota = daoInventario.grabarCabeceraNotaSalida(notaEsCabDTO, "");

            //2.Actualizar texto a imprimir
            daoInventario.actualizaTextoImpresion(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, strNumNota,
                                                  VariablesInventario.vTexImpr);

            //3.Generar guia
            daoInventario.grabarGuiaRemision(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                             FarmaVariables.vIdUsu, strNumNota);

            daoInventario.commit();

            //3.9 Para la impresion
            VariablesInventario.vNumNotaEs = strNumNota;


        } catch (Exception ex) {
            daoInventario.rollback();
            log.error("Error al guardar los nota_es_cab.");
            flag = false;

            throw ex;
        }
        return flag;

    }

    public ArrayList<ArrayList<String>> listarGuiasNoMuevenStock(String cCodGrupoCia, String cCodLocal) {
        ArrayList<ArrayList<String>> lstListado = null;
        daoInventario.openConnection();
        try {
            lstListado = daoInventario.getListaGuiasNoMuevenstock(cCodGrupoCia, cCodLocal);
        } catch (SQLException e) {
            log.error("", e);
        }
        return lstListado;

    }

    public ArrayList<ArrayList<String>> listarMaestroProductos(String cCodGrupoCia, String cCodLocal) {
        ArrayList<ArrayList<String>> lstListado = null;
        daoInventario.openConnection();
        try {
            lstListado = daoInventario.getListaMaestroProductos(cCodGrupoCia, cCodLocal);
            // VariablesInventario.vArrayDevol=lstListado;
        } catch (SQLException e) {
            log.error("", e);
        }
        return lstListado;

    }

    public ArrayList<ArrayList<String>> buscaProductosPorDescripcion(String pDescripcion) {
        ArrayList<ArrayList<String>> lstListado = null;
        daoInventario.openConnection();
        try {
            lstListado = daoInventario.buscaProductosPorDescripcion(pDescripcion);
        } catch (SQLException e) {
            log.error("", e);
        }
        return lstListado;

    }

    public ArrayList obtenerProductosCodigoBarra(String pCodBarra) throws Exception {
        ArrayList lista = daoInventario.obtenerProductoCodigoBarra(pCodBarra);
        return lista;
    }

    public void grabaCabRecep(String codOC, String codProv, String NumNota, String canItem) {
        daoInventario.openConnection();
        try {
            daoInventario.grabaCabRecep(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal,
                                        codOC, codProv, canItem, FarmaVariables.vIdUsu, NumNota);
            daoInventario.commit();
        } catch (Exception e) {
            daoInventario.rollback();
            log.error("", e);
        }
    }


    public String actualizaOrdenDevolucion(String pNumGuia, String descEmpr,
                                           ArrayList<ArrayList<String>> lista) throws SQLException {
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        String salida = "";

        SqlSession sqlFasaSession = MyBatisUtil.getFasaSqlSessionFactory().openSession();

        try {
            String codLocalMigra = facadeRecaudacion.getCodLocalMigra();

            salida =
                    daoInventario.actualizarOrdenDevolucion(sqlFasaSession, "C", codLocalMigra, pNumGuia, descEmpr, "",
                                                            0);

            if (lista != null && lista.size() > 0) {
                for (int i = 0; i < lista.size(); i++) {
                    String codigo = ((ArrayList)lista.get(i)).get(1).toString();
                    String cantidad = ((ArrayList)lista.get(i)).get(2).toString();

                    salida =
                            daoInventario.actualizarOrdenDevolucion(sqlFasaSession, "D", codLocalMigra, pNumGuia, "", codigo,
                                                                    Integer.parseInt(cantidad));
                    log.debug("resultado de devolucion por guia");
                    log.debug(salida);

                }
            }

            if (salida.equals("OK")) {
                sqlFasaSession.commit();
            } else {
                sqlFasaSession.rollback();
            }

        } catch (Exception e) {
            sqlFasaSession.rollback();
            log.error("", e);
            //throw e;
        } finally {
            sqlFasaSession.close();
        }
        return salida;
    }

    public String getNumAjuste(String pCodProd, String pNumComPago, String pCodMotKardex, String pTipDocKardex) {
        String resultado = "";
        try {
            daoInventario.openConnection();
            resultado = daoInventario.getNumAjuste(pCodProd, pNumComPago, pCodMotKardex, pTipDocKardex);

        } catch (SQLException e) {

            log.error("Error", e);
        }


        return resultado.trim();
    }

    public String getIndicadorOC(String pNumerOC_in) {
        String resultado = "";
        try {
            daoInventario.openConnection();
            resultado = daoInventario.getIndOC(pNumerOC_in);

        } catch (SQLException e) {

            log.error("Error", e);
        }


        return resultado.trim();
    }

    /**
     * @author ERIOS
     * @since 06.10.2015
     * @return
     */
    public String getIndImprimirKardex(){
        String strRetorno = "N";
        try {
            strRetorno = DBPtoVenta.getIndImprimirKardex();
        } catch (SQLException e) {
            log.error("",e);
        }
        return strRetorno;
    }

}


