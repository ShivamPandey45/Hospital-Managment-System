package com.hospital.Hospital_management;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PatientDAO {
    private SessionFactory sessionFactory;

    public PatientDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addPatient(Patient patient) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(patient);
            tx.commit();
            System.out.println("Patient Added Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        try (Session session = sessionFactory.openSession()) {
            List<Patient> patients = session.createQuery("from Patient", Patient.class).list();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            for (Patient patient : patients) {
                System.out.printf("| %-10d | %-18s | %-8d | %-10s |\n", patient.getId(), patient.getName(), patient.getAge(), patient.getGender());
                System.out.println("+------------+--------------------+----------+------------+");
            }
        }
    }

    public boolean getPatientById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Patient.class, id) != null;
        }
    }
}
