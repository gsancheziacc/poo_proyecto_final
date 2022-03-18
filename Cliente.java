import java.util.UUID;
/**
 * @author Gabriel Sánchez Arrisi 
 * @version 2022.03.17
 */
public class Cliente
{
    // instancia de atributos
    private String id;
    private String rut;

    /**
     * Constructor sin parámetros
     */
    public Cliente()
    {
        this.id = generarId();
        this.rut = "";
    }
    
    /**
     * Constructor con parámetros
     */
    public Cliente(String rut)
    {
        this.id = generarId();
        this.rut = rut;
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
     * Método publico para obtener el Id del Cliente
     * @return     valor del id del cliente
     */
    public String obtenerId()
    {
        return this.id;
    }
    
    /**
     * Método publico para obtener el rut del Cliente
     * @return     valor del rut del cliente 
     */
    public String obtenerRut()
    {
        return this.rut;
    }
    
    /**
     * Método publico para obtener el rut del Cliente
     * 
     * @param  rut  indica el valor del rut para la clase
     */
    public void establecerRut(String rut)
    {
        this.rut = rut;
    }
}
