package tp.das.DTOs;

import java.io.Serializable;
import java.util.List;

public class CriarEventoDTO implements Serializable {
    private Integer organizadorId;
    private List<Integer> convidadosIds;
    private long dateTime;
    private long duracao;

    public CriarEventoDTO() {
    }

    public Integer getOrganizadorId() {
        return organizadorId;
    }

    public void setOrganizadorId(Integer organizadorId) {
        this.organizadorId = organizadorId;
    }

    public List<Integer> getConvidadosIds() {
        return convidadosIds;
    }

    public void setConvidadosIds(List<Integer> convidadosIds) {
        this.convidadosIds = convidadosIds;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public long getDuracao() {
        return duracao;
    }

    public void setDuracao(long duracao) {
        this.duracao = duracao;
    }
}
