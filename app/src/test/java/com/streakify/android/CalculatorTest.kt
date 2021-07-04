package com.streakify.android

import com.streakify.android.application.AppConstants
import com.streakify.android.utils.Calculator.Companion.getStreakData
import org.junit.Assert
import org.junit.Test

class CalculatorTest {
    @Test
    fun streakData_isCorrect() {
        //Today - Punched In And out for both type streaks
        val data1 = getStreakData("2021-07-04T10:39:55Z", AppConstants.STREAK_TYPE_DEFINITE, 2, true)
        val data2 = getStreakData("2021-07-04T10:39:55Z", AppConstants.STREAK_TYPE_DEFINITE, 2, false)
        val data3 = getStreakData("2021-07-04T10:39:55Z", AppConstants.STREAK_TYPE_INDEFINITE, 0, true)
        val data4 = getStreakData("2021-07-04T10:39:55Z", AppConstants.STREAK_TYPE_INDEFINITE, 0, false)
        Assert.assertEquals(1, data1.daysFinished)
        Assert.assertEquals(50F, data1.percentage)

        Assert.assertEquals(0, data2.daysFinished)
        Assert.assertEquals(0F, data2.percentage)

        Assert.assertEquals(1, data3.daysFinished)
        Assert.assertEquals(0, data4.daysFinished)


        //Tomorrow - Punched In And out for both type streaks
        val data5 = getStreakData("2021-07-03T10:39:55Z", AppConstants.STREAK_TYPE_DEFINITE, 2, true)
        val data6 = getStreakData("2021-07-03T10:39:55Z", AppConstants.STREAK_TYPE_DEFINITE, 2, false)
        val data7 = getStreakData("2021-07-03T10:39:55Z", AppConstants.STREAK_TYPE_INDEFINITE, 0, true)
        val data8 = getStreakData("2021-07-03T10:39:55Z", AppConstants.STREAK_TYPE_INDEFINITE, 0, false)
        Assert.assertEquals(2, data5.daysFinished)
        Assert.assertEquals(100F, data5.percentage)

        Assert.assertEquals(1, data6.daysFinished)
        Assert.assertEquals(50F, data6.percentage)

        Assert.assertEquals(2, data7.daysFinished)
        Assert.assertEquals(1, data8.daysFinished)

    }
}