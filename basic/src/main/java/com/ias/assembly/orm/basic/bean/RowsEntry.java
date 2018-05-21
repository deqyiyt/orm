package com.ias.assembly.orm.basic.bean;

import java.io.Serializable;

public abstract class RowsEntry implements Serializable {

	/**
	 * 
	 * @type long
	 * @date 2018年5月21日 上午10:46:39
	 */
	private static final long serialVersionUID = -1847048305498526541L;
	
	/**
	 * 当前行数的序号
	 * @type int
	 * @date 2018年5月21日 上午10:47:02
	 */
	private int rowsNo;

	public int getRowsNo() {
		return rowsNo;
	}

	public void setRowsNo(int rowsNo) {
		this.rowsNo = rowsNo;
	}
}
