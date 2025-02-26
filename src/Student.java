public class Student {
    private String studentName;
    private String studentId;
    private Module modules;

    /**
     * constructor for student class
     * @param studentId id of the student
     * @param studentName name of the student
     * @param modules module object
     */
    Student(String studentId, String studentName, Module modules){
        this.studentId = studentId;
        this.studentName = studentName;
        this.modules = modules;
    }

    /**
     * overload constructor
     * @param studentId id of the student
     * @param studentName name of the student
     */
    Student(String studentId, String studentName){
        this.studentName = studentName;
        this.studentId = studentId;
    }

    /**
     * overload constructor
     * @param studentId id of the student
     */
    Student(String studentId){
        this.studentId = studentId;
    }

    /**
     * method for getting student id
     * @return student id
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * getter for student name
     * @return student name
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * setter for student id
     * @param studentId id of the student
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * setter for the student name
     * @param studentName name of the student
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * setter for the module object
     * @param modules module object
     */
    public void setModules(Module modules) {
        this.modules = modules;
    }

    /**
     * getter for module object
     * @return module object
     */
    public Module getModules() {
        return modules;
    }

    public String toString(){
        return studentId + " " + studentName +
                "\nModule 1: " + getModules().getMarks(0) +
                "\nModule 2: " + getModules().getMarks(1) +
                "\nModule 3: " + getModules().getMarks(2) +
                "\nTotal: " + getModules().getTotal() +
                "\nAverage: " + getModules().getAverage() +
                "\nGrade: " + getModules().getGrade();
    }
}
