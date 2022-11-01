package com.gastbob40.domain.service;

import com.gastbob40.domain.entity.BoardEntity;
import com.gastbob40.domain.entity.CommandEntity;
import com.gastbob40.domain.entity.RoverEntity;
import com.gastbob40.utils.Assertions;
import lombok.val;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import java.util.Arrays;
import java.util.List;

import static com.gastbob40.utils.Seq.seq;

@ApplicationScoped
public class RoverService {
    /**
     * 5 5 1 2 N LMLMLMLMM 3 3 E MMRMMRMRRM
     */
    public String simulate(String input) {
        if (input == null || input.length() == 0) {
            throw new BadRequestException("Input is empty");
        }

        var lines = Arrays.asList(input.split(System.lineSeparator()));
        Assertions.assertNotEmpty(lines).orElseThrow(BadRequestException::new);

        val board = getBoard(lines.get(0));
        lines = lines.subList(1, lines.size());

        Assertions.assertEquals(lines.size() % 2, 0).orElseThrow(BadRequestException::new); // 2 lines per rover

        while (lines.size() > 0) {
            val rover = getRover(lines.get(0));
            val commands = getCommands(lines.get(1));
            lines = lines.subList(2, lines.size());
        }

        return null;
    }

    public BoardEntity getBoard(String line) {
        Assertions.assertNotNull(line).orElseThrow(BadRequestException::new);

        val board = Arrays.asList(line.split(" "));
        Assertions.assertThat(board.size() == 2).orElseThrow(BadRequestException::new);

        try {
            val width = Integer.parseInt(board.get(0));
            val height = Integer.parseInt(board.get(1));

            Assertions.assertThat(width > 0).orElseThrow(BadRequestException::new);
            Assertions.assertThat(height > 0).orElseThrow(BadRequestException::new);

            return new BoardEntity(width, height);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid board size");
        }
    }

    // 1 2 N
    public RoverEntity getRover(String line) {
        Assertions.assertNotNull(line).orElseThrow(BadRequestException::new);

        val parts = Arrays.asList(line.split(" "));
        Assertions.assertThat(parts.size() == 3).orElseThrow(BadRequestException::new);

        try {
            val x = Integer.parseInt(parts.get(0));
            val y = Integer.parseInt(parts.get(1));
            val orientation = RoverEntity.Orientation.valueOf(parts.get(2));

            Assertions.assertThat(x >= 0).orElseThrow(BadRequestException::new);
            Assertions.assertThat(y >= 0).orElseThrow(BadRequestException::new);

            return new RoverEntity(x, y, orientation);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid rover position or orientation");
        }
    }

    // LMLMLMLMM
    public List<CommandEntity> getCommands(String line) {
        Assertions.assertNotNull(line).orElseThrow(BadRequestException::new);

        try {
            val chars = line.chars().mapToObj(c -> (char) c).toList();
            return seq(chars).map(Object::toString).map(CommandEntity::valueOf).toList();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid command");
        }
    }
}
