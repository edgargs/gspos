package mifarma.ptoventa;


import mifarma.ptoventa.main.EconoFar_Matriz;
import mifarma.ptoventa.main.MainFarmaVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : EconoFar.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      27.12.2005   Creación<br>
 * ERIOS       20.06.2013   Modificacion<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class EconoFar{

    static private final Logger log = LoggerFactory.getLogger(EconoFar.class);

    public static void main(String[] args) {
        //20.12.2007 ERIOS Se modifica el metodo para cargar desde el jar.
        if (args.length == 3) {
            log.debug(args[0]);
            log.debug(args[1]);
            log.debug(args[2]);
            new MainFarmaVenta(args[0], args[1], args[2]);
        } else if (args.length == 2) /* 25.01.2008 ERIOS Ejecuta Ptoventa_Matriz */
        {
            new EconoFar_Matriz(args[0], args[1]);
        } else {
            new MainFarmaVenta();
        }
    }

}
