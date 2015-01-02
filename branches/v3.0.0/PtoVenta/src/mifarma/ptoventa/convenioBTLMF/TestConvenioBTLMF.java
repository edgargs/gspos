package mifarma.ptoventa.convenioBTLMF;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



import mifarma.common.FarmaConstants;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

public class TestConvenioBTLMF {




	 public TestConvenioBTLMF(){

		    FarmaVariables.vUsuarioBD 	= "ptoventa";
		    FarmaVariables.vClaveBD 	= "mundial265";
		    FarmaVariables.vSID 		= ConstantsPtoVenta.SID;
		    FarmaVariables.vPUERTO 		= ConstantsPtoVenta.PUERTO;
		    FarmaVariables.vIPBD 		= "10.11.1.202";
	 }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		TestConvenioBTLMF s= new TestConvenioBTLMF();



		final JFrame frame = new JFrame("Pantalladfdfdfdf Testeo Convenio");


        final JButton btnLogin       	= new JButton("Pantalla Listar Convenios BTLMF");
        final JButton btnMensaje    	= new JButton("Pantalla Mensaje Conveno       ");
        final JButton btnOpcConvenio 	= new JButton("Pantalla Opcion Conveno        ");
        final JButton btnDatosConvenio 	= new JButton("Pantalla Datossdsd del Conveno     ");
        final JButton btnDatosCovertura = new JButton("Pantalla Mensaje de Cobertura  ");
        final JButton btnDatosRetencion = new JButton("Pantalla Mensaje de Retencion  ");

        final JPanel panel = new JPanel();

        panel.setBounds(new Rectangle(0,0,300,400));

        //btnLogin.setBounds(new Rectangle(0,0,400,100));
        //btnMensaje.setBounds(new Rectangle(0,150,400,200));


        //Lista de convenios btlmf
        btnLogin.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                    	DlgListaConveniosBTLMF loginDlg = new DlgListaConveniosBTLMF(frame,"",false);
                        loginDlg.setVisible(true);
                    }
                });


      //Muestra la Regla en la pantalla. M1.
        btnMensaje.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                    	DlgMsjeImpresionCompBTLMF dlgLogin = new DlgMsjeImpresionCompBTLMF(frame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
                    	dlgLogin.setVisible(true);


                    }
                });


      //Muestra la opcion de convenio
        btnOpcConvenio.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                    	//DlgOpcionConvenio d = new DlgOpcionConvenio(frame,"Opcion Convenio",true);
                    	//d.setVisible(true);


                    }
                });




      //Muestra los datos ingresados de convenio

        btnDatosConvenio.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                    	DlgDatosConvenioBTLMF d = new DlgDatosConvenioBTLMF(frame,"Datos del Convenio: RIMAC TELEFÓNICA",true);
                    	d.setLocationRelativeTo(frame);
                    	d.setVisible(true);

                    }
                });

        //


      //Muestra el mensa de cobertura
        btnDatosCovertura.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                    	DlgMensajeCobertura d = new DlgMensajeCobertura(frame,"Mensaje Covertura",true);

                    	d.setLocationRelativeTo(frame);

                    	d.setVisible(true);

                    }
                });

        //



        //Muestra el mensaje retencion
        btnDatosRetencion.addActionListener(


                  new ActionListener()
                  {
                      public void actionPerformed(ActionEvent e)
                      {
                    	  VariablesConvenioBTLMF.vCodConvenio = "0000000084";
                    	  DlgMensajeRetencion d = new DlgMensajeRetencion(frame,"Mensaje Retencion Convenio",true);
                      	  d.setVisible(true);

                      }
                  });

          //







        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        panel.add(btnLogin);
        panel.add(btnMensaje);
        panel.add(btnOpcConvenio);
        panel.add(btnDatosConvenio);
        panel.add(btnDatosCovertura);
        panel.add(btnDatosRetencion);

        frame.getContentPane().add(panel);

        frame.setVisible(true);

	}

}
