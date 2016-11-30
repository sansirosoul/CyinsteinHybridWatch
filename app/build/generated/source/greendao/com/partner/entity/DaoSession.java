package com.partner.entity;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.partner.entity.Partner;

import com.partner.entity.PartnerDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig partnerDaoConfig;

    private final PartnerDao partnerDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        partnerDaoConfig = daoConfigMap.get(PartnerDao.class).clone();
        partnerDaoConfig.initIdentityScope(type);

        partnerDao = new PartnerDao(partnerDaoConfig, this);

        registerDao(Partner.class, partnerDao);
    }
    
    public void clear() {
        partnerDaoConfig.clearIdentityScope();
    }

    public PartnerDao getPartnerDao() {
        return partnerDao;
    }

}
