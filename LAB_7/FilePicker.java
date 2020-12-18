package com.company;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.ImageFilter;
import java.io.File;
import java.util.function.Consumer;

public class FilePicker extends JPanel {
    private Consumer<File> loadFileConsumer;
    private Consumer<File> saveFileConsumer;

    public void setLoadFileConsumer(Consumer<File> loadFileConsumer) {
        this.loadFileConsumer = loadFileConsumer;
    }

    public void setSaveFileConsumer(Consumer<File> saveFileConsumer) {
        this.saveFileConsumer = saveFileConsumer;
    }


    public FilePicker(Consumer<File> loadFileConsumer, Consumer<File> saveFileConsumer) {
        super(new GridBagLayout());
        var loadBtn = new JButton("Открыть");
        var saveBtn = new JButton("Сохранить");
        loadBtn.addActionListener(e -> loadBtnListener());
        saveBtn.addActionListener(e -> saveBtnListener());
        add(loadBtn, new GridBagConstraints());
        add(saveBtn, new GridBagConstraints());
        setLoadFileConsumer(loadFileConsumer);
        setSaveFileConsumer(saveFileConsumer);
    }

    public void loadBtnListener() {
        var jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter("Файлы изображений",
                                                      ImageIO.getReaderFileSuffixes()));
        jfc.setDialogTitle("Выбрать файл для открытия");
        var input = jfc.showOpenDialog(this);
        if (input == JFileChooser.APPROVE_OPTION) {
            loadFileConsumer.accept(jfc.getSelectedFile());
        } else {
            JOptionPane.showMessageDialog(this, "Неверный формат файла.");
        }
    }

    public void saveBtnListener() {
        var jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter("Файлы изображений",
                                                      ImageIO.getReaderFileSuffixes()));
        jfc.setDialogTitle("Выбрать файл для сохранения");
        var input = jfc.showSaveDialog(this);
        if (input == JFileChooser.APPROVE_OPTION) {
            saveFileConsumer.accept(jfc.getSelectedFile());
        } else {
            JOptionPane.showMessageDialog(this, "Неверный формат файла.");
        }
    }
}
