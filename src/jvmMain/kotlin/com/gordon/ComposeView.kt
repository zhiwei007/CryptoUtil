package com.gordon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gordon.viewmodel.desDec
import com.gordon.viewmodel.desEnc
import com.gordon.viewmodel.genKeys
import com.gordon.viewmodel.sm2_verify
import com.hash.HashUtil
import com.hash.SM3Digest.hashSm3
import com.util.Util
import java.lang.Exception
import java.util.*
import java.util.function.UnaryOperator

@Composable
fun topBar(title: String) {
    Box(modifier = Modifier.height(62.dp).fillMaxWidth().background(Color.White)) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Divider(color = Color.LightGray)
            Text(
                title,
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(20.dp)
            )
            Divider(color = Color.LightGray)
        }
    }
}

@Composable
fun rowCell(label: String, valueStr: MutableState<String>) {
    Row(
        modifier = Modifier.padding(10.dp).fillMaxWidth(fraction = 0.5f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$label:", color = Color.Black, textAlign = TextAlign.Center)
        TextField(value = valueStr.value, onValueChange = {
            valueStr.value = it.trim()
        },
            placeholder = { Text("$label ...", color = Color.LightGray, textAlign = TextAlign.Center) },
            trailingIcon = {
                IconButton(onClick = {
                    valueStr.value = ""
                }) {
                    Icon(
                        Icons.Sharp.Delete, contentDescription = "Favorite",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
            }
        )
    }
}


@Composable
fun dukptPage() {
    var ipek = remember { mutableStateOf("") }
    var ksn = remember { mutableStateOf("") }
    var pek = remember { mutableStateOf("") }
    var mek = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize().fillMaxHeight().background(Color.White)) {
        Column(modifier = Modifier.align(alignment = Alignment.Center)) {
            rowCell("IPEK", ipek)
            rowCell("KSN", ksn)
            rowCell("PEK", pek)
            rowCell("MEK", mek)
            Button(modifier = Modifier.width(330.dp).height(80.dp).padding(start = 50.dp, top = 20.dp),
                onClick = {
                    if (ipek.value.isEmpty()) {
                        ipek.value = "ipek can not be empty"
                        return@Button
                    }
                    if (ksn.value.isEmpty()) {
                        ksn.value = "ksn can not be empty"
                        return@Button
                    }
                    genKeys(ipek, ksn, pek, mek)
                }) {
                Text("Derive Keys")
            }
        }
    }
}

@Composable
fun hashCheckBox(checkIndexState: MutableState<Int>) {
    var boxCheckList = remember { mutableStateListOf(false, false, false, false, false, false) }
    Box {
        Column(modifier = Modifier.align(alignment = Alignment.Center)) {
            Row {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("MD5", textAlign = TextAlign.Center)
                    Checkbox(checked = boxCheckList[0], onCheckedChange = {
                        if (it) {
                            checkIndexState.value = 0
                            boxCheckList[0] = it
                            boxCheckList[1] = !it
                            boxCheckList[2] = !it
                            boxCheckList[3] = !it
                            boxCheckList[4] = !it
                            boxCheckList[5] = !it
                        }
                    })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("SHA1", textAlign = TextAlign.Center)
                    Checkbox(checked = boxCheckList[1], onCheckedChange = {
                        if (it) {
                            checkIndexState.value = 1
                            boxCheckList[0] = !it
                            boxCheckList[1] = it
                            boxCheckList[2] = !it
                            boxCheckList[3] = !it
                            boxCheckList[4] = !it
                            boxCheckList[5] = !it
                        }
                    })
                }
            }
            Row {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("SHA256", textAlign = TextAlign.Center)
                    Checkbox(checked = boxCheckList[2], onCheckedChange = {
                        if (it) {
                            checkIndexState.value = 2
                            boxCheckList[0] = !it
                            boxCheckList[1] = !it
                            boxCheckList[2] = it
                            boxCheckList[3] = !it
                            boxCheckList[4] = !it
                            boxCheckList[5] = !it
                        }
                    })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("SHA384", textAlign = TextAlign.Center)
                    Checkbox(checked = boxCheckList[3], onCheckedChange = {
                        if (it) {
                            checkIndexState.value = 3
                            boxCheckList[0] = !it
                            boxCheckList[1] = !it
                            boxCheckList[2] = !it
                            boxCheckList[3] = it
                            boxCheckList[4] = !it
                            boxCheckList[5] = !it
                        }
                    })
                }
            }

            Row {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("SHA512", textAlign = TextAlign.Center)
                    Checkbox(checked = boxCheckList[4], onCheckedChange = {
                        if (it) {
                            checkIndexState.value = 4
                            boxCheckList[0] = !it
                            boxCheckList[1] = !it
                            boxCheckList[2] = !it
                            boxCheckList[3] = !it
                            boxCheckList[4] = it
                            boxCheckList[5] = !it
                        }
                    })
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("SM3", textAlign = TextAlign.Center)
                    Checkbox(checked = boxCheckList[5], onCheckedChange = {
                        if (it) {
                            checkIndexState.value = 5
                            boxCheckList[0] = !it
                            boxCheckList[1] = !it
                            boxCheckList[2] = !it
                            boxCheckList[3] = !it
                            boxCheckList[4] = !it
                            boxCheckList[5] = it
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun hashPage() {
    var data = remember { mutableStateOf("") }
    var result = remember { mutableStateOf("") }

    var checkBoxState = remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxSize().fillMaxHeight().background(Color.White)) {
        LazyColumn(modifier = Modifier.align(alignment = Alignment.Center)) {
            item {
                hashCheckBox(checkBoxState)
                rowCell("data", data)
                rowCell("result", result)
                Button(modifier = Modifier.width(330.dp).height(80.dp).padding(start = 50.dp, top = 20.dp),
                    onClick = {
                        if (data.value.isEmpty()) {
                            data.value = "data can not be empty"
                            return@Button
                        }
                    }) {
                    Text("Hash")
                }
                Button(modifier = Modifier.width(330.dp).height(80.dp).padding(start = 50.dp, top = 20.dp),
                    onClick = {
                    }) {
                    Text("Hash File")
                }
            }
        }
    }

    try {
        when (checkBoxState.value) {
            0 -> result.value = Util.hexString(HashUtil.hashUtil(HashUtil.MD5, Util.hex2byte(data.value)))
            1 -> result.value = Util.hexString(HashUtil.hashUtil(HashUtil.SHA1, Util.hex2byte(data.value)))
            2 -> result.value = Util.hexString(HashUtil.hashUtil(HashUtil.SHA256, Util.hex2byte(data.value)))
            3 -> result.value = Util.hexString(HashUtil.hashUtil(HashUtil.SHA384, Util.hex2byte(data.value)))
            4 -> result.value = Util.hexString(HashUtil.hashUtil(HashUtil.SHA512, Util.hex2byte(data.value)))
            5 -> result.value = Util.hexString(HashUtil.hashUtil(HashUtil.SM3, Util.hex2byte(data.value)))
        }
    } catch (e: Exception) {
        result.value = "hash error:" + e.toString()
    }
}

@Composable
fun tabLayout() {
    var tabState = remember { mutableStateOf(0) }
    val titles = listOf("Dukpt", "3DES加解密", "PIN Block", "SM2", "HASH")
    Column {
//        ScrollableTabRow   /*可滚动的tab，每个tab宽度 不一定均匀*/
        TabRow(/*多个Tab 宽度相同 */
            selectedTabIndex = tabState.value,
            backgroundColor = Color.LightGray
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    text = { Text(title, color = Color.Black) },
                    selected = tabState.value == index,
                    onClick = { tabState.value = index }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        itemPage(tabState)
    }
}


@Composable
fun desTabPage() {
    var key = remember { mutableStateOf("") }
    var data = remember { mutableStateOf("") }
    var result = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize().fillMaxHeight().background(Color.White)) {
        Column(modifier = Modifier.align(alignment = Alignment.Center)) {
            rowCell("KEY", key)
            rowCell("data", data)
            rowCell("result", result)
            Button(modifier = Modifier.width(330.dp).height(80.dp).padding(start = 50.dp, top = 20.dp),
                onClick = {
                    desEnc(key, data, result)
                }) {
                Text("Enc")
            }
            Button(modifier = Modifier.width(330.dp).height(80.dp).padding(start = 50.dp, top = 20.dp),
                onClick = {
                    desDec(key, data, result)
                }) {
                Text("Dec")
            }
        }

    }
}

@Composable
fun sm2VerifyPage() {
    var plain = remember { mutableStateOf("") }
    var pubKey = remember { mutableStateOf("") }
    var result = remember { mutableStateOf("") }
    var ret = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize().fillMaxHeight().background(Color.White)) {
        Column(modifier = Modifier.align(alignment = Alignment.Center)) {
            rowCell("plain  ", plain)
            rowCell("pubKey", pubKey)
            rowCell("result ", result)
//            rowCell("sm2 verify:", ret)
            Row {
                Text("SM2 Verify:")
                Text(ret.value)
            }
            Button(modifier = Modifier.width(330.dp).height(80.dp).padding(start = 50.dp, top = 20.dp),
                onClick = {
                    sm2_verify(plain, pubKey, result, ret)
                }) {
                Text("sm2Verify")
            }

        }

    }
}

@Composable
fun itemPage(index: MutableState<Int>) {
    var pot: Int = index.value
    when (pot) {
        0 -> dukptPage()
        1 -> desTabPage()
        2 -> println("PIN Block")
        3 -> sm2VerifyPage()
        4 -> hashPage()
    }
}
