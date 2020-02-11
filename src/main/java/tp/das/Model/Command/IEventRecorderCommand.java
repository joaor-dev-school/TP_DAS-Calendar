package tp.das.Model.Command;

import tp.das.Model.Utilizador.Utilizador;

public interface IEventRecorderCommand {

    void execute(Utilizador client);
    void undo(Utilizador client);
}
