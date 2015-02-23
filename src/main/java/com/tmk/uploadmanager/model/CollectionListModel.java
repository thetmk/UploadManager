package com.tmk.uploadmanager.model;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.DefaultListModel;

/**
 * ListModel used for Uploads and Collections. Adds methods for replacing all
 * elements and sorting
 *
 * @author tmk
 */
public class CollectionListModel extends DefaultListModel {

	/**
	 * Replaces all stored data inside the model
	 *
	 * @param data List with new data
	 */
	public void replaceData(ArrayList<String> data) {
		removeAllElements();

		for (String s : data) {
			addElement(s);
		}
		fireContentsChanged(this, 0, getSize() - 1);
	}

	/**
	 * Sorts the elements in ascending order
	 */
	public void sort() {
		ArrayList<String> elemList = new ArrayList<>();
		for (int i = 0; i < getSize(); i++) {
			elemList.add((String) get(i));
		}
		Collections.sort(elemList);

		replaceData(elemList);
	}
}
