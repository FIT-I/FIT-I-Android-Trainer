package com.example.fit_i_trainer.ui.main.matching

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.databinding.FragmentMatchingBinding

class MatchingFragment : Fragment() {
    private lateinit var binding: FragmentMatchingBinding
    private val dataList = ArrayList<MatchingData>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchingBinding.inflate(inflater,container,false)
        val view = inflater.inflate(R.layout.fragment_mypage_matchingpage,container,false)


        //임의로 데이터 넣어보기, 나중에 사진 글라이드 기능 추가
        dataList.apply {
            add(MatchingData("김동현", "트레이너님이 와주세요", "어제"))
            add(MatchingData("김준기", "제가 직접 갈게요",  "월요일"))
            add(MatchingData("홍준혁", "트레이너님이 와주세요", "2023.1.4"))
        }
        val matchingAdapter = MatchingAdapter(dataList)
        binding.rcmatching.adapter = matchingAdapter
        var linearLayoutManager = LinearLayoutManager(context)
        binding.rcmatching.layoutManager = linearLayoutManager




        matchingAdapter.setItemClickListener(object : MatchingAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val matchingIngFragment = MatchingIngFragment()
                val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.fl_container,matchingIngFragment)
                transaction.commit()
                dataList.removeAt(position)
                matchingAdapter.notifyItemRemoved(position)
                matchingAdapter.notifyItemRangeChanged(position,dataList.size)
                Log.d("Tage","delete")

            }
        })




        return binding.root

    }

}