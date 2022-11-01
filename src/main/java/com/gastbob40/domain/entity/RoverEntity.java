package com.gastbob40.domain.entity;

import lombok.Value;
import lombok.With;

@Value @With
public class RoverEntity {
    int posX;
    int posY;
    Orientation orientation;

    public enum Orientation {
        N, E, S, W
    }
}
