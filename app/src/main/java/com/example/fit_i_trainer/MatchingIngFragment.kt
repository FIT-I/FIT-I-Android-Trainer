package com.example.fit_i_trainer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentTransaction


class MatchingIngFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_matching_ing,container,false)
        val btnaccept = view.findViewById<View>(R.id.btn_matching_accept)
        val btndecline = view.findViewById<View>(R.id.btn_matching_decline)

        btnaccept.setOnClickListener{
            val matchingFragment = MatchingFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container,matchingFragment)
            transaction.commit()
        }
        btndecline.setOnClickListener{
            val matchingFragment = MatchingFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container,matchingFragment)
            transaction.commit()
        }

        return view

    }


}