/**
 * 
 */
package cn.culturemap.pointsmap.modle;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import cn.culturemap.pointsmap.pojo.Organization;

/**
 * 机构存储
 * @author XP
 * @time   2015年11月25日 下午4:32:57
 */
public class OrganizationModle {
	
	private static final String ORGANIZATION_SAVING_KEY = "cn.culturemap.pointsmap.organization.key";
	private static final String ORGANIZATION_SAVING_ID = "cn.culturemap.pointsmap.organization.id";
	private static final String ORGANIZATION_SAVING_NAME = "cn.culturemap.pointsmap.organization.name";
	private static final String ORGANIZATION_SAVING_CODE = "cn.culturemap.pointsmap.organization.code";
	private static final String ORGANIZATION_SAVING_IMG = "cn.culturemap.pointsmap.organization.img";
	private static final String ORGANIZATION_SAVING_DESC = "cn.culturemap.pointsmap.organization.desc";
	
	// 存储分享信息
	SharedPreferences settings = null;
	Context context = null;
	
	Organization organization;
	
	public OrganizationModle(Context context) {
		this.context = context;
		this.settings = context.getSharedPreferences(ORGANIZATION_SAVING_KEY, 0);

		read();
	}
	
	public void read() {
		
		if (organization == null) {
			organization = new Organization();
		}
		
		organization.setId(settings.getInt(ORGANIZATION_SAVING_ID, 0));
		organization.setName(settings.getString(ORGANIZATION_SAVING_NAME, ""));
		organization.setCode(settings.getString(ORGANIZATION_SAVING_CODE, ""));
		organization.setImg(settings.getString(ORGANIZATION_SAVING_IMG, ""));
		organization.setDesc(settings.getString(ORGANIZATION_SAVING_DESC, ""));
	}
	
	public void save() {
		
		Editor editor = this.settings.edit();

		editor.putInt(ORGANIZATION_SAVING_ID, organization.getId());
		editor.putString(ORGANIZATION_SAVING_NAME, organization.getName());
		editor.putString(ORGANIZATION_SAVING_CODE, organization.getCode());
		editor.putString(ORGANIZATION_SAVING_IMG, organization.getImg());
		editor.putString(ORGANIZATION_SAVING_DESC, organization.getDesc());
		
		editor.commit();
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
}
