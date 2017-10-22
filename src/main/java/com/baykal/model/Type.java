package com.baykal.model;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:45
 */
public enum Type {
    BLACK,
    WHITE;

    public Type opposite(){
        return this == BLACK? WHITE : BLACK;
    }
}
