package controller_Service;

import dal.DAO_Loan;
import model.Loan_payments;
import dal.EmailService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
public class LoanReminder implements ServletContextListener{
     private Timer timer = new Timer();
     @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DAO_Loan d = new DAO_Loan();
                List<Loan_payments> duePayments = d.getDuePayments();

                for (Loan_payments payment : duePayments) {
                    EmailService.sendReminder(payment.getEmail(), 
                            payment.getPayment_amount(), payment.getPayment_date().toString());                  
                }
            }
        }, 0,24*60*60 * 1000); //  ( milisecond)
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        timer.cancel();
        EmailService.shutdown(); 
    }
}
