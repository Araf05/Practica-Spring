package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporada;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=8d725995";
    private ConvierteDatos conversor = new ConvierteDatos();

    // Busca los datos generales de las series
    public void muestraElMenu() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar: ");
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+ nombreSerie.replace(" ","+")+API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        // Busca los datos de todas las temporadas
        List<DatosTemporada> temporada = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season="+ i + API_KEY);
            var datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporada.add(datosTemporada);
        }
        temporada.forEach(System.out::println);
    }
}
