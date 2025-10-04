package org.zzzimmer.apiprototipo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.zzzimmer.apiprototipo.model.Usuario;


public record CadastroUsuarioDTO(
        @NotBlank
        String nomeCompleto,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String numeroCelular
) {

        public CadastroUsuarioDTO(Usuario usuario){
                this(usuario.getNomeCompleto(), usuario.getEmail(), usuario.getNumeroCelular());
        }
}
