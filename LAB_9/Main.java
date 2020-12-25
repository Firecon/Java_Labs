import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var parser = new TxtParser<>(" [],\t", "//", Main::convert);
        var records = parseRecords(parser, new File("info.txt"));
        records.forEach(System.out::println);
        var db = new StudentDataBase();
        records.forEach(db::addRecord);
        System.out.println();
        db.forEach(System.out::println);
        System.out.println();
        db.getPerfectStudents().forEach(System.out::println);
        var d = new Demo(f -> parseRecords(parser, f),
                         s -> {
                             var record = parser.parse(s);
                             db.addRecord(record);
                             return record;
                         }, db::getPerfectStudents,
                            f -> {
                                try {
                                    var writer = new FileWriter(f);
                                    writer.write(recordsToString(db.toRecords()));
                                    writer.close();
                                } catch (IOException exception) {
                                    JOptionPane.showMessageDialog(null, exception.getMessage());
                                    exception.printStackTrace();
                                }
                            }
                         );
        d.setRecords(records);
        d.setStudents(db.getPerfectStudents());
    }

    private static String recordsToString(ArrayList<Record> records) {
        var sb = new StringBuilder();
        for (var record : records)
            sb.append(record).append('\n');
        return sb.toString();
    }

    private static ArrayList<Record> parseRecords(TxtParser<Record> parser, File file) {
        var records = new ArrayList<Record>();
        try {
            records = parser.parse(new File("info.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }

    private static Record convert(ArrayList<String> params) {
        if (params == null || params.isEmpty()) return null;
        if (params.size() != 4)
            throw new IllegalArgumentException("Запись должна состоять из 4 полей.");
        var id = Integer.parseInt(params.get(0));
        var surname = params.get(1);
        var subject = params.get(2);
        var points = Integer.parseInt(params.get(3));
        return new Record(id, surname, subject, points);
    }
}
