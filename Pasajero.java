import java.util.UUID;
/**
 * @author Gabriel Sánchez Arrisi 
 * @version 2022.03.17
 */
public class Pasajero
{
    // instancia de atributos
    private String id;
    private String rut;
    private String nombre;
    private int edad;

    /**
     * Constructor sin parámetros
     */
    public Pasajero()
    {
        this.id = generarId();
        this.rut = ""; 
        this.nombre = "";
        this.edad = 0;
    }
    
    /**
     * Constructor con parámetros
     */
    public Pasajero(String rut, String nombre, int edad)
    {
        this.id = generarId();
        this.rut = rut; 
        this.nombre = nombre;
        this.edad = edad;
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
     * Método publico para obtener el Id del Pasajero
     * @return     valor del id del pasaje
     */
    public String obtenerId()
    {
        return this.id;
    }
    
    
    /**
     * Método publico para obtener el rut del Pasajero
     * @return     valor del rut del pasajero 
     */
    public String obtenerRut()
    {
        return this.rut;
    }
    
    /**
     * Método publico para establecer el rut del Pasajero
     * @param  cliente  indica el valor del rut para la clase
     */
    public void establecerRut(String rut)
    {
        this.rut = rut;
    }
    
    /**
     * Método publico para obtener el nombre del Pasajero
     * @return     valor del nombre del pasajero
     */
    public String obtenerNombre()
    {
        return this.nombre;
    }
    
    /**
     * Método publico para establecer el nombre del Pasajero
     * @param  cliente  indica el valor del rut para la clase
     */
    public void establecerNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    /**
     * Método publico para obtener el nombre del Pasajero
     * @return     valor del nombre del pasajero
     */
    public int obtenerEdad()
    {
        return this.edad;
    }
    
    /**
     * Método publico para establecer la edad del Pasajero
     * @param  cliente  indica el valor de la edad para la clase
     */
    public void establecerEdad(int edad)
    {
        this.edad = edad;
    }

}
