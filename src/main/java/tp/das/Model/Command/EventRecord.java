package tp.das.Model.Command;

import tp.das.Model.Utilizador;

import java.util.ArrayList;
import java.util.List;

public class EventRecord {


    private Utilizador client;
    private List<IEventRecorderCommand> undoList;
    private List<IEventRecorderCommand> redoList;

    public Utilizador getClient() {
        return client;
    }

    public List<IEventRecorderCommand> getUndoList() {
        return undoList;
    }

    public List<IEventRecorderCommand> getRedoList() {
        return redoList;
    }

    public EventRecord(Utilizador client) {
        this.client = client;
        this.undoList = new ArrayList<>();
        this.redoList = new ArrayList<>();
    }

    public void apply(IEventRecorderCommand command) {
        command.execute(client);
        undoList.add(command);
        redoList.clear();
    }

    public void undo() {
        if (undoList.size() > 0) {
            IEventRecorderCommand undoCommand = undoList.remove(undoList.size() - 1);
            undoCommand.undo(client);
            redoList.add(undoCommand);
        }
    }

    public void redo() {
        if (redoList.size() > 0) {
            IEventRecorderCommand redoCommand = redoList.remove(redoList.size() - 1);
            redoCommand.execute(client);
            undoList.add(redoCommand);
        }
    }

    @Override
    public String toString() {
        return "EventRecorder{" +
                "client=" + client +
                ", undoList=" + undoList +
                ", redoList=" + redoList +
                '}';
    }
}
