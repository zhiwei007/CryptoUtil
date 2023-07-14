package com.hash;

import java.security.MessageDigest;

public class HashUtil {
    public  static final  String MD5 = "MD5";
    public  static final  String SHA1 = "SHA1";
    public  static final  String SHA256 = "SHA-256";
    public  static final  String SHA384 = "SHA-384";
    public  static final  String SHA512 = "SHA-512";
    public  static final  String SM3 = "SM3";
    private  static  byte[] hash(String alg,byte[] input){
        try{
            MessageDigest  messageDigest  =  MessageDigest.getInstance(alg);
            messageDigest.update(input);
            return messageDigest.digest();
        }catch (Exception e){
            System.out.println(""+e.toString());
            return null;
        }
    }
     public static byte[] hashUtil(String alg,byte[] input){
         switch (alg){
             case MD5:
                 return hash(MD5,input);
             case SHA1:
                 return hash(SHA1,input);
             case SHA256:
                 return hash(SHA256,input);
             case SHA384:
                 return hash(SHA384,input);
             case SHA512:
                 return hash(SHA512,input);
             case SM3:
                 return SM3Digest.hashSm3(input);
             default:
                 return null;
         }
     }
}
