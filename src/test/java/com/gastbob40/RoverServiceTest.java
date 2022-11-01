package com.gastbob40;

import com.gastbob40.domain.service.RoverService;
import io.quarkus.test.junit.QuarkusTest;
import lombok.val;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class RoverServiceTest {
    @Inject RoverService roverService;

    @Test
    public void testBoardNull() {
        assertThrows(RuntimeException.class, () -> roverService.getBoard(null));
    }

    @Test
    public void testMalformedBoard() {
        assertThrows(RuntimeException.class, () -> roverService.getBoard("55"));
    }

    @Test
    public void testNotNumberBoard() {
        assertThrows(RuntimeException.class, () -> roverService.getBoard("55 A"));
    }

    @Test
    public void testNegativeBoard() {
        assertThrows(RuntimeException.class, () -> roverService.getBoard("-1 5"));
    }

    @Test
    public void testZeroBoard() {
        assertThrows(RuntimeException.class, () -> roverService.getBoard("0 5"));
    }

    @Test
    public void testValidBoard() {
        val board = roverService.getBoard("5 7");

        assertEquals(5, board.getWidth());
        assertEquals(7, board.getHeight());
    }
}
