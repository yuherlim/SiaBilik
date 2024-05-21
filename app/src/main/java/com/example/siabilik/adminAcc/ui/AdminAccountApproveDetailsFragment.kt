package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.adminAcc.data.AccountApproveViewModel
import com.example.siabilik.adminAcc.data.ListingApproveViewModel
import com.example.siabilik.databinding.FragmentAdminAccountApproveDetailsBinding
import com.example.siabilik.setImageBlob

class AdminAccountApproveDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAdminAccountApproveDetailsBinding
    private val nav by lazy { findNavController() }
    private val accountID by lazy { arguments?.getString("accountID") ?: "" }

    private val accountVM : AccountApproveViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminAccountApproveDetailsBinding.inflate(inflater, container, false)

        val account = accountVM.get(accountID)
        if(account == null){
            nav.navigateUp()
            return null
        }

        binding.accountProfileName.text = account.userName
        binding.accountProfileEmail.text = account.email
        binding.accountProfileImg.setImageBlob(account.profilePic)
        //binding.imgFront.setImageBlob(account.)
        binding.imgSelfie.setImageBlob(account.selfiePhoto)

        binding.approveButton.setOnClickListener {
            account.verificationStatus = "Approved"
            accountVM.set(account)
            nav.navigateUp()
        }

        binding.rejectButton.setOnClickListener {
            account.verificationStatus = "Rejected"
            accountVM.set(account)
            nav.navigateUp()
            //maybe can prompt a dialog to input reason and send email
        }

        return binding.root


    }
}