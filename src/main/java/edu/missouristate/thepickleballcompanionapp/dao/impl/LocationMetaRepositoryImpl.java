package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.LocationMetaRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.QLocationMeta;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class LocationMetaRepositoryImpl extends QuerydslRepositorySupport implements LocationMetaRepositoryCustom {
    QLocationMeta locationMeta = QLocationMeta.locationMeta;


    public LocationMetaRepositoryImpl() {
        super(QLocationMeta.class);
    }
}
