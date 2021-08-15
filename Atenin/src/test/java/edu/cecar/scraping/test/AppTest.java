package edu.cecar.scraping.test;

import java.sql.PreparedStatement;
import edu.cecar.controlador.ConectarMySQL;
import edu.cecar.controlador.Scraping;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 *
 */
public class AppTest {

    @Test
    public void scrapingTestElTiempo() {

        //Se define los datos e informacion esperada
        String tituloEsperado = "'No podemos dejar que haya voces que estigmaticen a la Fuerza Pública'";
        String fechaEsperada = "09/10/2020";
        String contenidoEsperado = "El presidente Iván Duque se pronunció este jueves tras la muerte de Javier Ordóñez, a manos de dos policías que lo sometieron en un caso de abuso policial, y lo sucedido en la noche de este miércoles cuando siete personas murieron y otras cientos resultaron heridas en medio de manifestaciones. (Le puede interesar: Así fueron las horas de caos en Bogotá tras protestas) El caos que se vivió en Bogotá y algunos otros municipios del país este miércoles 9 de septiembre tuvo como origen lo ocurrido en la madrugada, en el barrio Santa Cecilia de Engativá, cuando dos uniformados que aún no han sido identificados, agredieron hasta causarle la muerte al abogado y taxista Javier Ordóñez, en un acto que indignó al país. Varios sectores salieron a pedir reformas de la policía. (Vea también: Congresistas plantean puntos clave que tendría la reforma a la Policía) Frente a lo sucedido con Ordóñez, Duque expresó su solidaridad y aseguró que a través de la Cancillería se está trabajando para facilitar la llegada de familiares del abogado que viven en el exterior. \"Estos hechos se van a investigar con total rigor y prontitud; hoy la Fiscalía General de la Nación ha abierto investigación, se está haciendo la investigación en el cuerpo policial\", manifestó Duque en su intervención. El jefe de Estado aseguró que \"los colombianos nos hemos sentido orgullosos de nuestra Fuerza Pública\" y agregó que como el monopolio de la fuerza recae sobre ella, deben conducirse con transparencia y un estándar alto. \"Esos hechos deben individualizarse para que la investigación se conduzca con éxito, para que haya sanciones que sean entendidas por la ciudadanía. Varias personas perdieron la vida y nos duelen esas seis víctimas, nos duelen esos hechos de violencia, nos duelen los más de 110 policías heridos\", dijo Duque. (También le puede interesar: Madre y hermano de Javier Ordóñez podrán regresar al país) Frente a los hechos ocurridos en la noche de este miércoles, el mandatario rechazó la \"violencia, el vandalismo, las agresiones que se observaron frente a la Fuerza Pública y a muchas de las instalaciones que la Fuerza tiene para proteger a los ciudadanos\". \"La Fuerza Pública de nuestro país ha sido heroica, ha trabajado con excelencia, pero también ha habido momentos en que algunos de sus miembros no guardan el honor del uniforme. No podemos dejar que surjan voces que estigmaticen a nuestra Fuerza Pública. La Policía anualmente adelanta 30 millones de procedimientos\", señaló el jefe de Estado. Duque pidió finalmente celeridad en las investigaciones y resultados prontos para todos los ciudadanos. \"Estas investigaciones tienen que avanzar para conseguir resultados. No podemos aceptar que se estigmatice y se llame asesinos a quienes tienen la obligación de proteger a los ciudadanos\", dijo el mandatario. \"Tenemos que separar las conductas individuales de lo que es el actuar de una institución que les ha servido a los colombianos. Muchos de estos policías están en los cuadrantes, que protegen al vecino, que atienden una emergencia\", agregó Duque. El ministro de la Defensa, Carlos Holmes Trujillo, reiteró esta mañana que se ofrecen hasta 50 millones de pesos por información que permita identificar a las personas involucradas en los homicidios y actos de vandalismo, \"y estamos dispuestos a aumentar la cifra, pero esas muertes no pueden quedar en la impunidad\", aseguró. Señala la Policía, en el informe conocido por este diario, que 77 vehículos resultaron afectados, 37 buses de TransMilenio fueron vandalizados y 8 incinerados. Además de 49 buses del SITP vandalizados y cinco incinerados. (Además lea: ¿Quiénes son las víctimas fatales durante la noche de caos en Bogotá?) POLÍTICA ";



        Scraping scraping = new Scraping();
        String url = "https://www.eltiempo.com/politica/gobierno/presidente-ivan-duque-se-pronuncia-sobre-protestas-y-abuso-policial-537085";
        scraping.scraping(url);

        try {

            ConectarMySQL.getConectarMySQL("127.0.0.1", "atenin", "root", "");

            PreparedStatement consultarArticulo = ConectarMySQL.getConexion().prepareStatement("select titulo,fecha,contenido "
                    + "from articulos "
                    + "order by id desc "
                    + "limit 1");

            ResultSet resultado = consultarArticulo.executeQuery();
            resultado.next();

            String tituloObtenido = resultado.getString(1);
            String fechaObtenida = resultado.getString(2);
            String contenidoObtenido = resultado.getString(3);

            assertEquals("El titulo del articulo no corresponde", tituloEsperado, tituloObtenido);
            assertEquals("El titulo del articulo no corresponde", fechaEsperada, fechaObtenida);
            assertEquals("El contenido del articulo no corresponde", contenidoEsperado, contenidoObtenido);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error de conexion de la bases de datos " + e);

        }
    }

    @Test
    public void scrapingTestLaNacion() {
        
                //Se define los datos e informacion esperada
        String tituloEsperado = "Desarticularon a ‘Los Rumberos’: comercializaban cocaína dentro y fuera del país";
        String fechaEsperada = "09/09/2020";
        String contenidoEsperado = "Una peligrosa banda dedicada a la comercialización en pequeñas y grandes cantidades de sustancias psicoactivas fue desarticulada en el municipio de Pitalito. En total 13 personas fueron capturadas en una redada contra el narcotráfico.  Las investigaciones que iniciaron desde el año 2018, dejó como resultado la captura de 13 integrantes de una estructura delincuencial denominada ‘Los Rumberos’, dedicados al expendio y comercialización de sustancias psicoactivas en municipios como Pitalito, Timaná, San Agustín Neiva, y departamentos como Huila, Cundinamarca, Antioquia, Putumayo y Caquetá. Los delincuentes, fueron detenidos mediantes operativos simultáneos realizados por la Policía en las últimas horas en el casco urbano del Valle de Laboyos, y mediante allanamientos donde lograron desvertebrar esta peligrosa estructura y así efectuar un contundente golpe al tráfico de sustancias ilícitas. Modus operandi De acuerdo a las investigaciones de las autoridades, estos sujetos utilizaban principalmente vehículos de carga tipo camión o particulares con modalidad “caleta”, para así transportar el alucinógeno desde el Putumayo, hasta el municipio de Pitalito, donde era distribuido entre los integrantes de la banda, y así iniciaba la comercialización en diferentes municipios del Huila, como ciudades capitales de los diferentes departamentos, y la exportación a varios países. Como dato adicional, según las evidencias que están en poder de las autoridades, esta estructura delincuencial, hacía uso de mujeres atractivas para el trasporte de la sustancia a países como Ecuador, Chile y Guatemala. Detenidos Los capturados fueron identificados como Cristian Isaías Ruiz Peña ‘Isaías’ de 25 años, Yeison Ovidio Hincapié Peña ‘Pájaro’ de 23 años, Jhon Freddy Galindez Salazar ‘Mocho’ de 55 años, Pedronel Silva Cuéllar ‘Pedro’ de 45 años, Deiver Valderrama Gasca ‘Chapulo’ de 30 años, Darío Vladimir Ferreira Claros ‘Bairon’ de 35 años, Marino Erazo Bravo ‘Marino’ de 54 años, Diego Armando Gutiérrez ‘Diego’ de 25 años, Fernando Cardona Duque ‘Putumayo’ de 37 años, Jhon Freddy Mosquera Flórez ‘Fredy’ de 27 años, Faiber Narváez Vargas ‘Faiber’ de 27 años, Pablo Antonio Pulido Gaviria ‘El Negro’ de 25 años, Diego Sebastián Riveros Parra ‘Fox’ de 24 años; quienes deberán responder ante las autoridades por los delitos de concierto para delinquir en tráfico de estupefacientes o sustancias sicotrópicas y tráfico, fabricación o porte de estupefacientes. Las personas capturadas tenían roles como el transporte, la coordinación, logística de distribución, almacenamiento y surtido para el microtráfico, del alucinógeno que en su mayoría era cosiaca y bazuco. Es de resaltar, que está banda delictiva estaba conformada por 18 personas, de las cuales ya habían sido capturadas y condenadas 5, quedando así desarticulada en su totalidad. ";



        Scraping scraping = new Scraping();
        String url = "https://www.lanacion.com.co/desarticularon-a-los-rumberos-comercializaban-cocaina-dentro-y-fuera-del-pais/";
        scraping.scraping(url);

        try {

            ConectarMySQL.getConectarMySQL("127.0.0.1", "atenin", "root", "");

            PreparedStatement consultarArticulo = ConectarMySQL.getConexion().prepareStatement("select titulo,fecha,contenido "
                    + "from articulos "
                    + "order by id desc "
                    + "limit 1");

            ResultSet resultado = consultarArticulo.executeQuery();
            resultado.next();

            String tituloObtenido = resultado.getString(1);
            String fechaObtenida = resultado.getString(2);
            String contenidoObtenido = resultado.getString(3);

            assertEquals("El titulo del articulo no corresponde", tituloEsperado, tituloObtenido);
            assertEquals("El titulo del articulo no corresponde", fechaEsperada, fechaObtenida);
            assertEquals("El contenido del articulo no corresponde", contenidoEsperado, contenidoObtenido);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error de conexion de la bases de datos " + e);

        }

    }

}
