/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.ias.example.boot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Entity
@Table(name="t_system_user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequence")
    @Column(length = 32, unique = true, nullable = false)
    @ColumnType(jdbcType = JdbcType.BIGINT)
    private Long id;
    
    private String name;

    private Integer sex;
    

    public User() {
        super();
    }

    public User(String name, Integer sex) {
        super();
        this.name = name;
        this.sex = sex;
    }

    /**
     * id的获取.
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * 设定id的值.
     * @param Long
     */
    public void setId(Long id) {
        this.id = id;
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
     * sex的获取.
     * @return Integer
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设定sex的值.
     * @param Integer
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }
}