package mifarma.common;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class FarmaArrayListBean implements Serializable {

    protected List lista = new ArrayList();
    private boolean anterior; // indica si hay una pagina anterior
    private int disponible; // cuantos reg hay en la pagina
    private int numPagina; // en que nro de pagina esta	- empieza en cero hasta totalPaginas - 1
    private boolean siguiente; // indica si hay una pagina siguiente
    private int tamPagina; // nro de registros por página
    private int regSeleccionado; // indice del registro seleccionado
    private int totalPaginas; // nro total de páginas
    private Comparator comparador;
    private int indiceSeleccionado; // indice seleccionado de la lista
    private int paginaSeleccionada; // página del elemento seleccionado de la lista
    private ArrayList registrosSeleccionados = new ArrayList();

    // (cc)
    private int nroPagsDisponibles; // Cantidad de páginas que podrán mostrarse para acceso directo
    private int inicioRango; // Nro. de página inicio en el rango actual
    private boolean flagPrimerRango; // Flag primer registro
    private boolean flagUltimoRango; // Flag ultimo registro

    public FarmaArrayListBean() {
        indiceSeleccionado = -1;
    }

    public void cargaInfo() {
    }

    /**
     * Elimina el primer elemento de la lista
     */
    public void eliminarElemento() {
        try {
            Object element = lista.get(0);
            desmarcarElemento(element);
            lista.remove(0);
        } catch (Exception e) {
            System.out.println("Error al eliminar elemento: " + e);
        }
    }

    /**
     * Elimina un elemento de la lista
     * @param index Indice del elemento que se quiere eliminar
     */
    public void eliminarElementoLista(int index) {
        try {

            Object element = lista.get(index);
            desmarcarElemento(element);
            lista.remove(index);

        } catch (Exception e) {
            System.out.println("Error al eliminar el elemento (" + index + 
                               "): " + e);
        }
    }

    /**
     * Elimina un elemento de la página actual de la lista
     * @param index Indice del elemento que se quiere eliminar
     */
    public void eliminarElemento(int index) {
        try {
            lista.remove(index + tamPagina * numPagina);
        } catch (Exception e) {
            System.out.println("Error al eliminar el elemento (" + index + 
                               "): " + e);
        }
    }

    /**
     * Verifica si existe un elemento en la lista
     * @param elemento
     * @return boolean true si existe, false si no existe
     */
    public boolean existeElemento(Object elemento) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).equals(elemento))
                return true;
        }
        return false;
    }

    /**
     * Returns the disponible.
     * @return int
     */
    public int getDisponible() {
        return disponible;
    }

    /**
     * Returns el primer elemento de la lista.
     * @return Object
     */
    public Object getElemento() {
        Object elemento = null;
        try {
            elemento = (Object)lista.get(0);
        } catch (Exception e) {
            System.out.println("Error en el obtener el primer elemento de la lista: " + 
                               e);
        }
        return elemento;
    }

    /**
     * Retorna el elemento con indice index de la página activa  de la lista.
     * @param index indice del elemento de la pagina
     * @return Object
     */
    public Object getElemento(int index) {
        Object elemento = null;
        try {
            elemento = (Object)lista.get(index + tamPagina * numPagina);
        } catch (Exception e) {
            System.out.println("Error al obtener el elemento (" + index + 
                               "): " + e);
        }
        return elemento;
    }

    /**
     * Retorna el elemento con indice index de la lista.
     * @param index indice del elemento de la lista
     * @return Object
     */
    public Object getElementoLista(int index) {
        Object elemento = null;
        try {
            elemento = (Object)lista.get(index);
        } catch (Exception e) {
            System.out.println("Error al obtener el elemento (" + index + 
                               "): " + e);
        }
        return elemento;
    }

    /**
     * Permite pasar a la siguiente pagina
     */
    public void getNextPage() {
        int total;
        int restantes;
        total = getListSize();
        if (total > (numPagina + 1) * tamPagina) {

            numPagina++;

            restantes = total - numPagina * tamPagina;

            if (restantes >= tamPagina)
                disponible = tamPagina;
            else
                disponible = restantes;

            anterior = (numPagina > 0);

            if (restantes > tamPagina) //hay al menos un registro
                siguiente = true;
            else
                siguiente = false;
        }
    }

    /**
     * Permite pasar a la ultima pagina
     */
    public void getLast() {
        int total;
        int restantes;
        int auxnumpag = 0;

        total = getListSize();
        if (total > (numPagina + 1) * tamPagina) {

            calculaTotalPaginas();

            numPagina = totalPaginas - 1;
            restantes = total - numPagina * tamPagina;

            if (restantes >= tamPagina)
                disponible = tamPagina;
            else
                disponible = restantes;

            anterior = (numPagina > 0);

            if (restantes > tamPagina) //hay al menos un registro
                siguiente = true;
            else
                siguiente = false;
        }
    }

    /**
     * Returns the numPagina
     * @return int
     */
    public int getNumPagina() {
        return numPagina;
    }

    /**
     * Permite pasar a la pagina anterior
     */
    public void getPrevPage() {
        if (numPagina > 0) {
            numPagina--;
            disponible = tamPagina;
            siguiente = true;
            anterior = (numPagina > 0);
        }
    }

    /**
     * Permite pasar a la primera pagina
     */
    public void getFirst() {
        if (numPagina > 0) {
            numPagina = 0;
            disponible = tamPagina;
            siguiente = true;
            anterior = (numPagina > 0);
        }
    }

    /**
     * Returns the tamaño de la lista.
     * @return int
     */
    public int getListSize() {
        return lista.size();
    }

    /**
     * Returns the tamPagina.
     * @return int
     */
    public int getPageSize() {
        return tamPagina;
    }

    /**
     * Retorna true si hay una pagina anterior a la actual y falso si no hay
     * @return boolean
     */
    public boolean isAnterior() {
        return anterior;
    }

    /**
     * Retorna true si hay pagina siguiente a la actual y falso si no hay
     * @return boolean
     */
    public boolean isSiguiente() {
        return siguiente;
    }

    /**
     * Elimina todos los elementos de la lista
     */
    public void limpiarLista() {
        lista.clear();
    }

    /**
     * Sets the anterior.
     * @param anterior The anterior to set
     */
    public void setAnterior(boolean newAnterior) {
        anterior = newAnterior;
    }

    /**
     * Sets the disponible.
     * @param disponible The disponible to set
     */
    public void setDisponible(int newDisponible) {
        disponible = newDisponible;
    }

    /**
     * Setea un elemento a la lista
     * @param elemento
     */
    public void setElemento(Object elemento) {

        try {
            lista.add(lista.size(), elemento);
        } catch (Exception e) {
            lista.add(elemento);
        }

    }

    /**
     * Setea un elemento a la lista en una posicion determinada
     * @param elemento, index es el indice donde se quiere setear el elemento
     */
    public Object setElemento(Object elemento, int index) {
        try {
            lista.add(index, elemento);
        } catch (Exception e) {
            lista.add(elemento);
        }
        return elemento;
    }

    /**
     * Sets the numPagina.
     * @param numPagina The numPagina to set
     */
    public void setNumPagina(int newNumPagina) {
        numPagina = newNumPagina;
    }

    /**
     * Sets the siguiente.
     * @param siguiente The siguiente to set
     */
    public void setSiguiente(boolean newSiguiente) {
        siguiente = newSiguiente;
    }

    /**
     * Sets the tamPagina.
     * @param tamPagina The tamPagina to set
     */
    public void setTamPagina(int newTamPagina) {
        tamPagina = newTamPagina;
        // añadido (av)
        calculaTotalPaginas();
    }

    /**
     * Returns the regSeleccionado.
     * @return int
     */
    public int getRegSeleccionado() {
        return regSeleccionado;
    }

    /**
     * Sets the regSeleccionado.
     * @param regSeleccionado The regSeleccionado to set
     */
    public void setRegSeleccionado(int regSeleccionado) {
        this.regSeleccionado = regSeleccionado;
    }

    /**
     * Returns the totalPaginas.
     * @return int
     */
    public int getTotalPaginas() {
        return totalPaginas;
    }

    /**
     * Sets the totalPaginas.
     * @param totalPaginas The totalPaginas to set
     */
    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    /**
     * Permite calcular y obtener el total de paginas que tiene la lista.
     */
    public void calculaTotalPaginas() {
        int totalreg;
        int auxtot;
        int residuo;
        totalreg = getListSize();

        auxtot = totalreg / tamPagina;
        residuo = totalreg % tamPagina;
        if (residuo > 0)
            auxtot++;

        totalPaginas = auxtot;
    }

    /**
     * Permite inicializar la paginacion calcula las paginas y
     * la disponibilidad por pagina. Usa por defecto tamaño de
     * pagina 5 o el parametro regsPerSmallPage del web.xml
     */
    public void initSmall() {
        try {

            int nroregxpagina = 0;

            // (cc)
            int nroLinksXPagina = 
                0; // Número de páginas para enlaces por página

            //CommonDAO commondao = new CommonDAO();
            //nroregxpagina = commondao.getRegsPerSmallPage();
            nroregxpagina = getRowsPerSmallPage();

            // (cc)
            //nroLinksXPagina = commondao.getPagsPerPage();
            nroLinksXPagina = getLinksPerPage();

            if (nroregxpagina == 0) {
                tamPagina = 5;
            } else {
                tamPagina = nroregxpagina;
            }

            // (cc)
            if (nroLinksXPagina == 0) {
                nroPagsDisponibles = 5;
            } else {
                nroPagsDisponibles = nroLinksXPagina;
            }

            setFlagPrimerRango(true);
            setFlagUltimoRango(true);
            numPagina = -1;
            setInicioRango(0);
            getNextPage();
            calculaTotalPaginas();

            if (getTotalPaginas() > nroPagsDisponibles)
                setFlagUltimoRango(false);

        } catch (Exception e) {
            System.out.println("Error en el método initSmall: " + e);
        }
    }

    /**
     * Permite inicializar la paginacion calcula las paginas y
     * la disponibilidad por pagina. Usa por defecto tamaño de
     * pagina 10 o el parametro regsPerPage del web.xml
     */
    public void init() {
        try {

            int nroregxpagina = 0;

            // (cc)
            int nroLinksXPagina = 
                0; // Número de páginas para enlaces por página

            //CommonDAO commondao = new CommonDAO();
            //nroregxpagina = commondao.getRegsPerPage();
            nroregxpagina = getArrayListBeanPageSize();

            // (cc)
            //nroLinksXPagina = commondao.getPagsPerPage();
            nroLinksXPagina = getLinksPerPage();

            if (nroregxpagina == 0) {
                tamPagina = 10;
            } else {
                tamPagina = nroregxpagina;
            }

            // (cc)
            if (nroLinksXPagina == 0) {
                nroPagsDisponibles = 5;
            } else {
                nroPagsDisponibles = nroLinksXPagina;
            }

            setFlagPrimerRango(true);
            setFlagUltimoRango(true);
            numPagina = -1;
            setInicioRango(0);
            getNextPage();
            calculaTotalPaginas();

            if (getTotalPaginas() > nroPagsDisponibles)
                setFlagUltimoRango(false);

        } catch (Exception e) {
            System.out.println("Error al inicializar la paginación: " + e);
        }
    }

    /**
     * Permite inicializar la paginacion calcula las paginas y
     * la disponibilidad por pagina. Utiliza el tamaño de pagina
     * especificado como parametro.
     */
    public void init(int newTamPagina) {
        try {
            tamPagina = newTamPagina;
            numPagina = -1;
            getNextPage();
            calculaTotalPaginas();

        } catch (Exception e) {
            System.out.println("Error al inicializar la paginación: " + e);
        }
    }

    /**
     * Permite pasar a una pagina determinada
     * @param pagina es el numero de pagina, tampag nro de registros por pagina
     */
    public void setPagina(int pagina, int tampag) {
        try {
            numPagina = pagina - 1;
            tamPagina = tampag;
            getNextPage();
            calculaTotalPaginas();
        } catch (Exception e) {
            System.out.println("Error en el método setPagina: " + e);
        }
    }

    /**
     * Returns the comparador.
     * @return Comparator
     */
    public Comparator getComparador() {
        return comparador;
    }

    /**
     * Sets the comparador.
     * Setea la clase que va a realizar la comparación en el caso de las
     * listas que se tengan que ordenar
     * @param comparador The comparador to set
     */
    public void setComparador(Comparator comparador) {
        this.comparador = comparador;
    }

    public void ordenarLista() {
        ordenarLista(true);
    }

    /**
     * Realiza el ordenamiento de la lista, para utilizar este método se debe
     * setear el comparador
     * asc = true : ascendente
     * asc = false : descendente
     */
    public

    void ordenarLista(boolean asc) {

        try {
            //se valida que el comparador se haya seteado
            if (comparador == null) {
                throw new Exception("El comparador para el ordenamiento es nulo.");
            }
            // seteo del orden de ordenamiento
            try {
                Class array[] = { Boolean.class };
                Boolean arrayBoolean[] = { new Boolean(asc) };
                comparador.getClass().getMethod("setAscendente", 
                                                array).invoke(comparador, 
                                                              arrayBoolean);

            } catch (Exception e) {
                System.out.println("Error al ordenar lista: " + e);
            }

            //se le pasa la lista y el comparador
            Collections.sort(lista, comparador);
        } catch (Exception e) {
            System.out.println("Error al ordenar lista: " + e);
        }

    }

    /**
     * Realiza el ordenamiento de la lista,
     * alternando el valor del orden
     * para utilizar este método se debe
     * setear el comparador

     */
    public

    void ordenarListaAlternando() {

        try {
            //se valida que el comparador se haya seteado
            if (comparador == null) {
                throw new Exception("El comparador para el ordenamiento es nulo.");
            }
            //se le pasa la lista y el comparador
            Collections.sort(lista, comparador);
            //se cambia el orden del comparador
            try {

                boolean actual = 
                    ((Boolean)comparador.getClass().getMethod("isAscendente", 
                                                              null).invoke(comparador, 
                                                                           null)).booleanValue();

                Class array[] = { Boolean.class };
                Boolean arrayBoolean[] = { new Boolean(!actual) };
                comparador.getClass().getMethod("setAscendente", 
                                                array).invoke(comparador, 
                                                              arrayBoolean);

            } catch (Exception e) {
                System.out.println("Error al ordenar lista alternando1: " + e);
            }
        } catch (Exception e) {
            System.out.println("Error al ordenar lista alternando2: " + e);
        }

    }

    /**
     * Realiza una búsqueda binaria sobre los elementos de la lista,
     * La lista debe estar ordenada y el comparador debe estar seteado
     * @param elemento, el elemento a buscar en la lista
     * @return int, posición del objeto encontrado
     */
    public int busquedaBinaria(Object elemento) {
        int pos = -1;
        try {

            //se valida que el comparador se haya seteado
            if (comparador == null) {
                throw new Exception("El comparador para la búsqueda binaria es nulo.");
            }
            //se valida que el el elemento a buscar no sea nulo
            if (elemento == null) {
                throw new Exception("El elemento para la búsqueda binaria es nulo.");
            }
            pos = Collections.binarySearch(lista, elemento, comparador);

        } catch (Exception e) {
            System.out.println("Error en búsqueda binaria: " + e);
        } finally {
            return pos;
        }

    }

    /**
	 * Filtra los elementos de una lista ordenada bajo el criterio de filtro, los elementos que cumplan con el filtrado
	 * se colocan en la lista de elementos filtrados
	 * @param elemento, el elemento con el filtro seteado en la lista ordenada
	 * @return List, lista con los elementos filtrados
	 */

    /**
     * Returns the indiceSeleccionado.
     * @return int
     */
    public int getIndiceSeleccionado() {
        return indiceSeleccionado;
    }

    /**
     * Sets the indiceSeleccionado.
     * @param indiceSeleccionado The indiceSeleccionado to set
     */
    public void setIndiceSeleccionado(int indiceSeleccionado) {
        this.indiceSeleccionado = indiceSeleccionado;
    }

    /**
     * Returns the paginaSeleccionada.
     * @return int
     */
    public int getPaginaSeleccionada() {
        return paginaSeleccionada;
    }

    /**
     * Sets the paginaSeleccionada.
     * @param paginaSeleccionada The paginaSeleccionada to set
     */
    public void setPaginaSeleccionada(int paginaSeleccionada) {
        this.paginaSeleccionada = paginaSeleccionada;
    }

    /**
     * Returns the listaFiltrada.
     * @return List
     */
    public

    void marcarElemento(Object obj) {
        try {
            if (lista.contains(obj) && !registrosSeleccionados.contains(obj))
                registrosSeleccionados.add(obj);
        } catch (Exception e) {
            System.out.println("Error al marcar elemento: " + e);
        }
    }

    public void desmarcarElemento(Object obj) {
        try {
            registrosSeleccionados.remove(obj);
        } catch (Exception e) {
            System.out.println("Error al desmarcar elemento: " + e);
        }
    }

    public void limpiarMarcados() {
        try {
            registrosSeleccionados.clear();
        } catch (Exception e) {
            System.out.println("Error al limpiar marcados: " + e);
        }
    }

    public boolean estaSeleccionado(Object obj) {
        try {
            boolean retorno = registrosSeleccionados.contains(obj);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error en el método estaSeleccionado: " + e);
            return false;
        }
    }

    public boolean existeAlgunoSeleccionado() {
        try {
            boolean retorno = false;
            if (registrosSeleccionados.size() > 0)
                retorno = true;
            return retorno;
        } catch (Exception e) {
            System.out.println("Error en el métodod existeAlgunoSeleccionado: " + 
                               e);
            return false;
        }
    }

    public ArrayList getSeleccionados() {
        return registrosSeleccionados;
    }

    // Carlos Castañeda - Synopsis S.A.

    /**
     * Permite pasar a la página deseada
     */
    public void goToPage(int numPage) {
        setNumPagina(numPage);
        int restantes;
        int total;
        total = getListSize();

        restantes = total - numPagina * tamPagina;

        if (restantes >= tamPagina)
            disponible = tamPagina;
        else
            disponible = restantes;

        //Para validar anterior
        anterior = (numPagina > 0);
        //Para validar siguiente

        if (restantes > tamPagina) //hay al menos un registro
            siguiente = true;
        else
            siguiente = false;
    }

    /**
     * Returns the nroPagsDisponibles.
     * @return int
     */
    public int getNroPagsDisponibles() {
        return nroPagsDisponibles;
    }

    /**
     * Sets the nroPagsDisponibles.
     * @param nroPagsDisponibles The nroPagsDisponibles to set
     */
    public void setNroPagsDisponibles(int nroPagsDisponibles) {
        this.nroPagsDisponibles = nroPagsDisponibles;
    }

    /**
     * Permite ir al anterior grupo de páginas.
     */
    public void goToPrev() {

        int aux = inicioRango - nroPagsDisponibles;
        int restantes;
        int total;
        total = getListSize();

        if (aux <= 0) {
            setNumPagina(0);
            setInicioRango(0);
            setFlagPrimerRango(true);
            restantes = total - numPagina * tamPagina;

            if (restantes >= tamPagina)
                disponible = tamPagina;
            else
                disponible = restantes;
        } else {
            setNumPagina(aux);
            setInicioRango(aux);
            setFlagPrimerRango(false);
        }
        setFlagUltimoRango(false);
    }

    /**
     * Permite ir al siguiente grupo de páginas.
     */
    public void goToNext() {

        int aux = inicioRango + nroPagsDisponibles;
        int restantes;
        int total;
        total = getListSize();

        if (aux < totalPaginas) {
            setNumPagina(aux);
            setInicioRango(aux);

            setFlagPrimerRango(false);
            restantes = total - numPagina * tamPagina;

            if (restantes >= tamPagina)
                disponible = tamPagina;
            else
                disponible = restantes;

            if (!(aux + 5 <= totalPaginas)) {
                setFlagUltimoRango(true);
            }
        } else {
            setFlagUltimoRango(true);
        }
    }

    /**
     * Returns the inicioRango.
     * @return int
     */
    public int getInicioRango() {
        return inicioRango;
    }

    /**
     * Sets the inicioRango.
     * @param inicioRango The inicioRango to set
     */
    public void setInicioRango(int inicioRango) {
        this.inicioRango = inicioRango;
    }

    /**
     * Returns the flagPrimerRango.
     * @return boolean
     */
    public boolean isFlagPrimerRango() {
        return flagPrimerRango;
    }

    /**
     * Sets the flagPrimerRango.
     * @param flagPrimerRango The flagPrimerRango to set
     */
    public void setFlagPrimerRango(boolean flagPrimerRango) {
        this.flagPrimerRango = flagPrimerRango;
    }

    /**
     * Returns the flagUltimoRango.
     * @return boolean
     */
    public boolean isFlagUltimoRango() {
        return flagUltimoRango;
    }

    /**
     * Sets the flagUltimoRango.
     * @param flagUltimoRango The flagUltimoRango to set
     */
    public void setFlagUltimoRango(boolean flagUltimoRango) {
        this.flagUltimoRango = flagUltimoRango;
    }

    /**
     * Returns the registrosSeleccionados.
     * @return ArrayList
     */
    public ArrayList getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public int getLinksPerPage() {
        int regs = 0;
        try {
            //Integer.parseInt(InitParameter.getInstance().getLinksPerPage());
            if (regs == 0)
                regs = 5;
        } catch (Exception e) {
        }
        return regs;
    }

    public int getArrayListBeanPageSize() {
        int regs = 0;
        try {
            //regs = InitParameter.getInstance().getArrayListBeanPageSize();
            if (regs == 0)
                regs = 10;
        } catch (Exception e) {
        }
        return regs;
    }

    public int getRowsPerSmallPage() {
        int regs = 0;
        try {
            //regs = Integer.parseInt(InitParameter.getInstance().getRowsPerSmallPage());
            if (regs == 0)
                regs = 5;
        } catch (Exception e) {
        }
        return regs;
    }

    /**
     * Devuelve el ArrayList interno manejado por el ArrayListBean
     * @return ArrayList
     * @since 2004-11-19
     */
    public ArrayList getArrayList() {
        return (ArrayList)lista;
    }

    /**
     * Establece la lista interna al ArrayListBean
     * @param lista
     * @since 2005-03-17
     */
    protected void setArrayList(ArrayList lista) {
        this.lista = lista;
    }

    /**
     * Devuelve el numero de elementos seleccionados
     * @return int
     */
    public int getNumeroSeleccionados() {
        return registrosSeleccionados.size();
    }

    public int getNumeroSeleccionadosPagina() {
        int pagina = 0;
        for (Iterator it = getPageIterator(); it.hasNext(); ) {
            if (estaSeleccionado(it.next()))
                pagina++;
        }
        return pagina;
    }

    /**
     * Devuelve un iterador para la lista Completa
     * @return
     */
    public Iterator getListIterator() {
        //return new ArrayListBeanIterator(this, false);
        return lista.iterator();
    }

    /**
     * Devuelve un iterador para la página actual
     * @return
     */
    public Iterator getPageIterator() {
        return new FarmaArrayListBeanIterator(this, true);
    }

    /**
     * Devuelve un arreglo de booleanos indicando el estado de seleccion de los objetos
     * @return
     */
    public Boolean[] getSeleccionadosPagina() {
        Object objeto = null;
        Boolean[] listaBooleanos = new Boolean[tamPagina];

        int i = 0;
        try {
            if (lista != null) {
                for (Iterator it = getPageIterator(); it.hasNext(); i++) {
                    objeto = it.next();
                    listaBooleanos[i] = new Boolean(estaSeleccionado(objeto));
                }
            }
        } catch (Exception e) {
            System.out.println("Error en el método getSeleccionadosPagina: " + 
                               e);
        }
        return listaBooleanos;
    }

    /**
     * Elimina un elemento del ArrayListBean
     * @param obj
     * @return
     */
    public void remove(Object obj) {
        registrosSeleccionados.remove(obj);
        lista.remove(obj);
    }

    /**
     * Elimina los elementos seleccionados
     */
    public void removeSelection() {
        //Log.trace(getClass(),"removeSelection","Total Registros:"+lista.size());
        //Log.trace(getClass(),"removeSelection","Total Seleccionados:"+registrosSeleccionados.size());
        lista.removeAll(registrosSeleccionados);
        registrosSeleccionados.clear();
        //Log.trace(getClass(),"removeSelection","Total Registros Luego de Eliminar:"+lista.size());
        //Log.trace(getClass(),"removeSelection","Total Seleccionados Luego de Eliminar:"+registrosSeleccionados.size());
    }
}
