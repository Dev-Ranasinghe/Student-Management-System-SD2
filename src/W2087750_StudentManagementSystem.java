import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class W2087750_StudentManagementSystem {
    public static Scanner scanner = new Scanner(System.in);
    private static int studentCount = 0;
    private static final int MAX_SEATS = 100;
    private static int availableSeats = MAX_SEATS;
    public static Student[] studentArray = new Student[MAX_SEATS];

    public static void main(String[] args) {
        int option;
        // loading student data in the startup of the program
        loadStudentDetails();

        // looping the main menu until the user terminate it
        do {
            displayMenu();
            option = optionValidation();
            switch (option) {
                case 1: checkAvailability();
                    break;
                case 2: registerStudent();
                    break;
                case 3: deleteStudent();
                    break;
                case 4: findStudent();
                    break;
                case 5: saveStudentDetails();
                    break;
                case 6: loadStudentDetails();
                    break;
                case 7: listOfStudents();
                    break;
                case 8: additionalOptions();
                    break;
            }
        } while (option != 0);
    }

    /**
     * method for displaying the main menu
     */
    private static void displayMenu() {
        String intro = """
                                
                *********************************************
                |         STUDENT MANAGEMENT SYSTEM         |
                |                 MAIN MENU                 |
                *********************************************
                |        1. Check available seats           |
                |        2. Register student (ID)           |
                |        3. Delete student                  |
                |        4. Find student (ID)               |
                |        5. Store student details           |
                |        6. Load student details            |
                |        7. View the list of students       |
                |        8. Additional options              |
                |        0. Terminate the program           |
                *********************************************""";
        System.out.println(intro);
    }

    /**
     * method for validating user inputs for option number
     *
     * @return valid option number
     */
    private static int optionValidation() {
        while (true) {
            try {
                System.out.print("Option no: ");
                String input = scanner.nextLine();
                int option = Integer.parseInt(input);

                if (option < 0 || option > 8) {
                    throw new Exception("Invalid option");
                }
                return option;
            } catch (Exception e) {
                System.out.println("Invalid option !!!");
            }
        }
    }

    /**
     * method for validating user inputs for student id
     *
     * @param inputMessage custom input message can be used in various methods
     * @return valid student id
     */
    private static String studentIdValidation(String inputMessage) {
        while (true) {
            try {
                System.out.print(inputMessage + " (format 'w*******'): ");
                String studentId = scanner.nextLine().toLowerCase();
                if (studentId.length() != 8 || !Character.isLetter(studentId.charAt(0))) {
                    throw new Exception("Invalid Id");
                }
                if (studentId.charAt(0) != 'w') {
                    throw new Exception("Invalid ID");
                }
                for (int i = 1; i < studentId.length(); i++) {
                    if (!Character.isDigit(studentId.charAt(i))) {
                        throw new Exception("Invalid ID");
                    }
                }
                return studentId;
            } catch (Exception e) {
                System.out.println("Enter a valid student ID !!!");
            }
        }
    }

    /**
     * method for validating student name
     * checks whether the student name consist of two names and only alpha characters
     * @return validate student name
     */
    private static String studentNameValidation() {
        while (true) {
            try {
                System.out.print("Student name (first name and last name both): ");
                String studentName = scanner.nextLine().toUpperCase();
                if (studentName.isBlank()) {
                    throw new Exception("Empty data");
                }
                String [] fullName = studentName.trim().split(" ");
                if(fullName.length != 2){
                    System.out.print("Enter first name and last name both. ");
                    throw new Exception("Enter two names !!!");
                }
                String fName = fullName[0];
                String lName = fullName[1];

                if(!fName.matches("[A-Z]+") || !lName.matches("[A-Z]+")){
                    System.out.print("Alphabetical characters only. ");
                    throw new Exception("Alpha characters only !!!");
                }
                return studentName;
            } catch (Exception e) {
                System.out.println("Invalid input for name!!!");
            }
        }
    }


    /**
     * method for validating user inputs for module marks
     *
     * @param inputMessage custom input message can be used whenever calling this method
     * @return validated input for module mark
     */
    private static double moduleMarkValidation(String inputMessage) {
        while (true) {
            try {
                System.out.print(inputMessage + " :");
                String stringMark = scanner.nextLine();
                double mark = Double.parseDouble(stringMark);
                if (mark < 0 || mark > 100) {
                    throw new Exception("Out of range");
                }
                return mark;
            } catch (Exception e) {
                System.out.println("Enter a valid numerical value !!!");
            }
        }
    }

    /**
     * method for displaying the registered student ids
     */
    private static void displayStudentId(){
        System.out.println();
        System.out.println("Registered Student IDs >>>");
        for (int i = 0; i < studentCount; i++){
            System.out.println("> " + studentArray[i].getStudentId());
        }
        System.out.println();
    }

    /**
     * method for displaying available seat count and seat order using a diagram
     */
    private static void checkAvailability() {
        System.out.println("\nSeat Availability Details >>>>>");
        System.out.println("Available seats     : " + availableSeats);
        System.out.println("Total registrations : " + studentCount);
        System.out.println("\nSeat availability diagram ( X = registered, 0 = available )");
        for (int i = 1; i <= MAX_SEATS; i++) {
            if (i <= studentCount) {
                System.out.print("X ");
            } else {
                System.out.print("0 ");
            }
            if (i % 10 == 0) {
                System.out.println();
            }
        }
    }

    /**
     * method for registering new students to the system using student id
     */
    private static void registerStudent(){
        // checking whether the maximum capacity reached or not
        if (studentCount >= MAX_SEATS) {
            System.out.println("Maximum capacity reached !!! \nCannot access this feature !!!");
            System.out.println("House full !!!");
            return;
        }
        // registering new students
        String studentId = studentIdValidation("Student Id for registration");
        for (int i = 0; i < studentCount; i++) {
            if (studentArray[i].getStudentId().equals(studentId)) {
                System.out.println("Already existing student ID !!!");
                return;
            }
        }
        // creating a new student object and storing in the object array
        studentArray[studentCount++] = new Student(studentId);
        availableSeats--;
        System.out.println("Student ID registration success !!!");
        System.out.println("Total no of registrations >>> " + studentCount);
    }

    /**
     * method for deleting students using the student id
     */
    private static void deleteStudent() {
        // checking whether the system is empty
        if(studentCount == 0){
            System.out.println("Not a single student registered yet !!!");
            return;
        }
        boolean idFound = false;
        // displaying the existing student ids
        displayStudentId();
        String studentId = studentIdValidation("Student Id for deletion");
        for (int i = 0; i < studentCount; i++) {
            if (studentArray[i].getStudentId().equals(studentId)) {
                studentArray[i] = studentArray[--studentCount];
                studentArray[studentCount] = null;

                System.out.println("Student data deletion success !!!");
                availableSeats++;
                idFound = true;

                System.out.println("Total no of registrations >>> " + studentCount);
                break;
            }
        }
        if (!idFound) {
            System.out.println("Not a registered Student ID !!!");
        }
    }

    /**
     * method for finding students' positions using their student ids
     */
    private static void findStudent() {
        // checking whether the system is empty
        if(studentCount == 0){
            System.out.println("Not a single student registered yet !!!");
            return;
        }
        // displaying existing student ids
        displayStudentId();
        String studentId = studentIdValidation("Student Id that needs to be found: ");

        // checking and displaying the relevant student details
        for (int i = 0; i < studentCount; i++) {
            if (studentArray[i].getStudentId().equals(studentId)) {
                System.out.println("--------------------------------------------");
                System.out.printf("|           Student ID: %s           |\n", studentArray[i].getStudentId());
                System.out.println("--------------------------------------------");
                System.out.println(" Registration status  : Success");
                System.out.println(" Registered position  : " + (i + 1));

                if (studentArray[i].getStudentName() == null) {
                    System.out.println(" Student name         : Empty");
                }
                else {
                    System.out.println(" Student name         : " + studentArray[i].getStudentName());
                }

                if (studentArray[i].getModules() == null) {
                    System.out.println(" Module marks         : Empty");
                }
                else {
                    System.out.println(" Module 1             : " + studentArray[i].getModules().getMarks(0));
                    System.out.println(" Module 2             : " + studentArray[i].getModules().getMarks(1));
                    System.out.println(" Module 3             : " + studentArray[i].getModules().getMarks(2));
                    System.out.println(" Total                : " + studentArray[i].getModules().getTotal());
                    System.out.printf(" Average              : %.1f\n", studentArray[i].getModules().getAverage());
                    System.out.println(" Grade                : " + studentArray[i].getModules().getGrade());
                }
                System.out.println("--------------------------------------------");
                return;
            }
        }
        System.out.println("Not a registered Student ID !!!");
    }

    /**
     * method for validating the sub option user input
     * @return validated sub option string value
     */
    private static String subOptionValidation(){
        while(true) {
            try {
                System.out.print("Enter option (A, B, C, D, E): ");
                String subOption = scanner.nextLine().toLowerCase();
                if (subOption.charAt(0) != 'a' && subOption.charAt(0) != 'b' && subOption.charAt(0) != 'c' && subOption.charAt(0) != 'd' && subOption.charAt(0) != 'e') {
                    throw new Exception("Invalid !!!");
                }
                return subOption;
            } catch (Exception e) {
                System.out.println("Not a valid sub option !!!");
            }
        }
    }

    /**
     * method for validating the confirmation input from the user
     * @param inputMessage can be used customized inputs whenever calling this method
     * @return validated confirmation
     */
    private static boolean confirmationValidation(String inputMessage) {
        while (true) {
            try {
                System.out.print(inputMessage);
                String confirmation = scanner.nextLine().toLowerCase();
                if(confirmation.equals("y")){
                    return true;
                }
                else if(confirmation.equals("n")){
                    return false;
                }
                else{
                    throw new Exception("Invalid !!!");
                }
            } catch (Exception e) {
                System.out.println("Invalid inputs are 'Y' and 'N' !!!");
            }
        }
    }

    /**
     * method for displaying the additional options menu
     */
    private static void additionalOptionsIntro(){
        System.out.println("""
                
                ******************************************
                |           Additional Options           |
                ******************************************
                |         A. Manage student name         |
                |         B. Manage module marks         |
                |         C. System summary              |
                |         D. Display report              |
                |         E. Terminate                   |
                ******************************************""");
    }

    /**
     * method for handing all the additional options
     * including manage student name, manage student marks, generating a system summary, generating an overall report
     */
    private static void additionalOptions(){
        String subOption;
        do {
            additionalOptionsIntro();
            subOption = subOptionValidation();
            switch (subOption) {
                case "a":
                    manageStudentName();
                    break;
                case "b":
                    manageStudentModuleMarks();
                    break;
                case "c":
                    systemSummary();
                    break;
                case "d":
                    studentReport();
                    break;
            }
        }while(!subOption.equals("e"));
    }

    /**
     * method for allowing the user to add student names
     */
    private static void manageStudentName(){
        // checking whether the system is empty
        if(studentCount == 0){
            System.out.println("Not a single student registered yet !!!");
            return;
        }

        // displaying existing student ids
        displayStudentId();

        // setting up student names
        String studentId = studentIdValidation("Enter the Student ID");
        for(int i = 0; i<studentCount; i++){
            if (studentArray[i].getStudentId().equals(studentId)) {
                if(studentArray[i].getStudentName()!=null){
                    boolean confirmation = confirmationValidation("Do you want to update the existing name (Y or N): ");
                    if(!confirmation){
                        return;
                    }
                }
                String studentName = studentNameValidation();
                studentArray[i].setStudentName(studentName);
                System.out.println("Student name added successfully !!!");
                return;
            }
        }
        System.out.println("Not a valid Student ID. Please check again !!!");
    }

    /**
     * method for allowing the user to add module marks for each student
     */
    private static void manageStudentModuleMarks(){
        // checking whether the system is empty
        if(studentCount == 0){
            System.out.println("Not a single student registered yet !!!");
            return;
        }

        // displaying existing student ids
        displayStudentId();

        // setting up module marks
        String studentId = studentIdValidation("Enter the Student ID");
        for(int i = 0; i<studentCount; i++) {
            if (studentArray[i].getStudentId().equals(studentId)) {
                if (studentArray[i].getModules() != null) {
                    boolean confirmation = confirmationValidation("Do you want to update the existing module marks (Y or N): ");
                    if(!confirmation){
                        return;
                    }
                }
                double module1 = moduleMarkValidation("Enter module 1 mark");
                double module2 = moduleMarkValidation("Enter module 2 mark");
                double module3 = moduleMarkValidation("Enter module 3 mark");
                Module module = new Module(module1, module2, module3);
                studentArray[i].setModules(module);
                System.out.println("Module marks added successfully !!!");
                return;
            }
        }
        System.out.println("Not a valid Student ID. Please check again !!!");
    }

    /**
     * method for displaying the following details
     * total number of registrations
     * available seat count for new registrations
     * total number of students who got more than 40 for all 3 modules
     */
    private static void systemSummary(){
        System.out.println();
        System.out.println("Total number of student registrations: " + studentCount);
        System.out.println("Available seats for new registrations: " + availableSeats);

        // checking whether the array is empty
        if(studentCount == 0){
            System.out.println("Not a single student registered yet !!!");
            return;
        }
        // checking the all the module marks whether they are greater than or equal 40
        int tempMod1 = 0;
        int tempMod2 = 0;
        int tempMod3 = 0;
        int tempAllMod = 0;
        for (int i = 0; i < studentCount; i++){
            try {
                if (studentArray[i].getModules().getMarks(0) >= 40){
                    tempMod1++;
                } if (studentArray[i].getModules().getMarks(1) >= 40) {
                    tempMod2++;
                } if (studentArray[i].getModules().getMarks(2) >= 40) {
                    tempMod3++;
                }
                if(studentArray[i].getModules().getMarks(0) >= 40 && studentArray[i].getModules().getMarks(1) >= 40 && studentArray[i].getModules().getMarks(2) >= 40){
                    tempAllMod++;
                }
            } catch (Exception ignored){

            }
        }

        // displaying the system summary
        System.out.println("-------------------------------------------------------");
        System.out.println("|                   System Summary                    |");
        System.out.println("-------------------------------------------------------");
        System.out.printf("|       Scored at least 40 for module 1    : %-9d|\n", tempMod1);
        System.out.printf("|       Scored at least 40 for module 2    : %-9d|\n", tempMod2);
        System.out.printf("|       Scored at least 40 for module 3    : %-9d|\n", tempMod3);
        System.out.printf("|       Scored at least 40 for all modules : %-9d|\n", tempAllMod);
        System.out.println("-------------------------------------------------------");
    }

    /**
     * method for displaying student names in alphabetical order
     */
    private static void listOfStudents(){
        // checking whether the array is empty
        if(studentCount == 0){
            System.out.println("Not a single student registered yet !!!");
            return;
        }
        // creating a new array for student names
        String [] nameArray = new String[studentCount];
        int nameCount = 0;
        for(int i = 0; i < studentCount; i++){
            String name = studentArray[i].getStudentName();
            if(name != null){
                nameArray[nameCount] = name;
                nameCount++;
            }
        }
        // bubble sorting student names
        for(int i = 0; i < nameCount - 1; i++){
            for(int j = i + 1; j < nameCount; j++){
                if(nameArray[i].compareTo(nameArray[j]) > 0){
                    String temp = nameArray[i];
                    nameArray [i] = nameArray[j];
                    nameArray[j] = temp;
                }
            }
        }
        // displaying sorted student names
        System.out.println();
        System.out.println("Student names according to the alphabetical order >>>");
        for(int i = 0; i < nameCount; i++){
            System.out.println((i+1) + "> " + nameArray[i]);
        }
    }

    /**
     * method for creating a detailed student report for every student
     */
    private static void studentReport(){
        // bubble sorting the array according to the averages
        bubbleSort();

        // checking whether the system is empty
        if(studentCount == 0){
            System.out.println("Not a single student registered yet !!!");
            return;
        }

        // displaying each students data
        for(int i = 0; i<studentCount; i++){
            System.out.println();
            System.out.println("----------------------------------------");
            System.out.printf("|       Student ID: %s           |\n", studentArray[i].getStudentId());
            System.out.println("----------------------------------------");

            if (studentArray[i].getStudentName() == null) {
                System.out.println("\t\tStudent name: Empty ");
            }
            else {
                System.out.println("\t\tStudent name: " + studentArray[i].getStudentName());
            }

            if (studentArray[i].getModules() == null) {
                System.out.println("\t\tModule marks: Empty ");            }
            else {
                System.out.println("\t\tModule 1    : " + studentArray[i].getModules().getMarks(0));
                System.out.println("\t\tModule 2    : " + studentArray[i].getModules().getMarks(1));
                System.out.println("\t\tModule 3    : " + studentArray[i].getModules().getMarks(2));
                System.out.println("\t\tTotal       : " + studentArray[i].getModules().getTotal());
                System.out.printf("\t\tAverage     : %.1f\n", studentArray[i].getModules().getAverage());
                System.out.println("\t\tGrade       : " + studentArray[i].getModules().getGrade());
            }
            System.out.println("----------------------------------------");
            System.out.println();
        }
    }

    /**
     * method for bubble sorting the student array according to their averages
     * sorted from highest to the lowest averages
     */
    private static void bubbleSort(){
        double avg;
        double avgPlus;
        for(int i = 0; i<studentCount - 1; i++){
            for(int j = 0; j<studentCount - i -1; j++){
                if(studentArray[j].getModules() == null){
                    avg = 0;
                }
                else{
                    avg = studentArray[j].getModules().getAverage();
                }

                if(studentArray[j + 1].getModules() == null){
                    avgPlus = 0;
                }
                else{
                    avgPlus = studentArray[j + 1].getModules().getAverage();
                }

                if (avg < avgPlus) {
                    Student temp = studentArray[j];
                    studentArray[j] = studentArray[j + 1];
                    studentArray[j + 1] = temp;
                }
            }
        }
    }

    /**
     * method for saving student data into a text file
     */
    private static void saveStudentDetails(){
        try{
            FileWriter writer = new FileWriter("studentData.txt");
            for(int i = 0; i<studentCount; i++){
                writer.write(studentArray[i].getStudentId() + ",");

                // checking whether the name is null
                if(studentArray[i].getStudentName() != null){
                    writer.write(studentArray[i].getStudentName() + ",");
                }
                else{
                    writer.write("Empty" + ",");
                }

                // checking whether the marks are null
                if(studentArray[i].getModules() != null){
                    writer.write(studentArray[i].getModules().getMarks(0) + "," +
                            studentArray[i].getModules().getMarks(1) + "," +
                            studentArray[i].getModules().getMarks(2));
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("Student data saved into a file successfully!!!");
        } catch (IOException e) {
            System.out.println("Error caught " + e);
        }
    }

    /**
     * method for loading student data saved in a text file
     */
    private static void loadStudentDetails(){
        try {
            boolean idUnique;
            File file = new File("studentData.txt");
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                idUnique = true;
                String line = reader.nextLine();
                String[] lineData = line.split(",");

                // checking whether the id is unique
                for (int i = 0; i < studentCount; i++) {
                    if (studentArray[i].getStudentId().equals(lineData[0])) {
                        idUnique = false;
                        break;
                    }
                }
                // loading student data only having name and id
                if (idUnique) {
                    if (lineData.length == 2) {
                        String studentId = lineData[0];
                        if (!lineData[1].equals("Empty")) {
                            String studentName = lineData[1];
                            studentArray[studentCount++] = new Student(studentId, studentName);
                        } else {
                            studentArray[studentCount++] = new Student(studentId);
                        }
                        availableSeats--;
                    }
                    // loading student data which includes name, id and marks
                    if (lineData.length == 5) {
                        String studentId = lineData[0];
                        String studentName;
                        if (lineData[1].equals("Empty")) {
                            studentName = null;
                        } else {
                            studentName = lineData[1];
                        }
                        double module1 = Double.parseDouble(lineData[2]);
                        double module2 = Double.parseDouble(lineData[3]);
                        double module3 = Double.parseDouble(lineData[4]);
                        Module module = new Module(module1, module2, module3);
                        studentArray[studentCount++] = new Student(studentId, studentName, module);
                        availableSeats--;
                    }
                }
            }
            System.out.println("Student data loaded from the file successfully!!!");
            System.out.println("Total no of registrations >>> " + studentCount);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}