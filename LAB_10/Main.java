import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
                         f -> save(f, recordsToString(db.toRecords())),
                         f -> save(f, studentsToXml(db)),
                         f -> {
                             try {
                                 db.reset(read(f));
                             } catch (IOException | SAXException | ParserConfigurationException exception) {
                                 exception.printStackTrace();
                             }
                             return db.toRecords();
                         });
        d.setRecords(records);
        d.setStudents(db.getPerfectStudents());
    }

    private static ArrayList<Student> read(File file) throws IOException, SAXException, ParserConfigurationException {
        var result = new ArrayList<Student>();
        var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        var doc = builder.parse(file);
        var root = doc.getDocumentElement();
        var students = root.getChildNodes();
        for (int i = 0; i < students.getLength(); ++i) {
            var student = students.item(i);
            var props = student.getChildNodes();
            var id = props.item(0);
            var surname = props.item(1);
            var s = new Student(Integer.parseInt(id.getTextContent()), surname.getTextContent());
            var history = props.item(3).getChildNodes();
            for (int j = 0; j < history.getLength(); ++j) {
                var record = history.item(j).getChildNodes();
                s.history.put(record.item(0).getTextContent(),
                              Integer.parseInt(record.item(1).getTextContent()));
            }
            result.add(s);
        }
        return result;
    }

    private static void save(File file, String data) {
        try {
            var writer = new FileWriter(file);
            writer.write(data);
            writer.close();
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
            exception.printStackTrace();
        }
    }

    private static String studentsToXml(Iterable<Student> students) {
        var sb = new StringBuilder().append("<root>");
        for (var student : students)
            sb.append(student.toXmlString()).append('\n');
        return sb.append("</root>").toString();
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
            records = parser.parse(file);
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
