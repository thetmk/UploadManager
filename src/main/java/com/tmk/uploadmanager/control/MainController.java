package com.tmk.uploadmanager.control;

import com.tmk.uploadmanager.model.CollectionList;
import com.tmk.uploadmanager.model.CollectionListModel;
import com.tmk.uploadmanager.model.Serializer;
import com.tmk.uploadmanager.model.Upload;
import com.tmk.uploadmanager.model.UploadCollection;
import com.tmk.uploadmanager.view.MainFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FilenameUtils;

/**
 * Controls the main gui window
 *
 * @author tmk
 */
public class MainController {

	/**
	 * GUI Class instance
	 */
	private MainFrame mainFrame;

	/**
	 * Non-static class with nested classes for certain ActionListeners
	 */
	private MainFrameActions actions;

	/**
	 * CollectionList instance which stores UploadCollection instances
	 */
	private CollectionList collection;

	/**
	 * Constructor
	 */
	public MainController() {
		try {
			SwingUtilities.invokeAndWait(() -> {
				mainFrame = new MainFrame();
				setUpMainGui();
				setUpActionListeners();
			});
		} catch (InterruptedException | InvocationTargetException ex) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
		}
		collection = new CollectionList();
	}

	/**
	 * Sets some basic GUI stuff
	 */
	private void setUpMainGui() {
		mainFrame.setTitle("Upload Manager");
		mainFrame.setMinimumSize(new Dimension(1100, 600));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	/**
	 * Add ActionListeners to all buttons
	 */
	private void setUpActionListeners() {
		actions = new MainFrameActions(this);

		mainFrame.getButton("cSave").addActionListener(actions.new SaveBtnAction());
		mainFrame.getButton("cLoad").addActionListener(actions.new LoadBtnAction());
		mainFrame.getButton("cMkdir").addActionListener(actions.new CopyMkdirBtnAction());

		mainFrame.getButton("uName").addActionListener(actions.new CopyNameBtnAction());
		mainFrame.getButton("uTags").addActionListener(actions.new CopyTagsBtnAction());
		mainFrame.getButton("uCopy").addActionListener(actions.new CopyBtnAction());
		mainFrame.getButton("uSave").addActionListener(actions.new SaveUploadBtnAction());

		mainFrame.getButton("tEdit").addActionListener(actions.new EditTemplateBtnAction());

		mainFrame.getButton("cAdd").addActionListener(actions.new AddUploadBtnAction());
		mainFrame.getButton("cRemove").addActionListener(actions.new RemoveUploadBtnAction());

		mainFrame.getButton("clAdd").addActionListener(actions.new AddCollectionBtnAction());
		mainFrame.getButton("clRemove").addActionListener(actions.new RemoveCollectionBtnAction());

		mainFrame.getButton("cName").addActionListener(actions.new NameSetBtnAction());
		mainFrame.getButton("cTags").addActionListener(actions.new TagsSetBtnAction());

		mainFrame.getUploadList().addListSelectionListener(actions.new UploadSelectionListener());
		mainFrame.getCollectionList().addListSelectionListener(actions.new CollectionSelectionListener());
	}

	/**
	 * Update the title of an upload. Refreshes the Upload List
	 *
	 * @param coll UploadCollection which contains the upload
	 * @param oldName old title
	 * @param newName new title
	 */
	public void updateUploadTitle(UploadCollection coll, String oldName, String newName) {
		if (coll.contains(oldName) && !coll.contains(newName)) {
			Upload up = coll.getUpload(oldName);
			up.setTitle(newName);

			coll.removeUpload(oldName);
			coll.addUpload(newName, up);

			SwingUtilities.invokeLater(() -> {
				CollectionListModel model = mainFrame.getUploadListModel();
				model.removeElement(oldName);
				model.addElement(newName);
				model.sort();
			});
		}
	}

	/**
	 * Update the name of an UploadCollection. Refreshes the Collection List
	 *
	 * @param oldName old name
	 * @param newName new name
	 */
	public void updateCollectionName(String oldName, String newName) {
		if (collection.contains(oldName) && !collection.contains(newName)) {
			UploadCollection uc = collection.getCollection(oldName);
			uc.setName(newName);

			collection.removeCollection(oldName);
			collection.addCollection(newName, uc);

			SwingUtilities.invokeLater(() -> {
				CollectionListModel model = mainFrame.getCollectionListModel();
				model.removeElement(oldName);
				model.addElement(newName);
				model.sort();
			});
		}
	}

	/**
	 * Open a FileChooser to determine a file to save a serialized representation
	 * of the current CollectionList instance to. Called when the save button is
	 * clicked
	 */
	public void saveAction() {
		SwingUtilities.invokeLater(() -> {
			JFileChooser fileChooser = new JFileChooser();

			FileFilter filter = new FileNameExtensionFilter("Upload collection (*.ucoll)", new String[]{"ucoll"});
			fileChooser.addChoosableFileFilter(filter);
			fileChooser.setFileFilter(filter);

			int returnVal = fileChooser.showSaveDialog(mainFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (file == null) {
					return;
				}
				String path = file.getAbsolutePath();

				if (FilenameUtils.getExtension(file.getName()).isEmpty() || !FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("ucoll")) {
					path = path.concat(".ucoll");
				}
				String filePath = path;

				Thread thread = new Thread(() -> {
					try {
						Serializer.save(collection, filePath);
					} catch (IOException ex) {
						Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
					}
				});
				thread.start();
			}
		});
	}

	/**
	 * Opens a FileChooser to load a serialized CollectionList. Called when the
	 * load button is clicked
	 */
	public void loadAction() {
		SwingUtilities.invokeLater(() -> {
			JFileChooser fileChooser = new JFileChooser();

			FileFilter filter = new FileNameExtensionFilter("Upload collection (*.ucoll)", new String[]{"ucoll"});
			fileChooser.addChoosableFileFilter(filter);
			fileChooser.setFileFilter(filter);

			int returnVal = fileChooser.showOpenDialog(mainFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (file == null) {
					return;
				} else if (!file.exists() || !file.canRead()) {
					throw new RuntimeException(String.format("Unable to read data file %s", file.getAbsolutePath()));
				}

				String filePath = file.getAbsolutePath();

				Thread thread = new Thread(() -> {
					try {
						collection = Serializer.load(filePath);
						SwingUtilities.invokeLater(() -> {
							updateView(); // Update the collection list on the main frame
						});
					} catch (IOException ex) {
						Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
					}
				});
				thread.start();
			}
		});
	}

	/**
	 * Saves the template value to the current collection. Called when the
	 * template button is clicked
	 */
	public void editTemplateAction() {
		getSelectedCollection().setTemplate(mainFrame.getTemplate().getText());
	}

	/**
	 * Returns the currently selected Upload
	 *
	 * @return selected upload
	 */
	public Upload getSelectedUpload() {
		JList uploadList = mainFrame.getUploadList();
		String current = (String) uploadList.getSelectedValue();
		UploadCollection uc = getSelectedCollection();

		if (uc != null && uc.contains(current)) {
			return uc.getUpload(current);
		}
		return null;
	}

	/**
	 * Returns the currently selected UploadCollection
	 *
	 * @return selected collection
	 */
	public UploadCollection getSelectedCollection() {
		JList collectionList = mainFrame.getCollectionList();
		String current = (String) collectionList.getSelectedValue();

		if (collection.contains(current)) {
			return collection.getCollection(current);
		}

		return null;
	}

	/**
	 * Updates the Upload instance of the currently selected upload with values
	 * from the GUI. Called when the Save button is clicked.
	 */
	public void saveUploadAction() {
		SwingUtilities.invokeLater(() -> {
			Upload up = getSelectedUpload();

			if (up != null) {
				JTextField title = mainFrame.getTextField("uTitle");
				if (!up.getTitle().equals(title.getText())) {
					updateUploadTitle(getSelectedCollection(), up.getTitle(), title.getText());
				}

				up.setCover(mainFrame.getTextField("uCover").getText());
				up.setTags(mainFrame.getTextField("uTags").getText());
				up.setImage(mainFrame.getTextField("uImage").getText());
				up.setDescription(mainFrame.getDescription().getText());
			}
		});
	}

	/**
	 * Asks for a collection name and adds a new collection with that name.
	 */
	public void addCollectionAction() {
		SwingUtilities.invokeLater(() -> {
			String name = JOptionPane.showInputDialog("Please enter the collection title");
			if (name != null) {
				name = name.trim();
			}
			CollectionListModel model = mainFrame.getCollectionListModel();

			if (!collection.contains(name)) {
				UploadCollection uc = new UploadCollection(name, "", "");
				collection.addCollection(name, uc);

				model.addElement(name);
				model.sort();
			}
		});
	}

	/**
	 * Removes the currently selected collection
	 */
	public void removeCollectionAction() {
		SwingUtilities.invokeLater(() -> {
			UploadCollection selected = getSelectedCollection();

			if (collection.contains(selected.getName())) {
				collection.removeCollection(selected.getName());
				SwingUtilities.invokeLater(() -> {
					mainFrame.getCollectionList().clearSelection();
					CollectionListModel model = mainFrame.getCollectionListModel();
					model.removeElement(selected.getName());
				});
			}
		});
	}

	/**
	 * Asks for a new upload name and adds a new Upload with that name
	 */
	public void addUploadAction() {
		SwingUtilities.invokeLater(() -> {
			String name = JOptionPane.showInputDialog("Please enter the upload title");
			if (name != null) {
				name = name.trim();
			}
			CollectionListModel model = mainFrame.getUploadListModel();

			if (!getSelectedCollection().contains(name)) {
				Upload up = new Upload(name, "", "", "", "");
				getSelectedCollection().addUpload(name, up);

				model.addElement(name);
				model.sort();
			}
		});
	}

	/**
	 * Remove the currently selected upload
	 */
	public void removeUploadAction() {
		SwingUtilities.invokeLater(() -> {
			Upload u = getSelectedUpload();
			UploadCollection uc = getSelectedCollection();

			if (u != null && uc != null) {
				uc.removeUpload(u.getTitle());
				SwingUtilities.invokeLater(() -> {
					mainFrame.getUploadList().clearSelection();
					CollectionListModel model = mainFrame.getUploadListModel();
					model.removeElement(u.getTitle());
				});
			}
		});
	}

	/**
	 * Fill the collection template with data from the current upload and save it
	 * to clipboard
	 */
	public void copyAction() {
		SwingUtilities.invokeLater(() -> {
			Upload up = getSelectedUpload();

			if (up != null) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection s = new StringSelection(getSelectedCollection().format(up));
				clipboard.setContents(s, s);
			}
		});
	}

	/**
	 * Copy a combination of the current collection name and current upload name
	 * into clipboard
	 */
	public void copyNameAction() {
		SwingUtilities.invokeLater(() -> {
			UploadCollection uc = getSelectedCollection();
			Upload up = getSelectedUpload();

			if (up != null) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection s = new StringSelection(String.format("%s - %s", uc.getName(), up.getTitle()));
				clipboard.setContents(s, s);
			}
		});
	}

	/**
	 * Concatenates and copies the current collection's und upload's tags to
	 * clipboard
	 */
	public void copyTagsAction() {
		SwingUtilities.invokeLater(() -> {
			UploadCollection uc = getSelectedCollection();
			Upload up = getSelectedUpload();

			if (up != null) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection s = new StringSelection(String.format("%s %s", uc.getTags(), up.getTags()));
				clipboard.setContents(s, s);
			}
		});
	}

	/**
	 * Copy mkdir commands for each upload in the current collection to clipboard
	 */
	public void copyMkdirAction() {
		SwingUtilities.invokeLater(() -> {
			JList collectionList = mainFrame.getUploadList();
			UploadCollection col = getSelectedCollection();
			StringBuilder mkdir = new StringBuilder();

			for (String key : col.getCollection().keySet()) {
				Upload up = col.getUpload(key);
				mkdir.append(String.format("mkdir \"%s - %s\"\n", col.getName(), up.getTitle()));
			}
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection s = new StringSelection(mkdir.toString());
			clipboard.setContents(s, s);
		});
	}

	/**
	 * Update the name of the current collection with the value from the GUI
	 */
	public void nameSetAction() {
		UploadCollection uc = getSelectedCollection();
		String newName = mainFrame.getTextField("cName").getText();
		updateCollectionName(uc.getName(), newName);
	}

	/**
	 * Update the tags of the current collection with the value from the GUI
	 */
	public void tagsSetAction() {
		UploadCollection uc = getSelectedCollection();
		String tags = mainFrame.getTextField("cTags").getText();
		uc.setTags(tags);
	}

	/**
	 * Updates the GUI when a collection is selected
	 *
	 * @param idx Index of the JList element that was selected
	 */
	public void collectionSelected(int idx) {
		// Update GUI
		SwingUtilities.invokeLater(() -> {
			CollectionListModel model = mainFrame.getUploadListModel();

			JTextField title = mainFrame.getTextField("uTitle");
			JTextField tags = mainFrame.getTextField("uTags");
			JTextField cover = mainFrame.getTextField("uCover");
			JTextField image = mainFrame.getTextField("uImage");
			JTextArea desc = mainFrame.getDescription();

			if (idx < 0 || idx >= model.capacity()) {
				if (idx == -1) {
					title.setText(null);
					tags.setText(null);
					cover.setText(null);
					image.setText(null);
					desc.setText(null);
					model.clear();
				}
				return;
			}

			UploadCollection uc = getSelectedCollection();

			if (uc != null) {
				model.clear();
				for (String key : uc.getCollection().keySet()) {
					model.addElement(key);
				}
				model.sort();

				mainFrame.getTemplate().setText(uc.getTemplate());
				mainFrame.getTextField("cName").setText(uc.getName());
				mainFrame.getTextField("cTags").setText(uc.getTags());
			}
		});
	}

	/**
	 * Updates the GUI when an upload is selected
	 *
	 * @param idx Index of the JList element that was selected
	 */
	public void uploadSelected(int idx) {
		// Update GUI
		SwingUtilities.invokeLater(() -> {
			CollectionListModel model = mainFrame.getUploadListModel();

			JTextField title = mainFrame.getTextField("uTitle");
			JTextField tags = mainFrame.getTextField("uTags");
			JTextField cover = mainFrame.getTextField("uCover");
			JTextField image = mainFrame.getTextField("uImage");
			JTextArea desc = mainFrame.getDescription();

			if (idx < 0 || idx >= model.capacity()) {
				if (idx == -1) {
					title.setText(null);
					tags.setText(null);
					cover.setText(null);
					image.setText(null);
					desc.setText(null);
				}
				return;
			}

			String selected = (String) model.get(idx);
			UploadCollection col = getSelectedCollection();

			if (col.contains(selected)) {
				Upload up = col.getUpload(selected);
				title.setText(up.getTitle());
				tags.setText(up.getTags());
				cover.setText(up.getCover());
				image.setText(up.getImage());
				desc.setText(up.getDescription());
			}
		});
	}

	/**
	 * Replaces the contents of the collection JList
	 */
	public void updateView() {
		SwingUtilities.invokeLater(() -> {
			CollectionListModel cmodel = mainFrame.getCollectionListModel();
			cmodel.clear();
			for (String key : collection.getList().keySet()) {
				cmodel.addElement(key);
			}
			cmodel.sort();
		});
	}
}
