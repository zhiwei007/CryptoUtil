package com.gordon
import com.hash.HashUtil
import com.util.Util

fun main() {
//    System.loadLibrary("D:\\SM2Lib");
//    int ret = Dll.dd.sm2Verify();
//    println(ret)
    println(Util.hexString(HashUtil.hashUtil(HashUtil.SM3,Util.hex2byte("3221DEF0AE4B07B38B39C659168BC8E6"))))

//        System.setProperty("jna.encoding", "GBK");
//    val myLibraryPath = System.getProperty("user.dir")
//    println("myLibraryPath   :$myLibraryPath")
//    System.setProperty("java.library.path", myLibraryPath)
//    val ret = Dll.dd.SM2VERIFY2()
//    println("sm2Verify  ret:$ret")


}
