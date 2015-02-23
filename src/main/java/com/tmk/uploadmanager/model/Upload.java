package com.tmk.uploadmanager.model;

import java.io.Serializable;

/**
 * Data holder class for uploads
 *
 * @author tmk
 */
public class Upload implements Serializable {

	private static final long serialVersionUID = 123L;

	private String title;
	private String tags;
	private String image;
	private String cover;
	private String description;

	public Upload(String title, String tags, String image, String cover, String description) {
		this.title = title;
		this.tags = tags;
		this.image = image;
		this.cover = cover;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
}
