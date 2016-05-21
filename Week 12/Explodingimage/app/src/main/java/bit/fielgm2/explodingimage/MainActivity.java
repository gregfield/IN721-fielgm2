package bit.fielgm2.explodingimage;

import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.ExplodeAnimation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.explodeBtn);
        button.setOnClickListener(new OnClick());
    }

    public class OnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            ImageView image = (ImageView) findViewById(R.id.imageView);
            ExplodeAnimation exploder = new ExplodeAnimation(image);
            exploder.setExplodeMatrix(ExplodeAnimation.MATRIX_2X2);
            exploder.setInterpolator(new DecelerateInterpolator());
            exploder.setDuration(2000);
            exploder.setListener(new animationListner());
            exploder.animate();
        }
    }

    public class animationListner implements AnimationListener
    {
        @Override
        public void onAnimationEnd(Animation animation) {

        }
    }
}
