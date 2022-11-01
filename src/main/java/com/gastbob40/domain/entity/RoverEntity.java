package com.gastbob40.domain.entity;

import lombok.Value;
import lombok.With;

@Value @With
public class RoverEntity {
    String posX;
    String posY;
    Orientation orientation;

    public enum Orientation {
        N, E, S, W
    }
}
