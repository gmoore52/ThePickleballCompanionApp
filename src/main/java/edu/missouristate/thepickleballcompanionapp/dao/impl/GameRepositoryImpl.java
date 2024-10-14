package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.GameRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.QGame;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepositoryImpl extends QuerydslRepositorySupport implements GameRepositoryCustom {
    QGame game = QGame.game;


    public GameRepositoryImpl() {
        super(QGame.class);
    }
}
