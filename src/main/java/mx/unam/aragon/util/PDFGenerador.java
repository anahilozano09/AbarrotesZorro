package mx.unam.aragon.util;

import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Paragraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
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

        documento.add(new Paragraph("Factura de Compra"));
        documento.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new int[]{2, 4, 2, 2, 2, 2});

        agregarEncabezados(tabla);

        double total = 0;

        for (ProductoCompra prod : productos) {
            try {
                String rutaFisica = imageDirectory + "/" + prod.nombreArchivoImagen;
                Image img = Image.getInstance(rutaFisica);
                img.scaleToFit(50, 50);
                tabla.addCell(new PdfPCell(img, false));
            } catch (Exception e) {
                tabla.addCell("Sin imagen");
            }

            tabla.addCell(prod.nombre);
            tabla.addCell(String.valueOf(prod.cantidad));
            tabla.addCell(String.format("$%.2f", prod.precio));
            tabla.addCell(String.format("$%.2f", prod.getSubtotal()));
            total += prod.getSubtotal();
        }

        PdfPCell celdaTotal = new PdfPCell(new Phrase("Total"));
        celdaTotal.setColspan(5);
        celdaTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(celdaTotal);
        tabla.addCell(String.format("$%.2f", total));

        documento.add(tabla);
        documento.close();
    }

    private void agregarEncabezados(PdfPTable tabla) {
        tabla.addCell("Imagen");
        tabla.addCell("Nombre");
        tabla.addCell("Cantidad");
        tabla.addCell("Precio Unitario");
        tabla.addCell("Subtotal");
        tabla.addCell("Total");
    }
}

