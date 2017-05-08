package cn.com.dcs.crawl.monitor;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.InstanceAlreadyExistsException;
import javax.management.JMException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.dcs.crawl.monitor.impl.SpiderMonitorImpl;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.utils.Experimental;
import us.codecraft.webmagic.utils.UrlUtils;

/**
 * 爬虫监控类
 * 
 * @author shishb
 * @version 1.0
 */
@Experimental
public class CrawlMonitor {
	private static CrawlMonitor INSTANCE = new CrawlMonitor();
	@SuppressWarnings("unused")
	private AtomicBoolean started = new AtomicBoolean(false);
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());
	private MBeanServer mbeanServer;
	private String jmxServerName;
	private List<SpiderMonitor> spiderStatuses = new ArrayList<SpiderMonitor>();

	protected CrawlMonitor() {
		jmxServerName = "WisdomSpider";
		mbeanServer = ManagementFactory.getPlatformMBeanServer();
	}

	/**
	 * Register spider for monitor.
	 *
	 * @param spiders
	 *            spiders
	 * @return this
	 * @throws JMException
	 *             JMException
	 */
	public synchronized CrawlMonitor register(Spider... spiders) throws JMException {
		for (Spider spider : spiders) {
			MonitorSpiderListener monitorSpiderListener = new MonitorSpiderListener();
			if (spider.getSpiderListeners() == null) {
				List<SpiderListener> spiderListeners = new ArrayList<SpiderListener>();
				spiderListeners.add(monitorSpiderListener);
				spider.setSpiderListeners(spiderListeners);
			} else {
				spider.getSpiderListeners().add(monitorSpiderListener);
			}
			SpiderMonitor spiderStatusMBean = getSpiderStatusMonitor(spider, monitorSpiderListener);
			register(spiderStatusMBean);
			spiderStatuses.add(spiderStatusMBean);
		}
		return this;
	}

	protected SpiderMonitor getSpiderStatusMonitor(Spider spider, MonitorSpiderListener monitorSpiderListener) {
		return new SpiderMonitorImpl(spider, monitorSpiderListener);
	}

	public static CrawlMonitor instance() {
		return INSTANCE;
	}

	public List<SpiderMonitor> getSpiderStatuses() {
		return spiderStatuses;
	}

	public class MonitorSpiderListener implements SpiderListener {

		private final AtomicInteger successCount = new AtomicInteger(0);

		private final AtomicInteger errorCount = new AtomicInteger(0);

		private List<String> errorUrls = Collections.synchronizedList(new ArrayList<String>());

		@Override
		public void onSuccess(Request request) {
			successCount.incrementAndGet();
		}

		@Override
		public void onError(Request request) {
			errorUrls.add(request.getUrl());
			errorCount.incrementAndGet();
		}

		public AtomicInteger getSuccessCount() {
			return successCount;
		}

		public AtomicInteger getErrorCount() {
			return errorCount;
		}

		public List<String> getErrorUrls() {
			return errorUrls;
		}
	}

	protected void register(SpiderMonitor spiderStatus) throws MalformedObjectNameException,
			InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		ObjectName objName = new ObjectName(jmxServerName + ":name=" + UrlUtils.removePort(spiderStatus.getName()));
		mbeanServer.registerMBean(spiderStatus, objName);
	}
}
