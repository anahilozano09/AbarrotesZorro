package mx.unam.aragon.util;

import mx.unam.aragon.model.entity.CantidadProductoAlmacenEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class AlmacenExcel {
    public ByteArrayOutputStream exportarExcel(List<CantidadProductoAlmacenEntity> productoAlmacen)
    throws IOException {
        try(Workbook workbook = new XSSFWorkbook()) {
            //creacion del libro de Excel
            Sheet sheet = workbook.createSheet("Inventario de Productos");

            //Estilo del encabezado de la tabla
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            //Creacion o declaracion de los headers de la tabla
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Producto", "Precio Unitario", "Cantidad", "Tipo Producto"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            //Insercion de datos en la tabla
            int rowNum = 1;
            for (CantidadProductoAlmacenEntity item : productoAlmacen) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(item.getProducto().getNombre());
                row.createCell(1).setCellValue(item.getProducto().getPrecio().doubleValue());
                row.createCell(2).setCellValue(item.getCantidad());
                row.createCell(3).setCellValue(item.getProducto().getTipoProducto().getTipo());
            }

            //Ajuste automatico de cada una de las columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream;

        } catch (IOException e) {
            throw new IOException("Error al generar el archivo de Excel", e);
        }
    }
}
