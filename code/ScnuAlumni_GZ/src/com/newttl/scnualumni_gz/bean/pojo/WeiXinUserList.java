package com.newttl.scnualumni_gz.bean.pojo;

import java.util.List;

/**
 * 公众号关注者列表
 * 
 * @author lgc
 *
 * @date 2017年7月5日 上午9:28:43
 */
public class WeiXinUserList {

	/*
	{
		"total":2,
		"count":2,
		"data":
				{
					"openid":["","OPENID1","OPENID2"]
				},
		"next_openid":"NEXT_OPENID"
	}
	*/
	
	//关注者总数
	private int total;
	//拉取 opendId的个数，最多为10000个
	private int count;
	//拉取的opendId列表
	private List<String> data;
	//拉取的opendId列表的最后一个opendId
	private String nextOpenId;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	public String getNextOpenId() {
		return nextOpenId;
	}
	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}
	
	public String getWeiXinUserList(){
		
		return "total::"+getTotal()+"\n"+"count::"+getCount()+"\n"+"data::"+getData()
		+"\n"+"nextOpenId::"+getNextOpenId();
	}
	
}
