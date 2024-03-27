package com.tsystems.core.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

class BufferedServletInputStream extends ServletInputStream {

    private ByteArrayInputStream inputStream;
    private boolean finished = false;

    public BufferedServletInputStream(byte[] buffer) {
        this.inputStream = new ByteArrayInputStream(buffer);
    }
    @Override
    public int available() throws IOException {
        return inputStream.available();
    }
    @Override
    public int read() throws IOException {
    	 int data = inputStream.read();
    	 if (data == -1) {
             this.finished = true;
         }
         return data;
    }
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
    	int data = inputStream.read( b, off, len );
	   	 if (data == -1) {
	            this.finished = true;
	        }
        return data;

    }
	@Override
	public boolean isFinished() {
		 return this.finished;
	}
	@Override
	public boolean isReady() {
		return true;
	}
	@Override
	public void close() throws IOException {
        super.close();
        this.inputStream.close();
    }
	@Override
	public void setReadListener(ReadListener listener) {
		throw new UnsupportedOperationException();
	}
}





