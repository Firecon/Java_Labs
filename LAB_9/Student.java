import java.util.ArrayList;
import java.util.HashMap;

public class Student {
    public final int id;
    public final String surname;
    public final HashMap<String, Integer> history;

    public Student(int id, String surname) {
        this.id = id;
        this.surname = surname;
        this.history = new HashMap<>();
    }

    public ArrayList<Record> tpRecords() {
        var result = new ArrayList<Record>();
        history.forEach((key, val) -> result.add(new Record(id, surname, key, val)));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student other = (Student) o;
        if (id != other.id) return false;
        if (!surname.equals(other.surname))
            throw new RuntimeException("Студенты с разными фамилиями не могут иметь одинаковый номер зачетки.");
        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return id + " " + surname + " " + history;
    }
}
