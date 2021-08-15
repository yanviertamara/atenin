package edu.cecar.controlador;

import edu.cecar.modelo.Url;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que controla las consultas de base de datos
 *
 */
public class BaseDatos {
    
    

    public void guardarUrl(String url) {
        try {
            ConectarMySQL.getConectarMySQL("127.0.0.1", "atenin", "root", "");

            PreparedStatement ps = ConectarMySQL.getConexion().prepareStatement("INSERT into urls values(null,?)");

            ps.setString(1, url);
            ps.execute();
            ps.close();

            System.out.println("URL guardada de forma exitosa");

        } catch (Exception ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarUrl(int id) {
        try {
            ConectarMySQL.getConectarMySQL("127.0.0.1", "atenin", "root", "");

            PreparedStatement ps = ConectarMySQL.getConexion().prepareStatement("DELETE FROM urls WHERE id=" + id);

            ps.executeUpdate();
            ps.close();

            System.out.println("URL eliminada de forma exitosa");

        } catch (Exception ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Url> consultarUrl() {

        try {
            ConectarMySQL.getConectarMySQL("127.0.0.1", "atenin", "root", "");

            PreparedStatement ps = ConectarMySQL.getConexion().prepareStatement("SELECT * FROM urls");

            ResultSet rs = ps.executeQuery();

            List<Url> urls = new ArrayList();

            while (rs.next()) {
                Url u = new Url();
                int id = rs.getInt("id");
                String url = rs.getString("url");

                u.setId(id);
                u.setUrl(url);

                urls.add(u);

            }

            ps.close();
            System.out.println("Consulta realizada de forma exitosa");
            return urls;
        } catch (Exception ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public void editarUrl(String url, int id) {

        try {
            ConectarMySQL.getConectarMySQL("127.0.0.1", "atenin", "root", "");

            PreparedStatement ps = ConectarMySQL.getConexion().prepareStatement("UPDATE urls SET url=? WHERE id=?");

            ps.setString(1, url);
            ps.setInt(2, id);

            ps.executeUpdate();

            ps.close();
            System.out.println("URL editada de forma exitosa");

        } catch (Exception ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void guardarArticulos(String titulo, String fecha, String contenido) {

        try {

            ConectarMySQL.getConectarMySQL("127.0.0.1", "atenin", "root", "");

            PreparedStatement ps = ConectarMySQL.getConexion().prepareStatement("INSERT into articulos values(null,?,?,?)");

            ps.setString(1, titulo);
            ps.setString(2, fecha);
            ps.setString(3, contenido);
            ps.execute();
            ps.close();

            System.out.println("Articulo guardado de forma exitosa");
        } catch (Exception ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int buscarPalabraTitulo(String cadena, int valorAsignado) {

        try {
            ConectarMySQL.getConectarMySQL("127.0.0.1", "atenin", "root", "");

            PreparedStatement ps = ConectarMySQL.getConexion().prepareStatement("SELECT COUNT(titulo) FROM articulos WHERE titulo LIKE '%" + cadena + "%'");

            ResultSet rs = ps.executeQuery();

            rs.next();

            int valor = rs.getInt(1);

            ps.close();

            valor *= valorAsignado;

            return valor;

        } catch (Exception ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

        //SELECT COUNT(contenido) FROM articulos WHERE contenido LIKE '%"cadena"%'
    }

    public int buscarPalabraContenido(String cadena, int valorAsignado) {

        try {
            ConectarMySQL.getConectarMySQL("127.0.0.1", "atenin", "root", "");

            PreparedStatement ps = ConectarMySQL.getConexion().prepareStatement("SELECT COUNT(contenido) FROM articulos WHERE contenido LIKE '%" + cadena + "%'");

            ResultSet rs = ps.executeQuery();

            rs.next();
            
            int valor = rs.getInt(1);
            
            ps.close();

            valor *= valorAsignado;

            return valor;
            
        } catch (Exception ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

}
