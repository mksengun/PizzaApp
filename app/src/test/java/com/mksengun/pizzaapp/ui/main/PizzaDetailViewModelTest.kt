package com.mksengun.pizzaapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import com.mksengun.pizzaapp.mainModule
import com.mksengun.pizzaapp.model.data.Image
import com.mksengun.pizzaapp.model.data.Pizza
import com.mksengun.pizzaapp.model.repository.PizzaRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.with
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import org.koin.standalone.get
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotBlank

class PizzaDetailViewModelTest : AutoCloseKoinTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var lifecycle: LifecycleRegistry
    private val lifecycleOwner = Mockito.mock(LifecycleOwner::class.java)
    private val pizzaRepository = mockk<PizzaRepository>()
    private val mockedPizzaPlace = mockk<MutableLiveData<Pizza>>()
    private lateinit var viewModel: PizzaDetailViewModel

    @Before
    fun before() {
        StandAloneContext.startKoin(listOf(mainModule, module {
            single(override = true) { pizzaRepository }
        })) with (mockk())

        viewModel = get(parameters = { parametersOf("1") })

        viewModel.pizzaPlace = mockedPizzaPlace
    }

    @Test
    fun `when pizza place is set openingHours are NOT blank`() {

        with(mockedPizzaPlace) {
            every { value } returns Pizza(
                "1",
                "name",
                "phone",
                "website",
                "address",
                "",
                listOf("Monday 01:00", "Tuesday 02:00"),
                0.0,
                0.0,
                listOf(Image("1", "url", "caption", "expiration"))
                , listOf("friend 1", "friend 2")
            )

        }

        expectThat(viewModel.getOpeningHours()).isNotBlank()
    }

    @Test
    fun `when pizza place is set openingHours string is separated by new line`() {

        with(mockedPizzaPlace) {
            every { value } returns Pizza(
                "1",
                "name",
                "phone",
                "website",
                "address",
                "",
                listOf("Monday 01:00", "Tuesday 02:00"),
                0.0,
                0.0,
                listOf(Image("1", "url", "caption", "expiration"))
                , listOf("friend 1", "friend 2")
            )

        }

        expectThat(viewModel.getOpeningHours()).isEqualTo("Monday 01:00\nTuesday 02:00\n")
    }


}