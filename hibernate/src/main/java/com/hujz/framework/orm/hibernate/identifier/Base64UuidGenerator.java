package com.hujz.framework.orm.hibernate.identifier;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
 
public class Base64UuidGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SessionImplementor session, Object object)
            throws HibernateException {
        // TODO Auto-generated method stub
        return UuidUtils.compressedUuid();
    }
}

