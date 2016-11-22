package com.hujz.framework.orm;

import com.alibaba.druid.filter.config.ConfigTools;


/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2016年1月6日 下午4:22:45
 * @Create Author: jiuzhou.hu@baozun.cn
 * @File Name: DruidDecryptTest
 * @Function: druid 加密解密
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class DruidDecryptTest {
	public static void main(String[] args) throws Exception {
		//加密
		String encode = ConfigTools.encrypt("tt789_1qaz");
		System.out.println(encode);
		//解密
		String password = ConfigTools.decrypt(encode);
		System.out.println(password);
	}
}
