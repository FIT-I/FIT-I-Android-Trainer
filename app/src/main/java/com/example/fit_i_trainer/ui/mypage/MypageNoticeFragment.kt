package com.example.fit_i_trainer.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.databinding.FragmentMypageNoticeBinding


class MypageNoticeFragment : Fragment() {
    private lateinit var binding: FragmentMypageNoticeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mypage_notice,container,false)
        val ibpre = view.findViewById<View>(R.id.ib_pre) as ImageButton
        val btnper = view.findViewById<View>(R.id.btn_per)
        val btnok = view.findViewById<View>(R.id.btn_okok)
        ibpre.setOnClickListener{
            val mypageFragment = MypageFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()

            //이전 화면으로 이동
            transaction.replace(R.id.fl_container,mypageFragment)
            transaction.commit()

        }
        btnper.setOnClickListener{
            val mypageNoticeIngeFragment = MypageNoticeIngFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container,mypageNoticeIngeFragment)
            transaction.commit()
        }
        return view
    }

}