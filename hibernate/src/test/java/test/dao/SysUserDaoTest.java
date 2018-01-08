package test.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hujz.framework.orm.hibernate.test.dao.SysUserDao;
import com.hujz.framework.orm.hibernate.test.entity.SysUser;
import com.hujz.framework.orm.util.QueryCondition;
import com.hujz.framework.orm.util.RandomUtils;
import com.hujz.framework.orm.util.TimeUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml"})
public class SysUserDaoTest {
	
	@Autowired
	private SysUserDao sysUserDao;
	
	/**
	 * @Method testQuery方法.<br>
	 * @Description 测试按条件查询，分页，排序
	 * @author jiuzhou.hu
	 * @date 2015年9月14日 上午10:48:44
	 */
	@Test
	public void testQuery() {
		QueryCondition cond = new QueryCondition(20,0);
		cond.less("birthday", new Date());
		cond.greate("birthday", "1991-02-21 19:52:21");
		cond.equals("isDelete", 0);
		cond.put("name", "胡");
		cond.orderAsc("birthday");
		cond.orderDesc("name");
		List<SysUser> list = sysUserDao.query(cond);
		Assert.assertNotNull(list);
	}
	
	/**
	 * @Method testQueryCount方法.<br>
	 * @Description 按条件查询总条数
	 * @author jiuzhou.hu
	 * @date 2015年9月14日 上午10:49:04
	 */
	@Test
	public void testQueryCount() {
		QueryCondition cond = new QueryCondition();
		cond.less("birthday", TimeUtils.toCalendar("2015-03-16 22:44:39").getTime());
		cond.greate("birthday", "1991-02-21 19:52:21");
		System.out.println(sysUserDao.queryCount(cond));
	}
	
	/**
	 * @Method testIsUnique方法.<br>
	 * @Description 按条件查询对象是否存在
	 * @author jiuzhou.hu
	 * @date 2015年9月14日 上午10:49:17
	 */
	@Test
	public void testIsUnique() {
		QueryCondition cond = new QueryCondition();
		cond.less("birthday", TimeUtils.toCalendar("2015-03-16 22:44:39").getTime());
		cond.greate("birthday", "1991-02-21 19:52:21");
		System.out.println(sysUserDao.isUnique(cond));
	}
	
	/**
	 * @Method testBatchUpdate方法.<br>
	 * @Description 按条件更新数据
	 * @author jiuzhou.hu
	 * @date 2015年9月14日 上午10:49:31
	 */
	@Test
	public void testBatchUpdate() {
		QueryCondition cond = new QueryCondition();
		cond.batchUpdate("name", "胡久洲");
		cond.equals("id", 8);
		sysUserDao.batchUpdate(cond);
	}
	
	/**
	 * @Method testBatchDelete方法.<br>
	 * @Description 按条件删除数据
	 * @author jiuzhou.hu
	 * @date 2015年9月14日 上午10:49:42
	 */
	@Test
	public void testBatchDelete() {
		QueryCondition cond = new QueryCondition();
		cond.equals("id", 7);
		sysUserDao.batchDelete(cond);
	}
	
	/**
	 * @Method testGet方法.<br>
	 * @Description 根据ID查询数据
	 * @author jiuzhou.hu
	 * @date 2015年9月14日 上午10:50:08
	 */
	@Test
	public void testGet() {
		SysUser user = sysUserDao.get("1");
		Assert.assertNotNull(user);
	}
	
	/**
	 * @Method testDelete方法.<br>
	 * @Description 根据ID删除数据
	 * @author jiuzhou.hu
	 * @date 2015年9月14日 上午10:50:18
	 */
	@Test
	public void testDelete() {
		sysUserDao.delete("8");
	}
	
	/**
	 * @Method testDeleteAll方法.<br>
	 * @Description 根据ID批量删除数据
	 * @author jiuzhou.hu
	 * @date 2015年9月14日 上午10:50:29
	 */
	@Test
	public void testDeleteAll() {
		sysUserDao.deleteAll("9","13");
	}
	
	/**
	 * @Method testSave方法.<br>
	 * @Description 报错对象
	 * @author jiuzhou.hu
	 * @date 2015年9月14日 上午10:50:46
	 */
	@Test
	public void testSave() {
		SysUser user = new SysUser();
		user.setBirthday(new Date());
		user.setEmail("352deqyiyt@163.com");
		user.setGender(1);
		user.setLoginName("jiuzhou.hu");
		user.setSalt(RandomUtils.getRandomValue(RandomUtils.KEY7, 5));
		user.setPassword("123456");
		user.setLoginCount(0l);
		user.setName("胡久洲");
		sysUserDao.save(user);
	}
	
	/**
	 * @Method testUpdate方法.<br>
	 * @Description 更新对象
	 * @author jiuzhou.hu
	 * @date 2015年9月14日 上午10:50:54
	 */
	@Test
	public void testUpdate() {
		SysUser user = sysUserDao.get("15");
		user.setName("hujz");
		sysUserDao.update(user);
	}
}
