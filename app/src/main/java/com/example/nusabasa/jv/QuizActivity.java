package com.example.nusabasa.jv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nusabasa.R;
import com.example.nusabasa.ResultActivity;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private int currentPosition = 1;
    private int selectedOption = 0;
    private int correctAnswers = 0;
    private ArrayList<Question> questionList = null;
    private TextView tvQuestion;
    private TextView tvOptionOne;
    private TextView tvOptionTwo;
    private TextView tvOptionThree;
    private TextView tvProgress;
    private Button btnSubmit;
    private ProgressBar progressBar;
    public static final String EXTRA_QUESTION = "extra_question";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionList = getIntent().getParcelableArrayListExtra(EXTRA_QUESTION);
        tvOptionOne = findViewById(R.id.tv_option_one);
        tvOptionTwo = findViewById(R.id.tv_option_two);
        tvOptionThree = findViewById(R.id.tv_option_three);
        btnSubmit = findViewById(R.id.btn_submit);
        tvProgress = findViewById(R.id.tv_progress);
        tvQuestion = findViewById(R.id.tv_question);
        progressBar = findViewById(R.id.progressbar);
        tvOptionOne.setOnClickListener(this);
        tvOptionTwo.setOnClickListener(this);
        tvOptionThree.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        setQuestion();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_option_one){
            selectedOptionView(tvOptionOne,1);
        }
        else if (v.getId() == R.id.tv_option_two) {
            selectedOptionView(tvOptionTwo,2);
        }
        else if (v.getId() == R.id.tv_option_three) {
            selectedOptionView(tvOptionThree,3);
        }
        else if (v.getId() == R.id.btn_submit){
            if (selectedOption == 0){
                currentPosition++;
                if (currentPosition <= questionList.size()) {
                    setQuestion();
                } else {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra(QuestionData.CORRECT_ANSWERS, correctAnswers);
                    intent.putExtra(QuestionData.TOTAL_QUESTION, questionList.size());
                    startActivity(intent);
                    finish();
                }
            } else {
                Question question = questionList.get(currentPosition - 1);
                if (question.correctAnswer != selectedOption) {
                    answersView(selectedOption,R.drawable.wrong_option);
                } else {
                    correctAnswers++;
                }
                answersView(question.correctAnswer, R.drawable.correct_option);
                if (currentPosition == questionList.size()){
                    btnSubmit.setText("SELESAI");
                } else {
                    btnSubmit.setText("KE PERTANYAAN SELANJUTNYA");
                }
                selectedOption = 0;
            }
        }
    }


    private void setQuestion() {
        Question question = questionList.get(currentPosition - 1);
        defaultOptionView();
        if (currentPosition == questionList.size()) {
            btnSubmit.setText("SELESAI");
        } else {
            btnSubmit.setText("JAWAB");
        }
        progressBar.setProgress(currentPosition);
        tvProgress.setText(currentPosition + "/" + progressBar.getMax());
        tvQuestion.setText(question.question);
        tvOptionOne.setText(question.optionOne);
        tvOptionTwo.setText(question.optionTwo);
        tvOptionThree.setText(question.optionThree);
    }

    private void selectedOptionView(TextView tv, int selecteOptionNum) {
        defaultOptionView();
        selectedOption = selecteOptionNum;
        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setBackground(ContextCompat.getDrawable(QuizActivity.this,R.drawable.selected_option));

    }

    private void defaultOptionView() {
        ArrayList<TextView> options = new ArrayList<>();
        options.add(0,tvOptionOne);
        options.add(1,tvOptionTwo);
        options.add(2,tvOptionThree);


        for (int i = 0;i < options.size();i++){
            TextView option = options.get(i);
            option.setTextColor(Color.parseColor("#363A43"));
            option.setTypeface(Typeface.DEFAULT);
            option.setBackground(ContextCompat.getDrawable(QuizActivity.this,R.drawable.default_option_border_bg));
        }
    }

    private void answersView(int answers, int drawableView) {
        if (answers == 1) {
            tvOptionOne.setBackground(ContextCompat.getDrawable(QuizActivity.this,drawableView));
        } else if (answers == 2) {
            tvOptionTwo.setBackground(ContextCompat.getDrawable(QuizActivity.this,drawableView));
        } else if (answers == 3) {
            tvOptionThree.setBackground(ContextCompat.getDrawable(QuizActivity.this,drawableView));
        }
    }
}