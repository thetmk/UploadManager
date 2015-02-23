package com.tmk.uploadmanager.view;

import com.tmk.uploadmanager.model.CollectionListModel;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame {

	private JPanel leftPanel;
	private JPanel collectionListPanel;
	private JPanel uploadListPanel;
	private JPanel uploadSettingsPanel;
	private JPanel buttonPanel;
	private JPanel templatePanel;
	private JPanel namePanel;
	private MigLayout mainLayout;
	private MigLayout leftLayout;
	private MigLayout nameLayout;
	private MigLayout collectionListLayout;
	private MigLayout uploadListLayout;
	private MigLayout uploadSettingsLayout;
	private MigLayout buttonPanelLayout;
	private MigLayout templatePanelLayout;
	private CollectionListModel collectionListModel;
	private CollectionListModel uploadListModel;
	private JList collectionList;
	private JList uploadList;
	private JScrollPane collectionListScrollPane;
	private JScrollPane uploadListScrollPane;
	private JScrollPane templateScrollPane;
	private JScrollPane descriptionScrollPane;
	private JTextArea template;
	private JTextArea description;
	private HashMap<String, JButton> buttonList;
	private HashMap<String, JTextField> textFieldList;
	private HashMap<String, JLabel> labelList;

	public MainFrame() {
		super();

		buttonList = new HashMap<>();
		textFieldList = new HashMap<>();
		labelList = new HashMap<>();

		mainLayout = new MigLayout("fill", "", "[][][min!]");
		setLayout(mainLayout);

		initLeftPanel();
		initUploadSettingsPanel();
		initButtonPanel();
		initTemplatePanel();

		add(leftPanel, "dock west, grow 40, width 500::600");
		add(uploadSettingsPanel, "growx 80, top, wrap");
		add(templatePanel, "grow, height 200::, wrap");
		add(buttonPanel, "align right, bottom, wrap");
	}

	private void initLeftPanel() {
		initCollectionList();
		initUploadList();
		leftLayout = new MigLayout("fill, insets 0", "[][]", "[min!][]");
		leftPanel = new JPanel(leftLayout);

		nameLayout = new MigLayout("fillx, insets 0", "[min!][][min!]", "");
		namePanel = new JPanel(nameLayout);
		namePanel.setBorder(new TitledBorder("Collection Settings"));
		namePanel.add(addLabel("cName", "Name"), "top, gaptop 2, height 25, width 45::, shrinky");
		namePanel.add(addTextField("cName", ""), "width 100::, growx");
		namePanel.add(addButton("cName", "Set"), "shrinkx, right, wrap");
		namePanel.add(addLabel("cTags", "Tags"), "top, gaptop 2, height 25, width 45::, shrinky");
		namePanel.add(addTextField("cTags", ""), "width 100::, growx");
		namePanel.add(addButton("cTags", "Set"), "shrinkx, right");
		leftPanel.add(namePanel, "spanx, right, growx, shrinky, top, wrap");
		leftPanel.add(collectionListPanel, "grow, spany");
		leftPanel.add(uploadListPanel, "grow, spany, wrap");
	}

	private void initCollectionList() {
		collectionListLayout = new MigLayout("fill, insets 0", "[]", "[][min!]");
		collectionListPanel = new JPanel(collectionListLayout);
		collectionListPanel.setBorder(new TitledBorder("Collections"));

		collectionListModel = new CollectionListModel();
		collectionList = new JList(collectionListModel);

		collectionListScrollPane = new JScrollPane(collectionList);
		collectionListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		collectionListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		collectionListPanel.add(collectionListScrollPane, "grow, wrap");
		collectionListPanel.add(addButton("clAdd", "Add"), "split 2, align center");
		collectionListPanel.add(addButton("clRemove", "Remove"), "");
	}

	private void initUploadList() {
		uploadListLayout = new MigLayout("fill, insets 0", "[]", "[][min!]");
		uploadListPanel = new JPanel(uploadListLayout);
		uploadListPanel.setBorder(new TitledBorder("Uploads"));

		uploadListModel = new CollectionListModel();
		uploadList = new JList(uploadListModel);

		uploadListScrollPane = new JScrollPane(uploadList);
		uploadListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		uploadListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		uploadListPanel.add(uploadListScrollPane, "grow, wrap");
		uploadListPanel.add(addButton("cAdd", "Add"), "split 2, align center");
		uploadListPanel.add(addButton("cRemove", "Remove"), "");
	}

	private void initUploadSettingsPanel() {
		uploadSettingsLayout = new MigLayout("fillx, insets 0", "[]", "[min!][min!][min!][min!]");
		uploadSettingsPanel = new JPanel(uploadSettingsLayout);
		uploadSettingsPanel.setBorder(new TitledBorder("Upload Details"));

		description = new JTextArea("");
		descriptionScrollPane = new JScrollPane(description);
		descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		descriptionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		uploadSettingsPanel.add(addLabel("uTitle", "Title"), "top, gaptop 2, height 25, width 40::, shrinky, split 2");
		uploadSettingsPanel.add(addTextField("uTitle", ""), "top, height 25, width 50:100:, growx, shrinky, span, wrap");

		uploadSettingsPanel.add(addLabel("uTags", "Tags"), "top, gaptop 2, height 25, width 40::, shrinky, split 2");
		uploadSettingsPanel.add(addTextField("uTags", ""), "top, height 25, width 50:100:, growx, shrinky, wrap");

		uploadSettingsPanel.add(addLabel("uCover", "Cover"), "top, gaptop 2, height 25, width 40::, shrinky, split 2");
		uploadSettingsPanel.add(addTextField("uCover", ""), "top, height 25, width 50:100:, growx, shrinky, wrap");

		uploadSettingsPanel.add(addLabel("uImage", "Image"), "top, gaptop 2, height 25, width 40::, shrinky, split 2");
		uploadSettingsPanel.add(addTextField("uImage", ""), "top, height 25, width 50:100:, growx, shrinky, wrap");

		uploadSettingsPanel.add(addLabel("uDesc", "Desc"), "top, gaptop 2, height 25, width 40::, shrinky, split 2");
		uploadSettingsPanel.add(descriptionScrollPane, "top, width 50:100:, height 100::, growx, wrap");

		uploadSettingsPanel.add(addButton("uName", "Copy complete title"), "split 4, right");
		uploadSettingsPanel.add(addButton("uTags", "Copy tags"), "right");
		uploadSettingsPanel.add(addButton("uCopy", "Copy filled template"), "right");
		uploadSettingsPanel.add(addButton("uSave", "Save"), "right");

	}

	private void initButtonPanel() {
		buttonPanelLayout = new MigLayout("");
		buttonPanel = new JPanel(buttonPanelLayout);

		buttonPanel.add(addButton("cMkdir", "Copy Mkdir"), "");
		buttonPanel.add(addButton("cLoad", "Load File"), "");
		buttonPanel.add(addButton("cSave", "Save File"), "");
	}

	private void initTemplatePanel() {
		templatePanelLayout = new MigLayout("fill, insets 0", "", "bottom");
		templatePanel = new JPanel(templatePanelLayout);
		templatePanel.setBorder(new TitledBorder("Collection Template"));

		template = new JTextArea("");
		templateScrollPane = new JScrollPane(template);
		templateScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		templateScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		templatePanel.add(templateScrollPane, "grow 200, push, wrap");
		templatePanel.add(addButton("tEdit", "Edit"), "right");
	}

	private JButton addButton(String key, String caption) {
		JButton btn = new JButton(caption);
		buttonList.put(key, btn);

		return btn;
	}

	public JButton getButton(String key) {
		return buttonList.get(key);
	}

	private JTextField addTextField(String key, String content) {
		JTextField tf = new JTextField(content);
		textFieldList.put(key, tf);

		return tf;
	}

	public JTextField getTextField(String key) {
		return textFieldList.get(key);
	}

	private JLabel addLabel(String key, String content) {
		JLabel lb = new JLabel(content);
		labelList.put(key, lb);

		return lb;
	}

	public JList getUploadList() {
		return uploadList;
	}

	public CollectionListModel getUploadListModel() {
		return uploadListModel;
	}

	public JTextArea getTemplate() {
		return template;
	}

	public JTextArea getDescription() {
		return description;
	}

	public JList getCollectionList() {
		return collectionList;
	}

	public CollectionListModel getCollectionListModel() {
		return collectionListModel;
	}
}
