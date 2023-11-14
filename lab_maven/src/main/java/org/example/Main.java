import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        // создание объекта exam
        Exam exam = new Exam("Math", 90);

        // создание объекта ObjectMapper для работы с JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // преобразование объекта exam в JSON строку
        String json = objectMapper.writeValueAsString(exam);
        System.out.println(json);

        // преобразование JSON строки в объект Exam
        Exam examFromJson = objectMapper.readValue(json, Exam.class);
        System.out.println(examFromJson);
    }

    static class Exam {
        private String subject;
        private int score;

        public Exam() {}

        public Exam(String subject, int score) {
            this.subject = subject;
            this.score = score;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Exam{" +
                    "subject='" + subject + '\'' +
                    ", score=" + score +
                    '}';
        }
    }
}