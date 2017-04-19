package cn.com.dcs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 抓取页面工具包
 * 
 * @author shishb
 * @version 1.0
 */
public class PageUtil {

	/**
	 * 爬虫抓取页面时获得页面的默认编码
	 * 
	 * @param html
	 * @return
	 */
	public static String pageDefaultCharset(Html html) {
		String charset = "utf-8";
		Selectable selectable = html.xpath("//meta[@charset]");
		// data format=<meta charset="utf-8">
		if (null != selectable.get() && !"".equals(selectable.get())) {
			String charserStr = selectable.get();
			Pattern pattern = Pattern.compile("(utf-8|gb2312)");
			Matcher matcher = pattern.matcher(charserStr);
			if (matcher.find()) {
				charset = matcher.group(0);
			}
		}
		// <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		else {
			selectable = html.xpath("//meta[@http-equiv='content-type']");
			String charserStr = selectable.get();
			Pattern pattern = Pattern.compile("(utf-8|gb2312)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(charserStr);
			if (matcher.find()) {
				charset = matcher.group(0);
			}
		}
		return charset;
	}
}
