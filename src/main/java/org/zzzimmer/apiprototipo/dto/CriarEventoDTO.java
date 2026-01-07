package org.zzzimmer.apiprototipo.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.zzzimmer.apiprototipo.model.Convite;
import org.zzzimmer.apiprototipo.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CriarEventoDTO(
        @NotBlank(message = "Nome do evento é obrigatório")
        @Size(max = 200, message = "Muitos caracteres.")
        String name,
        @NotNull(message = "Data é obrigatório")
//        @Size(max = 200, message = "Muitos caracteres.")
        @FutureOrPresent
        LocalDate data,
        @NotNull(message = "Horario é obrigatório")
//        @Size(max = 10, message = "Muitos caracteres.")
        LocalTime horario,
        @NotNull(message = "Local é obrigatório")
        @Size(max = 200, message = "Muitos caracteres.")
        String local,
//        Boolean ativo,
//        Usuario responsavel,
        //todo esse atributo abaixo não precisa ir
        List<String> conviteList
) {
}
