package com.gastbob40.domain.entity;

import com.gastbob40.utils.Assertions;
import com.gastbob40.utils.Logged;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import lombok.val;

import javax.ws.rs.BadRequestException;
import java.util.List;

@With @AllArgsConstructor @Getter
public class RoverEntity implements Logged {
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

            Assertions.assertThat(board.isInside(posX, posY)).orElseThrow(() -> new BadRequestException("Rover is out of the board"));
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

        logger().debug("Rover moved to {} {}", posX, posY);
    }

    public enum Orientation implements Logged {
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

        // Calculate the new orientation using the value of the enum
        public Orientation turnLeft() {
            val orientation = Orientation.fromValue((value - 1 + 4) % 4);

            logger().debug("Rover turned left from {} to {}", this, orientation);
            return orientation;
        }

        // Calculate the new orientation using the value of the enum
        public Orientation turnRight() {
            val orientation = Orientation.fromValue((value + 1) % 4);

            logger().debug("Rover turned right from {} to {}", this, orientation);
            return orientation;
        }
    }
}
