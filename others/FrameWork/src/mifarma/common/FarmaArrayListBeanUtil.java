package mifarma.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mifarma.common.FarmaArrayListBean;

/**
 * Sirve para convertir un FarmaArrayListBean
 */
public class FarmaArrayListBeanUtil {

    /**
     * Permite convertir un ArrayList a un FarmaArrayListBean
     * @param size Tamaño de la pagina
     * @param lista La lista
     * @return
     */
    public static FarmaArrayListBean toFarmaArrayListBean(int size, 
                                                          List lista) {
        FarmaArrayListBean alb = new FarmaArrayListBean();
        for (Iterator iterator = lista.iterator(); iterator.hasNext(); ) {
            alb.setElemento(iterator.next());
        }
        alb.init(size);
        return alb;
    }

    public static FarmaArrayListBean toFarmaArrayListBean(List lista) {
        FarmaArrayListBean alb = new FarmaArrayListBean();
        for (Iterator iterator = lista.iterator(); iterator.hasNext(); ) {
            alb.setElemento(iterator.next());
        }
        alb.init(0);
        return alb;
    }

    /**
     * Genera un FarmaArrayListBean en base a un ArrayList
     * @param lista
     * @return
     */
    public static FarmaArrayListBean setListBeanTo(ArrayList lista) {
        FarmaArrayListBean alb = new FarmaArrayListBean();
        alb.setArrayList(lista);
        alb.init();
        return alb;
    }
}
