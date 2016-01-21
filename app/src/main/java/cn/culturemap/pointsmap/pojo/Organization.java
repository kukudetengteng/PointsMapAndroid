/**
 * 
 */
package cn.culturemap.pointsmap.pojo;

/**
 * 机构
 * @author XP
 * @time   2015年11月25日 下午2:27:53
 */
public class Organization {
	// ID
	public int id;
	// 名称
	public String name;
	// 编码
	public String code;
	// 图片
	public String img;
	// 描述
	public String desc;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
