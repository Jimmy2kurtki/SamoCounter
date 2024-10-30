package com.lessons.samocounter.money

class MoneyCount {
    fun moneyCount(c:Int,pochasovka: Boolean): Int{
        var count = c
        if(pochasovka) {
            if(count < 23) {
                count = 23
            }
        }
        var intMoney: Int = 0
        when {
            count <= 23 -> intMoney = count * 146
            count <= 30 -> intMoney = (count - 23) * 190 + 23.times(146)
            count <= 33 -> intMoney = 7.times(190) + 23.times(146) + (count - 23 - 7).times(210)
            else -> intMoney = 3.times(210) + 7.times(190) + 23.times(146) + (count - 23 - 7 - 3).times(230)
        }
        return intMoney
    }

    fun simCount(count:Int): Int{
        var intMoney: Int = 0
        when{
            count <= 22 * 146 -> intMoney = count / 146
            count <= (30 - 23) * 190 + 23.times(146) -> intMoney = (count - 23 * 146) / 190 + 23
            count <= 7.times(190) + 23.times(146) + (30 - 23 - 7).times(210) -> intMoney =  ((count - 23 * 146) - (7 * 190)) / 210 + 23 + 7
            else -> intMoney = ((count - 23 * 146) - (7 * 190) - (3 * 210)) / 230 + 23 + 7 + 3
        }
        return intMoney
    }
}