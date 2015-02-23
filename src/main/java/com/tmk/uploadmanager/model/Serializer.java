package com.tmk.uploadmanager.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * (De-)Serialization of CollectionList objects
 *
 * @author tmk
 */
public class Serializer {

	/**
	 * Save CollectionList object to a file
	 *
	 * @param obj CollectionList instance
	 * @param path Filepath
	 * @throws IOException
	 */
	public static void save(CollectionList obj, String path) throws IOException {
		if (path != null && !path.isEmpty()) {
			try (FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(obj);
			}
		} else {
			throw new IllegalArgumentException("Invalid path");
		}

	}

	/**
	 * Load a CollectionList object from a file
	 *
	 * @param path Filepath
	 * @return CollectionList instance or null
	 * @throws IOException
	 */
	public static CollectionList load(String path) throws IOException {
		if (path != null && !path.isEmpty()) {
			try (FileInputStream fis = new FileInputStream(path); ObjectInputStream ois = new ObjectInputStream(fis)) {
				return (CollectionList) ois.readObject();
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(Serializer.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			throw new IllegalArgumentException("Invalid path");
		}
		return null;
	}
}
