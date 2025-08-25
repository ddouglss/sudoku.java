package br.com.doug.model;

import java.util.Collection;
import java.util.List;

import static br.com.doug.model.GameStatusEnum.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


public class Board {

    private final List<List<Space>> space;

    public Board(List<List<Space>> space) {
        this.space = space;
    }

    public List<List<Space>> getSpace() {
        return space;
    }

    public GameStatusEnum getStatus(){
        if(space.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))){
            return NON_STARTED;
        }

        return space.stream().flatMap(Collection::stream)
                .anyMatch(s -> isNull(s.getActual())) ?
                INCOMPLETE : COMPLETE;
    }

    public boolean hasError(){
        if(getStatus() == NON_STARTED) {
            return false;
        }

        return space.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    public boolean changeValue(final int col, final int row, final int value){
        var spaces = space.get(col).get(row);
        if (spaces.isFixed()){
            return false;
        }
        spaces.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row){
        var spaces = space.get(col).get(row);
        if (spaces.isFixed()){
            return false;
        }
        spaces.clearSpace();
        return true;
    }

    public void reset(){
        space.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished(){
        return !hasError() && getStatus().equals(COMPLETE);
    }

}
