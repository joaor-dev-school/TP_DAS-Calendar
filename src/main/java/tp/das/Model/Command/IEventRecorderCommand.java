package tp.das.Model.Command;

import tp.das.Model.Sistema.Utilizador;

public interface IEventRecorderCommand {

    void execute(Utilizador client);
    void undo(Utilizador client);
}
