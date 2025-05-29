package mx.unam.aragon.util;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String remitente;

    @Value("${spring.mail.password}")
    private String password;

    public void enviarCorreoConAdjunto(String to, String subject, String body, File attachment) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Crear partes del mensaje
            MimeBodyPart textoPart = new MimeBodyPart();
            textoPart.setText(body);

            MimeBodyPart adjuntoPart = new MimeBodyPart();
            adjuntoPart.attachFile(attachment);
            adjuntoPart.setFileName(attachment.getName());

            // Combinar partes
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textoPart);
            multipart.addBodyPart(adjuntoPart);

            message.setContent(multipart);
            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo con adjunto", e);
        }
    }

    public void enviarCorreoConPDF(String destinatario, String asunto, String cuerpo,
                                   ByteArrayOutputStream pdfStream, String nombreArchivo) {
        try {
            InputStream inputStream = new ByteArrayInputStream(pdfStream.toByteArray());

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);

            // Crear partes del mensaje
            MimeBodyPart textoPart = new MimeBodyPart();
            textoPart.setText(cuerpo);

            MimeBodyPart pdfPart = new MimeBodyPart();
            pdfPart.setDataHandler(new DataHandler(new InputStreamDataSource(inputStream, "application/pdf")));
            pdfPart.setFileName(nombreArchivo);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textoPart);
            multipart.addBodyPart(pdfPart);

            message.setContent(multipart);
            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo con PDF adjunto", e);
        }
    }

    private static class InputStreamDataSource implements DataSource {
        private final InputStream inputStream;
        private final String contentType;

        public InputStreamDataSource(InputStream inputStream, String contentType) {
            this.inputStream = inputStream;
            this.contentType = contentType;
        }

        @Override
        public InputStream getInputStream() {
            return inputStream;
        }

        @Override
        public OutputStream getOutputStream() {
            throw new UnsupportedOperationException("No se admite escritura");
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public String getName() {
            return "InputStreamDataSource";
        }
    }
}