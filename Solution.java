import java.util.*;

public class Solution {
    public static class Student {
        int id; String name; double cgpa;
        public Student(int id, String name, double cgpa) {
            this.id = id;
            this.name = name;
            this.cgpa = cgpa;
        }

        public int getID() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public double getCGPA() {
            return this.cgpa;
        }
    }
    public static class Priorities {
        PriorityQueue<Student> mypq;

        public Priorities() { // mark, john, shafaet
            // shafaet
            // shafaet 
            // ashley shafaet 
            // ashley shafaet maria 
            // anik ashley shafaet maria 
            // 
            this.mypq = new PriorityQueue<>((Student a, Student b) -> {
                if (a.getCGPA() != b.getCGPA()) {
                    if (b.getCGPA() >= a.getCGPA()) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else if (!a.getName().equals(b.getName())) {
                    return (a.getName().compareTo(b.getName()));
                } else {
                    return (a.getID() - b.getID());
                }
            });
        }

        List<Student> getStudents(List<String> events) {
            for (int i=0; i<events.size(); i++) {
                String temp = events.get(i);
                String[] splittedString = temp.split(" ");
                if (splittedString[0].equals("ENTER")) {
                    this.mypq.offer(new Student(Integer.parseInt(splittedString[3]), splittedString[1], Double.parseDouble(splittedString[2])));
                } else {
                    this.mypq.poll();
                }
            }
            
            List<Student> answer = new ArrayList<>();
            for (Student s: mypq) {
                answer.add(s);
            }
            return answer;
        }

    }
    private final static Scanner scan = new Scanner(System.in);
    private final static Priorities priorities = new Priorities();
    
    public static void main(String[] args) {
        int totalEvents = Integer.parseInt(scan.nextLine());    
        List<String> events = new ArrayList<>();
        
        while (totalEvents-- != 0) {
            String event = scan.nextLine();
            events.add(event);
        }
        
        List<Student> students = priorities.getStudents(events);
        
        if (students.isEmpty()) {
            System.out.println("EMPTY");
        } else {
            for (Student st: students) {
                System.out.println(st.getName());
            }
        }
    }
}
