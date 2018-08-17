package com.bestdo.bestdoStadiums.control.photo.util;

import java.io.File;

import android.widget.CheckBox;
import android.widget.TextView;

public class TaskParamInfo {
	private String filename;
	private String filepath;
	private File file;
	private int ItemWidth;
	private int imgindex;// 每张图片对应的标记
	TextView checkBox;
	boolean checkStatus;

	public TaskParamInfo() {
		super();
	}

	public TaskParamInfo(int imgindex) {
		super();
		this.imgindex = imgindex;
	}

	public TaskParamInfo(File file, String filepath) {
		super();
		this.file = file;
		this.filepath = filepath;
	}

	public TextView getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(TextView checkBox) {
		this.checkBox = checkBox;
	}

	public boolean isCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(boolean checkStatus) {
		this.checkStatus = checkStatus;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getItemWidth() {
		return ItemWidth;
	}

	public void setItemWidth(int itemWidth) {
		ItemWidth = itemWidth;
	}

	public int getImgindex() {
		return imgindex;
	}

	public void setImgindex(int imgindex) {
		this.imgindex = imgindex;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
}
