package io.crate.planner.symbol;

import io.crate.operator.Input;
import org.cratedb.DataType;

public abstract class Literal<ValueType, LiteralType> extends ValueSymbol
        implements Input<ValueType>, Comparable<LiteralType> {

    public static Literal forType(DataType type, Object value) {
        if (value == null) {
            return Null.INSTANCE;
        }

        switch (type) {
            case BYTE:
                return new IntegerLiteral(((Byte) value).intValue());
            case SHORT:
                return new IntegerLiteral(((Short)value).intValue());
            case INTEGER:
                return new IntegerLiteral((Integer)value);
            case TIMESTAMP:
            case LONG:
                return new LongLiteral((Long)value);
            case FLOAT:
                return new FloatLiteral((Float)value);
            case DOUBLE:
                return new DoubleLiteral((Double)value);
            case BOOLEAN:
                return new BooleanLiteral((Boolean)value);
            case IP:
            case STRING:
                return new StringLiteral((String)value);
            case OBJECT:
                break;
            case NOT_SUPPORTED:
                throw new UnsupportedOperationException();
        }

        return null;
    }

    public Literal convertTo(DataType type) {
        if (valueType() == type) {
            return this;
        }
        throw new UnsupportedOperationException("Invalid input for type " + type.getName() + ": " + value().toString());
    }
}
