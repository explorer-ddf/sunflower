/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.google.samples.apps.sunflower.data.AppDatabase
import com.google.samples.apps.sunflower.data.PlantDao
import com.google.samples.apps.sunflower.utilities.getValue
import com.google.samples.apps.sunflower.utilities.testPlant
import com.google.samples.apps.sunflower.utilities.testPlants
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlantDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var plantDao: PlantDao

    @Before fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        plantDao = database.plantDao()

        plantDao.insertAll(testPlants)
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetPlants() {
        assertThat(getValue(plantDao.getPlants()).size, equalTo(3))
    }

    @Test fun testGetPlantsWithGrowZoneNumber() {
        assertThat(getValue(plantDao.getPlantsWithGrowZoneNumber(1)).size, equalTo(2))
        assertThat(getValue(plantDao.getPlantsWithGrowZoneNumber(2)).size, equalTo(1))
        assertThat(getValue(plantDao.getPlantsWithGrowZoneNumber(3)).size, equalTo(0))
    }

    @Test fun testGetPlant() {
        assertThat(getValue(plantDao.getPlant(testPlant.plantId)), equalTo(testPlant))
    }
}