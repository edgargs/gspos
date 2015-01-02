package mifarma.ptoventa.administracion.usuarios.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsUsuarios {
    public ConstantsUsuarios() {
    }
    public static final String TIP_NUMERA_USUARIO = "002";
    public static final String ESTADO_ACTIVO = "A";
    public static final String USUARIO_TODOS = "";
    //Se agrego la columna de RRHH
    //28.11.2007  dubilluz  modificacion
    public static final FarmaColumnData[] columnsListaUsuarios =
    { new FarmaColumnData("Nro. Sec.", 70, JLabel.CENTER), new FarmaColumnData("Ap. Paterno", 120, JLabel.LEFT),
      new FarmaColumnData("Ap. Materno", 120, JLabel.LEFT), new FarmaColumnData("Nombres", 150, JLabel.LEFT),
      new FarmaColumnData("Id. Usuario", 96, JLabel.LEFT), new FarmaColumnData("Estado", 60, JLabel.CENTER),
        //new FarmaColumnData("clave", 0, JLabel.LEFT), Modificado dveliz 02.09.08
        new FarmaColumnData("direcc", 0, JLabel.LEFT), new FarmaColumnData("telf", 0, JLabel.LEFT),
        new FarmaColumnData("fecnac", 0, JLabel.LEFT), new FarmaColumnData("gcia", 0, JLabel.LEFT),
        new FarmaColumnData("local", 0, JLabel.LEFT), new FarmaColumnData("codTrab", 0, JLabel.LEFT),
        new FarmaColumnData("", 0, JLabel.LEFT), //DNI
        new FarmaColumnData("", 0, JLabel.LEFT) }; //CODIGO DE RRHH

    //public static final Object[] defaultValuesListaUsuarios = {" "," ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " "," " };
    public static final Object[] defaultValuesListaUsuarios =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    public static final FarmaColumnData[] columnsListaRolesAsignados =
    { new FarmaColumnData("Codigo", 70, JLabel.CENTER), new FarmaColumnData("Descripcion de rol", 200, JLabel.LEFT), };

    public static final Object[] defaultValuesListaRolesAsignados = { " ", " " };

    public static final FarmaColumnData[] columnsListaRoles =
    { new FarmaColumnData("Sel", 40, JLabel.CENTER), new FarmaColumnData("Codigo", 50, JLabel.CENTER),
      new FarmaColumnData("Descripcion de rol", 200, JLabel.LEFT), };

    public static final Object[] defaultValuesListaRoles = { " ", " ", " " };


    //Se agrego la columna de Recursos Humanos
    //27.11.2007 dubilluz modificacion
    public static final FarmaColumnData[] columnsListaTrabajadores =
    { new FarmaColumnData("", 0, JLabel.CENTER), new FarmaColumnData("Código", 60, JLabel.CENTER),
      new FarmaColumnData("Ap. Paterno", 110, JLabel.LEFT), new FarmaColumnData("Ap. Materno", 110, JLabel.LEFT),
      new FarmaColumnData("Nombres", 90, JLabel.LEFT), new FarmaColumnData("DNI", 70, JLabel.LEFT),
      new FarmaColumnData("Estado", 40, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT), };

    public static final Object[] defaultValuesListaTrabajadores =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    // ASOLIS
    //17.02.2009
    public static final FarmaColumnData[] columnsListaTrabajadoresLocal =
    { new FarmaColumnData("", 0, JLabel.CENTER), new FarmaColumnData("Código", 60, JLabel.CENTER),
      new FarmaColumnData("Ap. Paterno", 110, JLabel.LEFT), new FarmaColumnData("Ap. Materno", 110, JLabel.LEFT),
      new FarmaColumnData("Nombres", 90, JLabel.LEFT), new FarmaColumnData("DNI", 70, JLabel.LEFT),
      new FarmaColumnData("Estado", 62, JLabel.CENTER), new FarmaColumnData("Nro Carne", 104, JLabel.CENTER),
      new FarmaColumnData("", 0, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT), };

    public static final Object[] defaultValuesListaTrabajadoresLocal =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };


}
