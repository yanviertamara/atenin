package edu.cecar.vista;

import edu.cecar.controlador.BaseDatos;
import edu.cecar.controlador.Scraping;
import edu.cecar.controlador.ValidarUrl;

import edu.cecar.modelo.Tendencia;
import edu.cecar.modelo.Url;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal
 *
 */
public class Principal {

    public static void main(String[] args) {
        ValidarUrl vu = new ValidarUrl();
        BaseDatos bd = new BaseDatos();

        int opcion;
        Tendencia tendencia = new Tendencia();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            boolean seguir = false;

            System.out.println("Sistema ATENIN");
            System.out.println("");
            System.out.println("1. Añadir URLs");
            System.out.println("2. Editar URLs");
            System.out.println("3. Eliminar URLs");
            System.out.println("4. Mostrar URLs");
            System.out.println("5. Scraping");
            System.out.println("6. Analisis de tendencias");
            System.out.println("0. Salir");
            System.out.println("Digite el numero de la opcion a seleccionar");
            opcion = sc.nextInt();
            System.out.println("");

            if (opcion < 0 || opcion > 6) {
                System.out.println("Digite una opcion valida");
                System.out.println("");
                System.out.println("Presione Enter para volver al menu");
                sc.nextLine();
                sc.nextLine();
            }
            //Añadir URL
            if (opcion == 1) {
                boolean flag = true;
                int op;
                List<Url> urls = bd.consultarUrl();
                while (!seguir) {

                    String baseUrl = "";
                    System.out.println("Digite una URL de un medio informativo");
                    sc.nextLine();
                    baseUrl = sc.nextLine();

                    int codigo = vu.validarUrl(baseUrl);
                    if (codigo == 200) {
                        for (Url url : urls) {
                            String comparador = url.getUrl();
                            if (comparador.equals(baseUrl)) {
                                flag = false;
                                break;
                            }
                        }

                        if (flag) {
                            bd.guardarUrl(baseUrl);
                        } else {
                            System.out.println("URL ya se encuentra registrada en el sistema");
                        }

                    }
                    System.out.println("");
                    System.out.println("Desea digitar otra URL?");
                    System.out.println("(1) = SI   (2) = Volver al menu");

                    op = sc.nextInt();

                    if (op == 2) {
                        seguir = true;
                    }
                    if (op < 1 || op > 2) {
                        System.out.println("Digite una opcion valida");
                    }

                }

            }
            //Editar URL
            if (opcion == 2) {
                List<Url> urls = bd.consultarUrl();
                int id = 0;
                String u = "";
                if (!urls.isEmpty()) {
                    System.out.println("ID  URL");
                    for (Url url : urls) {
                        System.out.print(url.getId() + "  ");
                        System.out.println(url.getUrl());

                    }
                    System.out.println("Digite el id del URL a editar");
                    id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Digite el nuevo URL");
                    u = sc.nextLine();

                    bd.editarUrl(u, id);
                    sc.nextLine();
                    System.out.println("Presione Enter para volver al menu");
                    sc.nextLine();

                } else {
                    System.out.println("No se han insertado datos");
                    System.out.println("Presione Enter para volver al menu");
                    sc.nextLine();
                    sc.nextLine();
                }

            }
            //Eliminar URL
            if (opcion == 3) {
                List<Url> urls = bd.consultarUrl();
                int id = 0;

                if (!urls.isEmpty()) {
                    System.out.println("ID  URL");
                    for (Url url : urls) {
                        System.out.print(url.getId() + "  ");
                        System.out.println(url.getUrl());

                    }
                    System.out.println("Digite el id del URL a eliminar");
                    id = sc.nextInt();
                    sc.nextLine();

                    bd.eliminarUrl(id);
                    sc.nextLine();
                    System.out.println("Presione Enter para volver al menu");
                    sc.nextLine();

                } else {
                    System.out.println("No se han insertado datos");
                    System.out.println("Presione Enter para volver al menu");
                    sc.nextLine();
                    sc.nextLine();
                }

            }
            //Ver URL
            if (opcion == 4) {

                List<Url> urls = bd.consultarUrl();

                if (!urls.isEmpty()) {
                    System.out.println("ID  URL");
                    for (Url url : urls) {
                        System.out.print(url.getId() + "  ");
                        System.out.println(url.getUrl());

                    }

                    System.out.println("Presione Enter para volver al menu");
                    sc.nextLine();
                    sc.nextLine();

                } else {
                    System.out.println("No se han insertado datos");
                    System.out.println("Presione Enter para volver al menu");
                    sc.nextLine();
                    sc.nextLine();
                }

            }
            //Scraping
            if (opcion == 5) {

                Scraping scrap = new Scraping();

                List<String> urlArticulos = new ArrayList();

                List<Url> urls = bd.consultarUrl();
                if (!urls.isEmpty()) {
                    for (Url u : urls) {
                        String url = u.getUrl();
                        urlArticulos = scrap.obtenerUrl(url);
                    }

                    System.out.println("Scraping iniciado");

                    for (String url : urlArticulos) {

                        scrap.scraping(url);

                    }

                    System.out.println("Scraping finalizado");

                } else {
                    System.out.println("No hay enlaces para scraping");
                }

                System.out.println("Presione Enter para volver al menu");
                sc.nextLine();
                sc.nextLine();

            }
            //Analisis de tendencias
            if (opcion == 6) {
                
                while (!seguir) {
                    
                    int pesoTitulo = tendencia.getPesoTitulo();
                    int pesoContenido = tendencia.getPesoContenido();

                    if (tendencia.getPesoTitulo() == 0 && tendencia.getPesoContenido() == 0) {
                        do {
                            System.out.println("Digite el peso del titulo: ");
                            sc.nextLine();
                            pesoTitulo = sc.nextInt();
                            tendencia.setPesoTitulo(pesoTitulo);

                            System.out.println("Digite el peso del contenido");
                            sc.nextLine();
                            pesoContenido = sc.nextInt();
                            tendencia.setPesoContenido(pesoContenido);

                            if (pesoTitulo < pesoContenido) {
                                System.out.println("El peso del titulo debe ser mayor");
                                System.out.println("al peso del contenido");
                            }
                        } while (pesoTitulo < pesoContenido || pesoTitulo == pesoContenido);
                    }

                    if (tendencia.getPalabraTendencia() != null) {
                        System.out.println("Palabra tendencia: " + tendencia.getPalabraTendencia());
                        System.out.println("Valor Tendencia " + tendencia.getValorTendencia());
                        System.out.println("");

                    }

                    System.out.println("Digite una palabra");
                    sc.nextLine();
                    String palabra = sc.nextLine();

                    int valorTitulo = bd.buscarPalabraTitulo(palabra, pesoTitulo);
                    int valorContenido = bd.buscarPalabraContenido(palabra, pesoContenido);

                    int resultado = valorTitulo + valorContenido;
                    System.out.println("resultado: "+resultado);
                    int op;

                    if (tendencia.getValorTendencia() == 0) {

                        tendencia.setPalabraTendencia(palabra);
                        tendencia.setValorTendencia(resultado);
                        System.out.println("");
                        System.out.println("Palabra Tendencia " + tendencia.getPalabraTendencia());
                        System.out.println("Valor Tendencia " + tendencia.getValorTendencia());
                        System.out.println("");
                        System.out.println("Desea digitar otra palabra?");
                        System.out.println("(1) = SI   (2) = Volver al menu");

                        op = sc.nextInt();

                        if (op == 2) {
                            seguir = true;
                        }
                        if (op < 1 || op > 2) {
                            System.out.println("Digite una opcion valida");
                        }

                    } else if (resultado > tendencia.getValorTendencia()) {

                        tendencia.setPalabraTendencia(palabra);
                        tendencia.setValorTendencia(resultado);
                        System.out.println("");
                        System.out.println("Palabra Tendencia " + tendencia.getPalabraTendencia());
                        System.out.println("Valor Tendencia " + tendencia.getValorTendencia());
                        System.out.println("");
                        System.out.println("Desea digitar otra palabra?");
                        System.out.println("(1) = SI   (2) = Volver al menu");

                        op = sc.nextInt();

                        if (op == 2) {
                            seguir = true;
                        }
                        if (op < 1 || op > 2) {
                            System.out.println("Digite una opcion valida");
                        }

                    } else if (resultado < tendencia.getValorTendencia()) {
                        System.out.println("");
                        System.out.println("Palabra Tendencia " + tendencia.getPalabraTendencia());
                        System.out.println("Valor Tendencia " + tendencia.getValorTendencia());
                        System.out.println("");
                        System.out.println("Desea digitar otra palabra?");
                        System.out.println("(1) = SI   (2) = Volver al menu");

                        op = sc.nextInt();

                        if (op == 2) {
                            seguir = true;
                        }
                        if (op < 1 || op > 2) {
                            System.out.println("Digite una opcion valida");
                        }
                    }
                }
            }

            if (opcion == 0) {
                salir = true;
            }

        }
    }
}
