package lk.ijse.ahms.bo;

import lk.ijse.ahms.bo.custom.DoctorBO;
import lk.ijse.ahms.bo.custom.impl.*;
import lk.ijse.ahms.dao.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOTypes {
        APPOINTMENT, DOCTOR, EMPLOYEE, MEDICINE, PAYMENT, PET, PET_OWNER, PRESCRIPTION, PRESCRIPTION_DETAIL, USER, PLACE_ORDER
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case APPOINTMENT:
                return new AppointmentBOImpl();
            case DOCTOR:
                return new DoctorBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case MEDICINE:
                return new MedicineBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PET:
                return new PetBOImpl();
            case PET_OWNER:
                return new PetOwnerBOImpl();
            case PRESCRIPTION:
                return new PrescriptionBOImpl();
            case PRESCRIPTION_DETAIL:
                return new PrescriptionDetailsBOImpl();
            case USER:
                return new UserBOImpl();
            case PLACE_ORDER:
                return new PlaceOrderBOImpl();
            default:
                return null;
        }
    }
}
