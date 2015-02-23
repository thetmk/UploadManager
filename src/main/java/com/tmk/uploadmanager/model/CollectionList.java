package com.tmk.uploadmanager.model;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Simple UploadCollection storage
 *
 * @author tmk
 */
public class CollectionList implements Serializable {

	private static final long serialVersionUID = 123L;

	private TreeMap<String, UploadCollection> list;

	public CollectionList() {
		list = new TreeMap<>();
	}

	public void addCollection(String name, UploadCollection collection) {
		if (name != null && !name.isEmpty() && collection != null) {
			getList().put(name, collection);
		}
	}

	public UploadCollection getCollection(String name) {
		if (name != null && !name.isEmpty() && getList().containsKey(name)) {
			return getList().get(name);
		}
		return null;
	}

	public void removeCollection(String name) {
		if (name != null && !name.isEmpty() && getList().containsKey(name)) {
			getList().remove(name);
		}
	}

	public boolean contains(String name) {
		return (name != null) ? getList().containsKey(name) : null;
	}

	public TreeMap<String, UploadCollection> getList() {
		return list;
	}
}
