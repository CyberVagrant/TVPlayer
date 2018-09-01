package cn.codedoge.tvplayer.entity;

import java.io.Serializable;

public class Live implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String url;
	private String path;
	
	public Live() {
	}
	
	public Live(String name, String url, String path) {
		super();
		this.name = name;
		this.url = url;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Live [name=" + name + ", url=" + url + ", path=" + path + "]";
	}
	

}
