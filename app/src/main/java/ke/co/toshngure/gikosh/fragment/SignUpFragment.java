package ke.co.toshngure.gikosh.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ke.co.toshngure.basecode.networking.ResponseHandler;
import ke.co.toshngure.basecode.utils.BaseUtils;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.activity.BaseActivity;
import ke.co.toshngure.gikosh.activity.FragmentActivity;
import ke.co.toshngure.gikosh.network.BackEnd;
import ke.co.toshngure.gikosh.network.Client;
import ke.co.toshngure.gikosh.utils.PrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends BaseFragment {


    @BindView(R.id.firstNameMET)
    MaterialEditText firstNameMET;
    @BindView(R.id.lastNameMET)
    MaterialEditText lastNameMET;
    @BindView(R.id.phoneMET)
    MaterialEditText phoneMET;
    @BindView(R.id.emailMET)
    MaterialEditText emailMET;
    @BindView(R.id.passwordMET)
    MaterialEditText passwordMET;
    @BindView(R.id.confirmPasswordMET)
    MaterialEditText confirmPasswordMET;
    @BindView(R.id.signUpBtn)
    Button signUpBtn;
    @BindView(R.id.privacyPolicyTV)
    TextView privacyPolicyTV;
    @BindView(R.id.signInBtn)
    Button signInBtn;
    Unbinder unbinder;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        privacyPolicyTV.setText(Html.fromHtml(getString(R.string.privacy_policy_warning)));
        signInBtn.setText(Html.fromHtml(getString(R.string.have_an_account_sign_in)));

        BaseUtils.cacheInput(firstNameMET, R.string.first_name, PrefUtils.getInstance());
        firstNameMET.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_is_required)));

        BaseUtils.cacheInput(lastNameMET, R.string.last_name, PrefUtils.getInstance());
        lastNameMET.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_is_required)));

        BaseUtils.cacheInput(emailMET, R.string.email, PrefUtils.getInstance());
        emailMET.addValidator(BaseUtils.createEmailValidator(getString(R.string.error_email)));

        BaseUtils.cacheInput(phoneMET, R.string.phone, PrefUtils.getInstance());
        phoneMET.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_is_required)));
        phoneMET.addValidator(BaseUtils.createPhoneValidator(getString(R.string.error_phone)));
        phoneMET.addValidator(BaseUtils.createLengthValidator(10));

        BaseUtils.cacheInput(passwordMET, R.string.password, PrefUtils.getInstance());
        passwordMET.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_is_required)));

        BaseUtils.cacheInput(confirmPasswordMET, R.string.confirm_password, PrefUtils.getInstance());
        confirmPasswordMET.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_is_required)));
    }

    @Override
    public void connect() {
        super.connect();

        if (firstNameMET.validate() && lastNameMET.validate() && phoneMET.validate()
                && emailMET.validate() && passwordMET.validate() && confirmPasswordMET.validate()){

            if (TextUtils.equals(passwordMET.getText().toString(), confirmPasswordMET.getText().toString())){

                RequestParams params = new RequestParams();
                params.put(BackEnd.Params.FIRST_NAME, firstNameMET.getText().toString());
                params.put(BackEnd.Params.LAST_NAME, lastNameMET.getText().toString());
                params.put(BackEnd.Params.PHONE, phoneMET.getText().toString());
                params.put(BackEnd.Params.EMAIL, emailMET.getText().toString());
                params.put(BackEnd.Params.PASSWORD, passwordMET.getText().toString());
                //params.put(BackEnd.Params.GENDER, user.getGender());
                Client.getInstance().getClient()
                        .post(Client.absoluteUrl(BackEnd.EndPoints.PHONE_SIGN_UP),
                                params, new ResponseHandler(this));
            } else {
                toast(R.string.error_password);
            }
        }

    }

    @Override
    protected void onSuccessResponse(JSONObject data, JSONObject meta) {
        super.onSuccessResponse(data, meta);
        ((BaseActivity) getActivity()).onAuthSuccessful(data, meta);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.signUpBtn)
    public void onSignUpBtnClicked() {
        connect();
    }

    @OnClick(R.id.signInBtn)
    public void onSignInBtnClicked() {
        ((FragmentActivity) getActivity()).addFragment(SignInFragment.newInstance(), getString(R.string.sign_in));
    }
}
