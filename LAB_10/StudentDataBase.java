import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDataBase implements Iterable<Student> {
    private final HashSet<Student> students = new HashSet<>();

    public void reset(ArrayList<Student> newStudents) {
        students.clear();
        students.addAll(newStudents);
    }

    public void addRecord(Record record) {
        if (record == null) return;
        for (var student : students) {
            if (student.id == record.id && student.surname.equals(record.surname)) {
                if (student.history.containsKey(record.subject))
                    throw new RuntimeException("Оценка по этому предмету уже выствалена.");
                student.history.put(record.subject, record.points);
                return;
            }
        }
        var newStudent = new Student(record.id, record.surname);
        newStudent.history.put(record.subject, record.points);
        students.add(newStudent);
    }

    public List<Student> getPerfectStudents() {
        return students.stream()
                       .filter(s -> s.history.values().stream().allMatch(p -> p >= 9))
                       .collect(Collectors.toList());
    }

    public ArrayList<Record> toRecords() {
        var result = new ArrayList<Record>();
        for (var elem : this) {
            result.addAll(elem.tpRecords());
        }
        return result;
    }

    @Override
    public Iterator<Student> iterator() {
        return students.iterator();
    }
}
