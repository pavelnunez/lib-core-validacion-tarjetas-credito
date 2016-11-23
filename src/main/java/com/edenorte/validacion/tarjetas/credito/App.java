/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edenorte.validacion.tarjetas.credito;

import com.edenorte.validacion.tarjetas.credito.logs.LoggerInterno;
import com.edenorte.validacion.tarjetas.credito.tipos.TipoTarjeta;
import com.edenorte.validacion.tarjetas.credito.utilidad.DetectarTipo;
import com.edenorte.validacion.tarjetas.credito.utilidad.Validador;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class para ejecutarlo como comandos
 * @author panunez
 */
public class App {

    //static final Logger Log = LogManager.getLogger(App.class.getName());
    private static final LoggerInterno Log = new LoggerInterno(App.class);
    
    /// Algunas constantes importantes
    private static final int LONGITUD_MAXIMA_TARJETA_CREDITO = 16;
    private static final int LONGITUD_MINIMA_TARJETA_CREDITO = 13;
    private static final int LONGITUD_MAXIMA_CVV = 4;
    private static final int LONGITUD_MINIMA_CVV = 3;
    
    
    /**
     * Apliacion principal o main de la librería.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /// Preguntar por los argumentos
        
        // Probar con números de tarjeta 
        Options opciones = new Options();
        /// Agregar opciones
        opciones.addOption("n", true, "el número de tarjeta a validar");
        opciones.addOption("c", "el código CVV/CVV2 de la tarjeta a validar");
        
        /// Analizar la linea de comandos
        CommandLineParser parser = new DefaultParser();
        
        try {
            /// Linea de comandos
            CommandLine cmd = parser.parse(opciones, args);
            
            /// Preguntar si a linea de comandos se usa
            if(cmd.hasOption("n") == true){
                String numero = cmd.getOptionValue("n");
                /// Validar
                validar(numero);
                
            } else {
                validar(args[1]);
            }
            
            
        } catch (ParseException ex) {
            Log.error(ex.toString());
        }
        
        
    }
    
    /***
     * Método para aislar la funcionalidad de validación
     * @since 0.0.1 
     **/
    private static void validar(String numero){
        
        Log.info(new StringBuilder().append("El argumento n: ").append(numero).toString());
        /// Log del tipo
        TipoTarjeta tipo = DetectarTipo.detectarTipo(numero);
        
        /// Validar si no está vacío
        if(numero == null){
            Log.error("Debe especificar un número de tarjeta");
            System.exit(0);
        } 

        /// Validar que la longitud no esté fuera de rango
        if(numero.length() < App.LONGITUD_MINIMA_CVV && numero.length() > App.LONGITUD_MAXIMA_CVV){
            Log.error("El número de tarjeta tiene una longitud incorrecta");
            System.exit(0);
        } 

        /// Que sea numérico
        if(!numero.matches("\\d+")){
            Log.error("El número de tarjeta, contiene caracteres inválidos");
            System.exit(0);
        }

        Validador validador = new Validador(numero);

        if(validador.validar() == true){
            Log.info(new StringBuilder().append("El número: ").append(numero).append(" es válido!!!").toString());
            System.exit(0);
        } else {
            Log.error(new StringBuilder().append("El número: ").append(numero).append(" NO es válido").toString());
            System.exit(0);
        }
        
    } /// Validar
    
}
