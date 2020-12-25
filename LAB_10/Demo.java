import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Demo extends JFrame {
    private final JTextPane leftLabel;
    private final JTextPane rightLabel;


    public void setRecords(Iterable<Record> records) {
        var sb = new StringBuilder();
        for (var record : records) sb.append(record).append('\n');
        if (sb.length() > 0)
            leftLabel.setText(sb.substring(0, sb.length() - 1));
    }
    public void setStudents(Iterable<Student> students) {
        var sb = new StringBuilder();
        for (var student : students) sb.append(student).append('\n');
        if (sb.length() > 0)
            rightLabel.setText(sb.substring(0, sb.length() - 1));
    }

    public Demo(Function<File, Iterable<Record>> recordsGetter,
                Function<String, Record> additionGetter,
                Supplier<Iterable<Student>> studentSupplier,
                Consumer<File> txtSaver,
                Consumer<File> xmlSaver,
                Function<File, Iterable<Record>> xmlRecordsGetter) {
        super("Demo");
        setLayout(new GridLayout(1, 2));
        leftLabel = new JTextPane();
        leftLabel.setEditable(false);
        rightLabel = new JTextPane();
        rightLabel.setEditable(false);
        add(new JScrollPane(leftLabel));
        add(new JScrollPane(rightLabel));
        setMinimumSize(new Dimension(400, 300));
        setJMenuBar(createMenuBar(createOpenTxtListener(recordsGetter),
                                  createSaveListener(txtSaver),
                                  createSaveListener(xmlSaver),
                                  createAdditionListener(additionGetter),
                                  createQueryListener(studentSupplier),
                                  createOpenXmlListener(xmlRecordsGetter)));
        setVisible(true);
    }

    private ActionListener createOpenTxtListener(Function<File, Iterable<Record>> recordsGetter) {
        return e -> {
            try {
                var file = openFile("txt");
                setRecords(recordsGetter.apply(file));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
                exception.printStackTrace();
            }
        };
    }

    private ActionListener createOpenXmlListener(Function<File, Iterable<Record>> recordsGetter) {
        return e -> {
            try {
                var file = openFile("xml");
                setRecords(recordsGetter.apply(file));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
                exception.printStackTrace();
            }
        };
    }

    private ActionListener createSaveListener(Consumer<File> saver) {
        return e -> {
            try {
                var file = saveFile();
                saver.accept(file);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
                exception.printStackTrace();
            }
        };
    }

    private ActionListener createAdditionListener(Function<String, Record> recordsGetter) {
        return e -> {
            try {
                var newRecord = recordsGetter.apply(JOptionPane.showInputDialog("Новая запись"));
                if (newRecord == null) return;
                leftLabel.setText(leftLabel.getText() + "\n" + newRecord);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
                exception.printStackTrace();
            }
        };
    }

    private ActionListener createQueryListener(Supplier<Iterable<Student>> studentSupplier) {
        return e -> {
            try {
                setStudents(studentSupplier.get());
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
                exception.printStackTrace();
            }
        };
    }

    private static JMenuBar createMenuBar(ActionListener openListener,
                                          ActionListener saveListener,
                                          ActionListener saveXmlListener,
                                          ActionListener additionListener,
                                          ActionListener listingListener,
                                          ActionListener openXmlListener) {
        var bar = new JMenuBar();

        var menu = new JMenu("Файл");
        var item = new JMenuItem("Открыть");
        item.addActionListener(openListener);
        menu.add(item);
        item = new JMenuItem("Открыть xml");
        item.addActionListener(openXmlListener);
        menu.add(item);
        item = new JMenuItem("Сохранить txt");
        item.addActionListener(saveListener);
        menu.add(item);
        item = new JMenuItem("Сохранить xml");
        item.addActionListener(saveXmlListener);
        menu.add(item);
        bar.add(menu);

        menu = new JMenu("Изменить");
        item = new JMenuItem("Добавить");
        item.addActionListener(additionListener);
        menu.add(item);
        bar.add(menu);

        menu = new JMenu("Запрос");
        item = new JMenuItem("Список отличников");
        item.addActionListener(listingListener);
        menu.add(item);
        bar.add(menu);

        return bar;
    }

    private static File openFile(String extension) {
        var jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter(extension, extension));
        jfc.setDialogTitle("Выбрать файл для открытия");
        var input = jfc.showOpenDialog(null);
        if (input == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        } else {
            return null;
        }
    }

    private static File saveFile() {
        var jfc = new JFileChooser();
        jfc.setDialogTitle("Выбрать файл для сохранения");
        var input = jfc.showSaveDialog(null);
        if (input == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        }
        return null;
    }
}
