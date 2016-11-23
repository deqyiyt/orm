package com.hujz.framework.orm.hibernate.identifier;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.hujz.framework.orm.util.StringUtils;
 
public class UuidGenerator implements IdentifierGenerator {
   
	@Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        return StringUtils.createSystemDataPrimaryKey();
    }
}

