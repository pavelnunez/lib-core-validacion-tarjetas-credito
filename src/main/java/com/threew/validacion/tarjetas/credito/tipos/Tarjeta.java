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
package com.threew.validacion.tarjetas.credito.tipos;

/**
 *
 * @author Pavel Núñez Deschamps
 * <pndeschamps@gmail.com>,<pndeschamps@gmail.com>
 */
public class Tarjeta {
    private Long numeroTarjeta;
    private String cvv;
    
    public Tarjeta(Long numero){
        this.numeroTarjeta = numero; 
    }
    
    public Tarjeta(Long numero, String cvvCode){
        this.numeroTarjeta = numero;
        this.cvv = cvvCode;
    }

    public Long getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(Long numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    
}
