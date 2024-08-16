package com.hospital.Hospital_management;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class DoctorDAO {
    private SessionFactory sessionFactory;

    public DoctorDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void viewDoctors() {
        try (Session session = sessionFactory.openSession()) {
            List<Doctor> doctors = session.createQuery("from Doctor", Doctor.class).list();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");
            for (Doctor doctor : doctors) {
                System.out.printf("| %-10d | %-18s | %-16s |\n", doctor.getId(), doctor.getName(), doctor.getSpecialization());
                System.out.println("+------------+--------------------+------------------+");
            }
        }
    }

    public boolean getDoctorById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Doctor.class, id) != null;
        }
    }
}

