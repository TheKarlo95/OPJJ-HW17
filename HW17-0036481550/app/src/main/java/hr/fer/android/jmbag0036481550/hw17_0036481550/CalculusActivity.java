package hr.fer.android.jmbag0036481550.hw17_0036481550;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * {@code CalculusActivity} is a main launcher activity class that handles the user input(two operand and operator)
 * and does the calculation.
 *
 * @author Karlo Vrbić
 * @version 1.0
 * @see AppCompatActivity
 */
public class CalculusActivity extends AppCompatActivity {

    /**
     * Request form code.
     */
    private static final int REQUEST_FORM = 314;

    /**
     * Sign that signals what mathematical operation is currently selected.
     * <p>
     * It can be:
     * <ul>
     * <li>"+" - addition</li>
     * <li>"-" - subtraction</li>
     * <li>"*" - multiplication</li>
     * <li>"/" - divison</li>
     * </ul>
     * </p>
     */
    private String operation = "+";
    /** EditText of the first number. */
    private EditText etFirst;
    /** EditText of the second number. */
    private EditText etSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        etFirst = (EditText) findViewById(R.id.editTextNumber1);
        etSecond = (EditText) findViewById(R.id.editTextNumber2);

        etFirst.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_FORM:
                if (resultCode == RESULT_OK && data != null) {
                    EditText etFirst = (EditText) findViewById(R.id.editTextNumber1);
                    EditText etSecond = (EditText) findViewById(R.id.editTextNumber2);
                    etFirst.setText("");
                    etSecond.setText("");
                    etFirst.requestFocus();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Makes the calculation with specified operands and operations and starts {@linkplain DisplayActivity} and sends
     * result(or error information).
     *
     * @param view the view that was clicked
     */
    public void onCalculateClick(View view) {
        Intent intent = new Intent(CalculusActivity.this, DisplayActivity.class);

        double first;
        try {
            first = Double.parseDouble(etFirst.getText().toString());
        } catch (NumberFormatException e) {
            error(intent, "Pogrešan format prvog broja.", operation, null, null);
            startActivityForResult(intent, REQUEST_FORM);
            return;
        }
        double second;
        try {
            second = Double.parseDouble(etSecond.getText().toString());
        } catch (NumberFormatException e) {
            error(intent, "Pogrešan format drugog broja.", operation, first, null);
            startActivityForResult(intent, REQUEST_FORM);
            return;
        }

        switch (operation) {
            case "+":
                success(intent, operation, first, second, first + second);
                break;
            case "-":
                success(intent, operation, first, second, first - second);
                break;
            case "*":
                success(intent, operation, first, second, first * second);
                break;
            case "/":
                success(intent, operation, first, second, first / second);
                break;
            default:
                intent.putExtra("error", "Nepodržana matematička operacija.");
                startActivityForResult(intent, REQUEST_FORM);
                return;
        }

        startActivityForResult(intent, REQUEST_FORM);
    }

    /**
     * Handles all of the radio button clicks and sets the operation to the one user chose. This method is invoked
     * whenever the user clicks on any of the radio buttons.
     *
     * @param view the view that was clicked
     */
    public void onRadioButtonClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (!checked)
            return;

        switch (view.getId()) {
            case R.id.radioButton_add:
                operation = "+";
                break;
            case R.id.radioButton_sub:
                operation = "-";
                break;
            case R.id.radioButton_mul:
                operation = "*";
                break;
            case R.id.radioButton_div:
                operation = "/";
                break;
            default:
                throw new IllegalArgumentException("Illegal mathematical operation.");
        }
    }

    /**
     * Puts variables {@code msg}, {@code operation}, {@code num1} and {@code num2} in the {@code intent} extras. This
     * procedure is used when calculation fails for whatever reason.
     *
     * @param intent    the intent that is going to be passed to another activity
     * @param msg       the error message
     * @param operation current mathematical operation
     * @param num1      first operand
     * @param num2      second operand
     */
    private static void error(Intent intent, String msg, String operation, Double num1, Double num2) {
        intent.putExtra("error_msg", msg);
        intent.putExtra("operation", operation);
        if (num1 != null) {
            intent.putExtra("num1", num1.toString());
        }
        if (num2 != null) {
            intent.putExtra("num2", num2.toString());
        }
    }

    /**
     * Puts variables {@code operation}, {@code num1}, {@code num2} and {@code result} in the {@code intent} extras.
     * This procedure is used when calculation is successful.
     *
     * @param intent    the intent that is going to be passed to another activity
     * @param operation current mathematical operation
     * @param num1      first operand
     * @param num2      second operand
     * @param result    the result of the operation
     */
    private static void success(Intent intent, String operation, Double num1, Double num2, Double result) {
        intent.putExtra("operation", operation);
        if (num1 != null) {
            intent.putExtra("num1", num1.toString());
        }
        if (num2 != null) {
            intent.putExtra("num2", num2.toString());
        }
        if (result != null) {
            intent.putExtra("result", result.toString());
        }
    }
}
