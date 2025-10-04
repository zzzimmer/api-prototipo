package org.zzzimmer.apiprototipo.service;

import org.springframework.stereotype.Service;
import org.zzzimmer.apiprototipo.model.Usuario;


@Service
public class UsuarioService {

    public Usuario criarUsuario(){
        Usuario usuarioTeste = new Usuario();
        return usuarioTeste;
    }
}
