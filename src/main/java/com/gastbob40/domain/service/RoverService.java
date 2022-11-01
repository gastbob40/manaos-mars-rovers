package com.gastbob40.domain.service;

import com.gastbob40.domain.entity.BoardEntity;
import com.gastbob40.utils.Assertions;
import lombok.val;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import java.util.Arrays;

@ApplicationScoped
public class RoverService {
    /**
     * 5 5 1 2 N LMLMLMLMM 3 3 E MMRMMRMRRM
     */
    public String simulate(String input) {
        if (input == null || input.length() == 0) {
            throw new BadRequestException("Input is empty");
        }

        val lines = Arrays.asList(input.split(System.lineSeparator()));
        Assertions.assertNotEmpty(lines).orElseThrow(BadRequestException::new);

        getBoard(lines.get(0));
        return null;
    }

    public BoardEntity getBoard(String line) {
        Assertions.assertNotNull(line).orElseThrow(BadRequestException::new);

        val board = Arrays.asList(line.split(" "));
        Assertions.assertThat(board.size() == 2).orElseThrow(BadRequestException::new);

        return new BoardEntity(Integer.parseInt(board.get(0)), Integer.parseInt(board.get(1)));
    }
}
