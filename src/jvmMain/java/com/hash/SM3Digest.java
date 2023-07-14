package com.hash;

import com.util.Util;

public class SM3Digest
{
    private static final int BYTE_LENGTH = 32;
     
    private static final int BLOCK_LENGTH = 64;
     
    private static final int BUFFER_LENGTH = BLOCK_LENGTH * 1;
     
    private byte[] xBuf = new byte[BUFFER_LENGTH];
     
    private int xBufOff;
     
    private byte[] V = SM3.iv.clone();
     
    private int cntBlock = 0;
 
    public SM3Digest() {
    }
 
    public SM3Digest(SM3Digest t)
    {
        System.arraycopy(t.xBuf, 0, this.xBuf, 0, t.xBuf.length);
        this.xBufOff = t.xBufOff;
        System.arraycopy(t.V, 0, this.V, 0, t.V.length);
    }
     
    public int doFinal(byte[] out, int outOff)
    {
        byte[] tmp = doFinal();
        System.arraycopy(tmp, 0, out, 0, tmp.length);
        return BYTE_LENGTH;
    }
 
    public void reset() 
    {
        xBufOff = 0;
        cntBlock = 0;
        V = SM3.iv.clone();
    }
 
    public void update(byte[] in, int inOff, int len)
    {
        int partLen = BUFFER_LENGTH - xBufOff;
        int inputLen = len;
        int dPos = inOff;
        if (partLen < inputLen) 
        {
            System.arraycopy(in, dPos, xBuf, xBufOff, partLen);
            inputLen -= partLen;
            dPos += partLen;
            doUpdate();
            while (inputLen > BUFFER_LENGTH) 
            {
                System.arraycopy(in, dPos, xBuf, 0, BUFFER_LENGTH);
                inputLen -= BUFFER_LENGTH;
                dPos += BUFFER_LENGTH;
                doUpdate();
            }
        }
 
        System.arraycopy(in, dPos, xBuf, xBufOff, inputLen);
        xBufOff += inputLen;
    }
 
    private void doUpdate() 
    {
        byte[] B = new byte[BLOCK_LENGTH];
        for (int i = 0; i < BUFFER_LENGTH; i += BLOCK_LENGTH) 
        {
            System.arraycopy(xBuf, i, B, 0, B.length);
            doHash(B);
        }
        xBufOff = 0;
    }
 
    private void doHash(byte[] B)
    {
        byte[] tmp = SM3.CF(V, B);
        System.arraycopy(tmp, 0, V, 0, V.length);
        cntBlock++;
    }
 
    private byte[] doFinal() 
    {
        byte[] B = new byte[BLOCK_LENGTH];
        byte[] buffer = new byte[xBufOff];
        System.arraycopy(xBuf, 0, buffer, 0, buffer.length);
        byte[] tmp = SM3.padding(buffer, cntBlock);
        for (int i = 0; i < tmp.length; i += BLOCK_LENGTH)
        {
            System.arraycopy(tmp, i, B, 0, B.length);
            doHash(B);
        }
        return V;
    }
 
    public void update(byte in) 
    {
        byte[] buffer = new byte[] { in };
        update(buffer, 0, 1);
    }
     
    public int getDigestSize() 
    {
        return BYTE_LENGTH;
    }
     
    public static void main(String[] args) 
    {
        byte[] md = new byte[32];
        byte[] msg1 = "228000000004".getBytes();
        SM3Digest sm3 = new SM3Digest();
        sm3.update(msg1, 0, msg1.length);
        sm3.doFinal(md, 0);
        String as= new String(Util.hexString(md));
        System.out.println(as.toUpperCase());
    }

    public static byte[] hashSm3(byte[] data){
        byte[] md = new byte[32];
        SM3Digest sm3 = new SM3Digest();
        sm3.update(data, 0, data.length);
        sm3.doFinal(md, 0);
//        String as =  (Util.hexString(md));
//        System.out.println(as.toUpperCase());
        return md;
    }
    
    public static String getSm3Str(String sn){
    	 byte[] md = new byte[32];
         byte[] msg = sn.getBytes();
         SM3Digest sm3 = new SM3Digest();
         sm3.update(msg, 0, msg.length);
         sm3.doFinal(md, 0);
         
         byte[] sm3HashXorSn = new byte[16];
         for (int  i = 0; i < 16;i++) {
     		sm3HashXorSn[i] = (byte) (md[i]^md[16+i]);
     	}
         String as= new String(Util.hexString(sm3HashXorSn));
         return as.toUpperCase();
    }
    
    
    public static String getSm3Str2(String sn){
   	    byte[] md = new byte[32];
        byte[] msg1 = sn.getBytes();
        SM3Digest sm3 = new SM3Digest();
        sm3.update(msg1, 0, msg1.length);
        sm3.doFinal(md, 0);
        
        byte[] sm3HashXorSn = new byte[16];
        for (int  i = 0;   i < 16;i++) {
    		sm3HashXorSn[i] = (byte) (md[i]^md[32-i-1]);  
    	}
        String as=  (Util.hexString(sm3HashXorSn));
        return as.toUpperCase();
   }
    
}