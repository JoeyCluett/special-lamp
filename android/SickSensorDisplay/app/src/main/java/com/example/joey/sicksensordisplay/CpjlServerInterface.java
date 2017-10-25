package com.example.joey.sicksensordisplay;

/**
 * Created by joey on 10/11/17.
 */
import java.io.*;   // InputStream, OutputStream
import java.net.*;  // Socket
import java.util.*; // ArrayList<>
import java.nio.*;  // ByteBuffer

public class CpjlServerInterface {
    private String hostname;
    private int portnumber;

    // tcp socket stuff needed
    private Socket       sock;
    private OutputStream out = null;
    private InputStream  in  = null;

    // incoming data is stored here
    private ArrayList<Byte> rx_buffer = null;
    private byte[] raw_byte_buffer = null;

    private static final String type_desc = "JAVA\0"; // null-terminated string

    public CpjlServerInterface(String hostname, int portnumber) {
        this.hostname = hostname;
        this.portnumber = portnumber;

        try {
            sock = new Socket(this.hostname, this.portnumber);
            out  = sock.getOutputStream();
            in   = sock.getInputStream();

            rx_buffer = new ArrayList<Byte>(256); // initial capacity
            raw_byte_buffer = new byte[1024];

            // network socket is now setup, tell server what kind of process we are (Java)
            out.write(type_desc.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // blocks until the proper number of bytes is available
    private byte[] getChunk(int chunk_size) {
        while(rx_buffer.size() < chunk_size) // wait for enough data to show up
            updateRxBuffer();
        byte[] bb = new byte[chunk_size];

        for(int i = 0; i < chunk_size; i++) {
            bb[i] = rx_buffer.get(0); // retreive
            rx_buffer.remove(0);      // and remove
        }

        return bb;
    }

    private void writeChunk(byte[] chunk) {
        try {
            this.out.write(chunk);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // find the index of the first null, -1 if there is none
    private int containsNull() {
        int s = rx_buffer.size();
        for(int i = 0; i < s; i++) {
            if(rx_buffer.get(i) == 0)
                return i;
        }
        return -1;
    }

    // read available data and put in rx_buffer
    private void updateRxBuffer() {
        try {
            int num_bytes_read = in.read(raw_byte_buffer);

            if(num_bytes_read > 0) {
                for(int i = 0; i < num_bytes_read; i++)
                    rx_buffer.add(raw_byte_buffer[i]);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public float extractFloat32() {
        ByteBuffer bb = ByteBuffer.wrap(getChunk(4));
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getFloat();
    }

    public void insertFloat32(float f32) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putFloat(f32);
        writeChunk(bb.array());
    }

    public double extractFloat64() {
        ByteBuffer bb = ByteBuffer.wrap(getChunk(8));
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getDouble();
    }

    public void insertFloat64(double f64) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putDouble(f64);
        writeChunk(bb.array());
    }

    public int extractInt32() {
        ByteBuffer bb = ByteBuffer.wrap(getChunk(4));
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }

    public void insertInt32(int i32) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(i32);
        writeChunk(bb.array());
    }

    public long extractInt64() {
        ByteBuffer bb = ByteBuffer.wrap(getChunk(8));
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getLong();
    }

    public void insertInt64(long i64) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putLong(i64);
        writeChunk(bb.array());
    }

    public String extractString() {
        int null_index = containsNull();
        while(null_index == -1) { // wait until a string appears in the rx stream
            updateRxBuffer();
            null_index = containsNull();
        }

        // copy correct number of bytes into seperate byte buffer
        byte[] str_byte = new byte[null_index];
        for(int i = 0; i < str_byte.length; i++)
            str_byte[i] = rx_buffer.get(i);

        // remove the string, and the null-terminator at the end
        for(int i = 0; i < null_index+1; i++)
            rx_buffer.remove(0);

        return new String(str_byte);
    }

    public void insertString(String str) {
        byte[] str_buffer = str.getBytes();
        try {
            this.out.write(str_buffer);
            this.out.write(0);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}