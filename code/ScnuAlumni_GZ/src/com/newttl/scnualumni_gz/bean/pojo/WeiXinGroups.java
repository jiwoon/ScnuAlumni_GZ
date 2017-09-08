package com.newttl.scnualumni_gz.bean.pojo;

/**
 * 用户分组信息
 * 
 * @author lgc
 *
 * @date 2017年7月5日 上午11:09:36
 */
public class WeiXinGroups {

	private int id;
	private String name;
	private int count;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public String getWeiXinGroups(){
		return "id::"+getId()+"\n"+"name::"+getName()+"\n"+"count::"+getCount();
	}
	
}
