package hr.fer.android.jmbag0036481550.hw17_0036481550;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * {@code DisplayActivity} is an activity that displays the result or error report passed from the {@linkplain
 * CalculusActivity}.
 *
 * @author Karlo Vrbić
 * @version 1.0
 * @see AppCompatActivity
 */
public class DisplayActivity extends AppCompatActivity {

    /**
     * TextView that shows the result of the calculation or error report.
     */
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        tvResult = (TextView) findViewById(R.id.editTextResult);

        Bundle extras = getIntent().getExtras();
        String error = extras.getString("error_msg");
        String operation = extras.getString("operation");
        String num1 = getFormattedStringNumber(extras.getString("num1"));
        String num2 = getFormattedStringNumber(extras.getString("num2"));

        if (error != null) {
            tvResult.setText(String.format("Prilikom obavljanja operacije %s nad unosima %s i %s " +
                    "došlo je do sljedeće greške: %n%s", operation, num1, num2, error));
        } else {
            String result = getFormattedStringNumber(extras.getString("result"));
            tvResult.setText(String.format("Rezultat operacije %s %s %s je %s.", num1, operation, num2, result));
        }
    }

    /**
     * Sets the result of the activity to {@code RESULT_OK} and calls {@linkplain #finish()} to signal that this
     * activity is done and should be closed.
     *
     * @param v the view that was clicked
     * @see AppCompatActivity#setResult(int, Intent)
     * @see AppCompatActivity#finish()
     */
    public void onOKClick(View v) {
        setResult(RESULT_OK, new Intent());
        finish();
    }

    /**
     * Gives user the option of sending an e-mail to {@code ana.baotic@infinum.hr} with the report of the result.
     *
     * @param v the view that was clicked
     */
    public void onReportEMailClick(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ana.baotic@infinum.hr"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "0036481550: dz report");
        intent.putExtra(Intent.EXTRA_TEXT, tvResult.getText());

        try {
            startActivity(Intent.createChooser(intent, "Send e-mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DisplayActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Returns the string with the same number as in {@code str} but rounded to 4 decimal places or returns {@code
     * "null"} if parameter {@code str} is a {@code null} reference.
     *
     * @param str string representation of a number
     * @return formatted string representation of a number
     * @throws NumberFormatException if {@code str} cannot be parsed as a double value.
     */
    private static String getFormattedStringNumber(String str) {
        Double num = str != null ? Double.valueOf(str) : null;
        if (num != null) {
            return new DecimalFormat("#.####").format(num);
        } else {
            return null;
        }
    }
}
