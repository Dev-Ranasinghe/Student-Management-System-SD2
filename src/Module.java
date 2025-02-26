import java.util.Arrays;

public class Module {
    // double array for storing module marks
    double [] marks = new double[3];

    /**
     * Module class constructor
     * @param module1 marks
     * @param module2 marks
     * @param module3 marks
     */
    Module(double module1, double module2, double module3){
        this.marks[0] = module1;
        this.marks[1] = module2;
        this.marks[2] = module3;
    }

    /**
     * getter for module marks
     * @param index double array index
     * @return relevant module mark
     */
    public double getMarks(int index) {
        return marks[index];
    }

    /**
     * method for calculating total
     * @return total module marks
     */
    public double getTotal(){
        double total = 0.0;
        for(double mark : marks){
            total += mark;
        }
        return total;
    }

    /**
     * method for calculating average
     * @return module average
     */
    public double getAverage(){
        return getTotal()/3.0;
    }

    /**
     * method for calculating grade
     * @return module grade
     */
    public String getGrade(){
        double average = getAverage();
        if(average >= 80){
            return "Distinction";
        }
        else if(average >= 70){
            return "Merit";
        }
        else if(average >= 40){
            return "Pass";
        }
        else{
            return "Fail";
        }
    }

    public String toString(){
        return Arrays.toString(marks);
    }
}
