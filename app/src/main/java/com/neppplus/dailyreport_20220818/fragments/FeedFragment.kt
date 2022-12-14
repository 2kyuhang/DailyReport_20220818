package com.neppplus.dailyreport_20220818.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.dailyreport_20220818.R
import com.neppplus.dailyreport_20220818.adapters.FeedRecyclerViewAdapter
import com.neppplus.dailyreport_20220818.databinding.FragmentFeedBinding
import com.neppplus.dailyreport_20220818.datas.BasicResponse
import com.neppplus.dailyreport_20220818.datas.FeedData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedFragment: BaseFragment() {

    lateinit var binding : FragmentFeedBinding
    lateinit var mFeedRecyclerAdapter : FeedRecyclerViewAdapter
    val mFeedList = ArrayList<FeedData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.fab.setOnClickListener{

        }
    }

    override fun setValues() {
        mFeedRecyclerAdapter = FeedRecyclerViewAdapter(mContext, mFeedList)
        binding.feedRecyclerView.adapter = mFeedRecyclerAdapter
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(mContext)
        binding.feedRecyclerView.addOnScrollListener(scrollListener)

        getFeedDataFromServer(0)
    }

    fun getFeedDataFromServer(pageNum: Int) {
        apiList.getRequestFeed(pageNum).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mFeedList.clear()
                    mFeedList.addAll(br.data.feeds)
                    mFeedRecyclerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    val scrollListener = object : RecyclerView.OnScrollListener(){
//        ?????? ????????????????????? ???????????? ?????? ????????? ?????????? ??????
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (recyclerView.canScrollVertically(1) &&
                newState == RecyclerView.SCROLL_STATE_SETTLING
            ) {
//                ?????????????????? ?????????
//                ??? ?????????(pageNum = 0) ????????? ????????????
                Log.d("?????? ??????", "?????????")
            }
            else if (recyclerView.canScrollVertically(-1) &&
                newState == RecyclerView.SCROLL_STATE_SETTLING) {
//                ?????????????????? ?????????
//                ?????????????????? ?????? ????????????(page + 1)
                Log.d("?????? ??????", "?????????")
            }
            else {
//                ??????????????? ??? ?????? ???
            }
        }
    }
}