package testbase;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class CopyPaste {

	private void copy()
	{
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String text = "value";
		StringSelection selection = new StringSelection(text);
		clipboard.setContents(selection, null);
	}
	
	private void paste() throws UnsupportedFlavorException, IOException
	{
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		DataFlavor flavor = DataFlavor.stringFlavor;
		String txt = (String) clipboard.getData(flavor);
		System.out.println(txt);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
