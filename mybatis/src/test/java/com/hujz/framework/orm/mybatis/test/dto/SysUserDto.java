package com.hujz.framework.orm.mybatis.test.dto;

import java.util.Date;

import com.hujz.sb.framework.dto.BasicDto;

public class SysUserDto extends BasicDto {

	/**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2015年9月10日 下午12:47:21
	 * 含义 TODO
	 */
	private static final long serialVersionUID = -1859291782578817221L;

	private String id;

	private String loginName;
	
	private String name;
	
	private String password;
	
	private String salt;
	
	private Date birthday;
	
	private Integer gender;
	
	private String email;
	
	private String phone;
	
	private String icon;
	
	private Boolean state;
	
	private String description;
	
	private Long loginCount;
	
	private Date previousVisit;
	
	private Date lastVisit;

	/**
	 * id的获取.
	 * @return Long
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设定id的值.
	 * @param Long
	 */
	public void setId(String id) {
		this.id = id;
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
