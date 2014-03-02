/**
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This custom view/widget was inspired and guided by:
 * Anis Mesmouki - Copyright 2011 
 * 
 * The code was used as part of this application, and as a result, it was extremely helpful
 * 
 */

package com.example.gamification_research;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Custom view that draws a vertical time "ruler" representing the chronological
 * progression of a single day. Usually shown along with {@link BlockView}
 * instances to give a spatial sense of time.
 */
public class Time_Ruler extends View {

	private int mHeaderWidth = 70;
	private int mHourHeight = 45;
	private int mLabelTextSize = 20;
	private int mLabelPaddingLeft = 5;
	private int mLabelColor = Color.BLACK;
	private int mDividerColor = Color.LTGRAY;
	private int mStartHour = 0;
	private int mEndHour = 24;
	private Paint mDividerPaint = new Paint();
	private Paint mLabelPaint = new Paint();

	//CONSTRUCTOR 1
	public Time_Ruler(Context context) 
	{
		this(context, null);
	}
  
	//CONSTRUCTOR 2
	public Time_Ruler(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	//CONSTROCTOR 3
	public Time_Ruler(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
  
	//DRAW 
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		final int hourHeight = mHourHeight;

		final Paint dividerPaint = mDividerPaint;
		dividerPaint.setColor(mDividerColor);
		dividerPaint.setStyle(Style.FILL);

		final Paint labelPaint = mLabelPaint;
		labelPaint.setColor(mLabelColor);
		labelPaint.setTextSize(mLabelTextSize);
		labelPaint.setTypeface(Typeface.DEFAULT_BOLD);
		labelPaint.setAntiAlias(true);

		final FontMetricsInt metrics = labelPaint.getFontMetricsInt();
		final int labelHeight = Math.abs(metrics.ascent);
		final int labelOffset = labelHeight + mLabelPaddingLeft;

		final int right = getRight();

		// Walk left side of canvas drawing time stamps
		final int hours = mEndHour - mStartHour;
		for (int i = 0; i <= hours; i++) {
			final int dividerY = hourHeight * i;
			final int nextDividerY = hourHeight * (i + 1);
			canvas.drawLine(0, dividerY, right, dividerY, dividerPaint);

			// draw text title for time stamp
			canvas.drawRect(0, dividerY, mHeaderWidth, nextDividerY, dividerPaint);

			
			// 24-hour mode when set in framework.
			final int hour = mStartHour + i;
			String label;
			if (hour == 0) {
				label = "12am";
			} else if (hour <= 11) {
				label = hour + "am";
			} else if (hour == 12) {
				label = "12pm";
			} else {
				label = (hour - 12) + "pm";
			}

			final float labelWidth = labelPaint.measureText(label);

			canvas.drawText(label, 0, label.length(), mHeaderWidth - labelWidth
					- mLabelPaddingLeft, dividerY + labelOffset, labelPaint);
		}
	}
}
