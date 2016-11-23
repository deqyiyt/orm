package com.hujz.framework.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.hujz.framework.orm.bean.PageTools;
/**
 *********************************************** 
 * @Create Date: 2014-2-24 下午2:12:02
 * @Create Author: jiuzhou.hu
 * @File Name: QueryResult
 * @Function: 查询结果保存
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class QueryResult<T> implements Serializable, Iterable<T> {

    /**
     * 序列化时为了保持版本的兼容性，即在版本升级时反序列化仍保持对象的唯一性
     */
    private static final long serialVersionUID = -6878647695532225271L;
 
    /**
     * 用于sqlserver查询区分大小写
     */
    public static String SQLSERVER_ENCODING="COLLATE Chinese_PRC_CS_AS";
    
    /**
     * @Title rows
     * @type List<T>
     * @date 2015-3-16 下午5:45:07
     * 含义 结果集
     */
    private List<T> rows = new ArrayList<T>(0);
    
    /**
     * @Title pageTools
     * @type PageTools
     * @date 2015-3-16 下午5:44:58
     * 含义 翻页信息
     */
    private PageTools pageTools = new PageTools();
    
    /**
     * @Title total
     * @type int
     * @date 2015-3-17 上午10:50:53
     * 含义 总条数
     */
    private int total;

    /**
	 * pageTools的获取.
	 * @return PageTools
	 */
	public PageTools getPageTools() {
		return pageTools;
	}

	/**
	 * 设定pageTools的值.
	 * @param PageTools
	 */
	public void setPageTools(PageTools pageTools) {
		this.pageTools = pageTools;
	}
	
	/**
	 * rows的获取.
	 * @return List<T>
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * 设定rows的值.
	 * @param List<T>
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	

	/**
	 * total的获取.
	 * @return int
	 */
	public int getTotal() {
		if(this.getPageTools() != null) {
			this.total = this.getPageTools().getRecordCount();
		}
		return total;
	}

	/**
     * 把qr中的对象的顺序混淆打乱
     * @param qr 查詢結果對象
     */
    public void shuffle(List<T> list) {
        if ((list == null) || (list.size() == 0)) {
            return;
        } else {
            Collections.shuffle(list);
        }
    }

	
	public int size() {
		return this.getRows().size();
	}

	
	public boolean isEmpty() {
		return this.getRows().isEmpty();
	}

	
	public boolean contains(Object o) {
		return this.getRows().contains(o);
	}

	
	public Iterator<T> iterator() {
		return this.getRows().iterator();
	}

	
	public Object[] toArray() {
		return this.getRows().toArray();
	}

	
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		return this.getRows().toArray(a);
	}
	
	public boolean add(T e) {
		return this.getRows().add(e);
	}

	
	public boolean remove(Object o) {
		return this.getRows().remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.getRows().remove(c);
	}

	
	public boolean addAll(Collection<? extends T> c) {
		return this.getRows().addAll(c);
	}
	
	public boolean addAll(QueryResult<? extends T> c) {
		int oldSize = this.size();
		for(T t:c) {
			this.add(t);
		}
		return this.size() != oldSize;
	}
	
	
	public boolean addAll(int index, Collection<? extends T> c) {
		return this.getRows().addAll(index, c);
	}
	
	public boolean addAll(int index, QueryResult<? extends T> c) {
		int oldSize = this.size();
		for(T t:c) {
			this.add(index + oldSize, t);
		}
		return this.size() != oldSize;
	}

	
	public boolean removeAll(Collection<?> c) {
		return this.getRows().removeAll(c);
	}

	
	public boolean retainAll(Collection<?> c) {
		return this.getRows().retainAll(c);
	}

	
	public void clear() {
		this.getRows().clear();
	}

	
	public T get(int index) {
		return this.getRows().get(index);
	}

	
	public T set(int index, T element) {
		return this.getRows().set(index, element);
	}

	
	public void add(int index, T element) {
		this.getRows().add(index, element);
	}

	
	public T remove(int index) {
		return this.getRows().remove(index);
	}

	
	public int indexOf(Object o) {
		return this.getRows().indexOf(o);
	}

	
	public int lastIndexOf(Object o) {
		return this.getRows().lastIndexOf(o);
	}

	
	public ListIterator<T> listIterator() {
		return this.getRows().listIterator();
	}

	
	public ListIterator<T> listIterator(int index) {
		return this.getRows().listIterator(index);
	}

	
	public List<T> subList(int fromIndex, int toIndex) {
		return this.getRows().subList(fromIndex, toIndex);
	}
}
