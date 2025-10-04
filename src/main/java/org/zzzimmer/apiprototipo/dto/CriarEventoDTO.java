package org.zzzimmer.apiprototipo.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.zzzimmer.apiprototipo.model.Convite;
import org.zzzimmer.apiprototipo.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CriarEventoDTO(
        @NotBlank
        String name,
        @NotNull
        @FutureOrPresent
        LocalDate data,
        @NotNull
        LocalTime horario,
//        Boolean ativo,
//        Usuario responsavel,
        List<String> conviteList
) {
}
