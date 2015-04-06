package com.tmk.uploadmanager.model;

import java.io.Serializable;
import java.util.TreeMap;
import java.util.regex.Matcher;

/**
 * Storage class for uploads
 *
 * @author tmk
 */
public class UploadCollection implements Serializable {

	private static final long serialVersionUID = 123L;

	private TreeMap<String, Upload> collection;
	private String name;
	private String template;
	private String tags;

	public UploadCollection(String name, String template, String tags) {
		this.name = name;
		this.template = template;
		this.tags = tags;

		this.collection = new TreeMap<>();
	}

	public void addUpload(String key, Upload up) {
		if (up != null && key != null) {
			collection.put(key, up);
		}
	}

	public void removeUpload(String key) {
		if (key != null) {
			collection.remove(key);
		}
	}

	public String format(Upload u) {
		if (u == null) {
			return null;
		}

		return template
						.replaceAll("_COLLECTIONNAME_", Matcher.quoteReplacement(name))
						.replaceAll("_UPLOADNAME_", Matcher.quoteReplacement(u.getTitle()))
						.replaceAll("_UPLOADDESC_", Matcher.quoteReplacement(u.getDescription()))
						.replaceAll("_UPLOADIMAGE_", Matcher.quoteReplacement(u.getImage()));
	}

	public int size() {
		return collection.size();
	}

	public boolean contains(String key) {
		return (name != null) ? collection.containsKey(key) : null;
	}

	public Upload getUpload(String key) {
		return collection.get(key);
	}

	public TreeMap<String, Upload> getCollection() {
		return collection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
