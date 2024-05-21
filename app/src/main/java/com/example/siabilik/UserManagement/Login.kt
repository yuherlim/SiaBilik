package com.example.siabilik.UserManagement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.siabilik.databinding.FragmentLoginBinding


class Login : Fragment() {

    lateinit var binding:FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater,container, false)

        //Default check tenant button at first run
        binding.tbUserType.check(binding.tenantButton.id)
        //Check on the checked button
        binding.tbUserType.addOnButtonCheckedListener { buttons, checkedId, isChecked ->
            // Perform actions based on the selected RadioButton
            when (checkedId) {
                binding.tenantButton.id -> {
                    Log.d("MyTag","Tenant button clicked")}
                binding.ownerButton.id-> {Log.d("MyTag","Owner button clicked")}
            }
        }

        return binding.root
    }



}