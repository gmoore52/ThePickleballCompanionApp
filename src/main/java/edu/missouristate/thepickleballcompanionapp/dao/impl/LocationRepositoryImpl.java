package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.LocationRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Location;
import edu.missouristate.thepickleballcompanionapp.domain.QLocation;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepositoryImpl extends QuerydslRepositorySupport implements LocationRepositoryCustom {
    QLocation location = QLocation.location;


    public LocationRepositoryImpl() {
        super(QLocation.class);
    }

    public Location getLocationById(Long id){
        return from(location)
                .select(location)
                .where(location.id.eq(id))
                .fetchOne();
    }
}
