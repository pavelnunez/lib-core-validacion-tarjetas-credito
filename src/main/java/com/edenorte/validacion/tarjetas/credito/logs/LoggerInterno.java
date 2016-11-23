/*
 * Copyright (C) 2016 Pavel Núñez Deschamps <pndeschamps@gmail.com>,<panunez@edenorte.com.do>
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
package com.edenorte.validacion.tarjetas.credito.logs;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pavel Núñez Deschamps
 * <pndeschamps@gmail.com>,<panunez@edenorte.com.do>
 */
public class LoggerInterno {
    private final Class clase;
    private final Logger Log;
    
    public LoggerInterno(Class clazz){
        this.clase = clazz;
        this.Log = Logger.getLogger(clazz.getName());
    }
    
    public void info(String msg){
        Log.log(Level.INFO, msg);
    }
    
    public void error(String msg){
        Log.log(Level.SEVERE, msg);
    }
    
    public void warn(String msg){
        Log.log(Level.WARNING, msg);
    }
}
