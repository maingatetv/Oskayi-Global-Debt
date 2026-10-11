package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.OrcaatrateRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Orcaatrate", appName)
  }

  @Test
  fun `verify financial calculations and circle killer detection`() {
    val repository = OrcaatrateRepository()
    val companies = repository.companies.value
    assertTrue("Verified companies list should not be empty", companies.isNotEmpty())

    val nvda = companies.first { it.ticker == "NVDA" }
    assertEquals("NVIDIA Corporation", nvda.name)
    assertTrue("NVDA Net Cap should be positive", nvda.netCapitalization > 5_000_000_000_000.0)

    val circles = repository.detectCircles()
    assertTrue("Should detect circular debt path", circles.isNotEmpty())
    val circle = circles.first()
    assertEquals(0.0, circle.cashMovedUsd, 0.001) // Exactly $0 moved per London Clearing House principle
  }

  @Test
  fun `verify tokenized debt on Base L2 backed 1-for-1 by assignment doc`() {
    val repository = OrcaatrateRepository()
    val tokens = repository.tokenizedDebts.value
    assertTrue("Should have tokenized debt tranches", tokens.isNotEmpty())

    val mtnToken = tokens.first { it.ticker == "MTNGH" }
    assertEquals(8453L, mtnToken.chainId)
    assertEquals("0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913", mtnToken.usdcContract)
    assertTrue("Should have SHA256 backing hash", mtnToken.backingDocHash.startsWith("SHA256:"))

    val initialAvailable = mtnToken.availableTokens
    val purchaseResult = repository.purchaseTokens(mtnToken.refCode, 10L, "0x71C...49A")
    assertTrue("Token purchase should succeed", purchaseResult.isSuccess)
    val txHash = purchaseResult.getOrNull()
    assertNotNull(txHash)
    assertTrue(txHash!!.startsWith("0x"))

    val updatedToken = repository.tokenizedDebts.value.first { it.ticker == "MTNGH" }
    assertEquals(initialAvailable - 10L, updatedToken.availableTokens)
  }
}
