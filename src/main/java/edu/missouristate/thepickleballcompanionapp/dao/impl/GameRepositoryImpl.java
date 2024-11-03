package edu.missouristate.thepickleballcompanionapp.dao.impl;

import edu.missouristate.thepickleballcompanionapp.dao.custom.GameRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.Game;
import edu.missouristate.thepickleballcompanionapp.domain.QGame;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRepositoryImpl extends QuerydslRepositorySupport implements GameRepositoryCustom {
    QGame game = QGame.game;

    public GameRepositoryImpl() {
        super(QGame.class);
    }

    @Override
    public List<Game> getUserGames(String username){
        return from(game)
                .fetch();
// todo fix this
        //        return from(game)
//                .where(game.player1.userName.eq(username)
//                        .or(game.player2.userName).eq(username))
//                        .or(game.player2.userName).eq(username))
//                        .or(game.player3.userName).eq(username))
//                        .or(game.player4.userName).eq(username)))
//                .fetch();
    }
}
