package com.newttl.scnualumni_gz.logs;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * log4j初始化类
 * 
 * @author lgc
 *
 * 2017年9月7日 下午7:36:37
 */
public class ScnuAlumniLogs {

	private static Logger logger;
	static{
		try {
			logger=Logger.getLogger(ScnuAlumniLogs.class);
		} catch (Exception e) {
			System.out.println("初始化log4j失败::"+e.toString());
		}
	}
	public static Logger getLogger() {
		return logger;
	}
}
