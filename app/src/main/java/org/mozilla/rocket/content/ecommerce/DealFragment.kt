package org.mozilla.rocket.content.ecommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_coupon.spinner
import kotlinx.android.synthetic.main.fragment_deal.*
import org.mozilla.focus.R
import org.mozilla.rocket.adapter.AdapterDelegatesManager
import org.mozilla.rocket.adapter.DelegateAdapter
import org.mozilla.rocket.content.appComponent
import org.mozilla.rocket.content.ecommerce.adapter.Coupon
import org.mozilla.rocket.content.ecommerce.adapter.CouponAdapterDelegate
import org.mozilla.rocket.content.ecommerce.adapter.Runway
import org.mozilla.rocket.content.ecommerce.adapter.RunwayAdapterDelegate
import org.mozilla.rocket.content.getActivityViewModel
import javax.inject.Inject

class DealFragment : Fragment() {

    @Inject
    lateinit var shoppingViewModelCreator: Lazy<ShoppingViewModel>

    private lateinit var shoppingViewModel: ShoppingViewModel
    private lateinit var dealAdapter: DelegateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        shoppingViewModel = getActivityViewModel(shoppingViewModelCreator)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_deal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDeals()
        bindListData()
        bindPageState()
    }

    private fun initDeals() {
        dealAdapter = DelegateAdapter(
            AdapterDelegatesManager().apply {
                add(Runway::class, R.layout.item_runway_list, RunwayAdapterDelegate(shoppingViewModel))
                add(Coupon::class, R.layout.item_coupon, CouponAdapterDelegate(shoppingViewModel))
            }
        )
        content_deals.apply {
            adapter = dealAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun bindListData() {
        shoppingViewModel.couponItems.observe(this@DealFragment, Observer {
            dealAdapter.setData(it)
        })
    }

    private fun bindPageState() {
        shoppingViewModel.isDataLoading.observe(this@DealFragment, Observer {
            spinner.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}