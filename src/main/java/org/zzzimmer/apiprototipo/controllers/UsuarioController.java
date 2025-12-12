package org.zzzimmer.apiprototipo.controllers;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.zzzimmer.apiprototipo.dto.*;
import org.zzzimmer.apiprototipo.model.Evento;
import org.zzzimmer.apiprototipo.model.Usuario;
import org.zzzimmer.apiprototipo.repository.EventoRepository;
import org.zzzimmer.apiprototipo.repository.UsuarioRepository;
import org.zzzimmer.apiprototipo.service.EventoService;
import org.zzzimmer.apiprototipo.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.GET
        , RequestMethod.PUT, RequestMethod.POST})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping("/criar-evento")
    public ResponseEntity criarEvento(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid CriarEventoDTO dados, UriComponentsBuilder uriBuilder){

        var evento = new Evento(dados);
        evento.setUsuario(usuario);

        eventoRepository.save(evento);

        var uri = uriBuilder.path("/usuario/eventos/{id}").buildAndExpand(evento.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhesEventoDTO(evento));
    }

    @GetMapping ("/meus-eventos")
    public ResponseEntity<List<DetalhesEventoDTO>> eventosCriadosPorUser(@AuthenticationPrincipal Usuario usuario){
        List<Evento> listaEventos = eventoRepository.findAllByUsuarioId(usuario.getId());

        //todo implementar otimização de projeção de DTO aqui para treinar/conhecer
        List<DetalhesEventoDTO> dtos = listaEventos.stream()
                .map(DetalhesEventoDTO::new).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/eventos/{idEvento}")
    public ResponseEntity<DetalhesEventoDTO> eventoEspecifico(@PathVariable Long idEvento, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.of(
            eventoRepository.findByIdAndUsuarioId(idEvento, usuario.getId())
                    .map(DetalhesEventoDTO::new)
            );
    }


    @PutMapping("/eventos/{eventoAtualId}/convidar")
    public ResponseEntity<EmailDTO> convidarEmail (@PathVariable Long eventoAtualId, @RequestBody @Valid EmailDTO dto){
        String email = dto.email();
        eventoService.adicionarConvidado(eventoAtualId,email);
        return ResponseEntity.ok(dto);
    }

}
