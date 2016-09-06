package stan.bulls.cows.ui.views.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircleProgressView
        extends View
{
    private Paint paint;
    private Paint paintCircle;
    private RectF bounds;
    private float startAngle;
    private float maxProgress;
    private float currentProgress;
    private int size;
    private int color;
    private int thickness;

    public CircleProgressView(Context context)
    {
        super(context);
        init();
    }

    public CircleProgressView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        bounds = new RectF();
        startAngle = -90;
        maxProgress = 100;
        size = 0;
        thickness = 5;
        updatePaint();
        updateBounds();
    }

    private void updatePaint()
    {
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thickness);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paintCircle.setColor(color);
        paintCircle.setStyle(Paint.Style.FILL);
    }

    private void updateBounds()
    {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        bounds.set(paddingLeft + thickness, paddingTop + thickness, size - paddingLeft - thickness, size - paddingTop - thickness);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        float radius = size/2;
        float sweepAngle = currentProgress / maxProgress * 360;
        canvas.drawArc(bounds, startAngle, sweepAngle, false, paint);
        canvas.drawCircle(radius, thickness, thickness/2, paintCircle);
        double x1 = Math.cos(Math.toRadians(sweepAngle + startAngle)) * (radius-thickness) + radius;
        double y1 = Math.sin(Math.toRadians(sweepAngle + startAngle)) * (radius-thickness) + radius;
        canvas.drawCircle((float) x1, (float) y1, thickness/2, paintCircle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int xPad = getPaddingLeft() + getPaddingRight();
        int yPad = getPaddingTop() + getPaddingBottom();
        int width = getMeasuredWidth() - xPad;
        int height = getMeasuredHeight() - yPad;
        size = (width < height) ? width : height;
        setMeasuredDimension(size + xPad, size + yPad);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        size = (w < h) ? w : h;
        updateBounds();
    }

    public void setMaxProgress(float m)
    {
        maxProgress = m;
    }

    public void setCurrentProgress(float p)
    {
        if(p < 0 || p > maxProgress)
        {
            return;
        }
        currentProgress = p;
        invalidate();
    }

    public float getCurrentProgress()
    {
        return currentProgress;
    }

    public void setColor(int c)
    {
        color = c;
        updatePaint();
    }

    public void setThickness(int t)
    {
        thickness = t;
        updatePaint();
        updateBounds();
    }
}