package Interface;
import java.util.List;

import Entities.Payments;

public interface PaymentsDao {
    void addPayment(Payments payment);
    List<Payments> getAllPayments();
    Payments getPaymentById(int paymentID);
    void updatePayment(Payments payment);
    void deletePayment(int paymentID);
}