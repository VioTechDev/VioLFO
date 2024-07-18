package com.vio

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.vio.adapter.LFOAdapter
import com.vio.languageopen.R
import com.vio.languageopen.databinding.FragmentLfoBinding
import com.vio.model.Language
import com.vio.utils.LfoConstants


abstract class BaseLFOFragment : Fragment() {

    protected lateinit var binding: FragmentLfoBinding
    private lateinit var myContext: Context
    protected lateinit var myActivity: Activity
    protected val lfoAdapter by lazy {
        LFOAdapter(
            myContext,
            VioLFO.lfoConfig.itemLimit,
            VioLFO.listLanguageValid
        )
    }

    private fun init() {
        binding = FragmentLfoBinding.inflate(layoutInflater)
    }


    protected abstract fun initView()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
        myActivity = myContext as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init()
        handleView()
        initView()
        handleBack()
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseAnalytics.getInstance(myActivity).logEvent(this::class.simpleName.toString(), Bundle())
    }

    private fun handleView() {
        binding.lSwitchAccess.setOnClickListener {
            navigateToAccess()
        }
        setupToolBar()
        setupShimmerAd()
        binding.cltRequestRepeat.isVisible = VioLFO.lfoConfig.showLFO3
        updateBackgroundColor()
    }

    private fun updateBackgroundColor() {
        VioLFO.lfoConfig.backgroundColorLfo?.let {
            binding.ctlParent.setBackgroundColor(
                ContextCompat.getColor(
                    myActivity,
                    it
                )
            )
        }
    }

    private fun setupToolBar() {
        val toolBarView = VioLFO.lfoConfig.layoutToolbar.let { layout ->
            LayoutInflater.from(myContext)
                .inflate(layout, binding.flToolbar, false)
        } ?: run {
            LayoutInflater.from(myContext)
                .inflate(R.layout.layout_toolbar_lfo, binding.flToolbar, false)
        }
        val toolbarParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        toolBarView.layoutParams = toolbarParams
        binding.flToolbar.addView(toolBarView)
        toolBarView.findViewById<View>(R.id.imgChooseLanguage).setOnClickListener {
            lfoAdapter.getLanguageSelected()?.let { it1 -> chooseLanguage(it1) }
        }
        toolBarView.findViewById<View>(R.id.imgBack).setOnClickListener {
            myActivity.finish()
        }
        toolBarView.findViewById<View>(R.id.imgChooseLanguage).isEnabled =
            lfoAdapter.getLanguageSelected() != null
    }

    private fun setupShimmerAd() {
        val lfoConfig = VioLFO.lfoConfig

        val shimmerView =
            if (lfoConfig.isShowMeta && lfoConfig.isShowMetaAllPlatform
            ) {
                lfoConfig.shimmerNativeAdMeta.let { layout ->
                    LayoutInflater.from(myContext)
                        .inflate(layout, binding.flShimmerNative, false)
                } ?: run {
                    LayoutInflater.from(myContext)
                        .inflate(R.layout.native_language_meta, binding.flShimmerNative, false)
                }
            } else
                lfoConfig.shimmerNativeAd.let { layout ->
                    LayoutInflater.from(myContext)
                        .inflate(layout, binding.flShimmerNative, false)
                } ?: run {
                    LayoutInflater.from(myContext)
                        .inflate(R.layout.native_language_big, binding.flShimmerNative, false)
                }

        val toolbarParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        shimmerView.layoutParams = toolbarParams
        binding.flShimmerNative.addView(shimmerView)
    }

    protected fun navigateToSelect(languagePosition: Int, scrollY: Int) {
        val bundle = Bundle()
        bundle.putBoolean(LfoConstants.KEY_OPEN_FROM_MAIN, true)
        bundle.putInt(LfoConstants.KEY_SELECT_POSITION, languagePosition)
        bundle.putInt(LfoConstants.KEY_SCROLL_Y, scrollY)
        findNavController().navigate(R.id.action_LFOFragment_to_LFOSelectFragment, bundle)
    }

    private fun navigateToAccess() {
        val bundle = Bundle()
        bundle.putBoolean(LfoConstants.KEY_OPEN_FROM_MAIN, true)
        bundle.putInt(LfoConstants.KEY_SELECT_POSITION, 1)
        findNavController().navigate(R.id.action_LFOFragment_to_LFOAccessFragment, bundle)
    }

    private fun chooseLanguage(language: Language) {
        VioLFO.invokeListenerAdCallback().onChangeLanguage(language)
        myActivity.finish()
    }

    private fun handleBack() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    VioLFO.invokeListenerAdCallback().onBackPressLanguage()
                    myActivity.finish()
                }
            })
    }
}
