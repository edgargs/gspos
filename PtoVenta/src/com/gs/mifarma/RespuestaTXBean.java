package com.gs.mifarma;

import java.io.Serializable;

public class RespuestaTXBean
  implements Serializable
{
  private String codigoAprobacion;
  private String codigoPIN;
  private String codigoRespuesta;
  private String numeroTarjeta;
  private String numeroTrace;
  private String descripcion;
  private String fechaTX;
  private String horaTX;
  private String datosImprimir;

  public String getCodigoAprobacion()
  {
     return this.codigoAprobacion;
  }

  public void setCodigoAprobacion(String codigoAprobacion)
  {
     this.codigoAprobacion = codigoAprobacion;
  }

  public String getCodigoPIN()
  {
     return this.codigoPIN;
  }

  public void setCodigoPIN(String codigoPIN)
  {
     this.codigoPIN = codigoPIN;
  }

  public String getCodigoRespuesta()
  {
     return this.codigoRespuesta;
  }

  public void setCodigoRespuesta(String codigoRespuesta)
  {
     this.codigoRespuesta = codigoRespuesta;
  }

  public String getNumeroTarjeta()
  {
     return this.numeroTarjeta;
  }

  public void setNumeroTarjeta(String numeroTarjeta)
  {
     this.numeroTarjeta = numeroTarjeta;
  }

  public String getNumeroTrace()
  {
     return this.numeroTrace;
  }

  public void setNumeroTrace(String numeroTrace)
  {
     this.numeroTrace = numeroTrace;
  }

  public String getDescripcion()
  {
     return this.descripcion;
  }

  public void setDescripcion(String descripcion)
  {
     this.descripcion = descripcion;
  }

  public String getFechaTX()
  {
     return this.fechaTX;
  }

  public void setFechaTX(String fechaTX)
  {
     this.fechaTX = fechaTX;
  }

  public String getHoraTX()
  {
     return this.horaTX;
  }

  public void setHoraTX(String horaTX)
  {
     this.horaTX = horaTX;
  }

  public String getDatosImprimir()
  {
     return this.datosImprimir;
  }

  public void setDatosImprimir(String datosImprimir)
  {
     this.datosImprimir = datosImprimir;
  }
}
