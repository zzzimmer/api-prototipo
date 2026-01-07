package org.zzzimmer.apiprototipo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
//import org.zzzimmer.apiprototipo.dto.CadastroUsuarioDTO;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
@Entity
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200, message = "Muitos caracteres.")
    private String nomeCompleto;
    @NotBlank(message = "Email é obrigatório")
    @Size(max = 200, message = "Muitos caracteres.")
    @Email
    private String email;
    private String numeroCelular;
    @NotBlank(message = "Senha é obrigatória")
//    @Size(max = 40, message = "Muitos caracteres.")
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evento> eventoList = new ArrayList<>();

}
