package com.famstudio.tiktok.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.famstudio.tiktok.databinding.ActivityLandingPageBinding
import com.famstudio.tiktok.util.*
import com.famstudio.tiktok.util.BaseUrlProvider.getSignInId
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LandingPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingPageBinding
    lateinit var mAuth: FirebaseAuth
    private var googleSignInClient: GoogleSignInClient? = null
    private val TAG = "mainTag"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(FirebaseAuth.getInstance().currentUser!=null){
            var intent  = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
        mAuth = FirebaseAuth.getInstance();
        binding.imageView12.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.imageButton.setOnClickListener {
            startActivity(Intent(this,PremiumPackageActivity::class.java))
        }
        val gso  = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getSignInId())
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso);

        binding.imageView11.setOnClickListener {
            signInM();
        }
    }

    //when the signIn Button is clicked then start the signIn Intent
    private fun signInM() {
        binding.progressBar.visibility = View.VISIBLE
        val singInIntent: Intent = googleSignInClient!!.getSignInIntent()
        startForResult.launch(singInIntent)
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {

        //we use try catch block because of Exception.
        try {
            binding.progressBar.visibility = View.GONE
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            Toast.makeText(this, "Signed In successfully", Toast.LENGTH_LONG).show()
            //SignIn successful now show authentication
            FirebaseGoogleAuth(account)
        } catch (e: ApiException) {
            binding.progressBar.visibility = View.GONE
            e.printStackTrace()
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
    private fun FirebaseGoogleAuth(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        //here we are checking the Authentication Credential and checking the task is successful or not and display the message
        //based on that.
        mAuth.signInWithCredential(credential).addOnCompleteListener(this
        ) { task ->
            if (task.isSuccessful) {
                val firebaseUser: FirebaseUser? = mAuth.currentUser
                UpdateUI(firebaseUser)
            } else {
                showDialogMessage(
                    this,
                    "Signing Failed!",
                    "Signing failed, please try again later."
                )
            }
        }
    }
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
    }
    private fun UpdateUI(fUser: FirebaseUser?) {
        val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
        if (account != null) {
            var sharedPreferences: SharedPreferences =
                getSharedPreferences("MySharedPref", MODE_PRIVATE)
            var myEdit = sharedPreferences.edit()
            myEdit.putString(PERSON_NAME, account.displayName)
            myEdit.putString(PERSON_GIVEN_NAME, account.givenName)
            myEdit.putString(PERSON_EMAIL, account.email)
            myEdit.putString(PERSON_ID, account.id)
            myEdit.putString(PHOTO_URL, account.photoUrl.toString())
            myEdit.apply()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}