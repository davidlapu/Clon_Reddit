package cat.itb.clonreddit.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class AddListenerOnTextChange implements TextWatcher {
    private final TextInputLayout t;

    public AddListenerOnTextChange(Context context, TextInputLayout t) {
        this.t = t;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        t.setError(null);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        t.setError(null);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}