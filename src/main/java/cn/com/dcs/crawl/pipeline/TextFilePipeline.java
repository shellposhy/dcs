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
	 * create a FilePipeline with default path"/data/dcs/txt"
	 */
	public TextFilePipeline() {
		setPath("/data/dcs/txt");
	}

	/**
	 * create a FilePipeline with path {@code path}
	 * 
	 * @param path
	 * @return
	 */
	public TextFilePipeline(String path) {
		setPath(path);
	}

	/**
	 * Txt File Process and Create txt file download
	 * 
	 * @param resultItems
	 * @param task
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public void process(ResultItems resultItems, Task task) {
		String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
		try {
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(
							getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".txt")),
					task.getSite().getCharset()));
			printWriter.println("url:\t" + resultItems.getRequest().getUrl());
			for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
				if (entry.getValue() instanceof Iterable) {
					Iterable value = (Iterable) entry.getValue();
					printWriter.println(entry.getKey() + ":");
					for (Object o : value) {
						System.out.println("===value=" + o);
						printWriter.println(o);
					}
				} else {
					System.out.println("===value=" + entry.getValue());
					printWriter.println(entry.getKey() + ":\t" + entry.getValue());
				}
			}
			printWriter.close();
		} catch (IOException e) {
			logger.warn("write file error", e);
		}
	}
}
