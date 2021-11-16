package com.nikevg.chebez.elephant.elephants_data_mock

import com.nikevg.chebez.elephant.R
import com.nikevg.chebez.elephant.database.Elephant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

object ElephantSource {

    fun generateElephants(): Flow<List<Elephant>> {
        return flowOf(
            List(100) {
                Elephant(
                    id = Random.nextInt(0, 100),
                    nameRes = when (Random.nextInt(0, 3)) {
                        0 -> R.string.elephant_name_fred
                        1 -> R.string.elephant_name_jeannie
                        2 -> R.string.elephant_name_george
                        else -> R.string.elephant_name_percy
                    },
                    drawableRes = when (Random.nextInt(0, 3)) {
                        0 -> R.drawable.elephant_1
                        1 -> R.drawable.elephant_2
                        2 -> R.drawable.elephant_3
                        else -> R.drawable.elephant_4
                    }
                )
            }
        )
    }
}
