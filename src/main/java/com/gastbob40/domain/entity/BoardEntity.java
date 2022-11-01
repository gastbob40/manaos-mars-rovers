package com.gastbob40.domain.entity;

import lombok.Value;
import lombok.With;

@Value @With
public class BoardEntity {
    int width;
    int height;

    public boolean isInside(int posX, int posY) {
        return posX >= 0 && posX <= width && posY >= 0 && posY <= height;
    }
}
