package com.hujz.framework.orm.hibernate.identifier;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.ias.common.utils.id.UUIDUtils;
 
public class UuidGenerator implements IdentifierGenerator {
   
	@Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        return UUIDUtils.createSystemDataPrimaryKey();
    }
}

