package com.teetov.scratch.model.symbol;

import com.teetov.scratch.exception.ScratchGameException;

import java.util.Objects;

public abstract class Symbol {

    protected final String name;

    protected Symbol(String name) {
        if (name == null || name.isEmpty()) {
            throw new ScratchGameException("Name of one of symbol empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol symbol = (Symbol) o;

        return Objects.equals(name, symbol.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
