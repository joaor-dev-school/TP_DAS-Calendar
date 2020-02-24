package tp.das.Model.Evento;

import tp.das.Model.Data.Data;
import tp.das.Model.Sistema.Utilizador;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    private Utilizador organizador;
    private List<Utilizador> convidados;
    private List<Utilizador> presentes;
    private Data data;

    public Evento(Utilizador organizador, List<Utilizador> convidados, Data data) {
        this.organizador = organizador;
        this.convidados = convidados;
        this.presentes = new ArrayList<>();
        this.data = data;
    }

    public Utilizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Utilizador organizador) {
        this.organizador = organizador;
    }

    public List<Utilizador> getConvidados() {
        return convidados;
    }

    public void setConvidados(List<Utilizador> convidados) {
        this.convidados = convidados;
    }

    public List<Utilizador> getPresentes() {
        return presentes;
    }

    public void setPresentes(List<Utilizador> presentes) {
        this.presentes = presentes;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
