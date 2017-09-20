package com.newttl.scnualumni_gz.bean.menu;

/**
 * 个性化菜单
 * @author lgc
 *
 * 2017年9月20日 上午8:51:50
 */
public class PersonalMenu {

	//菜单组
	private Button[] button;
	//匹配项
	private Matchrule matchrule;
	
	public Button[] getButton() {
		return button;
	}
	public void setButton(Button[] button) {
		this.button = button;
	}
	public Matchrule getMatchrule() {
		return matchrule;
	}
	public void setMatchrule(Matchrule matchrule) {
		this.matchrule = matchrule;
	}
	
	
}
