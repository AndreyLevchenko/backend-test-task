package io.mohami.test.backend;

import io.mohami.test.backend.controller.NotATextException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author andrey
 */
public class TextFileOutputStream extends FileOutputStream {
    
    public TextFileOutputStream(File file) throws FileNotFoundException {
        super(file);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        for (byte b : bytes) {
            if (b < 0x09) {
                throw new NotATextException();
            }
        }
        super.write(bytes); 
    }

    @Override
    public void write(int b) throws IOException {
        if (b < 0x09) {
            throw new NotATextException();
        }
        super.write(b); 
    }

    @Override
    public void write(byte[] bytes, int off, int len) throws IOException {
        for (int i = off; i < len ; i++) {
            if (bytes[i] < 0x09) {
                throw new NotATextException();
            }
        }
        super.write(bytes, off, len); 
    }
    
    
}
