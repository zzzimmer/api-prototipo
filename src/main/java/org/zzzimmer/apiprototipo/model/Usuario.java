package org.zzzimmer.apiprototipo.model;

import jakarta.persistence.*;
import lombok.*;
import org.zzzimmer.apiprototipo.dto.CadastroUsuarioDTO;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nomeCompleto;
    private String email;
    private String numeroCelular;
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evento> eventoList = new ArrayList<>();

    public Usuario(CadastroUsuarioDTO dados){
        this.nomeCompleto = dados.nomeCompleto();
        this.email = dados.email();
        this.numeroCelular = dados.numeroCelular();
    }


    public void criarEvento(){
        Evento eventoA = new Evento();
        this.eventoList.add(eventoA);
        eventoA.setAtivo(true);
        eventoA.setUsuario(this);
        eventoA.setName("Primavera Soundss");
    }

}
