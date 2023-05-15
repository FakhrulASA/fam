package com.famstudio.tiktok.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.famstudio.tiktok.databinding.ActivityLandingPageBinding
import com.famstudio.tiktok.util.BaseUrlProvider.getSignInId
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LandingPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingPageBinding
    lateinit var mAuth: FirebaseAuth
    private var googleSignInClient: GoogleSignInClient? = null
    private val RESULT_CODE_SINGIN = 999
    private val TAG = "mainTag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        startActivityForResult(singInIntent, RESULT_CODE_SINGIN)
    }

    // onActivityResult (Here we handle the result of the Activity )
    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_CODE_SINGIN) {        //just to verify the code
            //create a Task object and use GoogleSignInAccount from Intent and write a separate method to handle singIn Result.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
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
        mAuth.signInWithCredential(credential).addOnCompleteListener(this,
            OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()
                    val firebaseUser: FirebaseUser? = mAuth.getCurrentUser()
                    UpdateUI(firebaseUser)
                } else {
                    Toast.makeText(this, "Failed!", Toast.LENGTH_LONG).show()
                    UpdateUI(null)
                }
            })
    }

    //Inside UpdateUI we can get the user information and display it when required
    private fun UpdateUI(fUser: FirebaseUser?) {
        val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
        if (account != null) {
            val personName = account.displayName
            val personGivenName = account.givenName
            val personEmail = account.email
            val personId = account.id
            Toast.makeText(this, "$personName  $personEmail", Toast.LENGTH_LONG).show()
        }
    }
}