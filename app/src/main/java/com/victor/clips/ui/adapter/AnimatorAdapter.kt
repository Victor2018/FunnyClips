package com.victor.clips.ui.adapter

import android.animation.Animator
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.victor.clips.util.ViewAnimator
import android.os.Bundle
import android.os.Parcelable
import android.animation.ObjectAnimator
import android.view.View
import com.victor.clips.util.AnimUtil


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AnimatorAdapter.java
 * Author: Victor
 * Date: 2019/11/11 16:09
 * Description:
 * -----------------------------------------------------------------
 */
abstract class AnimatorAdapter<T : RecyclerView.ViewHolder>
(var mAdapter: RecyclerView.Adapter<T>, var mRecyclerView: RecyclerView): RecyclerView.Adapter<T>() {

//    private var mAdapter: RecyclerView.Adapter<T>? = null

    /**
     * Saved instance state key for the ViewAnimator
     */
    private val SAVEDINSTANCESTATE_VIEWANIMATOR = "savedinstancestate_viewanimator"

    /**
     * The ViewAnimator responsible for animating the Views.
     */
    private var mViewAnimator: ViewAnimator? = null

//    protected var mRecyclerView: RecyclerView? = null

    init {
        mViewAnimator = ViewAnimator(mRecyclerView)
    }

    //-----------------------------------------------------------------------------
    // Animators methods
    //-----------------------------------------------------------------------------

    /**
     * Returns the Animators to apply to the views.
     *
     * @param view The view that will be animated, as retrieved by onBindViewHolder().
     */
    abstract fun getAnimators(view: View): Array<Animator>

    /**
     * Alpha property
     */
    private val ALPHA = "alpha"

    /**
     * Animates given View
     *
     * @param position the position of the item the View represents.
     * @param view     the View that should be animated.
     */
    private fun animateView(view: View, position: Int) {
        assert(mViewAnimator != null)
        assert(mRecyclerView != null)

        val animators = getAnimators(view)
        val alphaAnimator = ObjectAnimator.ofFloat(view, ALPHA, 0f, 1f)
        val concatAnimators = AnimUtil.concatAnimators(animators, alphaAnimator)
        mViewAnimator?.animateViewIfNecessary(position, view, concatAnimators)
    }

    fun getViewAnimator(): ViewAnimator {
        return mViewAnimator!!
    }

    //-----------------------------------------------------------------------------
    // SaveInstanceState
    //-----------------------------------------------------------------------------

    /**
     * Returns a Parcelable object containing the AnimationAdapter's current dynamic state.
     */
    fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()

        if (mViewAnimator != null) {
            bundle.putParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR, mViewAnimator?.onSaveInstanceState())
        }

        return bundle
    }

    /**
     * Restores this AnimationAdapter's state.
     *
     * @param parcelable the Parcelable object previously returned by [.onSaveInstanceState].
     */
    fun onRestoreInstanceState(parcelable: Parcelable) {
        if (parcelable is Bundle) {
            if (mViewAnimator != null) {
                mViewAnimator?.onRestoreInstanceState(parcelable.getParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR))
            }
        }
    }

    //-----------------------------------------------------------------------------
    // RecyclerView methods
    //-----------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return mAdapter?.onCreateViewHolder(parent, viewType)!!
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        mAdapter?.onBindViewHolder(holder, position)
        mViewAnimator?.cancelExistingAnimation(holder.itemView)
        animateView(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return mAdapter?.getItemCount()!!
    }

    override fun getItemViewType(position: Int): Int {
        return mAdapter?.getItemViewType(position)!!
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
        mAdapter?.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        mAdapter?.unregisterAdapterDataObserver(observer)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        mAdapter?.setHasStableIds(hasStableIds)
    }

    override fun getItemId(position: Int): Long {
        return mAdapter?.getItemId(position)!!
    }

    override fun onViewRecycled(holder: T) {
        mAdapter?.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: T): Boolean {
        return mAdapter?.onFailedToRecycleView(holder)!!
    }

    override fun onViewAttachedToWindow(holder: T) {
        mAdapter?.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: T) {
        mAdapter?.onViewDetachedFromWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mAdapter?.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        mAdapter?.onDetachedFromRecyclerView(recyclerView)
    }

}