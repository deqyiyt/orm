package com.hujz.framework.orm.entity;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.hujz.framework.orm.annotation.CreateTime;
import com.hujz.framework.orm.annotation.UpdateTime;

/**
 *********************************************** 
 * @Create Date: 2014-2-25 下午5:48:16
 * @Create Author: jiuzhou.hu
 * @File Name: BasicEntity
 * @Function: 实体类超类
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
@MappedSuperclass
public abstract class BasicEntity extends SuperEntity{

	/**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2014-2-25 下午5:42:00 
	 * 含义 
	 */
	@Transient
	private static final long serialVersionUID = -1464340211467283689L;
	
	@Id
	/*
	//  hibernate使用UUID方式
	@GenericGenerator(name = "uuidGenerator", strategy = "com.hujz.framework.orm.hibernate.identifier.UuidGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	*/
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 32, unique = true, nullable = false)
	private String id;

	/**
	 * @Title created
	 * @type Date
	 * @date 2014-2-24 下午12:49:24 
	 * 含义 创建时间
	 */
	@Column(updatable=false)
	@CreateTime
	private Date createDt;

	/**
	 * @Title modified
	 * @type Date
	 * @date 2014-2-24 下午12:49:35 
	 * 含义 最后一次修改时间
	 */
	@Column
	@UpdateTime
	private Date updateDt;
	
	/**
	 * @Title isDelete
	 * @type Integer
	 * @date 2015年9月16日 下午1:55:05
	 * 含义 删除标识0未删，1 删除
	 */
	@Column
	private Integer isDelete;
	
	/**
	 * id的获取.
	 * @return String
	 */
	public String getId() {
		return (id == null || "".equals(id.trim())) ? null : id;
	}

	/**
	 * 设定id的值.
	 * @param String
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * isDelete的获取.
	 * @return Integer
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * 设定isDelete的值.
	 * @param Integer
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * createDt的获取.
	 * @return Date
	 */
	public Date getCreateDt() {
		return createDt;
	}

	/**
	 * 设定createDt的值.
	 * @param Date
	 */
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	/**
	 * updateDt的获取.
	 * @return Date
	 */
	public Date getUpdateDt() {
		return updateDt;
	}

	/**
	 * 设定updateDt的值.
	 * @param Date
	 */
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	/**
     * java.sql.Clob 转化为String的方法
     * @author jiuzhou.hu
     * @date 2014年8月21日 上午11:12:37
     * @param clob
     * @return
     */
    public String clobToString(Clob clob) {
        if (clob == null) {
            return null;
        }
        try {
            Reader inStreamDoc = clob.getCharacterStream();

            char[] tempDoc = new char[(int) clob.length()];
            inStreamDoc.read(tempDoc);
            inStreamDoc.close();
            return new String(tempDoc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException es) {
            es.printStackTrace();
        }

        return null;
    }
}
