package com.igaworks.dfinerysample.view;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.igaworks.dfinery.constants.DF;
import com.igaworks.dfinerysample.R;
import com.igaworks.dfinerysample.Utils;
import com.igaworks.dfinerysample.databinding.EventFragmentBinding;
import com.igaworks.dfinerysample.view.eventdetails.EventDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = Utils.getTagFromClass(EventFragment.class);
    private EventFragmentBinding binding;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    public EventFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult activityResult) {
                if(activityResult.getResultCode() == RESULT_OK){
                    Snackbar.make(binding.eventConstraintLayoutContainer, "이벤트가 등록되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else if (activityResult.getResultCode() == RESULT_CANCELED){
                    Snackbar.make(binding.eventConstraintLayoutContainer, "이벤트 등록이 취소되었습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else{
                    Snackbar.make(binding.eventConstraintLayoutContainer, "이벤트가 등록 중 오류가 발생했습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EventFragmentBinding.inflate(inflater, container, false);
        setClickListener();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.event_button_login) {
            startEventDetailActivity(DF.Event.LOGIN);
        } else if (id == R.id.event_button_logout) {
            startEventDetailActivity(DF.Event.LOGOUT);
        } else if (id == R.id.event_button_customEvent) {
            showInputFormForCustomEventName();
        } else if (id == R.id.event_button_signUp) {
            startEventDetailActivity(DF.Event.SIGN_UP);
        } else if (id == R.id.event_button_purchase) {
            startEventDetailActivity(DF.Event.PURCHASE);
        } else if (id == R.id.event_button_viewHome) {
            startEventDetailActivity(DF.Event.VIEW_HOME);
        } else if (id == R.id.event_button_productView) {
            startEventDetailActivity(DF.Event.VIEW_PRODUCT_DETAILS);
        } else if (id == R.id.event_button_addToCart) {
            startEventDetailActivity(DF.Event.ADD_TO_CART);
        } else if (id == R.id.event_button_addToWishlist) {
            startEventDetailActivity(DF.Event.ADD_TO_WISHLIST);
        } else if (id == R.id.event_button_refund) {
            startEventDetailActivity(DF.Event.REFUND);
        } else if (id == R.id.event_button_search) {
            startEventDetailActivity(DF.Event.VIEW_SEARCH_RESULT);
        } else if (id == R.id.event_button_share) {
            startEventDetailActivity(DF.Event.SHARE_PRODUCT);
        } else if (id == R.id.event_button_listView) {
            startEventDetailActivity(DF.Event.VIEW_LIST);
        } else if (id == R.id.event_button_cartView) {
            startEventDetailActivity(DF.Event.VIEW_CART);
        } else if (id == R.id.event_button_paymentInfoAdded) {
            startEventDetailActivity(DF.Event.ADD_PAYMENT_INFO);
        } else if (id == R.id.event_button_removeCart){
            startEventDetailActivity(DF.Event.REMOVE_CART);
        }
    }
    private void showInputFormForCustomEventName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("커스텀 이벤트의 이름을 입력하세요");
        // Set up the input
        final EditText editText = new EditText(getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editText);
        builder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = editText.getText().toString();
                if(TextUtils.isEmpty(input)){
                    Snackbar.make(binding.eventConstraintLayoutContainer, "값이 입력되지 않았습니다.", BaseTransientBottomBar.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                startEventDetailActivity(input);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void setClickListener(){
        binding.eventButtonLogin.setOnClickListener(this);
        binding.eventButtonLogout.setOnClickListener(this);
        binding.eventButtonCustomEvent.setOnClickListener(this);
        binding.eventButtonSignUp.setOnClickListener(this);
        binding.eventButtonPurchase.setOnClickListener(this);
        binding.eventButtonViewHome.setOnClickListener(this);
        binding.eventButtonProductView.setOnClickListener(this);
        binding.eventButtonAddToCart.setOnClickListener(this);
        binding.eventButtonAddToWishlist.setOnClickListener(this);
        binding.eventButtonRefund.setOnClickListener(this);
        binding.eventButtonSearch.setOnClickListener(this);
        binding.eventButtonListView.setOnClickListener(this);
        binding.eventButtonCartView.setOnClickListener(this);
        binding.eventButtonShare.setOnClickListener(this);
        binding.eventButtonPaymentInfoAdded.setOnClickListener(this);
        binding.eventButtonRemoveCart.setOnClickListener(this);
    }
    private void startEventDetailActivity(String eventName){
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra(EventDetailsActivity.BUNDLE_KEY_EVENT_NAME, eventName);
        activityResultLauncher.launch(intent);
    }
}
