package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.LocationMetaRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Location;
import edu.missouristate.thepickleballcompanionapp.domain.LocationMeta;
import edu.missouristate.thepickleballcompanionapp.domain.QLocationMeta;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class LocationMetaRepositoryImpl extends QuerydslRepositorySupport implements LocationMetaRepositoryCustom {
    QLocationMeta locationMeta = QLocationMeta.locationMeta;


    public LocationMetaRepositoryImpl() {
        super(QLocationMeta.class);
    }

//    public LocationMeta getLocationMetaById(Long id){
//        return from(locationMeta)
//                .select(locationMeta)
//                .where(locationMeta.id.eq(id))
//                .fetchOne();
//    }
}
