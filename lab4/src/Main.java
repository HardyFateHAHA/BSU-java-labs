import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class GradeBook {
    private String lastName;
    private String firstName;
    private String middleName;
    private int course;
    private String group;
    private String studentID;
    private List<Session> sessions;

    public GradeBook(String lastName, String firstName, String middleName, int course, String group, String studentID) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
        this.studentID = studentID;
        this.sessions = new ArrayList<>();
    }

    public void addSession(int sessionNumber, String subject, int grade) {
        Session session = new Session(sessionNumber, subject, grade);
        sessions.add(session);
    }

    public boolean isExcellentStudent() {
        for (Session session : sessions) {
            if (session.getGrade() < 9) {
                return false;
            }
        }
        return true;
    }

    public boolean hasPassedAllExams() {
        for (Session session : sessions) {
            if (!session.getSubject().equals("Зачет")) {
                if (session.getGrade() < 9) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getInfo() {
        return lastName + " " + firstName + " " + middleName + ", Курс: " + course + " Группа: " + group + " Номер студенческого " + studentID;
    }

    public String getExcellentStudentInfo() {
        StringBuilder info = new StringBuilder(getInfo() + " - Отличник");
        for (Session session : sessions) {
            info.append("\nСессия ").append(session.getSessionNumber()).append(": ");
            info.append(session.getSubject()).append(" - ").append(session.getGrade());
        }
        return info.toString();
    }

    public String getStudentID() {
        return studentID;
    }

    public static class Session {
        private int sessionNumber;
        private String subject;
        private int grade;

        public Session(int sessionNumber, String subject, int grade) {
            this.sessionNumber = sessionNumber;
            this.subject = subject;
            this.grade = grade;
        }

        public int getSessionNumber() {
            return sessionNumber;
        }

        public String getSubject() {
            return subject;
        }

        public int getGrade() {
            return grade;
        }
    }
}
public class Main {
    public static void main(String[] args) {
        List<GradeBook> gradeBooks = new ArrayList<>();
        try{
            // Чтение из input.txt
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String line;
            GradeBook currentGradeBook = null;
            int currentSessionNumber = -1;

            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) {
                    if (currentGradeBook != null) {
                        // Пустая строка является разделителем между записями о студентах
                        // Добавьте текущую GradeBook в список и сбросьте текущего студента
                        gradeBooks.add(currentGradeBook);
                        currentGradeBook = null;
                        currentSessionNumber = -1;
                    }
                    continue; // Пропустить пустые строки
                }
                if (trimmedLine.startsWith("Сессия")) {
                    currentSessionNumber = Integer.parseInt(trimmedLine.substring(7).trim());
                } else if (currentGradeBook == null) {
                    String[] studentInfo = trimmedLine.split(" ");
                    String lastName = studentInfo[0];
                    String firstName = studentInfo[1];
                    String middleName = studentInfo[2];
                    int course = Integer.parseInt(studentInfo[4]);
                    String group = studentInfo[6];
                    String studentID = studentInfo[8];
                    currentGradeBook = new GradeBook(lastName, firstName, middleName, course, group, studentID);
                } else {
                    String[] sessionInfo = trimmedLine.split(" ");
                    String subject = sessionInfo[0];
                    int grade = Integer.parseInt(sessionInfo[1]);
                    currentGradeBook.addSession(currentSessionNumber, subject, grade);
                }

                if (currentSessionNumber == 4) {
                    // The last session of a student is reached, add the GradeBook to the list
                    gradeBooks.add(currentGradeBook);
                    currentGradeBook = null;
                    currentSessionNumber = -1;
                }
            }
            reader.close();

            // Запись в output.txt
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            for (GradeBook gradeBook : gradeBooks) {
                if (gradeBook.isExcellentStudent() && gradeBook.hasPassedAllExams()) {
                    String info = gradeBook.getExcellentStudentInfo();
                    writer.write(info);
                    writer.newLine();
                }
            }
            writer.close();

            // Сортировка списка по номерам студенческого
            gradeBooks.sort(new StudentIDComparator());
            //int studentIDToFind = 2; // Номер студенческого для поиска
            int studentIDToFind = 2; // Номер студенческого для поиска
            GradeBook searchKey = new GradeBook("", "", "", 0, "" , ""); // Создаем пустой объект GradeBook
            searchKey.setStudentID(studentIDToFind); // Устанавливаем номер студенческого

// Создаем компаратор исходя из номера студенческого
            Comparator<GradeBook> studentIDComparator = new StudentIDComparator();

// Выполняем бинарный поиск
            int index = gradeBooks.binarySearch(gradeBooks, searchKey, studentIDComparator);

            if (index >= 0) {
                GradeBook foundStudent = gradeBooks.get(index);
                System.out.println("Найден студент: " + foundStudent.getInfo());
            } else {
                System.out.println("Студент с номером студенческого " + studentIDToFind + " не найден.");
            }
            /*int index = gradeBooks.binarySearch(gradeBooks, new GradeBook("", "", "", 0, "", studentIDToFind), new StudentIDComparator());

            if (index >= 0) {
                GradeBook foundStudent = gradeBooks.get(index);
                System.out.println("Найден студент: " + foundStudent.getInfo());
            } else {
                System.out.println("Студент с номером студенческого " + studentIDToFind + " не найден.");
            }*/
            // Вывод отсортированного списка
            System.out.println("Отсортированный список по номерам студенческого:");
            for (GradeBook gradeBook : gradeBooks) {
                System.out.println(gradeBook.getInfo());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}