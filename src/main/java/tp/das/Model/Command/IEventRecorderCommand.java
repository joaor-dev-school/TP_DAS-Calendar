package tp.das.Model.Command;

import tp.das.Model.Utilizador.UserModel;

public interface IEventRecorderCommand {

    void execute(UserModel client);
    void undo(UserModel client);
}
