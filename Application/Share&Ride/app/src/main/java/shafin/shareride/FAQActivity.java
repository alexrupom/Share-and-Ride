package shafin.shareride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FAQActivity extends AppCompatActivity {
    TextView answer1,answer2,answer3,answer4,answer5,answer11,answer22,answer33,answer44,answer55;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        answer1=(TextView)findViewById(R.id.answer1) ;
        answer2=(TextView)findViewById(R.id.answer2);
        answer3=(TextView)findViewById(R.id.answer3);
        answer4=(TextView)findViewById(R.id.answer4);
        answer5=(TextView)findViewById(R.id.answer5);
        answer11=(TextView)findViewById(R.id.question1) ;
        answer22=(TextView)findViewById(R.id.question2);
        answer33=(TextView)findViewById(R.id.question3);
        answer44=(TextView)findViewById(R.id.question4);
        answer55=(TextView)findViewById(R.id.question5);

        answer11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answer1.setVisibility(View.VISIBLE);
                answer2.setVisibility(View.GONE);
                answer3.setVisibility(View.GONE);
                answer4.setVisibility(View.GONE);
                answer5.setVisibility(View.GONE);

            }
        });
        answer22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1.setVisibility(View.GONE);
                answer2.setVisibility(View.VISIBLE);
                answer3.setVisibility(View.GONE);
                answer4.setVisibility(View.GONE);
                answer5.setVisibility(View.GONE);

            }
        });
        answer33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1.setVisibility(View.GONE);
                answer2.setVisibility(View.GONE);
                answer3.setVisibility(View.VISIBLE);
                answer4.setVisibility(View.GONE);
                answer5.setVisibility(View.GONE);

            }
        });
        answer44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1.setVisibility(View.GONE);
                answer2.setVisibility(View.GONE);
                answer3.setVisibility(View.GONE);
                answer4.setVisibility(View.VISIBLE);
                answer5.setVisibility(View.GONE);

            }
        });
        answer55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1.setVisibility(View.GONE);
                answer2.setVisibility(View.GONE);
                answer3.setVisibility(View.GONE);
                answer4.setVisibility(View.GONE);
                answer5.setVisibility(View.VISIBLE);

            }
        });

    }
}
