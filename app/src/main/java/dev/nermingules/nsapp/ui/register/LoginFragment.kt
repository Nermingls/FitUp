package dev.nermingules.nsapp.ui.register


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dev.nermingules.nsapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        currentUser?.let {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomePageFragment())
        }
        initViews()
    }
    private fun initViews() {
        setOnClickListener()
    }
    private fun setOnClickListener() {
        binding.apply {
            singupButton.setOnClickListener {
                val email = email.text.toString().trim()
                val password = password.text.toString().trim()
                Singup(email, password)
            }
            loginButton.setOnClickListener {
                val email = email.text.toString().trim()
                val password = password.text.toString().trim()
                Login(email, password)
            }
        }
    }


    private fun Singup(Email:String?,Password:String?){
        if( Email.equals("") || Password.equals("") ){
            Toast.makeText(
                requireContext(),
                "Please fill in all fields",
                Toast.LENGTH_LONG).show()
        }else{
            auth.createUserWithEmailAndPassword(Email!!, Password!!)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Succed Register ", Toast.LENGTH_LONG).show()
                  //  findNavController().navigate(LoginFragmentDirections.loginToHome())
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }
    }
    private fun Login(Email:String?,Password:String?){
        if( Email.equals("") || Password.equals("") ){
            Toast.makeText(
                requireContext(),
                "Enter email and password! ",
                Toast.LENGTH_LONG).show()
        }else{
            auth.signInWithEmailAndPassword(Email!!, Password!!)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Succed Login ", Toast.LENGTH_LONG).show()
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomePageFragment())
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }
    }

}