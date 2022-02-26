package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAccount extends AppCompatActivity {
    private static final String TAG = "tag";
    private String firstName, lastName, birthday, gender, phoneNumber, email, nationalId, physicalAddress, password, confirmPassword;
    private TextInputEditText textPassword, textConfirmPassword, textEmailAddress;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private ImageView signupBackBtn;
    private Button signupBtn;

    private TextInputLayout materialPassword, materialConfirmPassword, materialEmail;
    public boolean valid;
    String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        textEmailAddress = findViewById(R.id.email_address);
        textPassword = findViewById(R.id.password);
        textConfirmPassword = findViewById(R.id.confirm_password);

        materialPassword = findViewById(R.id.material_password);
        materialConfirmPassword = findViewById(R.id.material_confirm_password);
        materialEmail = findViewById(R.id.material_email_address);

        signupBtn = findViewById(R.id.create_account_btn);
        signupBackBtn = findViewById(R.id.create_account_back_btn);

        getUserintentExtras();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFinalForm() && validateEmail()){
                    Toast.makeText(getApplicationContext(), " in the boc", Toast.LENGTH_SHORT).show();
                    performSignUp();
                    Toast.makeText(getApplicationContext(), birthday, Toast.LENGTH_LONG).show();

                }
            }
        });
        signupBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount.super.onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CreateAccount.this, Login.class));
    }

    private boolean validateFinalForm(){
        valid = false;
        email = textEmailAddress.getText().toString().trim();
        password = Objects.requireNonNull(textPassword.getText().toString());
        confirmPassword = Objects.requireNonNull(textConfirmPassword.getText().toString());

        try{
            if(email.isEmpty()){
                materialEmail.setError("Please enter email address");
            }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                materialEmail.setError("Invalid email address");
            } else if(password.isEmpty()){
                materialPassword.setError("Please enter password");
            } else if (password.length() <= 6) {
                materialPassword.setError("Password must be more than 6 characters");
            } else if(!password.matches(confirmPassword)){
                materialConfirmPassword.setError("Passwords do not match");
            } else {
                valid = true;
            }


        } catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, e.getMessage());
        }

        return valid;
    }

    private boolean validateEmail(){
        db.collection("Users")
                .whereEqualTo("Email Address", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Email already exist!");
                                materialEmail.setError("Email address already in use");
                                valid = false;
                            }
                        } else {
                            Log.d(TAG, "Email address not in use", task.getException());
                            valid = true;
                        }
                    }
                });
        return valid;
    }

    private void getUserintentExtras() {
        Intent intent = getIntent();

        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        birthday = intent.getStringExtra("birthday");
        gender = intent.getStringExtra("gender");
        phoneNumber = intent.getStringExtra("phoneNumber");
        nationalId = intent.getStringExtra("nationalId");
        physicalAddress = intent.getStringExtra("physicalAddress");

    }

    private void addUser(){

        if(validateFinalForm()){
            db = FirebaseFirestore.getInstance();
            Map<String, Object> user = new HashMap<>();

            user.put("First Name", firstName);
            user.put("Surname", lastName);
            user.put("Date of Birth", birthday);
            user.put("Gender", gender);
            user.put("Phone Number", phoneNumber);
            user.put("Email Address", email);
            user.put("National ID", nationalId);
            user.put("Physical Address", physicalAddress);
            user.put("Status", "customer");
            user.put("Number of Trips", "N/A");
            user.put("Rating", "N/A");
            user.put("Profile Pic", "/9j/4AAQSkZJRgABAQAAAQABAAD" +
                    "/4gIoSUNDX1BST0ZJTEUAAQEAAAIYAAAAAAIQAABtbnRyUkdCIFhZWiAAAAAAAAAAAAAAAABhY3NwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAA9tYAAQAAAADTLQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlkZXNjAAAA8AAAAHRyWFlaAAABZAAAABRnWFlaAAABeAAAABRiWFlaAAABjAAAABRyVFJDAAABoAAAAChnVFJDAAABoAAAAChiVFJDAAABoAAAACh3dHB0AAAByAAAABRjcHJ0AAAB3AAAADxtbHVjAAAAAAAAAAEAAAAMZW5VUwAAAFgAAAAcAHMAUgBHAEIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFhZWiAAAAAAAABvogAAOPUAAAOQWFlaIAAAAAAAAGKZAAC3hQAAGNpYWVogAAAAAAAAJKAAAA+EAAC2z3BhcmEAAAAAAAQAAAACZmYAAPKnAAANWQAAE9AAAApbAAAAAAAAAABYWVogAAAAAAAA9tYAAQAAAADTLW1sdWMAAAAAAAAAAQAAAAxlblVTAAAAIAAAABwARwBvAG8AZwBsAGUAIABJAG4AYwAuACAAMgAwADEANv" +
                    "/bAEMAEAsMDgwKEA4NDhIREBMYKBoYFhYYMSMlHSg6Mz08OTM4N0BIXE5ARFdFNzhQbVFXX2JnaGc+TXF5cGR4XGVnY" +
                    "//bAEMBERISGBUYLxoaL2NCOEJjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY" +
                    "//AABEIAMgAlgMBIgACEQEDEQH/xAAbAAACAwEBAQAAAAAAAAAAAAACAwABBAUGB/" +
                    "/EADYQAAICAQMCBAQDBwUBAQAAAAECAxEABBIhMUETIlFhBTJxgUKR8BQjobHB0eEGM1Ji8RUk" +
                    "/8QAGQEAAwEBAQAAAAAAAAAAAAAAAQIDAAQF/8QAJBEAAgICAgICAgMAAAAAAAAAAAECESExAxITQSJRI2EyQnH" +
                    "/2gAMAwEAAhEDEQA/AOlM5MqFaKhqojNGgmMenDI/KsRz3GYk3REMWBA" +
                    "/LH6R9OIyCbLE8E+p9Omc94JKqOhL8SR9P4Wn3GUcgjtnmdbO3gRpR3RsVPehxWdbUaml2Rwou09M4nxOZo3WQJbN1" +
                    "/lgTsVZOjDJ4mjUHkVXQ8EZUDEQkCwpPJzNodYE+FTxSgbg1i" +
                    "/cf4zR8PMbacq0m0k9AeMScXWwNMNvEpST5l74p23yyMw8xrn6Y0wMxvebJ64qeNUlB+e6AsYqT0mFN6KnkeSTfVEgYyKahtZu3bLZTsjKEq4BsnFMbVAsR3f8geubq7yAajMV7X3vLikMT7nXdwaC4sF42tgenI9M6Xw1DJqlJj3UCa49MdQyFHL1LBCvPB9MdJvJVytEran1zL8TcmRiqqq+x75qPOk0sjEmlKgdxR/zlJaGehZQsg2ghSeSctYEIvrXTnKac2KsAnKje5CN3TpfTJ0xchxwndza13rGxTNGaRyevQffB8UMAK5U37HIrK9tyGIo9sdCsTIGlkOwbW68jJmlo/FjXij6nJgZjMPCYWjspJ+Ug4LIpJ8NmJAG6xVHGlWMiiRW30TdVghVSQhvNYs+2BRqLQY6oqpAQWarUEe+I+J1JpZBt3beRjpBUaFWrqOecXIpaNqb5hzipUw6ZwYtSqKYqJD0bPqP/c9DpGb/AObCoCjj7m889qtLJHKGIBs9RnoI6TSqqn8IAOUllYNLQ3xZKCITyOTluCvynkdSTeDpidtse3TLkW1dlA297PXFSYFsW0zGCnHANXila3BI4vpeNhj8fQNbDiQEfSjhGIHaqgnbhcR3Gi0YAE7SbGb/AIS7xaiRwwvwzQPTOcRTFabgY/THw7YcNWbso5YLMuuRqSzYPOPAY6EbqADGj35GVIXNhWPmFE1fBw5HA0vhOpDbrDfaqxVOMkHaMTWZF5FAYSAn0r2xiaUkbgLs3bHG+CiKRa335w9qVmcXQCSbWAsAHGRshIth7V2xJiUsG2XX/bjIrKFraRi+RPQtGppkA55GTExgAcKCPcWcmGpfQKGQbnskNbCwKxLb0m5Viu6iRidNMf2gmSgwPls3hz6vdqCrKpF8gHj7YHKV0kaNrAyRdi04O0E0K6XmZWYqSSTXYds2kSSgJ4tRE0eMEhEIRHjaj5gp9MZvAbPP6jXzRMikAjodwzrQzxPCpYHkXWZtfp1m6iyDYG7ph6AEaUBk27eDx1w2qDhnQLKIqQrdcXilZgfPtA9zmchtxZjYHAWuuYw7Al2O6jyDiSb1EyR19OUg0bI7qwauR9eMoSKGtGJzjyapmUK7ABflAGNgjkdAZJCqn0xZdq2M8s6DzlFJHW+B64tppNoJIs9gcTVuNikBf45TSNzVAeuS5IRToDVBiZzJRYn26DDfV70CGg10DXOZ55I22+FYIFM3rgQFlbebFd8ZLxvAKo2NKI2VJJfmHALdMuJd/wArWTxVYkTK8gZoVNY5dSqsdsWweoPXK/jk7sJNrxqSwasP9w6AliGPXn+mZ5p2Zr44HGWs0ZABUlslUVJ0ZINSYmtXNdLBrJgDaRQaufbJlF09sNDnkTcKiIbtfpmPVNucPGu2wACO5GbkjDLbhS93x/LEamQ6bez7No5ChrxycTVDI7qBK1X0HrjDCkdfvAF68ZzRrDqAAwq1FUeBi31myEl33EHgH1vE65BTB+K6SEvcepDPfQntivhsoRmDMSvHX1zaiPJFDvEUjsDuDD+uZlRDqGKKBxyegH1yl4KLQ2eUnyqTXXnM5UcKDV/xyF/OR89dPfC08ckst+XyD1usir2EfDp4hGXloEdzycgpqLDgfKKwkhjVd0ignrllupHU4kuRXUQtr0UpJLlQAwNg3/AZRjK9TXtkRCRuvnvmrTfD5fiDnw3phV9a+56DKOPkeA1ZkLJH2BIwCd/m6DPYwf6f0Gn0bJP+8Y9ZTwR9PTPI6mIxamSMWQjlbA4IBxpcTSM0yoKEhZug9OMKfa7u6EUf4YccO5yrElB" +
                    "/xOJkI3khB7DAlSoKQITkeYEeuEwG87TtHbB3lVsj3IHbB8csaC/niywsIV0hqPsFHY2TAMZDecE+lHJh7SWKD2/RJNasZH4gTXoQc52q1RldwST5ebzWsZ1sqQxLulJ4CLd56TQ/6WgVRN8RILV/sx8D7nqft/HOiMbBFW8Hl9E96fy2WqjlyowYArvX0u6z3UcWi0yhdNpY464sKL/PrlvNuUg8j0xnAfxHldNCa3LJXWhXTB+JXthQgChZr+udedIBLwpjPfYa/hmN4GnsFPFZe6KbI9ayHJaQr43HJyAu4jmsam1CI0Fk9hmyXSSxxsfAdQOTaHM6fu7aFiGA+Y9sX+tsz1ZUkh+ULddzjtLpX1D8kLGOSxwtD8Pn1NbAWH4mbgD7/wBM7GmGi0K0WWeToxu1B9AP75uLiUvRowsyaf4XNqTvRfC098M3cew75vPxDTaCFdLGPDQd+7H39+n55NZ8VAXcW59M83q9S+smpVHHVj2y0VDit+yy46VnVh+NSsHWRtyA+QH9dMxsN7O5ZjZurxcOyCMqnJYck8kYXz11oZDl5PSF5Jp4Q1JQvVQVAqgMzOR4hIFD0AxrbAvr7YQdRER4Kk381njE45dl8nomnZlVA5Fbgf8AiRzmhoVRSZEA9APXADlWsdRgu7SMT39sp5I1gDaA3i+APyyYSQ9i6qfc5MHabygWz1ui0ej+FxbdNGAxFM7cs31P9MkuoLZleYk9cDcSec7TqUUhzNuwfFVDycAnFGO2JJwDGppISVLxozHgWtnGvqTtr8gM54QBwdxP1OaUmRRZ69sBmgDLPK+2JGc9OO31woPg8KEyasA82IlPA+v+Ma/xNVUUe2czVfFSbANe+K0ns3WzpazVxxxGJNqKBQVRQGea1OoWDd4dG+lH+n5fc4rU61pLAsscxrpnkkEkvlUHnD3BJJDlkl1HLsQg6n+gxq7QNgTag565GjYqPDArsMIKwdg1EKePfOOUm32Oec2xke0sN7EL6DDJDuBHYAPJJxe1iL7Vlqrjg8DOe36JVYxtrHjatcA84BjDmgG3E9boZoOg1TxL4emmZDzuVDyPyxSSGNSgYhSelZePxzMZJLYv9m22Q9gd7GH+5RvJfvZxZZBY5vsbyzG5o7h+WCfJdKGAN+kMtOoUfTJgqoApmr75Mh2msWCzsAjmzk31mZpfvinno9c9c7jYZeMW81XzmJpicHcT3wWNQ6bVGzt6+2Z7nkYXuUEWOas41AoIB75bMOrcfTAYVTV9czTQyM31/lnVgCk2QODyfTNccWnvc3X64sk3oJxoYhCh8NOe7Ec4xYpp28sby1/xUnO4s+k062kabszar474aVGLNcZNcL+yHjftmQfDNU3h7owlnncwH+c1D/T7tNuM8aJ3o7iP5ZyJPjmq3Mx+VW2m6uv0cYnx07BbEmu5x/FCKo0eNPB1j8J00HE2odyegWl/vjB8Q+H6BLiiRH7MeSPuc83q/jZdK6Hsc5yNqdX8quw9T0/PMvHBWkN0jE+mR/EdONEkwkDblBu7vPLfFHjm10k0YWmIJod65zDpVeCDwml49BhA38pN5zc3Mp4RDllHSDVkRf8AbANck84HiBySDdYPic88Dplgj8NZFz7v5aJXbBkG02wYD2yYZko9CPrky9cX2PgoyHK3E98zGWhgGY31zrbO017xlGau+YzMSOuLaXjFsJtbVbbHTM8ms7FjV5lMoHfEtOoBGawNm9dewI2k2OlcYZ+KSbQCT+eYtNodXqQHVRHGejuaGbIvgwb/AHJWaupqh0v75rt0J5ELf4kT+M1mcyy6glYkkdv+gJOa00sUTlRGhrpfObGkZD4jECxQxJScVYk+VpHJX4XqnYllWOxdu" +
                    "/8Aa81J8LRQPGnZjXyoKo/XHCV3sjnGhP3dhHaQnsOgyDfLPRHvN6FR6bTxnyxqfduTjWk6haxrQ702gbb/ADzMF2MVN2D3HXEnwyjmTsRp+xySKsTAruY9/TNSmOPTrXO4cc85hsuW2gAdaJ6Y2Ib0YSSEBRYPYZocjjhCElZDRUbT3GJLnBdyTS9sWXB+ZuMm7k7ZqGh+ObyYoSWeOmTGphMzSe+KaWu+Z2mN9cUZCc7j0bSNLzYozHL02k1GrkCwoW9T2H3z0ug+B6XS0+pdZXrj0H0zUI5nH0XwjV60hnBhiP42HUew7529J8F0mmcHaZK5LvR/LsM0sFCUDbDqb7YYjIiKRk0f+RxbISk9sWwj1CbBYVe5HB74oyyNJsiKEX3GPZVO+NwR2sDjKaGONXVyI9qk+X0/8wREj9CJodP4blnIcEWt2c5GoZ5yl7qXoM7G7TaVVcv4jOeLHNfTMk7wghvMR81Ba+14+yjjaMys0ZoAhx26VmqCdttE8nrkk8EJvLfvWF17YuGMMLIoHgEDphiqESpGkamKNwNhLD3y9XqINnQFz/DOW+slorGm3sTmRlnJPT7ZnbTQ9WjoAhjwVsYDSi6btmMRygWAR98Fd4JsGzkvAqF8ZqecVS4ArhuxwGRlAJHBzVpl3bA4pGJvMuNJCNUJFEc3fqcmaJdO4ZQilhXLdiRkw9AWc+D4PrJSNyeGP+/H+c6+l/09p4/NPI0hvp0GdlVaQ/yypHTdtZq29exvKWXcgFihg2xRxhOLodPtgySbkUgD92enW8OMxKrRueFHls3hvCkbB0CAVub0BIxGxO2RcaB34C3V0cBoCzbrIFcAkCsYAGJlPyg8HvgSuZbQE0vb1OLfoS80EyAfOxBP4iTzlzbTCqKoZiKP/mVTxoPxBvfkX6emSPhy/G5TfDWa++NeBrvYAhZmCEW3uKxE0cIpQDY7++af2gRSu6IWbb1P4ScW6JGiKwZieevT2xk6HToz7RS7k3m+vrhdItoLRspPlqwcb4SoisjNyefb6YtUZ72nkDgHCNhmZo2Y8qt9MEaQO5JpeeQOubAF43iuaOEu3xHJjPI/D2w39G/w50mi23e4kfyyLp0EbPuNLV" +
                    "/fOg6sT5RaFPlPJ/XOJ8ONUvkL3H98yujAt+zoVQRqAfMC3QZaW4dmAda8wrt2zSYo2h3BSVRdx7fTFrzsIDAEfKnf9G8SyTkHo5IIncuhcEAFe1+oyYGxU/ebgob8JHfJjYDUWaSUU7t7Aj0wfDR5WKCwB9cYo2zbCUAK9+pOWg/ZwXPmLLQ56fbA5r0HuvQutrBALBN+bkj3yo5nDuHQGutnv6ZUUyIQeHkY/hN1eKkSUsWd1W+4bjE2J/IdIXkSwAprjzdMHeyKWIo9weK98A6fcqATKFAry9b6417UEUSB1JF/5OI1kR4ZNRCzLuhcMvHej9MILSqANoBABJ+bIknSLciB+SKxjlCAQVO0A0cdsaUksIERqD5bq7UX1OKY+HTFuX7dayTN4jMeDTcm+mMVkAjWQl3UEIaFLgUrBGbexYYq7CtzDy0QRQyEHxArkgHsODj0Dkgq7Hy87heIdAXBK7kBFnjnGT9BjKwXKpJ4dNQAJscn2x8ZLK3hkqR1K8YlmLSOdu7aCRx054+uUm4IVCld3UA8N3+2N+im0Vcyy7Wsi+g4P1vDfTDcBusHr9cWrSJIUdCVArceSD64RZlAYjzLfmzN0CTaHagtGjFGDBqUKF68c4tZwsREYpiAAe4wFf8AZxtkJO89R0B/vlHwxW1QUPvioCQLLuYs4PXofX9DJhSbHUCBGJX5hkyioe0EpBUKwKEnoT37c5TbioZkG+gN3PSs1xwgMREBuVSTfQ85iacyKS5O0+i/y5ySSRKKoJUjhZnrcxF0FqqGVT0siIKNnZ6d8dFplXTiTdRPN1zXOVqdghWRA7c0UQHjAzNoURIxLo1MyhRZ6ccn9euXAZlQ+Xha5Lc+5w2PiKkdr2+Yc++FIxCb9wG4WQMGCd4shkCu5BL2PKAAawF8x8NQ2+gxs/Wh/DErG28MNxDXtIAFEe+XPM6zDdEWZh1H4cF" +
                    "/YHljNQrrdOdlc0BV+pxceq8oVkUGKrAolT+hlrEHJZQY23UVPU/l2xcyJGSQu0vZJ20L6j9e2Gs4D1NsEpVg6biaBoL2N/3GEzEqJKraSKrr9MyQu7OzIgB4Pt+fbGSyummURM6yE3b+nTG1gZJrA8JvNkhWBC3/AI+wxbwlmsSBa5AFc/XK8dl33tZh1F1hTaiMqXEJ4NqU9O/8cFh7MKWQMVQUCRe78/7YmZztAYfu24ux+eXC6zAEFWUcKrCqOUQsqlFDCutjj9f3w/sC3kSsfiuDFKCTyVu6r+mX4aiVygIvnaP6n88GMrAm8bi+7sRjgyyR7UlsGyCK" +
                    "/K8ZSvBTv6CVpY2OwAKeetDJi2RnVG2swqrGTGoYZp02OwJLrXrjoXVaQbQF6gDJkyLd0SbYjUTHTxiQmlHTcLvLeUuAwdkDdOwPvkyYrFS9iwqiPdIWckfNd173gI8atyGDAXbEVkyYHh4F0xm9iP8A87JyNxIN37ZDIWn2LGCVHmbrXfrkyYVsCw7CEse1wnMtWu71HpkiJBRJggkbg2P6dsmTGvJVhJs3iNw7qm49LurxbCDUOqhjuXghzVC757ZMmFrFmisWXSlQK8rduhv9HM0q8ed9rg1Y7D9HJkxo5QyRaNCInKBZGUA7W6Ejv/PLFyDbGNrOOgN2MmTMK8ARaKRQGjBHY0L" +
                    "/AIY/w/CIXYu4rtAB9cmTCnmhk7dBMJN+1jtCiqI4yZMmZtmbZ//Z");
            user.put("fcmToken", "N/A");


            db.collection("Users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "inserted successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "error! failed");
                        }
                    });

        }
    }

    private void performSignUp(){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing up please wait...");
        progressDialog.setTitle("UserSignup");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);

                    startActivity(new Intent(CreateAccount.this, HomeUser.class));
                    Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT).show();
                    addUser();
                } else {
                    progressDialog.dismiss();
                    Log.w(TAG, " UserSignup:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "User Signup failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    public void reload(){

    }

    private void updateUI(FirebaseUser user) {
    }
}
