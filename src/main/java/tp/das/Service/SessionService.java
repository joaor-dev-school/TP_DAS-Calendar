package tp.das.Service;

import tp.das.Model.Database.UnitOfWork;

public class SessionService {
    private static UnitOfWork unitOfWork;
    private SessionService() {
    }

    public static UnitOfWork getUnitOfWork() {
        if(unitOfWork == null){
            unitOfWork = new UnitOfWork();
        }
        return unitOfWork;
    }
}
