package simpleDb;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public class ByteReader {
    private ByteBuffer byteBuffer;
    private final int INT_SIZE = 4;
    private final int CHAR_SIZE = 2;
    private final int DOUBLE_SIZE = 8;
    private final int SHORT_SIZE = 2;
    private final int FLOAT_SIZE = 4;
    private final int LONG_SIZE = 8;


    public ByteReader(InputStream inputStream) throws IOException {
        byteBuffer = ByteBuffer.allocate(inputStream.available());
        byteBuffer.put(inputStream.readAllBytes());
        byteBuffer.rewind();

    }
    public int getInt() {
        if (byteBuffer.remaining() >= INT_SIZE)
            return byteBuffer.getInt();
        else
            throw new BufferOverflowException();
    }

    public char getChar() {
        if (byteBuffer.remaining() >= CHAR_SIZE)
            return byteBuffer.getChar();
        else
            throw new BufferOverflowException();
    }

    public String getString() {
        StringBuilder stringBuilder = new StringBuilder();
        while (byteBuffer.remaining() >= CHAR_SIZE)
            stringBuilder.append(getChar());
        return stringBuilder.toString();
    }

    public double getDouble(){
        double ans;
        if(byteBuffer.remaining() >= DOUBLE_SIZE){
            ans = byteBuffer.getDouble();
            return ans;
        }else {
            throw new BufferOverflowException();
        }
    }
    public float getFloat(){
        float ans;
        if(byteBuffer.remaining() >= FLOAT_SIZE){
            ans = byteBuffer.getFloat();
            return ans;
        }else {
            throw new BufferOverflowException();
        }
    }

    public short getShort(){
        short ans;
        if(byteBuffer.remaining() >= SHORT_SIZE){
            ans = byteBuffer.getShort();
            return ans;
        }else {
            throw new BufferOverflowException();
        }
    }
    public long getLong(){
        long ans;
        if(byteBuffer.remaining() >= LONG_SIZE){
            ans = byteBuffer.getLong();
            return ans;
        }else {
            throw new BufferOverflowException();
        }
    }
    public int[] getIntArray(){
        int size = byteBuffer.remaining() / INT_SIZE;
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = getInt();
        }
        return array;
    }
}
