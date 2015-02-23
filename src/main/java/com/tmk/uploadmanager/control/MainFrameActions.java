package com.tmk.uploadmanager.control;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Class containing several ActionListener classes
 *
 * @author tmk
 */
public class MainFrameActions {

	/**
	 * Controller instance
	 */
	private MainController controller;

	/**
	 * Constructor
	 *
	 * @param controller Controller object
	 */
	public MainFrameActions(MainController controller) {
		this.controller = controller;
	}

	/**
	 * Load File
	 */
	public class LoadBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.loadAction();
			}).start();
		}
	}

	/**
	 * Save File
	 */
	public class SaveBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.saveAction();
			}).start();
		}
	}

	/**
	 * Add Upload
	 */
	public class AddUploadBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.addUploadAction();
			}).start();
		}
	}

	/**
	 * Remove Upload
	 */
	public class RemoveUploadBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.removeUploadAction();
			}).start();
		}
	}

	/**
	 * Add Collection
	 */
	public class AddCollectionBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.addCollectionAction();
			}).start();
		}
	}

	/**
	 * Remove Collection
	 */
	public class RemoveCollectionBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.removeCollectionAction();
			}).start();
		}
	}

	/**
	 * Copy filled template
	 */
	public class CopyBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.copyAction();
			}).start();
		}
	}

	/**
	 * Copy current collection tags + current upload tags
	 */
	public class CopyTagsBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.copyTagsAction();
			}).start();
		}
	}

	/**
	 * Update/Save the current upload
	 */
	public class SaveUploadBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.saveUploadAction();
			}).start();
		}
	}

	/**
	 * Edit the collection template
	 */
	public class EditTemplateBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.editTemplateAction();
			}).start();
		}
	}

	/**
	 * Update the collection name
	 */
	public class NameSetBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.nameSetAction();
			}).start();
		}
	}

	/**
	 * Update the collection's tags
	 */
	public class TagsSetBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.tagsSetAction();
			}).start();
		}
	}

	/**
	 * Copy collection + upload name
	 */
	public class CopyNameBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.copyNameAction();
			}).start();
		}
	}

	/**
	 * Copy mkdir commands
	 */
	public class CopyMkdirBtnAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				controller.copyMkdirAction();
			}).start();
		}
	}

	/**
	 * ListSelectionListener for the upload list
	 */
	public class UploadSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				new Thread(() -> {
					controller.uploadSelected(((JList) e.getSource()).getSelectedIndex());
				}).start();
			}
		}
	}

	/**
	 * ListSelectionListener for the collection list
	 */
	public class CollectionSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				new Thread(() -> {
					controller.collectionSelected(((JList) e.getSource()).getSelectedIndex());
				}).start();
			}
		}
	}
}
