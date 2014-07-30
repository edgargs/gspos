package mifarma.common;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaColumnData.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA 07.01.2006 Creación<br>
 * <br>
 * 
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 * 
 */

public class FarmaColumnData {

    /**
     * Variable que almacena el Título de la Columna.
     */
    public String m_title;

    /**
     * Variable que almacena el ancho de la Columna.
     */
    public int m_width;

    /**
     * Variable que almacena la alineación de la Columna.
     */
    int m_alignment;

    /**
     * Constructor
     * @param title Título
     * @param width Ancho
     * @param alignment Alineación
     */
    public FarmaColumnData(String title, int width, int alignment) {
        m_title = title;
        m_width = width;
        m_alignment = alignment;
    }

}
