package cn.com.dcs.framework.base.constant;

public enum EFormulaType {

	XPath("XPath路径"), Css("Css样式"), Regex("正则表达式");

	public static EFormulaType valueof(int index) {
		return EFormulaType.values()[index];
	}

	private String title;

	private EFormulaType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
