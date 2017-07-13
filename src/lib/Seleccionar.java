package lib;

import com.csvreader.CsvReader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by edzzn on 7/12/17.
 */
public class Seleccionar {
    String nombreTabla;
    String nombreCampo;
    String valorCampo;
    String ordenamiento;
    int numElementosMostrar;

    void operar(Object[] comando){
        /*
        * [0] = nombre_tabla
        * [1] = nombre_campo
        * [2] = valor campo
        * [3] = Ordenado asc/dsc
        * [4] = numElementosMostrar
        * [5] = ubicacion del campo en la tabla
        *
        * */
        String nombreTabla = (String) comando[0];
        String nombreCampo = (String) comando[1];
        String valorCampo = (String) comando[2];
        int posicion = (int) comando[5];

        try {

            //Numero de filas y columnas
            File path = new File ("filesBD\\" + nombreTabla + ".bd");
            CsvReader reader = new CsvReader("filesBD\\" + nombreTabla + ".bd");

            if ( comando[2] != null){
                ordenamiento = (String) comando[3];
                // Ordenamos
                /* LLAMAR AL METODO DE ORDENAMIENTO AQUI */
            }



            int filas = 0;
            int columnas = 0;
            columnas = reader.getColumnCount();

            if ( comando[4] == null){
                // si NO se inserto el numero de elementos a mostrar
                // Optenemos el numero de filas a mostrar
                while(reader.readRecord()){
                    if(reader.get(posicion).equals(valorCampo)){
                        filas++;
                    }
                }
            } else{
                numElementosMostrar = (int) comando[4];
                filas = numElementosMostrar;
            }
            reader.close();

            //Conseguir las cabeceras
            reader = new CsvReader("filesBD\\" + nombreTabla + ".bd");
            reader.readRecord();
            String matriz[][] = new String[filas][columnas];
            String cabecera[] = new String[columnas];
            for (int i = 0; i<columnas; i++){
                cabecera[i] = reader.get(i);
            }

            //Conseguir el cuerpo de la tabla
            int i = 0;
            while(reader.readRecord()){
                if(reader.get(posicion).equals(valorCampo)){
                    for (int j = 0; j<columnas; j++)
                        matriz[i][j] = reader.get(j);
                    i++;
                }
            }
            reader.close();


            //Creacion de la tabla
            JTable table = new JTable(matriz, cabecera);
            table.setPreferredScrollableViewportSize(new Dimension(filas, columnas));

        } catch (FileNotFoundException ex) {
            throw new Error("Lo sentimos, algo salio mal con los archivos internos");
        }
        catch (IOException ex) {
            throw new Error("Error inesperado, algo salio mal con los archivos internos");
        }



    }
}