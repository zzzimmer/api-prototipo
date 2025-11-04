package org.zzzimmer.apiprototipo.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
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
@Entity(name = "Evento")
@Table(name = "eventos")
@EqualsAndHashCode(of = "id")
public class Evento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private LocalDate data;
    private LocalTime horario;
    private Boolean ativo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id") // Nome da coluna da chave estrangeira na tabela de eventos
    private Usuario responsavel;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Convite> conviteList = new ArrayList<>();

    public Evento(@Valid CriarEventoDTO dados) {
        this.name = dados.name();
        this.data = dados.data();
        this.horario = dados.horario();
        this.ativo = true;
    }
}
