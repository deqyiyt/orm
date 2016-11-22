package test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hujz.framework.orm.mybatis.test.dto.SysUserDto;
import com.hujz.framework.orm.mybatis.test.service.SysUserService;
import com.hujz.framework.orm.util.QueryCondition;
import com.hujz.framework.orm.util.QueryResult;
import com.hujz.soasoft.util.RandomUtils;
import com.hujz.soasoft.util.md5.MD5Util;
import com.hujz.soasoft.util.type.JsonUtil;
import com.hujz.soasoft.util.type.TimeUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml"})
public class SysUserServiceTest {
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testQuery方法.<br>
	 * @Description 测试按条件查询，分页，排序
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:48:44
	 */
	@Test
	public void testQuery() {
		QueryCondition cond = new QueryCondition(20,0);
		cond.less("birthday", TimeUtil.toCalendar("2015-03-16 22:44:39").getTime());
		cond.greate("birthday", "1991-02-21 19:52:21");
		cond.equals("isDelete", 0);
		cond.put("name", "张");
		cond.orderAsc("birthday");
		cond.orderDesc("name");
		QueryResult<SysUserDto> qr = sysUserService.query(cond);
		System.out.println(qr.getTotal());
		System.out.println(JsonUtil.buildNonNullBinder().toJson(qr.getRows()));
	}
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testQueryCount方法.<br>
	 * @Description 按条件查询总条数
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:49:04
	 */
	@Test
	public void testQueryCount() {
		QueryCondition cond = new QueryCondition();
		cond.less("birthday", TimeUtil.toCalendar("2015-03-16 22:44:39").getTime());
		cond.greate("birthday", "1991-02-21 19:52:21");
		System.out.println(sysUserService.queryCount(cond));
	}
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testIsUnique方法.<br>
	 * @Description 按条件查询对象是否存在
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:49:17
	 */
	@Test
	public void testIsUnique() {
		QueryCondition cond = new QueryCondition();
		cond.less("birthday", TimeUtil.toCalendar("2015-03-16 22:44:39").getTime());
		cond.greate("birthday", "1991-02-21 19:52:21");
		System.out.println(sysUserService.isUnique(cond));
	}
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testBatchUpdate方法.<br>
	 * @Description 按条件更新数据
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:49:31
	 */
	@Test
	public void testBatchUpdate() {
		QueryCondition cond = new QueryCondition();
		cond.batchUpdate("name", "张亲");
		cond.equals("id", 8);
		sysUserService.batchUpdate(cond);
	}
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testBatchDelete方法.<br>
	 * @Description 按条件删除数据
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:49:42
	 */
	@Test
	public void testBatchDelete() {
		QueryCondition cond = new QueryCondition();
		cond.equals("id", 7);
		sysUserService.batchDelete(cond);
	}
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testQueryAll方法.<br>
	 * @Description 查询整张表数据，不分页
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:49:53
	 */
	@Test
	public void testQueryAll() {
		QueryResult<SysUserDto> list = sysUserService.queryAll();
		System.out.println(JsonUtil.buildNormalBinder().toJson(list));
	}
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testGet方法.<br>
	 * @Description 根据ID查询数据
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:50:08
	 */
	@Test
	public void testGet() {
		SysUserDto user = sysUserService.get("1");
		System.out.println(JsonUtil.buildNormalBinder().toJson(user));
	}
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testDelete方法.<br>
	 * @Description 根据ID删除数据
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:50:18
	 */
	@Test
	public void testDelete() {
		sysUserService.delete("8");
	}
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testDeleteAll方法.<br>
	 * @Description 根据ID批量删除数据
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:50:29
	 */
	@Test
	public void testDeleteAll() {
		sysUserService.deleteAll("9","13");
	}
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testSave方法.<br>
	 * @Description 报错对象
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:50:46
	 */
	@Test
	public void testSave() {
		SysUserDto user = new SysUserDto();
		user.setBirthday(RandomUtils.randomDate("1970/01/01","2010/01/01"));
		user.setEmail("hujz@yschome.com");
		user.setGender(1);
		user.setLoginName("hujz");
		user.setSalt(RandomUtils.getRandomValue(RandomUtils.KEY7, 5));
		user.setPassword(MD5Util.getMD5Str(MD5Util.getMD5Str("123456") + user.getSalt()));
		user.setLoginCount(0l);
		user.setName("张亲");
		sysUserService.save(user);
	}
	
	/**
	 * @Project bc-erp-fa
	 * @Package com.hujz.bc.erp.fa.service
	 * @Method testUpdate方法.<br>
	 * @Description 更新对象
	 * @author 胡久洲
	 * @date 2015年9月14日 上午10:50:54
	 */
	@Test
	public void testUpdate() {
		SysUserDto user = sysUserService.get("15");
		user.setName("hujz");
		sysUserService.update(user);
	}
}
