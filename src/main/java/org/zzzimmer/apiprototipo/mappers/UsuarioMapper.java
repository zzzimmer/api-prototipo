package org.zzzimmer.apiprototipo.mappers;

import org.mapstruct.Mapper;
import org.zzzimmer.apiprototipo.dto.DetalhesUsuarioDTO;
import org.zzzimmer.apiprototipo.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    DetalhesUsuarioDTO usuarioToUsuarioDTO (Usuario usuario);


}
