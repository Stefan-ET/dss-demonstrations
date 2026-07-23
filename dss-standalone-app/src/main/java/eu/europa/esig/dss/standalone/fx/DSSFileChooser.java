package eu.europa.esig.dss.standalone.fx;

import eu.europa.esig.dss.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;

public class DSSFileChooser {

    private final FileChooser fileChooser;

    private SimpleObjectProperty<File> directoryProperty;

    public DSSFileChooser(String title, String fileExtensionFilter, String... fileExtensions) {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle(title);
        if (Utils.isStringNotEmpty(fileExtensionFilter) && Utils.isArrayNotEmpty(fileExtensions)) {
            this.fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(fileExtensionFilter, fileExtensions));
        }
        // NOTE: pattern changed from "*" to "*.*" — bare "*" is not parsed correctly by
        // JavaFX's native FileChooser on macOS (Glass toolkit), causing all files to
        // appear disabled/greyed-out in the NSOpenPanel. "*.*" is the standard JavaFX
        // idiom for "match all files" and works correctly across Windows/Linux/macOS.
        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
    }

    public void bindDirectoryProperty(SimpleObjectProperty<File> directoryProperty) {
        this.directoryProperty = directoryProperty;
        fileChooser.initialDirectoryProperty().bindBidirectional(directoryProperty);
    }

    public File showOpenDialog(Window window) {
        File file = fileChooser.showOpenDialog(window);
        if (file != null && directoryProperty != null) {
            directoryProperty.setValue(file.getParentFile());
        }
        return file;
    }

    public List<File> showOpenMultipleDialog(Window window) {
        List<File> files = fileChooser.showOpenMultipleDialog(window);
        if (Utils.isCollectionNotEmpty(files) && directoryProperty != null) {
            directoryProperty.setValue(files.get(0).getParentFile());
        }
        return files;
    }
}
