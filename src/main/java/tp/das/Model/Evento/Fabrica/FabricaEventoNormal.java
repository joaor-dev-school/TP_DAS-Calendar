package tp.das.Model.Evento.Fabrica;

import tp.das.DTOs.CriarEventoDTO;
import tp.das.Model.Data.Data;
import tp.das.Model.Evento.Evento;
import tp.das.Model.Utilizador.Utilizador;
import tp.das.Service.UtilizadoresService;

import java.util.List;
import java.util.stream.Collectors;

public class FabricaEventoNormal extends FabricaEvento {
    @Override
    public Evento criarEvento(CriarEventoDTO eventoDTO) {
        Utilizador organizador = UtilizadoresService.findById(eventoDTO.getOrganizadorId());
        List<Utilizador> convidados = eventoDTO.getConvidadosIds().stream()
                .map(UtilizadoresService::findById)
                .collect(Collectors.toList());
        Data data = new Data(eventoDTO.getDateTime(), eventoDTO.getDuracao());
        return new Evento(organizador, convidados, data);
    }
}
