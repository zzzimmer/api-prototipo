package org.zzzimmer.apiprototipo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Convite")
@Table(name = "convites")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Convite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long codigoAutenticador;
    private String emailConvidado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;
}