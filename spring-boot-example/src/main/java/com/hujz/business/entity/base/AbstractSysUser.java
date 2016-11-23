package com.hujz.business.entity.base;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import com.hujz.framework.orm.entity.BasicEntity;

@MappedSuperclass
public abstract class AbstractSysUser extends BasicEntity {

	
	/**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2015年9月16日 下午1:56:30
	 * 含义 
	 */
	private static final long serialVersionUID = 7603814227091349888L;

	/**
	 * @Title loginName
	 * @type String
	 * @date 2015年9月16日 下午1:56:57
	 * 含义 账号
	 */
	private String loginName;
	
	/**
	 * @Title name
	 * @type String
	 * @date 2015年9月16日 下午1:57:06
	 * 含义 昵称
	 */
	private String name;
	
	/**
	 * @Title password
	 * @type String
	 * @date 2015年9月16日 下午1:57:12
	 * 含义 密码
	 */
	private String password;
	
	/**
	 * @Title salt
	 * @type String
	 * @date 2015年9月16日 下午1:57:19
	 * 含义 盐值
	 */
	private String salt;
	
	/**
	 * @Title birthday
	 * @type Date
	 * @date 2015年9月16日 下午1:57:28
	 * 含义 生日
	 */
	private Date birthday;
	
	/**
	 * @Title gender
	 * @type Integer
	 * @date 2015年9月16日 下午1:57:34
	 * 含义 性别
	 */
	private Integer gender;
	
	/**
	 * @Title email
	 * @type String
	 * @date 2015年9月16日 下午1:57:48
	 * 含义 邮箱
	 */
	private String email;
	
	/**
	 * @Title phone
	 * @type String
	 * @date 2015年9月16日 下午1:58:00
	 * 含义 联系电话
	 */
	private String phone;
	
	/**
	 * @Title icon
	 * @type String
	 * @date 2015年9月16日 下午1:57:51
	 * 含义 图片
	 */
	private String icon;
	
	/**
	 * @Title state
	 * @type Boolean
	 * @date 2015年9月16日 下午1:58:12
	 * 含义 状态
	 */
	private Boolean state;
	
	/**
	 * @Title description
	 * @type String
	 * @date 2015年9月16日 下午1:58:16
	 * 含义 描述
	 */
	private String description;
	
	/**
	 * @Title loginCount
	 * @type Long
	 * @date 2015年9月16日 下午1:58:25
	 * 含义 登陆次数
	 */
	private Long loginCount;
	
	/**
	 * @Title previousVisit
	 * @type Date
	 * @date 2015年9月16日 下午1:58:33
	 * 含义 上次访问时间
	 */
	private Date previousVisit;
	
	/**
	 * @Title lastVisit
	 * @type Date
	 * @date 2015年9月16日 下午1:58:39
	 * 含义 最后访问时间
	 */
	private Date lastVisit;

	public AbstractSysUser() {
		super();
	}

	/**
	 * loginName的获取.
	 * @return String
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 设定loginName的值.
	 * @param String
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * name的获取.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设定name的值.
	 * @param String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * password的获取.
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设定password的值.
	 * @param String
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * salt的获取.
	 * @return String
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * 设定salt的值.
	 * @param String
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * birthday的获取.
	 * @return Date
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 设定birthday的值.
	 * @param Date
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * gender的获取.
	 * @return Integer
	 */
	public Integer getGender() {
		return gender;
	}

	/**
	 * 设定gender的值.
	 * @param Integer
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * email的获取.
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设定email的值.
	 * @param String
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * phone的获取.
	 * @return String
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设定phone的值.
	 * @param String
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * icon的获取.
	 * @return String
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设定icon的值.
	 * @param String
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * state的获取.
	 * @return Boolean
	 */
	public Boolean getState() {
		return state;
	}

	/**
	 * 设定state的值.
	 * @param Boolean
	 */
	public void setState(Boolean state) {
		this.state = state;
	}

	/**
	 * description的获取.
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设定description的值.
	 * @param String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * loginCount的获取.
	 * @return Long
	 */
	public Long getLoginCount() {
		return loginCount;
	}

	/**
	 * 设定loginCount的值.
	 * @param Long
	 */
	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

	/**
	 * previousVisit的获取.
	 * @return Date
	 */
	public Date getPreviousVisit() {
		return previousVisit;
	}

	/**
	 * 设定previousVisit的值.
	 * @param Date
	 */
	public void setPreviousVisit(Date previousVisit) {
		this.previousVisit = previousVisit;
	}

	/**
	 * lastVisit的获取.
	 * @return Date
	 */
	public Date getLastVisit() {
		return lastVisit;
	}

	/**
	 * 设定lastVisit的值.
	 * @param Date
	 */
	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}
	
}
