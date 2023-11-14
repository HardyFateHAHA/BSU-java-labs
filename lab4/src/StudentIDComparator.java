import java.util.Comparator;

public class StudentIDComparator implements Comparator<GradeBook> {
    @Override
    public int compare(GradeBook gb1, GradeBook gb2) {
        return gb1.getStudentID().compareTo(gb2.getStudentID());
    }
}
