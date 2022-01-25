package HospitalManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class Hospital {
	
	public static void main(String[] args) {
		//File f = new File("D:\\Backup.txt");
		try {
			String StoragePath = "D:\\HospitalManagementSystem";
			String AppointmentPath = "D:\\HospitalManagementSystem\\Appointments";
			String PatientDetailsPath = "D:\\HospitalManagementSystem\\PatientDetails";
			File StoragePathFolder = new File(StoragePath);
			File AppointmentPathFolder = new File(AppointmentPath);
			File PatientDetailsPathFolder = new File(PatientDetailsPath);
			if(!StoragePathFolder.exists()){
				StoragePathFolder.mkdir();	
			}
			if(!AppointmentPathFolder.exists()) {
				AppointmentPathFolder.mkdir();
			}
			if(!PatientDetailsPathFolder.exists()){
				PatientDetailsPathFolder.mkdir();	
			}
		        Scanner sc = new Scanner(System.in);
		        int choices, choices2;
		        char wish;
		        x:do{

		            System.out.println("\nSelect your choice :\n1. Hospital Facilities \n2. Available doctors \n3. Create an Appointment with Doctor\n4. Cancel Appointment \n5. Pay Bills \n6. Exit\n");
		            choices = sc.nextInt();
		            switch(choices){
		                case 1: System.out.println("\nChoose required service type :\n1. Ambulance service \n2. Other details \n");
		                        choices2 = sc.nextInt();
		                        Hospital.EmergencyService(choices2);
		                    break;
		                case 2:
		            		if(!CheckAvailabilityOfDoctors()) {
		            			System.out.println("\n No Doctors are available and please book appointment tomorrow!");	
		            		}
		            		else {
		            			System.out.println("\n Doctors are available and can get appointments for Today!");
		            		}
		                    break;
		                case 3: System.out.println("\n Sure!");
		                         Hospital.NormalService();                     
		                    break;
		                case 4:
		                         Hospital.CalcelAppointment();
		                         break;
		                case 5:                 
		                    System.out.print("Please pay the bills");
		                        Hospital.payBills();
		                         break;
		                case 6:break x;
		                    
		            }
		            System.out.println("\nContinue : (y/n)");
		            wish=sc.next().charAt(0); 
		            if(!(wish=='y'||wish=='Y'||wish=='n'||wish=='N'))
		            {
		                System.out.println("Invalid Option");
		                System.out.println("\nContinue : (y/n)");
		                wish=sc.next().charAt(0); 
		            }
		            
		        }
		        while(wish=='y'||wish=='Y');  
		}
		
		catch(Exception e) {
			System.out.println("Not a valid input  " + e.getMessage());
		}
	}

	static void NormalService(){
		System.out.println("Please enter the following details...");
		String patientFile = null;
		if(!checkPatientExists())
		{
			patientFile = getPatientDetails();	
		}	
		if(CheckAvailabilityOfDoctors()){
			getAppointment(patientFile);
		}
		else {
			System.out.println("\n No doctors are available... Please check with deskmanager!");
		}
		
	}
	
	static void EmergencyService(int choices2) {
		
		if(choices2 == 1) {
			System.out.println("\nAmbulance service is not available right now! Please check with deskmanager!");
		}
		else {
			System.out.println("\n 1. X-ray rooms 2. Covid special beds 3. Ac Rooms 4. Non-Ac rooms 5. 24hours service"); //need to add few facilities about ho
		}
	}
	
	static void CalcelAppointment() {
		
		Scanner sc = new Scanner(System.in);
		
        System.out.println("\n Enter the patient name : \n");
        String Name = sc.next();
        System.out.println("\n Enter the contact number ?\n");
        String ContactNo = sc.next();
        String Filename = Name + "" + ContactNo;
        
        File file = new File("D:\\HospitalManagementSystem\\Appointments\\" + Filename + ".txt");
        if(file.delete()) {
        	
        	System.out.println("Appointment has been cancelled!");
        }
        else {
        	System.out.println("Some error has occured. Please try again later!");
        }
		
	}
	
	static void payBills(){
		try {
		Scanner sc = new Scanner(System.in);
		
        System.out.println("\n Enter the patient name : \n");
        String Name = sc.next();
        System.out.println("\n Enter the contact number ?\n");
        String ContactNo = sc.next();
        String Filename = Name + "" + ContactNo;
        
        File file = new File("D:\\HospitalManagementSystem\\Appointments\\" + Filename + ".txt");
        Scanner reader = new Scanner(file);
        String PatientData = reader.next();
        String[] patientDetails = PatientData.split("&");
        Float fees = (float) 250; //Doctors Charge;
        switch (patientDetails[8]) {
		case "Fever":
			fees = fees + 300;
			break;
		case "Cold":
			fees = fees + 400;
			break;
		case "Stomach Pain":
			fees = fees + 250;
			break;

		default:
			fees = fees + 1000;
			break;
		}
        
        fees = fees + 450; //tablet or scan - common charge
        
        System.out.println("/n The payable fee is " + fees);
		}
		catch(Exception e) {
			
		}
        
	}
	
	static String getPatientDetails() {
		Scanner sc = new Scanner(System.in);
		
        System.out.println("\n Enter the patient name : \n");
        String Name = sc.next();
        System.out.println("\n Enter the patient blood group : \n");
        String Bloodgroup = sc.next();
        System.out.println("\n Enter the contact number ?\n");
        String ContactNo = sc.next();
        System.out.println("\n Enter the insurance number : \n");
        String InsuranceNo = sc.next();
        System.out.println("\n Is it complaint raised to police ?\n");
        String policeCase = sc.next();
        System.out.println("\n Enter the patient relation name ?\n");
        String patientRelationName = sc.next();
        System.out.println("\n Enter the patient relation Type ?\n");
        String RelationType = sc.next();
        System.out.println("\n Please Select the problem which you face ? \n 1. Fever \n 2. Cold \n 3. Stomach pain \n 4. If others, Please Specify \n");
        int PatientProblem = sc.nextInt();
        String PatientProblemDetails = GetProblemDetails(PatientProblem);

        String Result = Name + "&" + ContactNo  + "&" + Bloodgroup + "&" + InsuranceNo  + "&" + policeCase + "&" + patientRelationName + "&" + RelationType + "&" + PatientProblem + PatientProblemDetails;   
        String fileName = createNewPatient(Result); 
        
        return fileName; 
	}
	
	static String GetProblemDetails(int PatientProblem) {
		String[] listOfDetails = {"Fever", "Cold", "Stomach Pain"};
		String problemDetails = null;
		if(PatientProblem == 4) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Please describe the problem: ");
			problemDetails = sc.nextLine();
		}
		else {
			problemDetails = listOfDetails[PatientProblem];
		}
		return problemDetails;
	}
	
	static boolean CheckAvailabilityOfDoctors(){
		boolean isPresent = false;
		String AppointmentPath = "D:\\HospitalManagementSystem\\Appointments";
		File dir = new File(AppointmentPath);
		int fileCount = dir.list().length;
		if(fileCount > 8) {
			isPresent = false;
		}
		else {
			isPresent = true;
		}
		return isPresent;
	}
	
	static String createNewPatient(String PatientDetails) {
	
		try {
			
		String path = "D:\\HospitalManagementSystem\\PatientDetails";
		String[] Patient = PatientDetails.split("&");
		String FileName = Patient[0] + ""+ Patient[1];
		System.out.println("FileName : "  + FileName);	
		File file = new File(path + "\\" + FileName + ".txt");
		
		if(!file.exists()) {
			file.createNewFile();
			FileWriter fw = new FileWriter(path + "\\" + FileName + ".txt");
			fw.write(PatientDetails);
			fw.close();
			System.out.println("New patient added!");
		} 
		else {
			System.out.println("Patient already exists!");
		}
		
		return FileName;
		} 
		catch (IOException e) {
			return e.getMessage();
		}
	}
	
	static boolean checkPatientExists() {
		//if db present can check with name and contact number and blood group
		Scanner sc = new Scanner(System.in);
        System.out.println("\n Enter the patient name : \n");
        String Name = sc.next();
        System.out.println("\n Enter the contact number ?\n");
        String ContactNo = sc.next();
    	String FileName = Name + ""+ ContactNo;
    	String path = "D:\\HospitalManagementSystem\\PatientDetails";
    	File file = new File(path + "\\" + FileName + ".txt");
		return file.exists();
	}
	
	static boolean getAppointment(String PatientFile){
		boolean AppointmentResult = false;
		String path = "D:\\HospitalManagementSystem\\Appointments";
		try {
		File dir = new File(path);
		int fileCount = dir.list().length;
		
		if(fileCount > 8) {
			System.out.println("The appointment is full. Please try tomorrow!");
			AppointmentResult = false;
		}
		else {
			File file = new File(path + "\\" + PatientFile + ".txt");
			file.createNewFile();
			FileWriter fw = new FileWriter(path + "\\" + PatientFile + ".txt");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, fileCount);
			fw.write(PatientFile + "Got the Appointment at " + cal.getTime());
			fw.close();
			System.out.println("Appointment has been fixed at " + cal.getTime());
			AppointmentResult = true;
		}
		
		}
		catch(Exception e) {
			System.out.println("Appointment has been failed!");
			AppointmentResult = false;
		}
		return AppointmentResult;
	}
}

