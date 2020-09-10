package tp.das.Model.Event.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventCommandHandler {
    private final Map<Long, List<IEventCommand>> undoListMap;
    private final Map<Long, List<IEventCommand>> redoListMap;

    public EventCommandHandler() {
        this.undoListMap = new HashMap<>();
        this.redoListMap = new HashMap<>();
    }

    public void apply(Long userId, IEventCommand command) {
        if (this.getUndoList(userId) == null || this.getRedoList(userId) == null) {
            this.undoListMap.put(userId, new ArrayList<>());
            this.redoListMap.put(userId, new ArrayList<>());
        }
        command.doCommand();
        this.getUndoList(userId).add(command);
        this.getRedoList(userId).clear();
    }

    public void undo(Long userId) {
        final List<IEventCommand> undoList = this.getUndoList(userId);
        final List<IEventCommand> redoList = this.getRedoList(userId);
        final int undoListSize = undoList.size();

        if (undoListSize < 1) {
            return;
        }

        IEventCommand command = null;

        try {
            command = undoList.remove(undoListSize - 1);
            command.undoCommand();
            redoList.add(command);
        } catch (Exception ex) {
            if (command != null) {
                undoList.add(command);
            }
        }
    }

    public void redo(Long userId) {
        final List<IEventCommand> undoList = this.getUndoList(userId);
        final List<IEventCommand> redoList = this.getRedoList(userId);
        final int redoListSize = redoList.size();

        if (redoListSize < 1) {
            return;
        }

        IEventCommand command = null;

        try {
            command = redoList.remove(redoListSize - 1);
            command.doCommand();
            undoList.add(command);
        } catch (Exception ex) {
            if (command != null) {
                redoList.add(command);
            }
            throw ex;
        }
    }

    public void cleanUserRecord(Long userId) {
        this.undoListMap.remove(userId);
        this.redoListMap.remove(userId);
    }

    public void cleanAllUsersRecord() {
        this.undoListMap.clear();
        this.redoListMap.clear();
    }

    public int getUserUndoOperationsSize(Long userId) {
        final List<IEventCommand> undoList = this.getUndoList(userId);
        return undoList == null ? 0 : undoList.size();
    }

    public int getUserRedoOperationsSize(Long userId) {
        final List<IEventCommand> redoList = this.getRedoList(userId);
        return redoList == null ? 0 : redoList.size();
    }

    private List<IEventCommand> getUndoList(Long userId) {
        return this.undoListMap.get(userId);
    }

    private List<IEventCommand> getRedoList(Long userId) {
        return this.redoListMap.get(userId);
    }
}
