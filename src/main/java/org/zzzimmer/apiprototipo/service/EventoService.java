package org.zzzimmer.apiprototipo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzzimmer.apiprototipo.model.Convite;
import org.zzzimmer.apiprototipo.model.Evento;
import org.zzzimmer.apiprototipo.repository.EventoRepository;

import java.util.stream.Collectors;

@Service
public class EventoService {

@Autowired
private EventoRepository eventoRepository;

public void adicionarConvidado(Long id, String email){

    Evento evento = eventoRepository.findById(id).orElseThrow();

    evento.getConviteList().add(gerarConvite(email, evento));

    eventoRepository.save(evento);

}

public Long gerarNumeroAleatorio() {
        return 1000L + (long) (Math.random() * 9000);
    }

public Convite gerarConvite(String email, Evento evento){

    Long autenticador = gerarNumeroAleatorio();

    Convite convite = new Convite();

    convite.setCodigoAutenticador(autenticador);
    convite.setEmailConvidado(email);
    convite.setEvento(evento);

    return convite;
}


}
