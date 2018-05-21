package com.ias.assembly.orm.basic;

import com.alibaba.druid.filter.config.ConfigTools;


/**
 *********************************************** 
 * @Create Date: 2016年1月6日 下午4:22:45
 * @Create Author: jiuzhou.hu
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
		String encode = ConfigTools.encrypt("7yaeqjj;mM=mQtp1KVr");
		System.out.println(encode);
		//解密
		String password = ConfigTools.decrypt("ljHzx/J9WwkgmvVyTiuh8SZNc54UY0jXzKWD4RsboXavVIEgNxbmnJSziBXiU40Wx7ya3dtuSQf3HJjaJSI1QQ==");
		System.out.println(password);
	}
}
