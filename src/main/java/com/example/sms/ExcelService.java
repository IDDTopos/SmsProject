package com.example.sms;

import com.example.sms.dto.Datos;
import com.example.sms.service.SmsService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service
public class ExcelService {
    @Autowired
    private SmsService SmsService;
     public void leerArchivoExcel(String rutaArchivo) throws IOException {
         System.out.println("se mando a llamar leerArchivoExcel");
         try {
             // Paso 1: Cargar el archivo Excel
             FileInputStream archivo = new FileInputStream(rutaArchivo);
             // Paso 2: Crear una instancia de Workbook (para archivos .xlsx)
             Workbook workbook = new XSSFWorkbook(archivo);
             // Paso 3: Obtener la hoja de Excel que deseas leer
             Sheet hoja = workbook.getSheetAt(0); // Hoja en la posición 0 (primera hoja)
             // Variables para guardar datos
             Datos datos= new Datos();

             // Paso 4: Iterar a través de las filas y columnas para extraer datos
             for (Row fila : hoja) {
                 Cell from = fila.getCell(0); //from
                 Cell to = fila.getCell(1); //to
                 Cell text = fila.getCell(2); //text

                 // Verificar si las celdas no son nulas
                 if (from != null && to != null&& text != null) {
                     // Leer datos de las celdas
                     datos.setFrom(from.getStringCellValue());
                     datos.setTo(to.getStringCellValue());
                     datos.setText(text.getStringCellValue());
                     // Hacer algo con los datos, por ejemplo, imprimirlos
                     System.out.println("from: " + datos.getFrom());
                     System.out.println("to : " + datos.getTo());
                     System.out.println("text: " + datos.getText());
                     SmsService.enviarSms(datos);
                 }

             }

             // Paso 5: Cerrar el archivo
             archivo.close();
         } catch (IOException e) {
             e.printStackTrace();
         }

     }


}
