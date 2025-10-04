package org.zzzimmer.apiprototipo.dto;

import org.zzzimmer.apiprototipo.model.Evento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public record DetalhesEventoDTO(
        Long id,
        String name,
        LocalDate data,
        LocalTime horario,
        Boolean ativo,
        DetalhesUsuarioDTO responsavel,
        List<String> convidados // lista dos e-mails ao buscar detalhes do evento
) {

    public DetalhesEventoDTO(Evento evento){
        this(
                evento.getId(),
                evento.getName(),
                evento.getData(),
                evento.getHorario(),
                evento.getAtivo(),
                new DetalhesUsuarioDTO(evento.getResponsavel()),
                evento.getConviteList().stream().map(convite -> convite.getEmailConvidado())
                        .collect(Collectors.toList())
        );
    }
}
