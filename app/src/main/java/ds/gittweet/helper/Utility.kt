package ds.gittweet.helper

import android.view.View
import android.view.View.MeasureSpec


object Utility {

    fun isViewOverlapping(firstView: View, secondView: View): Boolean {
        val firstPosition = IntArray(2)
        val secondPosition = IntArray(2)

        firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        firstView.getLocationOnScreen(firstPosition)
        secondView.getLocationOnScreen(secondPosition)

        val r = firstView.measuredWidth + firstPosition[0]
        val l = secondPosition[0]
        return r >= l && r != 0 && l != 0
    }

}