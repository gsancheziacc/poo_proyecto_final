import java.util.UUID;
/**
 * @author Gabriel Sánchez Arrisi 
 * @version 2022.03.17
 */
public class Bus
{
    // constantes
    final int CAPACIDAD_MAXIMA = 40; //capacidad física restringida para todos los buses
    // instancia de atributos
    private String id;
    private String destino;
    private String fecha;
    private int capacidad;

    /**
     * Constructor sin parámetros
     */
    public Bus()
    {
        this.id = generarId();
        this.destino = "";
        this.fecha = "sin fecha";
        this.capacidad = CAPACIDAD_MAXIMA; 
    }
    
    /**
     * Constructor con parámetros
     */
    public Bus(String destino, String fecha)
    {
        this.id = generarId();
        this.destino = destino;
        this.fecha = fecha;
        this.capacidad = CAPACIDAD_MAXIMA;
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
     * Método publico para obtener el Id del Bus
     * @return     valor del id del bus
     */
    public String obtenerId()
    {
        return this.id;
    }
    
    /**
     * Método publico para obtener el destino del Bus
     * @return     valor del destino del bus 
     */
    public String obtenerDestino()
    {
        return this.destino;
    }
    
    /**
     * Método publico para establecer el destino del Bus
     * 
     * @param  destino  indica el valor del destino para la clase
     */
    public void establecerDestino(String destino)
    {
        this.destino = destino;
    }
    
    /**
     * Método publico para obtener la fecha del Bus
     * @return     valor del fecha del bus 
     */
    public String obtenerFecha()
    {
        return this.fecha;
    }
    
    /**
     * Método publico para establecer la fecha del Bus
     * 
     * @param  fecha  indica el valor de la fecha para la clase
     */
    public void establecerFecha(String fecha)
    {
        this.fecha = fecha;
    }
}
