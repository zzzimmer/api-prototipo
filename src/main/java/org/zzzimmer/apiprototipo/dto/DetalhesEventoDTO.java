package org.zzzimmer.apiprototipo.dto;

import org.zzzimmer.apiprototipo.model.Evento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record DetalhesEventoDTO(
        Long id,
        String name,
        LocalDate data,
        LocalTime horario,
        Boolean ativo,
        String local,
        DetalhesUsuarioDTO usuario,
        List<String> convidados // lista dos e-mails ao buscar detalhes do evento
) {

    public DetalhesEventoDTO(Evento evento){
        this(
                evento.getId(),
                evento.getName(),
                evento.getData(),
                evento.getHorario(),
                evento.getAtivo(),
                evento.getLocal(),
                new DetalhesUsuarioDTO(evento.getUsuario()),
                evento.getConviteList().stream().map(convite -> convite.getEmailConvidado())
                        .collect(Collectors.toList())
        );
    }
}
