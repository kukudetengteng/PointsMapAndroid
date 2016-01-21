/**
 * 
 */
package cn.culturemap.pointsmap.pojo;

import java.io.Serializable;

/**
 * 兴趣点
 * @author XP
 * @time   2015年11月16日 下午3:54:26
 */
public class Point implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// 名称
	public String name;
	// 电话
	public String  tel;
	// 描述
	public String  desc;
	// 省份
	public String  provice;
	// 城市
	public String  city;
	// 纬度
	public String  lat;
	// 经度
	public String  lng;
	// 地址
	public String  add;
	// 地址
	public String  picUrl;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
