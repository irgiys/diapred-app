package com.irgiys.diabpred1.utils

fun inputConversion(input: Array<String>) : Array<Float>{
    val inputResult: Array<Float> = Array(8) { 0f }
    val result : Array<Float> = Array(8) { 0f }

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
//    0gender 	1age 	2hypertension 	3heart_disease 	4smoking_history 	5bmi 	6HbA1c_level 	7blood_glucose_level
    result[0] = inputResult[4]
    result[1] = inputResult[0]
    result[2] = inputResult[5]
    result[3] = inputResult[6]
    result[4] = inputResult[7]
    result[5] = inputResult[1]
    result[6] = inputResult[2]
    result[7] = inputResult[3]

    return result
}