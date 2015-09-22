package mifarma.common;

import java.util.Iterator;
import java.util.ListIterator;

public class FarmaArrayListBeanIterator implements Iterator {

    FarmaArrayListBean arrayListBean = null;
    private boolean soloPaginaActual = true;
    private int currentIndex = 0;

    protected FarmaArrayListBeanIterator(FarmaArrayListBean arrayListBean, 
                                         boolean soloPaginaActual) {
        this.arrayListBean = arrayListBean;
        this.soloPaginaActual = soloPaginaActual;
        currentIndex = 0;
    }

    public boolean hasNext() {
        if (arrayListBean.getTotalPaginas() < 1)
            return false;
        if (soloPaginaActual) {
            if (currentIndex < arrayListBean.getDisponible())
                return true;
            return false;
        } else { //Desactivado. Era usado para iterar la lista de todos los elementos. Fue reemplazdo por: ArayListBean.getArrayList().iterator()
            if (currentIndex < arrayListBean.getListSize())
                return true;
            return false;
        }
    }

    public Object next() {
        Object retorno = arrayListBean.getElemento(currentIndex);
        currentIndex++;
        return retorno;
    }

    public void remove() {

    }
}
