import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class test {

    public class Person {
        String name;
        int age;
        
        public Person(String n, int a) {
            this.name = n;
            this.age = a;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(name, person.name);
        }    

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
    
        public static void main (String[] args) {
        test testvar = new test();
        Set<Person> personSet = new HashSet<>();
        Person person1 = testvar.new Person("aman", 20);
        Person person2 = testvar.new Person("aman", 20);  // Fixed: Using testvar.new to create Person instance
        personSet.add(person1);
        personSet.add(person2);
            
        System.out.println(personSet.size());
    }
}
