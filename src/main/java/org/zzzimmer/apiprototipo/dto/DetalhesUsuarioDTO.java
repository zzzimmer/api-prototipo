package org.zzzimmer.apiprototipo.dto;

import org.zzzimmer.apiprototipo.model.Usuario;

//criado para retornar dentro do evento de maneira contida, mas pode ter outros usos
public record DetalhesUsuarioDTO(
        Long id,
        String nomeCompleto,
        String email
) {
    public DetalhesUsuarioDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNomeCompleto(), usuario.getEmail());
    }
}
