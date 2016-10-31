import java.io.File;

import javax.swing.filechooser.*;

/**
 * This class adds the .xlsx so an excel file can be made
 * @author Suhas
 */
public class FileTypeFilter extends FileFilter{
	
	private final String extension;
	private final String description;
	
	//Creating a file type filter to get file location
	
	public FileTypeFilter(String extension, String description) {
		this.extension = extension;
		this.description = description;
	}

	@Override
	public boolean accept(File file) {
		if(file.isDirectory()){
			return true;
		}
		return file.getName().endsWith(extension);
	}

	@Override
	public String getDescription() {
		return description + String.format(" (*%s)", extension);
	}

}
