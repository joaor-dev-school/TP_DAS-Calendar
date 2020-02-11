package tp.das.Model.Evento.Fabrica;

import tp.das.DTOs.CriarEventoDTO;
import tp.das.Model.Evento.Evento;
import tp.das.Model.Evento.TipoEventosEnum;

public abstract class FabricaEvento {
    public static FabricaEvento obterFabrica(TipoEventosEnum tipo) {
        switch (tipo) {
            case AUTOMATICO:
                return new FabricaEventoAutomatico();
            case COLABORATIVO:
                return new FabricaEventoColaborativo();
            default:
                return new FabricaEventoNormal();
        }
    }

    public abstract Evento criarEvento(CriarEventoDTO eventoDTO);
}
