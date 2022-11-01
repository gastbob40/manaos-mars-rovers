package com.gastbob40;

import com.gastbob40.domain.entity.CommandEntity;
import com.gastbob40.domain.entity.RoverEntity;
import com.gastbob40.domain.service.RoverService;
import io.quarkus.test.junit.QuarkusTest;
import lombok.val;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class RoverServiceTest {
    @Inject RoverService roverService;

    @Test
    public void testBoardNull() {
        assertThrows(BadRequestException.class, () -> roverService.getBoard(null));
    }

    @Test
    public void testMalformedBoard() {
        assertThrows(BadRequestException.class, () -> roverService.getBoard("55"));
    }

    @Test
    public void testNotNumberBoard() {
        assertThrows(BadRequestException.class, () -> roverService.getBoard("55 A"));
    }

    @Test
    public void testNegativeBoard() {
        assertThrows(BadRequestException.class, () -> roverService.getBoard("-1 5"));
    }

    @Test
    public void testZeroBoard() {
        assertThrows(BadRequestException.class, () -> roverService.getBoard("0 5"));
    }

    @Test
    public void testValidBoard() {
        val board = roverService.getBoard("5 7");

        assertEquals(5, board.getWidth());
        assertEquals(7, board.getHeight());
    }

    @Test
    public void testRoverNull() {
        assertThrows(BadRequestException.class, () -> roverService.getRover(null));
    }

    @Test
    public void testMalformedRover() {
        assertThrows(BadRequestException.class, () -> roverService.getRover("1 2"));
    }

    @Test
    public void testNotNumberRover() {
        assertThrows(BadRequestException.class, () -> roverService.getRover("1 A N"));
    }

    @Test
    public void testNotOrientationRover() {
        assertThrows(BadRequestException.class, () -> roverService.getRover("1 2 A"));
    }

    @Test
    public void testNegativeRover() {
        assertThrows(BadRequestException.class, () -> roverService.getRover("-1 2 N"));
    }

    @Test
    public void testValidRover() {
        val rover = roverService.getRover("1 2 N");

        assertEquals(1, rover.getPosX());
        assertEquals(2, rover.getPosY());
        assertEquals(RoverEntity.Orientation.N, rover.getOrientation());
    }

    @Test
    public void testCommandsNull() {
        assertThrows(BadRequestException.class, () -> roverService.getCommands(null));
    }

    @Test
    public void testMalformedCommands() {
        assertThrows(BadRequestException.class, () -> roverService.getCommands("LMRA"));
    }

    @Test
    public void testNotCommandCommands() {
        assertThrows(BadRequestException.class, () -> roverService.getCommands("L M R"));
    }

    @Test void testValidCommands() {
        val commands = roverService.getCommands("LMR");
        assertEquals(3, commands.size());
        assertEquals(CommandEntity.L, commands.get(0));
        assertEquals(CommandEntity.M, commands.get(1));
        assertEquals(CommandEntity.R, commands.get(2));
    }
}
