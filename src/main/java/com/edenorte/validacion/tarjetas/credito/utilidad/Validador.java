/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edenorte.validacion.tarjetas.credito.utilidad;

import com.edenorte.validacion.tarjetas.credito.logs.LoggerInterno;
import com.edenorte.validacion.tarjetas.credito.tipos.Tarjeta;
import com.edenorte.validacion.tarjetas.credito.tipos.TipoTarjeta;
import org.apache.commons.validator.routines.CreditCardValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Clase de validación de tarjetas de crédito
 * @author Pavel Núñez Deschamps <panunez@edenorte.com.do>
 */
public class Validador {
    private final String numeroTarjetaString;
    private Long numeroTarjetaLong = Long.MIN_VALUE; 
    /// Algunas constantes importantes
    private final int LONGITUD_MAXIMA_TARJETA_CREDITO = 16;
    private final int LONGITUD_MINIMA_TARJETA_CREDITO = 13;
    private final int LONGITUD_MAXIMA_CVV = 4;
    private final int LONGITUD_MINIMA_CVV = 3;
    /// Mensajes de error
    private final String MENSAJE_TARJETA_CREDITO_LONGITUD_INVALIDA = "El número de tarjeta tiene una longitud inválida";
    private final String MENSAJE_CVV_LONGITUD_INVALIDA = "El CVV/CVV2 tiene una longitud inválida";
    private final String MENSAJE_TARJETA_CREDITO_NO_NUMERICO = "El número de tarjeta tiene caracteres no numéricos";
    private final String MENSAJE_CVV_NO_NUMERICO = "El CVV/CVV2 tiene caracteres no numéricos";
    private final String MENSAJE_TARJETA_CREDITO_VACIO = "El número de tarjeta NO puede estar vacío";
    private final String MENSAJE_CVV_VACIO = "El CVV/CVV2 NO puede estar vacío";
    private final String MENSAJE_TARJETA_CREDITO_CVV_INCONSISTENTE = "El CVV/CVV2 NO coincide con el tipo de tarjeta";
    /// Logger
    //private static final Logger Log = LogManager.getLogger(Validador.class);
    private static final LoggerInterno Log = new LoggerInterno(Validador.class);
    
    public Validador(String numero){
        this.numeroTarjetaString = numero; 
    }
    
    public Validador(Long numero){
        this.numeroTarjetaLong = numero; 
        this.numeroTarjetaString = numero.toString();
    }
    
    public Validador(Tarjeta tarjeta){
        this.numeroTarjetaString = tarjeta.getNumeroTarjeta().toString();
    }
    
    /***
     * Valida a partir de la propiedad número de tarjeta 
     * * @return boolean
     **/
    public boolean validar(){
        if(prevalidarTarjeta(this.numeroTarjetaString) == Boolean.FALSE){
            return Boolean.FALSE;
        }
        /// Crear instancia de validador de Apache
        CreditCardValidator validador = new CreditCardValidator(CreditCardValidator.AMEX + CreditCardValidator.VISA + CreditCardValidator.MASTERCARD);
        
        /// Procesar y retornar
        return validador.isValid(this.numeroTarjetaString);
    }
    
    /***
     * Valida a partir de la propiedad número de tarjeta 
     * @param numero Es el numero de tarjeta en formato string.
     * @return boolean
     **/
    public boolean validar(String numero){
        if(prevalidarTarjeta(numero) == Boolean.FALSE){
            return Boolean.FALSE;
        }
        /// Crear instancia de validador de Apache
        CreditCardValidator validador = new CreditCardValidator(CreditCardValidator.AMEX + CreditCardValidator.VISA + CreditCardValidator.MASTERCARD);
        
        /// Procesar y retornar
        return validador.isValid(numero);
    }
    
    /***
     * Valida a partir de la propiedad número de tarjeta 
     * @param tarjeta Es el objeto tipo Tarjeta que representa el número de
     * tarjeta.
     * @return boolean
     **/
    public boolean validar(Tarjeta tarjeta){
        if(prevalidarTarjeta(tarjeta) == Boolean.FALSE){
            return Boolean.FALSE;
        }
        
        return validar(tarjeta.getNumeroTarjeta().toString());
    }
    
    /***
     * Valida a partir de la propiedad número de tarjeta 
     * @param numero Es el objeto tipo Tarjeta que representa el número de
     * tarjeta.
     * @param cvv Es el código CVV/CVV2 del 
     * @return boolean
     **/
    public boolean validar(String numero, String cvv){
        /// Validar el cvv primero
        if(preValidarCvv(cvv) == Boolean.FALSE){
            return Boolean.FALSE;
        }
        
        /// Validar la tarjeta de crédito
        if(prevalidarTarjeta(numero) == Boolean.FALSE){
            return Boolean.FALSE;
        }
        
        /// Preguntar por el tipo
        TipoTarjeta tipo = DetectarTipo.detectarTipo(numero);
        
        /// Preguntar por el tipo
        switch(tipo){
            case VISA: 
            case MASTERCARD:
            case DINNERS:
            case DISCOVER:
            case JCB:
                /// Invalido puesto que en Visa el CVV es de 3 
                if(cvv.length() == LONGITUD_MAXIMA_CVV){
                    Log.info(MENSAJE_TARJETA_CREDITO_CVV_INCONSISTENTE);
                    return Boolean.FALSE;
                }
                break;
            case AMEX:
                /// Invalido puesto que en American Express el CVV es de 3 
                if(cvv.length() == LONGITUD_MINIMA_CVV){
                    Log.info(MENSAJE_TARJETA_CREDITO_CVV_INCONSISTENTE);
                    return Boolean.FALSE;
                }
                break;
            default:
        }
        /// Retornar la validación
        return validar(numero);
    }
    
    private boolean preValidarCvv(String cvv){
        
        /// Validar longitud cvv primero
        if(cvv == null){
            Log.error(MENSAJE_CVV_VACIO);
            return Boolean.FALSE;
        }
        
        if(!cvv.matches("\\d+")){
            Log.error(MENSAJE_CVV_NO_NUMERICO);
            return Boolean.FALSE;
        }
        
        if(cvv.length() < LONGITUD_MINIMA_CVV || cvv.length() > LONGITUD_MAXIMA_CVV){
            Log.error(MENSAJE_CVV_LONGITUD_INVALIDA);
            return Boolean.FALSE;
        }
        
        return Boolean.TRUE;
    }
    
    private boolean prevalidarTarjeta(Object objeto){
        /// Preguntar la instancia primero
        if(objeto == null){
            return Boolean.FALSE;
        }
        
        if(objeto instanceof Tarjeta){
            String numero = ((Tarjeta)objeto).getNumeroTarjeta().toString();
            if(numero == null){
                Log.error(MENSAJE_TARJETA_CREDITO_VACIO);
                return Boolean.FALSE;
            }
            if(numero.length() < LONGITUD_MINIMA_TARJETA_CREDITO || numero.length() > LONGITUD_MAXIMA_TARJETA_CREDITO){
                Log.error(MENSAJE_TARJETA_CREDITO_LONGITUD_INVALIDA);
                return Boolean.FALSE;
            }
            
            if(!numero.matches("\\d+")){
                Log.error(MENSAJE_TARJETA_CREDITO_NO_NUMERICO);
                return Boolean.FALSE;
            }
        } else if(objeto instanceof String){
            String numero = (String)objeto;
            if(numero == null){
                Log.error(MENSAJE_TARJETA_CREDITO_VACIO);
                return Boolean.FALSE;
            }
            if(numero.length() < LONGITUD_MINIMA_TARJETA_CREDITO || numero.length() > LONGITUD_MAXIMA_TARJETA_CREDITO){
                Log.error(MENSAJE_TARJETA_CREDITO_LONGITUD_INVALIDA);
                return Boolean.FALSE;
            }
            
            if(!numero.matches("\\d+")){
                Log.error(MENSAJE_TARJETA_CREDITO_NO_NUMERICO);
                return Boolean.FALSE;
            }
        } else {
            Log.error("Tipo no soportado. No es String o Tarjeta.");
        }
        return Boolean.TRUE;
    } /// Fin metodo prevalidarTarjeta
    
}
