package cn.com.dcs.crawl.pipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.com.dcs.crawl.constant.PageField;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

/**
 * 基于txt格式文件下载处理类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class TextFilePipeline extends FilePersistentBase implements Pipeline {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 默认构造器
	 * <p>
	 * 初始化下载文件路径
	 */
	public TextFilePipeline() {
		setPath("/data/dcs/txt");
	}

	/**
	 * 默认构造器
	 * <p>
	 * 待参数
	 * 
	 * @param path
	 * @return
	 */
	public TextFilePipeline(String path) {
		setPath(path);
	}

	/**
	 * 抓取数据时，抓取内容是否为空
	 * 
	 * @param resultItems
	 * @param task
	 * @return
	 */
	public boolean isNullOrEmpty(ResultItems resultItems, Task task) {
		if (null != resultItems && null != resultItems.getAll() && resultItems.getAll().size() > 0) {
			String value = null;
			for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
				if (PageField.CONTENT.equals(entry.getKey())) {
					if (entry.getValue() instanceof Iterable) {
						Iterable<?> iterable = (Iterable<?>) entry.getValue();
						for (Object o : iterable) {
							value += o;
						}
					} else {
						value = entry.getValue().toString();
					}
					break;
				}
			}
			System.out.println("content==========" + value);
			if (null != value && !"".equals(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 基于爬虫抓取数据后，保存到本地
	 * <p>
	 * 如果内容为空，不保存数据
	 * 
	 * @param resultItems
	 * @param task
	 * @return
	 */
	public void process(ResultItems resultItems, Task task) {
		if (!isNullOrEmpty(resultItems, task)) {
			String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
			try {
				PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(
								getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".txt")),
						task.getSite().getCharset()));
				printWriter.println("url:\t" + resultItems.getRequest().getUrl());
				printWriter.println("charset:\t" + task.getSite().getCharset());
				for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
					if (entry.getValue() instanceof Iterable) {
						Iterable<?> value = (Iterable<?>) entry.getValue();
						printWriter.println(entry.getKey() + ":");
						for (Object o : value) {
							printWriter.println(o);
						}
					} else {
						printWriter.println(entry.getKey() + ":\t" + entry.getValue());
					}
				}
				printWriter.close();
			} catch (IOException e) {
				logger.warn("write file error", e);
			}
		}
	}
}
