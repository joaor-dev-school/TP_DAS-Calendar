package tp.das.Model.Event.Decorators;

import tp.das.Model.Event.DateModel;

import java.util.List;

public interface IPeriodicEventDecorator {
    List<DateModel> getDates();

    DateModel getReferenceDate();
}
