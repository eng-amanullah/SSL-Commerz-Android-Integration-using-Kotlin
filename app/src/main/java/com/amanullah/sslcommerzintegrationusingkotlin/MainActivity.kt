package com.amanullah.sslcommerzintegrationusingkotlin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.amanullah.sslcommerzintegrationusingkotlin.databinding.ActivityMainBinding
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener

class MainActivity : AppCompatActivity(), SSLCTransactionResponseListener {

    private lateinit var binding: ActivityMainBinding

    private val storeId: String = "dhopa6198884b4d249"
    private val storePassword: String = "dhopa6198884b4d249@ssl"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.payNowButton.setOnClickListener {

            val amount = binding.amountTextField.editText?.text.toString()

            if (amount.isEmpty())
            {
                Toast.makeText(applicationContext, "Please Enter the Amount first", Toast.LENGTH_SHORT).show()
            }else {
                var sslCommerzInitialization = SSLCommerzInitialization(
                    storeId,
                    storePassword,
                    amount.toDouble(),
                    SSLCCurrencyType.BDT,
                    "123456789098765",
                    "yourProductType",
                    SSLCSdkType.TESTBOX
                )

                var sslCCustomerInfoInitializer: SSLCCustomerInfoInitializer = SSLCCustomerInfoInitializer(
                    "CustomerName",
                    "customerEmail",
                    "customerAddress",
                    "",
                    "1341",
                    "Bangladesh",
                    "1234567890",
                )

                var sslCProductInitializer: SSLCProductInitializer = SSLCProductInitializer(
                    "productName",
                    "productCategory",
                    SSLCProductInitializer.ProductProfile.TravelVertical(
                        "productProfile",
                        "hoursTillDeparture",
                        "flightType",
                        "pnr",
                        "journeyFromTo"
                    )
                )

//        val shipmentInfoInitializer = SSLCShipmentInfoInitializer(
//            "Courier",
//            2, SSLCShipmentInfoInitializer.ShipmentDetails(
//                "AA", "Address 1",
//                "Dhaka", "1000", "BD"
//            )
//        )

                IntegrateSSLCommerz
                    .getInstance(this)
                    .addSSLCommerzInitialization(sslCommerzInitialization)
                    .addCustomerInfoInitializer(sslCCustomerInfoInitializer)
                    .addProductInitializer(sslCProductInitializer)
                    .buildApiCall(this)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun transactionSuccess(s: SSLCTransactionInfoModel?) {
        //binding.textView.text = "Message1 : ${s?.apiConnect + s?.status.toString()}"
        //binding.textView.text = "Success"
        startActivity(Intent(applicationContext, HomeActivity::class.java))
    }

    @SuppressLint("SetTextI18n")
    override fun transactionFail(s: String?) {
        //binding.textView.text = "Message2 : $s"
        binding.textView.text = "Transaction Failed"
    }
    @SuppressLint("SetTextI18n")
    override fun merchantValidationError(s: String?) {
        //binding.textView.text = "Message3 : $s"
        binding.textView.text = "Merchant Error"
    }
}