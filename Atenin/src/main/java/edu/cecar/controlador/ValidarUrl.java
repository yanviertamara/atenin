package edu.cecar.controlador;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Clase que controla la validacion de url
 * 
 */
public class ValidarUrl {
    
        final public int validarUrl(String url) {
        int code = 0;
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            code = connection.getResponseCode();

        } catch (MalformedURLException ex) {
            System.out.println("Url no valida");
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        return code;
    }
    

}
