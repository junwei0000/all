package com.KiwiSports.utils.volley;

import java.io.File;
import java.util.Map;

public interface MultiPartRequest {

	public void addFileUpload(String param, File file);

	public void addStringUpload(String param, String content);

	public Map<String, File> getFileUploads();

	public Map<String, String> getStringUploads();
}