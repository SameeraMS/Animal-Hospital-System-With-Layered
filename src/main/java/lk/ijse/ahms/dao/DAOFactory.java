package lk.ijse.ahms.dao;

import lk.ijse.ahms.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDaoFactory() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        APPOINTMENT, DOCTOR, EMPLOYEE, MEDICINE, PAYMENT, PET, PET_OWNER, PRESCRIPTION, PRESCRIPTION_DETAIL, USER
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case APPOINTMENT:
                return new AppointmentDAOImpl();
            case DOCTOR:
                return new DoctorDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case MEDICINE:
                return new MedicineDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PET:
                return new PetDAOImpl();
            case PET_OWNER:
                return new PetOwnerDAOImpl();
            case PRESCRIPTION:
                return new PrescriptionDAOImpl();
            case PRESCRIPTION_DETAIL:
                return new PresDetailsDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}
