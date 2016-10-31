import java.io.*;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * This class exports a table into an excel file
 * @author Suhas
 */
public class ExcelExporter
{
	ExcelExporter(){}
			
	/*
	 * creating an excel document and adding data from database
	 */
	public void exportTable(JTable table, File file) throws IOException
	{
		TableModel model=table.getModel();
		FileWriter out=new FileWriter(file);
		BufferedWriter bw=new BufferedWriter(out);
		for(int i=0;i<model.getColumnCount();i++)
		{
			bw.write(model.getColumnName(i)+"\t");
		}
		bw.write("\n");
		
		for(int i=0;i<model.getRowCount();i++)
		{
			for(int j=0;j<model.getColumnCount();j++)
			{
				bw.write(model.getValueAt(i,j).toString()+"\t");
			}
			bw.write("\n");
		}
		bw.close();
	}
}
