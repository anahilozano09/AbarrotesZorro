package mx.unam.aragon.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.Color; // Importación corregida
import java.io.OutputStream;
import java.util.List;

@Component
public class PDFGenerador {

    @Value("${external.image.dir}")
    private String imageDirectory;

    public static class ProductoCompra {
        public String nombre;
        public int cantidad;
        public double precio;
        public String nombreArchivoImagen;

        public ProductoCompra(String nombre, int cantidad, double precio, String nombreArchivoImagen) {
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.precio = precio;
            this.nombreArchivoImagen = nombreArchivoImagen;
        }

        public double getSubtotal() {
            return cantidad * precio;
        }
    }

    public void generarFactura(List<ProductoCompra> productos, OutputStream outputStream) throws Exception {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, outputStream);
        documento.open();

        // Título
        Paragraph titulo = new Paragraph("Factura de Compra",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD));
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20f);
        documento.add(titulo);

        // Tabla de productos (5 columnas sin "Total")
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{1.5f, 4f, 1.5f, 2f, 2f});

        agregarEncabezados(tabla);

        double total = 0;

        for (ProductoCompra prod : productos) {
            // Imagen (o placeholder)
            PdfPCell celdaImagen = new PdfPCell();
            try {
                String rutaFisica = imageDirectory + "/" + prod.nombreArchivoImagen;
                Image img = Image.getInstance(rutaFisica);
                img.scaleToFit(60, 60);
                img.setAlignment(Image.MIDDLE);
                celdaImagen.addElement(img);
            } catch (Exception e) {
                celdaImagen.addElement(new Phrase("Sin imagen",
                        FontFactory.getFont(FontFactory.HELVETICA, 10)));
            }
            celdaImagen.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaImagen.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabla.addCell(celdaImagen);

            // Nombre
            tabla.addCell(crearCelda(prod.nombre, Element.ALIGN_LEFT));

            // Cantidad
            tabla.addCell(crearCelda(String.valueOf(prod.cantidad), Element.ALIGN_CENTER));

            // Precio Unitario
            tabla.addCell(crearCelda(String.format("$%.2f", prod.precio), Element.ALIGN_RIGHT));

            // Subtotal
            String subtotal = String.format("$%.2f", prod.getSubtotal());
            tabla.addCell(crearCelda(subtotal, Element.ALIGN_RIGHT));

            total += prod.getSubtotal();
        }

        documento.add(tabla);

        // Agregar espacio antes del total
        documento.add(new Paragraph(" "));

        // Fila para el total general
        PdfPTable tablaTotal = new PdfPTable(2);
        tablaTotal.setWidthPercentage(50);
        tablaTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaTotal.setWidths(new float[]{3f, 2f});

        // Celda etiqueta "TOTAL"
        PdfPCell celdaEtiqueta = new PdfPCell(new Phrase("TOTAL:",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD)));
        celdaEtiqueta.setBorder(Rectangle.NO_BORDER);
        celdaEtiqueta.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaTotal.addCell(celdaEtiqueta);

        // Celda valor total
        PdfPCell celdaTotal = new PdfPCell(new Phrase(String.format("$%.2f", total),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD)));
        celdaTotal.setBorder(Rectangle.NO_BORDER);
        celdaTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaTotal.addCell(celdaTotal);

        documento.add(tablaTotal);
        documento.close();
    }

    private void agregarEncabezados(PdfPTable tabla) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);

        tabla.addCell(crearCeldaEncabezado("Imagen"));
        tabla.addCell(crearCeldaEncabezado("Producto"));
        tabla.addCell(crearCeldaEncabezado("Cantidad"));
        tabla.addCell(crearCeldaEncabezado("Precio Unitario"));
        tabla.addCell(crearCeldaEncabezado("Subtotal"));
    }

    private PdfPCell crearCeldaEncabezado(String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setBackgroundColor(new Color(230, 230, 230)); // Usando java.awt.Color
        celda.setPadding(5f);
        return celda;
    }

    private PdfPCell crearCelda(String texto, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto));
        celda.setHorizontalAlignment(alineacion);
        celda.setPadding(5f);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return celda;
    }
}