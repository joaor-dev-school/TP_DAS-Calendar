package tp.das.Model;

import tp.das.Model.Evento.Evento;

public class Utilizador {
    private String username;
    private String password;
    private String nome;
    private  Evento evento;

    public Utilizador() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void removeEvent(Evento ev) {
    }

    public void addEvent(Evento ev) {
    }

    public Evento getEvent() {
        return evento;
    }
}
