package com.streakify.android

import com.streakify.android.utils.Util
import org.junit.Assert
import org.junit.Test

class UtilTest {

    @Test
    fun test_phoneNumberToAlphabetsForAvatar(){
        val input1 = "0123456789"
        val output1 = Util.phoneNumberToAlphabetsForAvatar(input1)
        Assert.assertEquals("abcdefghij",output1)

        val input2 = ""
        val output2 = Util.phoneNumberToAlphabetsForAvatar(input2)
        Assert.assertEquals("",output2)

        val input3 = null
        val output3 = Util.phoneNumberToAlphabetsForAvatar(input3)
        Assert.assertEquals("",output3)
    }
}