/**
 * 
 */
package com.rainbow.dto;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: FileInfo
 * @date 2018/9/15 15:32
 */
public class FileInfo {
	
	public FileInfo(String path){
		this.path = path;
	}
	
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
