package com.example.sms.Controllers;

import com.example.sms.ExcelService;
import com.example.sms.dto.Datos;
import com.example.sms.service.SmsService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController

public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @Autowired
    private SmsService SmsService;

    @GetMapping("/leer-excel")
    //este ya realiza la lectura del excel y adjunta las variables al json
    public void leerExcel1() throws IOException {
        String rutaArchivo= "C:\\Users\\isabel_topos\\Documents\\TOPOSTECHNOLOGY\\proyecto sms\\sms\\src\\main\\resources\\static\\prueba.xlsx";
        excelService.leerArchivoExcel(rutaArchivo);
       // System.out.println("entro aqui");
    }
    //envia un unico mensaje y un texto fijo
    @PostMapping("/enviarsmsprueba")
    public String  enviarMensaje() throws Exception {
        Datos datos = new Datos();
        datos.setFrom("+12136478050");
        datos.setTo("+15617026704");
        datos.setText("Hola, este es un mensaje de prueba desde Spring Boot");
        String respuesta = SmsService.enviarSms(datos);
        return respuesta;
    }

}

