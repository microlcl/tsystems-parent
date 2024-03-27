package com.tsystems.core.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.tsystems.core.utils.StringUtil;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;



public class RequestWrapper extends HttpServletRequestWrapper {

	private byte[] buffer;
    private long id;
    private static final int MAX_INCOMING_BYTES = 2 * 1024 * 1024;
    
    private boolean postRequest = false;

    public RequestWrapper(Long requestId, HttpServletRequest request) throws IOException{
        super(request);
        //如果是文件上传就不截取body
        if(!StringUtil.isAllEmptyWithTrim(request.getContentType())&&request.getContentType().indexOf("multipart/form-data")>=0){
        	return;
        }
        //如果不是 post 直接返回
        if (!request.getMethod().toUpperCase().equals("POST")) {
        	return;
        }
        this.postRequest = true;
        InputStream is = new BufferedInputStream(request.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buff[] = new byte[ 1024 ];
        int totalBytes = 0,read = 0;
        while((read = is.read( buff ) ) > 0 ) {
            baos.write( buff, 0, read );
        	totalBytes += read;
			if (totalBytes > MAX_INCOMING_BYTES) {
				throw new IOException("Input too large");
			}
        }
        this.buffer = baos.toByteArray();
        this.id = requestId;
    }
    
    public boolean isPostRequest() {
    	return this.postRequest;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
    	if(null == this.buffer) {
            return super.getInputStream();
        }
    	return new BufferedServletInputStream(this.buffer);
    }

    public byte[] toByteArray(){
        return this.buffer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
