package com.example.exa_105_pocketcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView tvOutput;
    private Button btClear;
    private Button bt7;
    private Button bt8;
    private Button bt9;
    private Button btDivide;
    private Button bt4;
    private Button bt5;
    private Button bt6;
    private Button btMultiply;
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button btMinus;
    private Button bt0;
    private Button btComma;
    private Button btPlusMinus;
    private Button btPlus;
    private Button btEnter;

    //String for output
    private String output = "";

    //stack for all numbers
    Stack stack = new Stack(10);

    //last pressed button
    String lastPressed = "";

    //plus or minus before number
    String sign = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BUTTONS VARIABLEN ZUWEISSEN
        tvOutput = findViewById(R.id.tvOutput);
        btClear = findViewById(R.id.btClear);
        bt7 = findViewById(R.id.bt7);
        bt8 = findViewById(R.id.bt8);
        bt9 = findViewById(R.id.bt9);
        btDivide = findViewById(R.id.btDivide);
        bt4 = findViewById(R.id.bt4);
        bt5 = findViewById(R.id.bt5);
        bt6 = findViewById(R.id.bt6);
        btMultiply = findViewById(R.id.btMultiply);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        btMinus = findViewById(R.id.btMinus);
        bt0 = findViewById(R.id.bt0);
        btComma = findViewById(R.id.btComma);
        btPlusMinus = findViewById(R.id.btPlusMinus);
        btPlus = findViewById(R.id.btPlus);
        btEnter = findViewById(R.id.btEnter);

        //Digit Buttons onClick
        OnDigitClick onDigitClick = new OnDigitClick();
        bt0.setOnClickListener(onDigitClick);
        bt1.setOnClickListener(onDigitClick);
        bt2.setOnClickListener(onDigitClick);
        bt3.setOnClickListener(onDigitClick);
        bt4.setOnClickListener(onDigitClick);
        bt5.setOnClickListener(onDigitClick);
        bt6.setOnClickListener(onDigitClick);
        bt7.setOnClickListener(onDigitClick);
        bt8.setOnClickListener(onDigitClick);
        bt9.setOnClickListener(onDigitClick);

        //Clear Button onClick
        OnClear onClear = new OnClear();
        btClear.setOnClickListener(onClear);

        //Enter Button onClick
        OnEnter onEnter = new OnEnter();
        btEnter.setOnClickListener(onEnter);

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Operator buttons on click
        onOperator onOperator = new onOperator();
        btPlus.setOnClickListener(onOperator);
        btMinus.setOnClickListener(onOperator);
        btMultiply.setOnClickListener(onOperator);
        btDivide.setOnClickListener(onOperator);

        //Comma Button clicked
        onComma onComma = new onComma();
        btComma.setOnClickListener(onComma);

        //Plus Minus Button clicked
        onSign onSign = new onSign();
        btPlusMinus.setOnClickListener(onSign);
    }

    class OnDigitClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(output.length() >= 12){
                Toast.makeText(getApplicationContext(), "Too many digits", Toast.LENGTH_SHORT).show();
                return;
            }

            //get name of button and add it to output
            String value = ((Button) view).getText().toString();

            //output cannot start with 0
            if(!(output.length() == 0 && value.equals("0"))){
                output = output.concat(value);
                lastPressed = "number";
            }

            tvOutput.setText(output);
        }
    }

    class OnClear implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            lastPressed = "clear";
            stack.resetTos();
            sign = "";
            output = "";
            tvOutput.setText(output);
            Toast.makeText(getApplicationContext(), "Stack cleared", Toast.LENGTH_SHORT).show();
        }
    }

    class OnEnter implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(lastPressed.equals("number") || lastPressed.equals("sign")){
                lastPressed = "enter";
                stack.push(Double.parseDouble(output));
                output = "";
            }
        }
    }

    class onOperator implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(stack.getTos() == 0){
                return;
            }
            if(lastPressed.equals("enter")){
                Toast.makeText(getApplicationContext(), "Just press the operator.\nNo 'Enter' needed", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!lastPressed.equals("operator")){
                stack.push(Double.parseDouble(output));
            }
            if(stack.getTos() > 1){
                lastPressed = "operator";

                String operator = ((Button) view).getText().toString();
                double result = 0;
                double number1 = stack.pop();
                double number2 = stack.pop();

                switch(operator){
                    case "+": result = number2+number1;break;
                    case "-": result = number2-number1;break;
                    case "*": result = number2*number1;break;
                    case "/": result = number2/number1;
                }

                stack.push(result);

                DecimalFormat format = new DecimalFormat("#.###");
                output = format.format(result);
                output = output.replace(",", ".");
                tvOutput.setText(output);
                stack.outputStack();
                output = "";
            }else{
                Toast.makeText(getApplicationContext(), "Only one number in Stack", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class onComma implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(output.contains(".")){
                return;
            }
            if(lastPressed.equals("number") || lastPressed.equals("sign")){
                lastPressed = "comma";
                output = output.concat(".");
                tvOutput.setText(output);
            }else if(output.length() == 0){
                lastPressed = "comma";
                output = output.concat("0.");
                tvOutput.setText(output);
            }
            else{
                Toast.makeText(getApplicationContext(), "Invalid Comma", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class onSign implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            lastPressed = "sign";
            if(output.length() == 0){
                output = output.concat("0.");
            }

            if(sign.equals("")){
                sign = "-";
                output = sign.concat(output);
                tvOutput.setText(output);
            }else{
                sign = "";
                output = output.replace("-", "");
                tvOutput.setText(output);
            }
        }
    }
}