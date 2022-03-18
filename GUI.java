import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
/**
 * @author  Gabriel Sánchez Arrisi
 * @version 2022.03.18.01
 */
public class GUI extends JFrame implements ActionListener, ItemListener
{
    //atributos globales
    private Cliente cliente;
    private java.util.List<Bus> listaBuses;
    private java.util.List<Pasajero> listaPasajeros;
    private java.util.List<Pasaje> listaPasajes;
    private int cantidadPasajeros;
    
    //componentes globales
    private JTabbedPane tabs;
    private JTextField txtRut;
    private JPanel pnlConfigPasajeros;
    private JPanel pnlResumen;
    private JComboBox cbBuses;
    private JComboBox cbCantidad;
    
    /**
     * Constructor sin parámetros
     */
    public GUI()
    {
        //Parametros Globales de la Interfaz
        super("IACC");
        setSize(800,500);
        setTitle("Comprar Pasaje");
        setLocationRelativeTo(null);
        
        //Inicializador de atributos expecíficos
        listaBuses = obtenerListaBuses();
        cantidadPasajeros = 1;
        listaPasajes = new ArrayList<Pasaje>();
        
        //Configuración inicial de Tabs (Proceso de Compra de Pasajes)
        tabs = new JTabbedPane();
        tabs.addTab("1.- Identificación Cliente", crearPanelIdentificacion());      //Panel de identificación del cliente
        tabs.addTab("2.- Fecha y Cantidad Pasajes", crearPanelSeleccionPasaje());   //Panel de Selector de bus y cantidad de pasajeros
        tabs.addTab("3.- Configuración Pasajes", crearPanelConfiguracion());        //Panel de configuración de los pasajes
        tabs.addTab("4.- Resumen", crearPanelResumen());                            //Panel de resumen de la compra y pago
        
        //Se activa el primer tag y además se selecciona
        activarTab(0, true);
        
        //Se agregan los tabs a la interfaz
        add(tabs);
        
        //Se habilita la visualización
        setVisible(true);
    }
    
    /**
     * Método privado que permite manipular los "tabs" activos
     * 
     * @param  tabIndex  activa el tab del indice indicado
     * @param  selectTab boleano que selecciona el tab indicado
     */
    private void activarTab(int tabIndex, boolean selectTab)
    {
        tabs.setEnabledAt(0, (tabIndex == 0));
        tabs.setEnabledAt(1, (tabIndex == 1));
        tabs.setEnabledAt(2, (tabIndex == 2));
        tabs.setEnabledAt(3, (tabIndex == 3));
        
        if(selectTab)
        {
            tabs.setSelectedIndex(tabIndex);
        }
    }
    
    /**
     * Método privado recepciona los eventos gatillados por los "Item Listener"
     * 
     * @return  devuelve el listado de buses configurados en duro en la aplicación
     */
    private java.util.List<Bus> obtenerListaBuses()
    {
        java.util.List<Bus> lista = new ArrayList<Bus>();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");  
        LocalDateTime minDate = LocalDateTime.now().plusDays(2); 
        
        lista.add(new Bus("Puerto Montt", dtf.format(minDate)));
        lista.add(new Bus("Osorno", dtf.format(minDate.plusHours(2))));
        lista.add(new Bus("Puerto Natales", dtf.format(minDate.plusHours(5))));

        return lista;
    }
    
    /**
     * Método privado recepciona los eventos gatillados por los "Action Listener"
     * 
     * @param  e  evento gatillador
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand() == "nextPanel1")
        {
            procesarPanelIdentificacion();
            activarTab(1,true);
        }
        else if (e.getActionCommand() == "backPanel2")
        {
            activarTab(0,true);
        }
        else if (e.getActionCommand() == "nextPanel2")
        {
            activarTab(2,true);
        }
        else if (e.getActionCommand() == "backPanel3")
        {
            activarTab(1,true);
        }
        else if (e.getActionCommand() == "nextPanel3")
        {
            procesarPanelConfiguracionPasajes();
            activarTab(3,true);
        }
        else if (e.getActionCommand() == "backPanel4")
        {
            activarTab(2,true);
        }
        else if (e.getActionCommand() == "pay")
        {
            JOptionPane.showMessageDialog(null, "Se ha enviado un correo con las instrucciones del pago, recuerde que debe pagar con 24 horas de anticipación");
            return;
        }
    }
    
    /**
     * Método privado que procesa la información de panel de identificación (primer panel)
     */
    private void procesarPanelIdentificacion()
    {
        String rutIngresado = txtRut.getText();
        if(!validarRut(rutIngresado))
        {
            JOptionPane.showMessageDialog(null, "El rut '" + rutIngresado + "' no es válido");
            return;
        }
        
        this.cliente = new Cliente();
        cliente.establecerRut(rutIngresado.trim().replace(".", ""));
    }
    
    /**
     * Método privado que procesa la información de panel de configuración de pasajes (tercer panel)
     */
    private void procesarPanelConfiguracionPasajes()
    {
        //Se extrae los valores de los textfields dinámicos para cargar el listado de pasajeros
        cargarListadoPasajeros();
        
        //Se obtiene el bus seleccionado por el usuario
        Bus bus = listaBuses.get(cbBuses.getSelectedIndex());
        
        Font font = new Font("Courier", Font.BOLD,12);
        JPanel pnlGeneral = new JPanel();
        pnlGeneral.setLayout(new GridBagLayout());
        GridBagConstraints cg = new GridBagConstraints();
        cg.weightx = 1;
        cg.fill = GridBagConstraints.HORIZONTAL;
        
        //Se general panel superior donde muestra la información general (Cliente y Bus)
        JPanel pnlDatosGenerales = crearPanelDatosGenerales(font, bus);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        pnlGeneral.add(pnlDatosGenerales, c);
        
        //Se general panel inferior que muestra los datos de los pasajeros
        JPanel pnlDatosPasajeros = crearPanelDatosPasajeros(font, bus);
        c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(100,0,0,0);
        pnlGeneral.add(pnlDatosPasajeros, c);
        
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        
        JButton btnAtras = new JButton();
        btnAtras.setText("Atras");
        btnAtras.setActionCommand("backPanel4");
        btnAtras.addActionListener(this);
        btnAtras.setPreferredSize(new Dimension(100, 75));
        c.ipady = 0;
        c.gridwidth = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        pnlButtons.add(btnAtras,c);
        
        JButton btnPagar = new JButton();
        btnPagar.setText("Pagar");
        btnPagar.setActionCommand("pay");
        btnPagar.addActionListener(this);
        btnPagar.setPreferredSize(new Dimension(100, 75));
        c.ipady = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;
        pnlButtons.add(btnPagar,c);
        
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(100,0,0,0);
        pnlGeneral.add(pnlButtons, c);
        
        pnlResumen.add(pnlGeneral, cg);
    }
    
    /**
     * Método privado que extrae los valores de los textfield dinámicos para crear los objetos de tipo pasajero
     */
    private void cargarListadoPasajeros()
    {
        listaPasajeros = new ArrayList<Pasajero>();
        JPanel pnlInterno = (JPanel)pnlConfigPasajeros.getComponent(0);
        Component[] componentes = pnlInterno.getComponents();
        JTextField[] texfields = new JTextField[cantidadPasajeros * 3];
        int txtEfectivo = 0;
        for(int i = 0; i < componentes.length; i++)
        {
            Component componente = componentes[i];
            if(componente instanceof JTextField)
            {
                texfields[txtEfectivo] = ((JTextField)componente);
                txtEfectivo++;
            }
        }
        
        for(int i = 0; i < cantidadPasajeros; i++)
        {
            String rutPasajero = "";
            String nombrePasajero = "";
            String edadPasajero = "";
            for(int j = 0; j < texfields.length; j++)
            {
                JTextField textfield = texfields[j];
                String rutField = "txtRutPasajero" + (i+1);
                String nombreField = "txtNombrePasajero" + (i+1);
                String edadField = "txtEdadPasajero" + (i+1);
                
                String textFieldName = textfield.getName();
                if(textFieldName.equalsIgnoreCase(rutField))
                {
                    rutPasajero = textfield.getText();
                }
                else if(textFieldName.equalsIgnoreCase(nombreField))
                {
                    nombrePasajero = textfield.getText();
                }
                else if(textFieldName.equalsIgnoreCase(edadField))
                {
                    edadPasajero = textfield.getText();
                }
            }
            
            int edadPasajeroInt = 0;
            if(this.esNumerico(edadPasajero))
            {
                edadPasajeroInt = Integer.parseInt(edadPasajero);
            }
            
            Pasajero pasajero = new Pasajero(rutPasajero,nombrePasajero,edadPasajeroInt); 
            listaPasajeros.add(pasajero);
        }
    }
    
    /**
     * Método privado que carga el panel que informa los datos generales del cliente
     * 
     * @param  font   fuente especifica los labels más destacados 
     * @param  bus    objeto de tipo Bus correspondiente al seleccionado por el usuario
     */
    private JPanel crearPanelDatosGenerales(Font font, Bus bus)
    {
        JPanel pnlDatosGenerales = new JPanel();
        pnlDatosGenerales.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        
        JLabel lblTitCliente = new JLabel("Cliente");
        lblTitCliente.setFont(font);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        pnlDatosGenerales.add(lblTitCliente, c);
        
        JLabel lblCliente = new JLabel(this.cliente.obtenerRut());
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        pnlDatosGenerales.add(lblCliente, c);
        
        JLabel lblTitDestino = new JLabel("Destino");
        lblTitDestino.setFont(font);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        pnlDatosGenerales.add(lblTitDestino, c);
        
        JLabel lblDestino = new JLabel(bus.obtenerDestino());
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        pnlDatosGenerales.add(lblDestino, c);
        
        JLabel lblTitFecha = new JLabel("Fecha");
        lblTitFecha.setFont(font);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        pnlDatosGenerales.add(lblTitFecha, c);
        
        JLabel lblFecha = new JLabel(bus.obtenerFecha());
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        pnlDatosGenerales.add(lblFecha, c);
        
        return pnlDatosGenerales;
    }
    
    /**
     * Método privado que carga el panel que informa los datos de los pasajeros
     * 
     * @param  font   fuente especifica los labels más destacados 
     * @param  bus    objeto de tipo Bus correspondiente al seleccionado por el usuario
     */
    private JPanel crearPanelDatosPasajeros(Font font, Bus bus)
    {
        JPanel pnlDatos = new JPanel();
        pnlDatos.setLayout(new GridBagLayout());
        pnlDatos.setPreferredSize(new Dimension(getWidth(), 300));
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        
        JLabel lblTitRut = new JLabel("Rut");
        lblTitRut.setFont(font);
        c.gridx = 1;
        c.gridy = 0;
        pnlDatos.add(lblTitRut, c);
        
        JLabel lblTitNombre = new JLabel("Nombre");
        lblTitNombre.setFont(font);
        c.gridx = 2;
        c.gridy = 0;
        pnlDatos.add(lblTitNombre, c);
        
        JLabel lblTitCosto = new JLabel("Costo");
        lblTitCosto.setFont(font);
        c.gridx = 3;
        c.gridy = 0;
        pnlDatos.add(lblTitCosto, c);
        int valorTotalPasaje = 0;
        for(int i = 0; i < listaPasajeros.size(); i++)
        {
            Pasajero pasajero = listaPasajeros.get(i);
            Pasaje pasaje = new Pasaje(this.cliente, bus, pasajero);
            listaPasajes.add(pasaje);
            
            JLabel lblPasajero = new JLabel("Pasajero " + (i+1));
            lblPasajero.setFont(font);
            c.gridx = 0;
            c.gridy = i+1;
            pnlDatos.add(lblPasajero, c);
            
            JLabel lblRut = new JLabel(pasajero.obtenerRut());
            c.gridx = 1;
            c.gridy = i+1;
            pnlDatos.add(lblRut, c);
            
            JLabel lblNombre = new JLabel(pasajero.obtenerNombre());
            c.gridx = 2;
            c.gridy = i+1;
            pnlDatos.add(lblNombre, c);
            
            valorTotalPasaje = valorTotalPasaje + pasaje.obtenerValor();
            JLabel lblCosto = new JLabel(pasaje.obtenerValor() + "");
            c.gridx = 3;
            c.gridy = i+1;
            pnlDatos.add(lblCosto, c);
            
        }
        
        JLabel lblTitTotal = new JLabel("Total");
        lblTitTotal.setFont(font);
        c.gridx = 2;
        c.gridy = listaPasajeros.size() + 1;
        pnlDatos.add(lblTitTotal, c);
        
        JLabel lblTotal = new JLabel(valorTotalPasaje + "");
        lblTotal.setFont(font);
        c.gridx = 3;
        c.gridy = listaPasajeros.size() + 1;
        pnlDatos.add(lblTotal, c);
        
        return pnlDatos;
    }
    
    /**
     * Método privado recepciona los eventos gatillados por los "Item Listener"
     * 
     * @param  e  evento gatillador
     */
    public void itemStateChanged(ItemEvent e)
    {
        if(e.getStateChange() == ItemEvent.DESELECTED)
        {
            cantidadPasajeros = Integer.parseInt(cbCantidad.getSelectedItem().toString());
            
            JPanel pnlPasajes = new JPanel();
            pnlPasajes.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.weightx = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            
            for(int i = 0; i < cantidadPasajeros; i++)
            {
                JLabel lblRutPasajero = new JLabel();
                lblRutPasajero.setText("Rut Pasajero " + (i+1));
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridy = i;
                c.fill = GridBagConstraints.HORIZONTAL;
                pnlPasajes.add(lblRutPasajero, c);
                
                JTextField txtRutPasajero = new JTextField();
                txtRutPasajero.setToolTipText("Rut Pasajero " + (i+1));
                txtRutPasajero.setPreferredSize(new Dimension(100, 25));
                txtRutPasajero.setName("txtRutPasajero"+ (i+1));
                c.weightx = 0.5;
                c.gridx = 1;
                c.gridy = i;
                c.fill = GridBagConstraints.HORIZONTAL;
                pnlPasajes.add(txtRutPasajero, c);
                
                JLabel lblNombrePasajero = new JLabel();
                lblNombrePasajero.setText("Nombre Pasajero " + (i+1));
                c.weightx = 0.5;
                c.gridx = 2;
                c.gridy = i;
                c.fill = GridBagConstraints.HORIZONTAL;
                pnlPasajes.add(lblNombrePasajero, c);
                
                JTextField txtNombre = new JTextField();
                txtNombre.setToolTipText("Nombre Pasajero " + (i+1));
                txtNombre.setPreferredSize(new Dimension(100, 25));
                txtNombre.setName("txtNombrePasajero"+ (i+1));
                c.weightx = 0.5;
                c.gridx = 3;
                c.gridy = i;
                c.fill = GridBagConstraints.HORIZONTAL;
                pnlPasajes.add(txtNombre, c);
                
                JLabel lblEdadPasajero = new JLabel();
                lblEdadPasajero.setText("Edad Pasajero " + (i+1));
                c.weightx = 0.5;
                c.gridx = 4;
                c.gridy = i;
                c.fill = GridBagConstraints.HORIZONTAL;
                pnlPasajes.add(lblEdadPasajero, c);
                
                JTextField txtEdad = new JTextField();
                txtEdad.setToolTipText("Edad Pasajero " + (i+1));
                txtEdad.setPreferredSize(new Dimension(100, 25));
                txtEdad.setName("txtEdadPasajero"+ (i+1));
                c.weightx = 0.5;
                c.gridx = 5;
                c.gridy = i;
                c.fill = GridBagConstraints.HORIZONTAL;
                pnlPasajes.add(txtEdad, c);
            }
            
            JPanel pnlButtons = new JPanel();
            pnlButtons.setLayout(new GridBagLayout());
            c = new GridBagConstraints();
            
            JButton btnAtras = new JButton();
            btnAtras.setText("Atras");
            btnAtras.setActionCommand("backPanel3");
            btnAtras.addActionListener(this);
            btnAtras.setPreferredSize(new Dimension(100, 50));
            c.ipady = 0;
            c.gridwidth = 1;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 0;
            pnlButtons.add(btnAtras,c);
            
            JButton btnSiguiente = new JButton();
            btnSiguiente.setText("Siguiente");
            btnSiguiente.setActionCommand("nextPanel3");
            btnSiguiente.addActionListener(this);
            btnSiguiente.setPreferredSize(new Dimension(100, 50));
            c.ipady = 0;
            c.gridwidth = 1;
            c.gridheight = 2;
            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 0;
            pnlButtons.add(btnSiguiente,c);
            
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = 1;
            c.gridx = 2;
            c.gridy = cantidadPasajeros;
            c.gridwidth = 2;
            pnlPasajes.add(pnlButtons, c);
            
            pnlConfigPasajeros.add(pnlPasajes);
        }
    }
    
    /**
     * Método privado que genera el panel del primer tab
     * 
     * @return  devuelve el panel con los controles para el primer tab
     */
    private JPanel crearPanelIdentificacion()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblRut = new JLabel();
        lblRut.setText("Ingrese su RUT");
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(lblRut);
        
        txtRut = new JTextField();
        c.weighty = 0;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,250,0,250);
        c.gridwidth = 2;
        panel.add(txtRut,c);
        
        JButton btnSiguiente = new JButton();
        btnSiguiente.setText("Siguiente");
        btnSiguiente.setActionCommand("nextPanel1");
        btnSiguiente.addActionListener(this);
        btnSiguiente.setPreferredSize(new Dimension(100, 50));
        c.ipady = 0;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.gridwidth = 1;
        c.insets = new Insets(10,300,0,300);
        panel.add(btnSiguiente,c);
        
        return panel;
    }
    
    /**
     * Método privado que genera el panel del segundo tab
     * 
     * @return  devuelve el panel con los controles para el segundo tab
     */
    private JPanel crearPanelSeleccionPasaje()
    {
        JPanel pnlGlobal = new JPanel();
        pnlGlobal.setLayout(new GridBagLayout());
        GridBagConstraints cg = new GridBagConstraints();
        cg.insets = new Insets(200,0,200,0);
        cg.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblBus = new JLabel();
        lblBus.setText("Seleccione el BUS");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(lblBus, c);
        
        cbBuses = new JComboBox();
        for(int i = 0; i < listaBuses.size(); i++)
        {
            Bus bus = listaBuses.get(i);
            String nombre = String.format("%s - %s", bus.obtenerFecha(), bus.obtenerDestino());
            cbBuses.addItem(nombre);
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        panel.add(cbBuses,c);
        
        JLabel lblPasajes = new JLabel();
        lblPasajes.setText("Cantidad de Pasajes");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        panel.add(lblPasajes, c);
        
        cbCantidad = new JComboBox();
        cbCantidad.addItem("1");
        cbCantidad.addItem("2");
        cbCantidad.addItem("3");
        cbCantidad.addItem("4");
        cbCantidad.addItem("5");
        cbCantidad.setActionCommand("cbCantidad");
        cbCantidad.addItemListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        panel.add(cbCantidad,c);
        
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridBagLayout());
        
        JButton btnAtras = new JButton();
        btnAtras.setText("Atras");
        btnAtras.setActionCommand("backPanel2");
        btnAtras.addActionListener(this);
        btnAtras.setPreferredSize(new Dimension(100, 75));
        c.ipady = 0;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        pnlButtons.add(btnAtras,c);
        
        JButton btnSiguiente = new JButton();
        btnSiguiente.setText("Siguiente");
        btnSiguiente.setActionCommand("nextPanel2");
        btnSiguiente.addActionListener(this);
        btnSiguiente.setPreferredSize(new Dimension(100, 200));
        c.ipady = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        pnlButtons.add(btnSiguiente,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        panel.add(pnlButtons, c);
        
        pnlGlobal.add(panel, cg);
        
        return pnlGlobal;
    }
    
    /**
     * Método privado que genera el panel del tercer tab
     * 
     * @return  devuelve el panel con los controles para el tercer tab
     */
    private JPanel crearPanelConfiguracion()
    {
        pnlConfigPasajeros = new JPanel();
        pnlConfigPasajeros.setLayout(new GridBagLayout());
        GridBagConstraints cg = new GridBagConstraints();
        cg.weightx = 1;
        cg.fill = GridBagConstraints.HORIZONTAL;
        
        return pnlConfigPasajeros;
    }
    
    /**
     * Método privado que genera el panel del cuarto tab
     * 
     * @return  devuelve el panel con los controles para el cuarto tab
     */
    private JPanel crearPanelResumen()
    {
        pnlResumen = new JPanel();
        pnlResumen.setLayout(new GridBagLayout());
        GridBagConstraints cg = new GridBagConstraints();
        cg.weightx = 1;
        cg.fill = GridBagConstraints.HORIZONTAL;
        
        return pnlResumen;
    }
    
    /**
     * Método que permite detectar si un rut es válido o no
     * @param   rut     valor correspondiente al rut para validar
     * 
     * @return          devuelve un booleano que indica si el rut es válido o no
     */
    private boolean validarRut(String rut) 
    {
        boolean validacion = false;
        try 
        {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));
    
            char dv = rut.charAt(rut.length() - 1);
    
            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }
        } 
        catch (java.lang.NumberFormatException e) 
        {
        } 
        catch (Exception e) 
        {
        }
        return validacion;  
    }
    
    /**
     * Método que permite detectar si valor de tipo String puede ser numérico
     * @param   strNum  valor correspondiente al String para validar
     * 
     * @return          devuelve un booleano que indica si el valor String es válido o no
     */
    private boolean esNumerico(String strNum) 
    {
        if (strNum == null) 
        {
            return false;
        }
        try 
        {
            double d = Double.parseDouble(strNum);
        } 
        catch (NumberFormatException nfe) 
        {
            return false;
        }
        return true;
    }
}

