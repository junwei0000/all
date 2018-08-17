package com.bestdo.bestdoStadiums.control.photo.util;

import java.io.IOException;
import java.io.Serializable;

import android.graphics.Bitmap;

public class ImageItem implements Serializable {
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	private Bitmap bitmap;
	public boolean isSelected = false;
	int position;

	private String vedioPath;
	private long vediosize;
	private String vedioduration;

	public String getVedioPath() {
		return vedioPath;
	}

	public void setVedioPath(String vedioPath) {
		this.vedioPath = vedioPath;
	}

	public long getVediosize() {
		return vediosize;
	}

	public void setVediosize(long vediosize) {
		this.vediosize = vediosize;
	}

	public String getVedioduration() {
		return vedioduration;
	}

	public void setVedioduration(String vedioduration) {
		this.vedioduration = vedioduration;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Bitmap getBitmap() {
		if (bitmap == null) {
			try {
				bitmap = Bimp.revitionImageSize(imagePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
