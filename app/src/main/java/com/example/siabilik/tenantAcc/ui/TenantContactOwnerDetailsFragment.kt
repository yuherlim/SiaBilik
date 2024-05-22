package com.example.siabilik.tenantAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.demo.data.AuthVM
import com.example.siabilik.UserManagement.SimpleEmail
import com.example.siabilik.data.Listing
import com.example.siabilik.data.Owner
import com.example.siabilik.data.Request
import com.example.siabilik.data.Tenant
import com.example.siabilik.databinding.FragmentTenantContactOwnerDetailsBinding
import com.example.siabilik.errorDialog
import com.example.siabilik.hideKeyboard
import com.example.siabilik.snackbar
import com.example.siabilik.tenantAcc.data.ListingViewModel
import com.example.siabilik.tenantAcc.data.RequestViewModel


class TenantContactOwnerDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTenantContactOwnerDetailsBinding
    private val nav by lazy { findNavController() }
    private val tenantId by lazy { arguments?.getString("tenantId") ?: "" }
    private val listingId by lazy { arguments?.getString("listingId") ?: "" }


    private val listingVM: ListingViewModel by activityViewModels()
    private val authVM: AuthVM by activityViewModels()
    private val requestVM: RequestViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTenantContactOwnerDetailsBinding.inflate(inflater, container, false)


        binding.btnContactOwnerCancelSendEmail.setOnClickListener {
            binding.edtContactOwnerTitle.text?.clear()
            binding.edtContactOwnerTitle.text?.clear()
            nav.navigateUp()
        }

        val listing = listingVM.get(listingId)
        if (listing == null) {
            nav.navigateUp()
            return null
        }

        val tenant = authVM.getTenantById(tenantId)
        if (tenant == null) {
            nav.navigateUp()
            return null
        }

        val owner = authVM.getOwnerById(listing.ownerID)
        if (owner == null) {
            nav.navigateUp()
            return null
        }

        var request = Request()

        //get current tenant email.
        binding.btnContactOwnerSendEmail.setOnClickListener {

            var requestId : Int = 0

            // Get the latest value
            val latestRequest = requestVM.getLatestRequest()
            if (latestRequest != null) {
                requestId = latestRequest.id.removePrefix("Request").toIntOrNull()!!
                requestId = requestId.plus(1)
                request.id = "Request${requestId.toString().padStart(6, '0')}"
            }

            request.listingId = listingId
            request.tenantId = tenant.id
            request.title = binding.edtContactOwnerTitle.text.toString()
            request.message = binding.edtContactOwnerMessage.text.toString()

            send(listing, tenant, owner, request)
        }

        return binding.root
    }

    private fun send(listing: Listing, tenant: Tenant, owner: Owner, request: Request) {
        val e = requestVM.validate(request)
        if (e != "") {
            errorDialog(e)
            return
        }

        // NOTE: Extension function --> Fragment.hideKeyboard()
        hideKeyboard()


        val number = "%04d".format((0..9999).random())

        val subject = "New contact request for listing: ${listing.title}";
        val content = """
            <p>There is a new contact request for your listing!</p>
            <br>
            <p><b>Name of the interested listing:</b> ${listing.title}</p>
            <br>
            <p><b>Title: </b>${binding.edtContactOwnerTitle.text.toString()}</p>
            <p><b>Message: </b></p>
            <p>${binding.edtContactOwnerMessage.text.toString()}</p>
            <br>
            <br>
            <p><b>Tenant email: </b>${tenant.email}</p>
            <p>Please be kind and at least drop a reply back to our respected users through the supplied email!</p>
            <p>Thank you!</p>
        """.trimIndent();
        // Add tenant email inside email.

        // TODO(1): Send email
        SimpleEmail()
            .to(owner.email)
            .subject(subject)
            .content(content)
            .isHtml()
            .send {
                snackbar("Email sent.")
                requestVM.set(request)
                nav.navigateUp()
            }

        snackbar("Sending email...")
    }



}