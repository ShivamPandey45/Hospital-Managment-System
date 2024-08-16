package com.hospital.Hospital_management;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.Scanner;

public class HospitalManagementSystem {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Scanner scanner = new Scanner(System.in)) {

            PatientDAO patientDAO = new PatientDAO(sessionFactory);
            DoctorDAO doctorDAO = new DoctorDAO(sessionFactory);
            AppointmentDAO appointmentDAO = new AppointmentDAO(sessionFactory);

            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add Patient
                        Patient patient = new Patient();
                        System.out.print("Enter Patient Name: ");
                        patient.setName(scanner.nextLine());
                        System.out.print("Enter Patient Age: ");
                        patient.setAge(scanner.nextInt());
                        System.out.print("Enter Patient Gender: ");
                        patient.setGender(scanner.next());
                        patientDAO.addPatient(patient);
                        System.out.println("Patient added successfully!");
                        break;
                    case 2:
                        // View Patients
                        patientDAO.viewPatients();
                        break;
                    case 3:
                        // View Doctors
                        doctorDAO.viewDoctors();
                        break;
                    case 4:
                        // Book Appointment
                        System.out.print("Enter Patient Id: ");
                        int patientId = scanner.nextInt();
                        System.out.print("Enter Doctor Id: ");
                        int doctorId = scanner.nextInt();
                        System.out.print("Enter appointment date (YYYY-MM-DD): ");
                        LocalDate appointmentDate = LocalDate.parse(scanner.next());
                        
                        if (patientDAO.getPatientById(patientId) && doctorDAO.getDoctorById(doctorId)) {
                            if (appointmentDAO.checkDoctorAvailability(doctorId, appointmentDate)) {
                                Appointment appointment = new Appointment();
                                appointment.setPatientId(patientId);
                                appointment.setDoctorId(doctorId);
                                appointment.setAppointmentDate(appointmentDate);
                                appointmentDAO.bookAppointment(appointment);
                                System.out.println("Appointment booked successfully!");
                            } else {
                                System.out.println("Doctor not available on this date!");
                            }
                        } else {
                            System.out.println("Either doctor or patient doesn't exist!");
                        }
                        break;
                    case 5:
                        System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM!");
                        return;
                    default:
                        System.out.println("Enter a valid choice!");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
