package mifarma.common;

import java.util.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicaci�n : FarmaTableComparator.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA 07.01.2006 Creaci�n<br>
 * <br>
 * 
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 * 
 */

public class FarmaTableComparator implements Comparator {

    /** Almacena columna para sorteo */
    protected int m_sortCol;

    /** Almacena modo de sorteo */
    protected boolean m_sortAsc;

    /**
     * Constructor : Inicializa la columna por la cual se realizar� el sorteo.
     * Tambi�n inicializa el modo de sorteo (ascendente o descendente)
     * 
     * @param sortCol
     *             Columna por la que se realiza el sort.
     * @param sortAsc
     *             Modo de sorteo.
     */
    public FarmaTableComparator(int sortCol, boolean sortAsc) {

        m_sortCol = sortCol;
        m_sortAsc = sortAsc;

    }

    /**
     * Realiza la comparaci�n de datos para efectuar el sorteo.
     * 
     * @param o1
     *             Colecci�n de objetos a sortear.
     * @param o2
     *             Colecci�n de objetos a comparar.
     * @return int Control - resultado del sorteo.
     */
    public int compare(Object o1, Object o2) {

        ArrayList arrayList1 = (ArrayList)o1;
        ArrayList arrayList2 = (ArrayList)o2;

        int result = 
            arrayList1.get(m_sortCol).toString().toLowerCase().compareTo(arrayList2.get(m_sortCol).toString().toLowerCase());

        if (!m_sortAsc)
            result = -result;

        return result;

    }

}
