package br.com.doug.model;

import java.util.List;

public class Board {

    private final List<List<Space>> space;

    public Board(List<List<Space>> space) {
        this.space = space;
    }

    public List<List<Space>> getSpace() {
        return space;
    }


}
