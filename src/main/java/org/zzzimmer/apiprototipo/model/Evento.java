package org.zzzimmer.apiprototipo.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.zzzimmer.apiprototipo.dto.CriarEventoDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eventos")
@EqualsAndHashCode(of = "id")
public class Evento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Nome do evento é obrigatório")
    @Size(max = 200, message = "Muitos caracteres.")
    private String name;
    @NotNull(message = "Data é obrigatório")
    @Size(max = 200, message = "Muitos caracteres.")
    @FutureOrPresent
    //essa notação imagino que precisa ficar atento devido a forma de funcionamento(nao fui verificar).
    // Se o servidor tiver um fuso diferente do local da aplicação, pode dar um ruim
    private LocalDate data;
    @NotNull(message = "Horario é obrigatório")
    @Size(max = 10, message = "Muitos caracteres.")
    private LocalTime horario;
    @NotNull(message = "Local é obrigatório")
    @Size(max = 200, message = "Muitos caracteres.")
    private String local;
    //todo implementar se um evento esta ativo ou nao. Trabalhar com esse atributo
    private Boolean ativo;
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Convite> conviteList = new ArrayList<>();

    public Evento(@Valid CriarEventoDTO dados) {
        this.name = dados.name();
        this.data = dados.data();
        this.horario = dados.horario();
        this.local = dados.local();
        this.ativo = true;
    }
}
