package com.dll;

import com.sun.jna.Library;
import com.sun.jna.Native;

public  interface  ExampleDLL  extends Library {
//  ExampleDLL  dd = (ExampleDLL ) Native.load("D:\\SM2Lib",ExampleDLL .class);
  ExampleDLL  dd = (ExampleDLL ) Native.load("SM2Lib",ExampleDLL .class);
  int  sm2_verify(String plain,String pubKey,String signResult);
  int  sm3_hash(String plain, byte[] hash32);
  int add(int a,int b);

}
