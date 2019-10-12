package com.eemf.sirgoingfar.numberformattutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.ikmich.numberformat.NumberFormatterTextWatcher;
import com.ikmich.numberformat.NumberInputFormatter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double number = 123456.023;

        Locale[] a = NumberFormat.getAvailableLocales();

        /* ------------> NumberFormat.getInstance() */
        putVerticalSpace("----- Number Instance ------");

        //Current Locale
        log("Current Locale: ", NumberFormat.getInstance().format(number));

        //Another Locale
        log("UK Locale: ", NumberFormat.getInstance(Locale.UK).format(number));
        log("French Locale: ", NumberFormat.getInstance(Locale.FRENCH).format(number));
        log("Germany Locale (format function): ", NumberFormat.getInstance(Locale.GERMANY).format(number));

        try {
            NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
            log("Germany Locale (parse function): ", nf.parse(nf.format(number)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
            log("French Locale (parse function): ", nf.parse(nf.format(number)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        putVerticalSpace("----- Integer Number Instance ------");

        /* ------------> NumberFormat.getIntegerInstance() */
        log("Number Integer Format (Current Locale | Format function | Input - Double): ", NumberFormat.getIntegerInstance().format(number));
        log("Number Integer Format (Current Locale | Format function | Input - Double): ", NumberFormat.getIntegerInstance().format(number));
        try {
            log("Number Integer Parse (France Locale | Parse function | Input - Double): ", NumberFormat.getIntegerInstance(Locale.FRANCE).parse(NumberFormat.getInstance(Locale.UK).format(number)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        putVerticalSpace("----- Number Percentage Instance ------");

        /* ------------> NumberFormat.getPercentInstance() */
        double value = 0.3446;
        log("Number Percentage Format (Current Locale | Format function | Input - Double): ", NumberFormat.getPercentInstance().format(number));
        log("Number Percentage Format (Current Locale | Format function | Input - Double): ", NumberFormat.getPercentInstance().format(value));
        log("Number Percentage Format (France Locale | Format function | Input - Double): ", NumberFormat.getPercentInstance(Locale.FRANCE).format(value));

        /* ------------> NumberFormat.getCurrencyInstance() */
        putVerticalSpace("----- Number Currency Instance ------");
        double amount = 223323345.34;

        log("Number Currency Format (Current Locale): ", NumberFormat.getCurrencyInstance().format(amount));

        NumberFormat b = NumberFormat.getCurrencyInstance(Locale.GERMAN);
        Currency bCurrency = b.getCurrency();
        log("Number Currency Format (German Locale): ", b.format(amount));

        NumberFormat c = NumberFormat.getCurrencyInstance(Locale.CHINESE);
        //Don't
        c.setGroupingUsed(false);
        Currency cCurrency = b.getCurrency();
        log("Number Currency Format (Chinese Locale): ", c.format(amount));

        NumberFormat d = NumberFormat.getCurrencyInstance(Locale.US);
        Currency dCurrency = d.getCurrency();
        log("Number Currency Format (US Locale): ", d.format(amount));


        putVerticalSpace("----- Decimal Separator and Integer Parsing ------");

        number = 12345.0;
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        NumberFormat df1 = NumberFormat.getInstance();
        NumberFormat df2 = NumberFormat.getInstance(Locale.GERMANY);
        df1.setParseIntegerOnly(true);
        df2.setParseIntegerOnly(true);
        log("Current Locale (Format function): ", df1.format(number));
        try {
            log("Current Locale (Parse function): ", df1.parse(df1.format(number)));
            log("Current Locale (Parse function): ", df2.parse(df2.format(number)));

            //By default, it's FALSE
            df.setDecimalSeparatorAlwaysShown(true);
            log("Current Locale (Format Function | Always show Decimal Separation): ", df.format(number));
            df.setDecimalSeparatorAlwaysShown(false);
            log("Current Locale (Format Function | Do not show Decimal Separation): ", df.format(number));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Parsing a Locale-formatted String in another Locale
        NumberFormat ng = NumberFormat.getInstance(Locale.UK);
        NumberFormat fr = NumberFormat.getInstance(Locale.FRENCH);

        String ngString = ng.format(number);
        try {
            Number frNumber = fr.parse(ngString);
            log("Ng -> Fr: ", frNumber.doubleValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final EditText editText = findViewById(R.id.et_amount);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                boolean wasCharacterDeleted = !TextUtils.isEmpty(s.toString()) && after == 0;

                putVerticalSpace("........Before Text Changed..........");
                log("text: ", s.toString());
                log("start: ", start);
                log("count: ", count);
                log("after: ", after);
                log("Cursor Position: ", String.valueOf(editText.getSelectionEnd()));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                putVerticalSpace("........On Text Changed..........");
                log("text: ", s.toString());
                log("start: ", start);
                log("before: ", before);
                log("count: ", count);
                log("Cursor Position: ", String.valueOf(editText.getSelectionEnd()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                putVerticalSpace("........After Text Changed..........");
                log("text: ", s.toString());
            }
        });
        editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        /* Ikenna's Libs - Start */
        NumberInputFormatter.Builder formatterBuilder = new NumberInputFormatter.Builder();
// formatterBuilder.formatInput(false);
// formatterBuilder.setMaxDecimalChars(2);
        formatterBuilder.showCurrency(true, Currency.getInstance(Locale.getDefault()).getSymbol());

        NumberInputFormatter inputFormatter = formatterBuilder.buildFor(editText);
        inputFormatter.setInputListener(new NumberFormatterTextWatcher.InputListener() {
            @Override
            public void onChange(String unformattedValue, String formattedValue) {
//                mUnformatted = unformattedValue;
//                mFormatted = formattedValue;
                log("Ikenna's Lib - Formatted: ", formattedValue);
                log("Ikenna's Lib - Unformatted: ", unformattedValue);
                putVerticalSpace("-----------------------------------------------");
            }
        });

        inputFormatter.setup(savedInstanceState != null);

        /* Ikenna's Libs - End */
    }

    private void putVerticalSpace(String msg) {
        log("", msg);
    }

    private void log(String msg, Number num) {
        log(msg, num.doubleValue());
    }

    private void log(String explainer, String msg) {
        msg = explainer + msg;
        Log.d("NUMBER_FORMAT", msg);
    }

    private void log(String msg, double val) {
        log(msg, String.valueOf(val));
    }
}
