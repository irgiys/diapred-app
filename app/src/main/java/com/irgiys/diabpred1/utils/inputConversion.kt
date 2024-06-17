package com.irgiys.diabpred1.utils

fun inputConversion(input: Array<String>) : Array<Float>{
    val inputResult: Array<Float> = Array(8) { 0f }
    for (i in input.indices) {
        if (i in 0..3) {
            inputResult[i] = input[i].toFloat()
        }
        if(i == 4){
            when(input[i]){
                "Perempuan" -> inputResult[i] = 0f
                "Laki-laki" -> inputResult[i] = 1f
            }
        }
        if (i == 5 || i == 6) {
            when (input[i]) {
                "Tidak" -> inputResult[i] = 0f
                "Ya" -> inputResult[i] = 1f
            }
        }
        if(i == 7){
            when(input[i]){
                "Mantan Perokok" -> inputResult[i] = 0f
                "Bukan Perokok" -> inputResult[i] = 1f
                "Perokok aktif" -> inputResult[i] = 2f
            }
        }
    }
    return inputResult
}