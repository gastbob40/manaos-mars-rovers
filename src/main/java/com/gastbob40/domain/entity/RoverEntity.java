package com.gastbob40.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.With;
import lombok.val;

import java.util.List;

@With @AllArgsConstructor @Getter
public class RoverEntity {
    int posX;
    int posY;
    Orientation orientation;

    public String simulate(BoardEntity board, List<CommandEntity> commands) {
        for (val command : commands) {
            switch (command) {
                case L -> orientation = orientation.turnLeft();
                case R -> orientation = orientation.turnRight();
                case M -> move();
            }
        }

        return String.format("%d %d %s", posX, posY, orientation);
    }

    private void move() {
        switch (orientation) {
            case N -> posY++;
            case E -> posX++;
            case S -> posY--;
            case W -> posX--;
        }
    }

    public enum Orientation {
        N(0), E(1), S(2), W(3);

        public final int value;

        Orientation(int value) {
            this.value = value;
        }

        public static Orientation fromValue(int value) {
            for (Orientation o : Orientation.values()) {
                if (o.value == value) {
                    return o;
                }
            }

            throw new IllegalArgumentException("Invalid orientation value");
        }

        public Orientation turnLeft() {
            return Orientation.fromValue((value - 1 + 4) % 4);
        }

        public Orientation turnRight() {
            return Orientation.fromValue((value + 1) % 4);
        }
    }
}
