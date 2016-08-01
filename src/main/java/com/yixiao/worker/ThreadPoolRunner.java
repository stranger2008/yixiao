package com.yixiao.worker;

import com.yixiao.config.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;


/**
 * 线程池执行类
 */
public class ThreadPoolRunner {
	private static Log logger = LogFactory.getLog(ThreadPoolRunner.class);

	public ThreadPoolRunner() {		
	}

	/**
	 * 线程池的执行函数
	 * @param pool 线程池对象
	 * @param dataGetterSet 用于执行取数操作的对象集合
	 * @param maxWaitTimeMillis 本次并发执行的线程组，最大等待时间，以毫秒为单位
	 * @return Map对象，每个线程一个key
	 */
	public static Map<String, Object> run(ExecutorService pool, Set<Callable> dataGetterSet,long maxWaitTimeMillis) {
		if (null == pool || null == dataGetterSet || dataGetterSet.isEmpty()) {
			return null;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		CompletionService<Map<String, Object>> cmp = new ExecutorCompletionService<Map<String, Object>>(pool);
		for (Callable handler : dataGetterSet) {
			cmp.submit(handler);
		}
		
		int size = dataGetterSet.size();
		int count = 0;
		long st = System.currentTimeMillis();
		while (count < size) {
			try {
				Future<Map<String, Object>> future = cmp.poll();
				if (null == future) {
					long time = System.currentTimeMillis() - st;
					if (time < maxWaitTimeMillis) {
						TimeUnit.MILLISECONDS.sleep(1L);
					} else {
						break;
					}
				} else {
					count++;
					
					Map<String, Object> tmp = future.get();
					if (null != tmp && tmp.size() > 0) {
						String threadName = (String) tmp.get(Constants.THREAD_COMMON_KEY_THREAD_NAME);
						if (null != threadName) {
							result.put(threadName, tmp);
						}
					}
				}
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			} catch (ExecutionException e) {
				logger.error(e.getMessage(), e);
			}
			
		}
		return result;
	}
}