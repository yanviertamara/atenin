package edu.cecar.controlador;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase que controla el scraping
 *
 */
public class Scraping {

    List<String> urls = new ArrayList<>();

    public List<String> obtenerUrl(String url) {//Metodo para extraer los enlaces a visitar de la pagina raiz

        try {

            Document doc;
            //for (Url u : enlaces) {
            //String url = u.getUrl();
            doc = Jsoup.connect(url).get();
            Elements links = doc.select("a");
            int i=0;
            for (Element enlace : links) {
                i++;
                String nuevoEnlace = enlace.attr("href");

                if (!nuevoEnlace.contains("eltiempo")
                        && !nuevoEnlace.contains("www.")
                        && nuevoEnlace.contains("/colombia")
                        || nuevoEnlace.contains("/bogota")
                        || nuevoEnlace.contains("/justicia")
                        || nuevoEnlace.contains("/politica")
                        || nuevoEnlace.contains("/deportes")
                        || nuevoEnlace.contains("/cultura")) {
                    nuevoEnlace = "https://www.eltiempo.com" + nuevoEnlace;
                }

                if (!nuevoEnlace.contains("?share=")) {
                    if (!nuevoEnlace.contains("#")) {
                        if (nuevoEnlace.startsWith(url) //Condicional para que los enlaces inicien solo con la pagina raiz
                                && !urls.contains(nuevoEnlace)
                                && i<70) {//Para que no repita enlaces

                            urls.add(nuevoEnlace);

                            System.out.println("Enlace obtenido " + nuevoEnlace);

                            obtenerUrl(nuevoEnlace);

                        }
                    }
                }
            }
            // }
            
            return urls;
        } catch (IOException ex) {
            
        }

        return null;
    }

    
    public void scraping(String url) {//Metodo que scrapea una url y la inserta en la base de datos
        BaseDatos bd = new BaseDatos();

        try {

            String fecha = "";

            Document doc = Jsoup.connect(url).get();
            System.out.println("Scrapeando " + url);

            String titulo = "";

            if (doc.select("h1.entry-title.penci-entry-title.penci-title-center").isEmpty()) {

                Elements h1 = doc.select("h1.titulo");

                titulo = h1.get(0).text();
            } else if (doc.select("h1.titulo").isEmpty()) {

                Elements h1 = doc.select("h1.entry-title.penci-entry-title.penci-title-center");

                titulo = h1.get(0).text();

            }

            Elements fechaPublicacion = null;
            if (doc.select("div.entry-meta.penci-entry-meta").isEmpty()) {

                fechaPublicacion = doc.select("span.fecha");

                String fe = "";

                fe = fechaPublicacion.get(0).text();

                Date date = new SimpleDateFormat("dd 'de' MMMMM yyyy").parse(fe);

                fecha = new SimpleDateFormat("MM/dd/yyyy").format(date);

            } else if (doc.select("span.fecha").isEmpty()) {

                fechaPublicacion = doc.select("time");

                String fe = fechaPublicacion.get(0).attr("datetime");

                Date date = new SimpleDateFormat("yyyy-MM-dd'T'").parse(fe);

                fecha = new SimpleDateFormat("MM/dd/yyyy").format(date);

            }

            Elements contenido = null;
            Elements con = null;

            if (doc.select("div.articulo-contenido").isEmpty()) {

                contenido = doc.select("div.penci-entry-content.entry-content");
                
                con = contenido.select("p");

            } else if (doc.select("div.penci-entry-content.entry-content").isEmpty()) {

                contenido = doc.select("div.articulo-contenido");
                
                con = contenido.select("p.contenido");
            }

             

            StringBuilder sb = new StringBuilder();

            for (Element e : con) {

                sb.append(e.text());
                sb.append(" ");

            }

            String content = sb.toString();
            
            if (!titulo.equals("") && !content.equals("")) {
                bd.guardarArticulos(titulo, fecha, content);
            }

        } catch (IOException | ParseException | IndexOutOfBoundsException ex) {
           // ex.printStackTrace();
        }

        

    }

}
