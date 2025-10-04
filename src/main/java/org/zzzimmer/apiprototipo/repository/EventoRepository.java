package org.zzzimmer.apiprototipo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zzzimmer.apiprototipo.model.Evento;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query(" SELECT e FROM Evento e WHERE e.responsavel.id = :idUsuario ")
    List<Evento> findAllByUsuarioId(Long idUsuario);
}
