package org.zzzimmer.apiprototipo.controllers;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<Page<CadastroUsuarioDTO>> listar(@PageableDefault(size = 10, sort = {"nomeCompleto"}) Pageable paginacao) {

//        var page = usuarioService.findAll(paginacao)
            var page = usuarioRepository.findAll(paginacao).map(CadastroUsuarioDTO::new);

            return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroUsuarioDTO dados, UriComponentsBuilder uriBuilder) {
        var usuario = new Usuario(dados);

        usuarioRepository.save(usuario);

        var uri = uriBuilder.path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }


//    cria evento
    @PostMapping("{idUsuario}/eventos")
    public ResponseEntity criarEvento(@PathVariable Long idUsuario, @RequestBody @Valid CriarEventoDTO dados, UriComponentsBuilder uriBuilder){
        //buscar o responsável pelo evento
        var usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        // criar entidade evento a partir do DTO
        var evento = new Evento(dados);
        evento.setResponsavel(usuario);


        //ja cria os convites a partir de uma lista fornecida. Pode ser assim no front-end, cada evento ter no minimo um convidado
//        if (dados.emailsConvidados() != null && !dados.emailsConvidados().isEmpty()) {
//            dados.emailsConvidados().forEach(email -> {
//                var novoConvite = new Convite();
//                novoConvite.setEmailConvidado(email);
//                novoConvite.setEvento(evento); // Associa o convite ao evento
//                // Aqui você pode gerar e setar o 'codigoAutenticador'
//                evento.getConviteList().add(novoConvite);
//            });
//        }

        eventoRepository.save(evento);

        var uri = uriBuilder.path("/eventos/{id}").buildAndExpand(evento.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhesEventoDTO(evento));
//        return ResponseEntity.created(uri).body(evento); //além de errado trazer o evento, da recursão infinita
    }

    @GetMapping ("{idUsuario}/eventos")
    public ResponseEntity<List<DetalhesEventoDTO>> eventosCriadosPorUser(@PathVariable Long idUsuario){
        List<Evento> listaEventos = eventoRepository.findAllByUsuarioId(idUsuario);

        //todo implementar otimização de projeção de DTO aqui para treinar/conhecer
        List<DetalhesEventoDTO> dtos = listaEventos.stream()
                .map(DetalhesEventoDTO::new).toList();

        return ResponseEntity.ok(dtos);
    }

//    @GetMapping ("/eventos/{idEvento}")
//    public ResponseEntity<DetalhesEventoDTO> eventoEspecifico(@PathVariable Long idEvento){
//
//        Optional<Evento> objevento = eventoRepository.findById(idEvento);
//
////        objevento.map(DetalhesEventoDTO::new);
//
//        DetalhesEventoDTO evento = new DetalhesEventoDTO(objevento);
//
//        return ResponseEntity.ok(evento);
//    }

        @GetMapping("/eventos/{idEvento}")
        public ResponseEntity<DetalhesEventoDTO> eventoEspecifico(@PathVariable Long idEvento) {
        return ResponseEntity.of(
            eventoRepository.findById(idEvento)
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
