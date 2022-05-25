package com.lwc.community.dao;

import org.springframework.stereotype.Repository;

/**
 * @author 刘文长
 * @version 1.0
 */
@Deprecated
@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{

    @Override
    public String select() {
        return "Hibernate";
    }
}
