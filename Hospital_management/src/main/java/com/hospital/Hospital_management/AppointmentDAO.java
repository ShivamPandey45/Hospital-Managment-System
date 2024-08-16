package com.hospital.Hospital_management;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;

public class AppointmentDAO {
    private SessionFactory sessionFactory;

    public AppointmentDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean checkDoctorAvailability(int doctorId, LocalDate appointmentDate) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Appointment WHERE doctorId = :doctorId AND appointmentDate = :appointmentDate", Long.class);
            query.setParameter("doctorId", doctorId);
            query.setParameter("appointmentDate", appointmentDate);
            return query.uniqueResult() == 0;
        }
    }

    public void bookAppointment(Appointment appointment) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(appointment);
            tx.commit();
            System.out.println("Appointment Booked!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

