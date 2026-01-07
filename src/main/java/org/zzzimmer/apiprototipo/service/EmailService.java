package org.zzzimmer.apiprototipo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.zzzimmer.apiprototipo.model.Evento;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private final String remetente = System.getenv("GMAIL");

    public void enviarEmailConvite(String destinatario, Evento evento) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetente);
            message.setTo(destinatario);
            message.setSubject("Convite para: " + evento.getName());

            // Corpo do e-mail simples
            String texto = String.format(
                    "Olá! Você foi convidado por: %s para o evento %s " +
                            "que acontecera no dia %s " +
                            "às %s horas." +
                            " O local do evento é %s." +
                            "Te esperamos lá :)",
                    evento.getUsuario().getNomeCompleto(),
                    evento.getName(),
                    evento.getData(),
                    evento.getHorario(),
                    evento.getLocal());

            message.setText(texto);
            mailSender.send(message);

            System.out.println("E-mail enviado com sucesso para: " + destinatario);

        } catch (Exception e) {
            // log do erro
            System.err.println("Falha ao enviar e-mail: " + e.getMessage());
            e.printStackTrace();
        }
    }
}