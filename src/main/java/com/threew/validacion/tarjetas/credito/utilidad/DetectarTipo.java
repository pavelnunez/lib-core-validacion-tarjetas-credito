/*
 * Copyright (C) 2016 Pavel Núñez Deschamps <pndeschamps@gmail.com>,<pndeschamps@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.threew.validacion.tarjetas.credito.utilidad;

import com.threew.validacion.tarjetas.credito.logs.LoggerInterno;
import com.threew.validacion.tarjetas.credito.tipos.TipoTarjeta;

/**
 * Esta clase estática se usa para detectar tipo de Tarjeta de Crédito.
 * @author Pavel Núñez Deschamps
 * <pndeschamps@gmail.com>,<pndeschamps@gmail.com>
 */
public class DetectarTipo {
        private static LoggerInterno Log = new LoggerInterno(DetectarTipo.class);
        /// Visa comienza con 4. Los nuevos plásticos tienen 16 dígitos, los viejos tienen 13.
	private static final String VISA_REGEX = "^4[0-9]{12}(?:[0-9]{3})?$";
 
        /// MasterCard comienza con 51 hasta 55. Todos los plásticos tienen 16 dígitos.
	private static final String MASTER_CARD_REGEX = "^5[1-5][0-9]{14}$";
	
        /// American Express comienza con 34 o 37, y tienen hasta 15 dígitos.
	private static final String AMERICAN_EXPRESS_REGEX = "^3[47][0-9]{13}$";
	
        /// Diners Club comienzan con 300,305,36 o 38. Todas tienen 14 dígitos. 
        /// Hay variantes que comienzan con 5 y deben ser procesadas como MasterCard
	private static final String DINERS_CLUB_REGEX = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
 
        /// Dciscover comienza con 6011 o 65. Todas tienen 16 dígitos
	private static final String DISCOVER_REGEX = "^6(?:011|5[0-9]{2})[0-9]{12}$";
	
        /// JCB comienza con 2131 o 1800. Todas tienen 15 dígitos. Otras JCB que comienzan con 35, tienen 16 dígitos
	private static final String JCB_REGEX = "^(?:2131|1800|35\\d{3})\\d{11}$";
        
        /// Mensaje
        private static final String TIPO_DETECTADO = "Tipo detectado: %s";
    
    public static TipoTarjeta detectarTipo(String numero, String cvv){
        /// Extraer la longitud
        int l = cvv.length();
        
            /// Para Mastercard, Visa, Discover y Dinners
            switch (l) {
                case 3:
                    /// Los CVV de longitud 3 son para estos proveedores
                    if(numero.matches(VISA_REGEX)){
                        Log.info(String.format(TIPO_DETECTADO, "VISA"));
                        return TipoTarjeta.VISA;
                    } else if(numero.matches(MASTER_CARD_REGEX)){
                        Log.info(String.format(TIPO_DETECTADO, "MASTERCARD"));
                        return TipoTarjeta.MASTERCARD;
                    } else if(numero.matches(DINERS_CLUB_REGEX)){
                        Log.info(String.format(TIPO_DETECTADO, "DINERS CLUB"));
                        return TipoTarjeta.DINNERS;
                    } else if(numero.matches(DISCOVER_REGEX)){
                        Log.info(String.format(TIPO_DETECTADO, "DISCOVER"));
                        return TipoTarjeta.DISCOVER;
                    } else if(numero.matches(JCB_REGEX)){
                        Log.info(String.format(TIPO_DETECTADO, "JCB"));
                        return TipoTarjeta.JCB;
                    } else {
                        Log.info(String.format(TIPO_DETECTADO, "INVALIDO"));
                        return TipoTarjeta.INVALIDO;
                    }
                case 4:
                    /// Los CVV de longitud 3 son para American Express
                    if(numero.matches(AMERICAN_EXPRESS_REGEX)){
                        Log.info(String.format(TIPO_DETECTADO, "AMERICAN EXPRESS"));
                        return TipoTarjeta.AMEX;
                    } else {
                        Log.info(String.format(TIPO_DETECTADO, "INVALIDO"));
                        return TipoTarjeta.INVALIDO;
                    }
                default:
                    Log.info(String.format(TIPO_DETECTADO, "INVALIDO"));
                    return TipoTarjeta.INVALIDO;
            }
    } /// Fin metodo detectarTipo 
    
    public static TipoTarjeta detectarTipo(String numero){
        
        /// Los CVV de longitud 3 son para estos proveedores
        if(numero.matches(VISA_REGEX)){
            Log.info(String.format(TIPO_DETECTADO, "VISA"));
            return TipoTarjeta.VISA;
        } else if(numero.matches(MASTER_CARD_REGEX)){
            Log.info(String.format(TIPO_DETECTADO, "MASTERCARD"));
            return TipoTarjeta.MASTERCARD;
        } else if(numero.matches(DINERS_CLUB_REGEX)){
            Log.info(String.format(TIPO_DETECTADO, "DINERS CLUB"));
            return TipoTarjeta.DINNERS;
        } else if(numero.matches(DISCOVER_REGEX)){
            Log.info(String.format(TIPO_DETECTADO, "DISCOVER"));
            return TipoTarjeta.DISCOVER;
        } else if(numero.matches(JCB_REGEX)){
            Log.info(String.format(TIPO_DETECTADO, "JCB"));
            return TipoTarjeta.JCB;
        } else if(numero.matches(AMERICAN_EXPRESS_REGEX)){
            Log.info(String.format(TIPO_DETECTADO, "AMERICAN EXPRESS"));
            return TipoTarjeta.AMEX;
        } else {
            Log.info(String.format(TIPO_DETECTADO, "INDETERMINADO"));
            return TipoTarjeta.INDETERMINADO;
        }
    } /// Fin metodo detectarTipo 
    
}
