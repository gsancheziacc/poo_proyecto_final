import java.util.UUID;
/**
 * @author Gabriel Sánchez Arrisi 
 * @version 2022.03.17
 */
public class Pasaje
{
    // constantes
    final int VALOR_DEFAULT = 5000; //valor del pasaje
    
    // instancia de atributos
    private String id;
    private Cliente cliente;
    private Bus bus;
    private Pasajero pasajero;

    /**
     * Constructor sin parámetros
     */
    public Pasaje()
    {
        this.id = generarId();
        this.cliente = new Cliente();
        this.bus = new Bus();
        this.pasajero = new Pasajero();
    }
    
    /**
     * Constructor con parámetros
     */
    public Pasaje(Cliente cliente, Bus bus, Pasajero pasajero)
    {
        this.id = generarId();
        this.cliente = cliente;
        this.bus = bus;
        this.pasajero = pasajero;
    }

    /**
     * Método privado para generar el ID de manera automática
     * @return     valor de tipo String con un GUID autogenerado 
     */
    private String generarId()
    {
        /*
         * Se pudo perfectamente obviar esta clase y dejar el método estático directamente en el contructor, 
         * pero esto permite facilitar la mantención futura en caso de aplicar reglas para la generación del ID
        */
        return java.util.UUID.randomUUID().toString();
    }
    
    /**
     * Método publico para obtener el Id del Pasaje
     * @return     valor del id del pasaje
     */
    public String obtenerId()
    {
        return this.id;
    }
    
    /**
     * Método publico para obtener el cliente del Pasaje
     * @return     valor del cliente del pasaje 
     */
    public Cliente obtenerCliente()
    {
        return this.cliente;
    }
    
    /**
     * Método publico para establecer el cliente del Pasaje
     * 
     * @param  cliente  indica el valor del cliente para la clase
     */
    public void establecerCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }
    
    /**
     * Método publico para obtener el bus del Pasaje
     * @return     valor del bus del pasaje 
     */
    public Bus obtenerBus()
    {
        return this.bus;
    }
    
    /**
     * Método publico para establecer el bus del Pasaje
     * 
     * @param  cliente  indica el valor del bus para la clase
     */
    public void establecerBus(Bus bus)
    {
        this.bus = bus;
    }
    
    /**
     * Método publico para obtener el valor del Pasaje
     * @return     valor del pasajero del pasaje 
     */
    public Pasajero obtenerPasajero()
    {
        return this.pasajero;
    }
    
    /**
     * Método publico para establecer el pasajero del Pasaje
     * 
     * @param  cliente  indica el valor del pasajero para la clase
     */
    public void establecerPasajero(Pasajero pasajero)
    {
        this.pasajero = pasajero;
    }
    
    
    public int obtenerValor()
    {
        int edadPasajero = this.pasajero.obtenerEdad();
        if(edadPasajero < 8)
        {
            return (int)Math.round(VALOR_DEFAULT - (VALOR_DEFAULT * 0.50));
        }
        else if (edadPasajero > 65)
        {
            return (int)Math.round(VALOR_DEFAULT - (VALOR_DEFAULT * 0.30));
        }
        else
        {
            return VALOR_DEFAULT;
        }
    }
    
}
