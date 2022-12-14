package com.gastbob40.domain.service;

import com.gastbob40.domain.entity.BoardEntity;
import com.gastbob40.domain.entity.CommandEntity;
import com.gastbob40.domain.entity.RoverEntity;
import com.gastbob40.utils.Assertions;
import com.gastbob40.utils.Logged;
import lombok.val;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import java.util.Arrays;
import java.util.List;

import static com.gastbob40.utils.Seq.seq;

@ApplicationScoped
public class RoverService implements Logged {
    public String simulate(String input) {
        Assertions.assertThat(input != null && input.length() != 0).orElseThrow(() -> new BadRequestException("Input cannot be empty"));

        var lines = Arrays.asList(input.split(System.lineSeparator()));
        Assertions.assertNotEmpty(lines).orElseThrow(BadRequestException::new);

        val board = getBoard(lines.get(0));
        lines = lines.subList(1, lines.size());

        Assertions.assertEquals(lines.size() % 2, 0).orElseThrow(BadRequestException::new); // 2 lines per rover

        val response = new StringBuilder();

        while (lines.size() > 0) {
            val rover = getRover(lines.get(0));
            val commands = getCommands(lines.get(1));

            response.append(rover.simulate(board, commands)).append(System.lineSeparator());

            lines = lines.subList(2, lines.size());
        }

        return response.toString();
    }

    /**
     * Get a board from input
     *
     * @param line
     *         Example format 1 2
     *
     * @return A new board
     */
    public BoardEntity getBoard(String line) {
        Assertions.assertNotNull(line).orElseThrow(BadRequestException::new);

        val board = Arrays.asList(line.split(" "));
        Assertions.assertEquals(board.size(), 2).orElseThrow(BadRequestException::new);

        try {
            val width = Integer.parseInt(board.get(0));
            val height = Integer.parseInt(board.get(1));

            Assertions.assertThat(width > 0).orElseThrow(BadRequestException::new);
            Assertions.assertThat(height > 0).orElseThrow(BadRequestException::new);

            logger().info("Board created: {}x{}", width, height);
            return new BoardEntity(width, height);
        } catch (NumberFormatException e) {
            logger().error("Invalid board input: {}", line);
            throw new BadRequestException("Invalid board size");
        }
    }

    /**
     * Get a rover from coordinates and orientation
     *
     * @param line
     *         Example format 1 2 N
     *
     * @return A new rover
     */
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

            logger().info("Rover created: {} {} {}", x, y, orientation);
            return new RoverEntity(x, y, orientation);
        } catch (IllegalArgumentException e) {
            logger().error("Invalid rover input: {}", line);
            throw new BadRequestException("Invalid rover position or orientation");
        }
    }

    /**
     * Get the list of commands from a string
     *
     * @param line
     *         Example format LMLMLMLMM
     *
     * @return A list of command
     */
    public List<CommandEntity> getCommands(String line) {
        Assertions.assertNotNull(line).orElseThrow(BadRequestException::new);

        try {
            val chars = line.chars().mapToObj(c -> (char) c).toList();
            val commands = seq(chars).map(Object::toString).map(CommandEntity::valueOf).toList();

            logger().info("Commands created: {}", commands);
            return commands;
        } catch (IllegalArgumentException e) {
            logger().error("Invalid commands input: {}", line);
            throw new BadRequestException("Invalid command");
        }
    }
}
