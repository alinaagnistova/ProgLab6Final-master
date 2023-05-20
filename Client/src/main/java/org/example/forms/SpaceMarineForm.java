package org.example.forms;

import org.example.utils.Readable;
import org.example.console.*;
import org.example.data.Chapter;
import org.example.data.Coordinates;
import org.example.data.SpaceMarine;
import org.example.utils.ExecuteFileManager;

import java.time.LocalDate;

public class SpaceMarineForm extends Form<SpaceMarine> {
    private final ReaderWriter console;
    private Readable readManager;
    private final UserInput scanner;

    public SpaceMarineForm(Console console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * Сконструировать новый элемент класса {@link SpaceMarine}
     *
     * @return объект класса {@link SpaceMarine}
     */
    @Override
    public SpaceMarine build() {
        LocalDate localDate = LocalDate.now();
        SpaceMarine spaceMarine = new SpaceMarine(
                        readManager.readName(),
                        readCoordinates(),
                        readManager.readHealth(),
                        localDate,
                        readManager.readCategory(),
                        readManager.readWeapon(),
                        readManager.readMeleeWeapon(),
                        readChapter()
                );
        return spaceMarine;
    }
    private Coordinates readCoordinates(){
        return new CoordinatesForm(console).build();
    }
    private Chapter readChapter(){
        return new ChapterForm(console).build();
    }
}
