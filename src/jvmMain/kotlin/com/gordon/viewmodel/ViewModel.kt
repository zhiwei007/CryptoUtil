package com.gordon.viewmodel

import androidx.compose.runtime.MutableState
import com.des.DesUtil
import com.dll.ExampleDLL
import com.dukpt.Dukpt
import com.util.Util

fun desEnc(keys: MutableState<String>, dataSource: MutableState<String>, ret: MutableState<String>){
    val key = Util.hex2byte(keys.value)
    val data = Util.hex2byte(dataSource.value)
    try {
        val result = DesUtil.tdesEnc(data, key)
        ret.value = Util.hexString(result)
        println("加密结果:"+ret.value)
    } catch (e: java.lang.Exception) {
        ret.value = "desEnc error:"+e.toString()
    }
}
fun desDec(keys: MutableState<String>, dataSource: MutableState<String>, ret: MutableState<String>){
    val key = Util.hex2byte(keys.value)
    val data = Util.hex2byte(dataSource.value)
    try {
        val result = DesUtil.tdesDec(data, key)
        ret.value = Util.hexString(result)
        println("解密结果:"+ret.value)
    } catch (e: java.lang.Exception) {
        ret.value = "desDec error:"+e.toString()
    }
}

fun genKeys(
    ipek: MutableState<String>
    ,ksn: MutableState<String>
    ,pek: MutableState<String>
    ,mek: MutableState<String>
){
    val ipek_hex = ipek.value
    val ksn_hex = ksn.value
    val ipekBytes =  Util.hex2byte(ipek_hex)
    val ksnBytes =  Util.hex2byte(ksn_hex)
//                    val ipek = Util.hex2byte("E69BA53691E7C240AD122D3D4582E8D9")//Fill the string into the TextField
//                    val ksn = Util.hex2byte("00000007172700000001")
    val keyRegister = Util.hex2byte("C0C0C0C000000000C0C0C0C000000000")
    val keyRegisterBitmask = Dukpt.toBitSet(keyRegister)

    val data = Util.hex2byte("00000000000000FF00000000000000FF")
    val dataVariantBitmask = Dukpt.toBitSet(data)

    val mac = Util.hex2byte("000000000000FF00000000000000FF00")
    val macVariantBitmask = Dukpt.toBitSet(mac)

    try {
        val PEK = Dukpt._getCurrentKey(
            Dukpt.toBitSet(ipekBytes),
            Dukpt.toBitSet(ksnBytes),
            keyRegisterBitmask,
            dataVariantBitmask
        )
        println("PEK:" + Util.hexString(Dukpt.toByteArray(PEK)))
        pek.value =  Util.hexString(Dukpt.toByteArray(PEK))

        val MEK = Dukpt._getCurrentKey(
            Dukpt.toBitSet(ipekBytes),
            Dukpt.toBitSet(ksnBytes),
            keyRegisterBitmask,
            macVariantBitmask
        )
        println("MEK:" + Util.hexString(Dukpt.toByteArray(MEK)))
        mek.value =  Util.hexString(Dukpt.toByteArray(MEK))
    } catch (e: Exception) {
        println(e)
        pek.value = "Gen keys cause error:\n"+e.toString()
    }
}

fun sm2_verify(
    plain: MutableState<String>,
    pubKey: MutableState<String>,
    signResult: MutableState<String>,
    verifyRet: MutableState<String>,
)
{
    val pbPlain =  plain.value
    val pbPubKey =  pubKey.value
    val pbSignResult =  signResult.value
    if (pbPlain.isEmpty()){
        verifyRet.value = "plain data can not empty"
        return
    }
    if (pbPubKey.isEmpty()  ){
        verifyRet.value = "pubkey can not empty"
        return
    }
    if (pbSignResult.isEmpty()){
        verifyRet.value = "sign result can not empty"
        return
    }
    try {
        val ret = ExampleDLL.dd.sm2_verify(pbPlain, pbPubKey, pbSignResult)
        verifyRet.value = ret.toString(16)
        println("验签结果:"+ verifyRet.value)
    } catch (e: java.lang.Exception) {
        verifyRet.value = "sm2_verify error:"+e.toString()
    }
}
