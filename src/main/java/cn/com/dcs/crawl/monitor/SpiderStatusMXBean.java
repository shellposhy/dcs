package cn.com.dcs.crawl.monitor;

import java.util.Date;
import java.util.List;

/**
 * 爬虫统计基础接口
 * 
 * @author shishb
 * @version 1.0
 */
public interface SpiderStatusMXBean {

	public String getName();

	public String getStatus();

	public int getThread();

	public int getTotalPageCount();

	public int getLeftPageCount();

	public int getSuccessPageCount();

	public int getErrorPageCount();

	public List<String> getErrorPages();

	public void start();

	public void stop();

	public Date getStartTime();

	public int getPagePerSecond();
}