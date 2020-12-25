public class Record {
    public final int id;
    public final String surname;
    public final String subject;
    public final int points;

    public Record(int id, String surname, String subject, int points) {
        if (id < 0)
            throw new IllegalArgumentException("Номер зачетки должнен быть неотрицательным числом.");
        if (surname == null || surname.equals(""))
            throw new IllegalArgumentException("Фамилия должна состоять из одного или больше символов.");
        if (subject == null || subject.equals(""))
            throw new IllegalArgumentException("Название предмета должна состоять из одного или больше символов.");
        if (points < 1 || points > 10)
            throw new IllegalArgumentException("Оценка должна быть больше 0 и меньше 11.");
        this.id = id;
        this.surname = surname;
        this.subject = subject;
        this.points = points;
    }

    @Override
    public String toString() {
        return id + " " + surname + " " + subject + " " + points;
    }
}
