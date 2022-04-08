package com.example.closhare.no_autorizado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.closhare.armario.PruebaArmario;
import com.example.closhare.R;
import com.example.closhare.home.PruebaHome;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {


//    Definiendo objetos de la vista
    Button entrar;
    TextView crear_cuenta, restaurar_pass;
    TextInputEditText usuario, password;
    ImageView googleButton;

//    Firebase
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser usuarioo;

//    API
    private final String url = "https://api.openweathermap.org/data/2.5/weather?q=Gandia&units=metric&appid=e96051f26738be95560f9d1a8d60feb6";

//    GOOGLE
    GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private final static int RC_SIGH_IN_GOOGLE = 2;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Difiniendo objetos con id de los elementos
        entrar = findViewById(R.id.login_entrar);
        crear_cuenta = findViewById(R.id.crear_cuenta);
        restaurar_pass = findViewById(R.id.restaurar_pass);
        usuario = findViewById(R.id.login_username);
        password = findViewById(R.id.login_pass);
        googleButton = findViewById(R.id.login_google);

//        Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usuarioo = FirebaseAuth.getInstance().getCurrentUser();

        if(mAuth.getCurrentUser() == null ){
            createRequest();
            funciones();
        } else {
            Log.d("Ususario entrado ","true");
            startActivity(new Intent(this, PruebaHome.class));
        }
    }

    public void funciones(){
        //        onClicks
        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registrar.class));
            }
        });

        restaurar_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RestaurarPass.class));
            }
        });
//        Funcion para entrar verificar y logear al usuario
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Declaramos strings como los valores de los inputs
                String user = usuario.getText().toString().trim();
                String pass = password.getText().toString();

//                Compobamos que los campos esten rellenados
                if(!user.isEmpty() && !pass.isEmpty()){
                    mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                if(!mAuth.getCurrentUser().isEmailVerified()){
                                    Toast.makeText(getApplicationContext(), "Verifica tu correo electronico", Toast.LENGTH_SHORT).show();
                                } else {
//                                    TODO
//                                   Entramos en HomePage
                                    startActivity(new Intent(getApplicationContext(), PruebaArmario.class));
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Incorrecto usuario y/o contraseña", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
//                Si los campos estan vacios
                if(user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Rellena los campos, por favor", Toast.LENGTH_SHORT).show();
                }
//                Si correo tiene la estructura incorrecta
                if(!isEmailValid(user)){
                    Toast.makeText(getApplicationContext(), "El email tiene la forma incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGH_IN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("Demo", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Demo", "Google sign in failed", e);
            }
        }

    }



//    GOOGLE

    private void createRequest(){

// Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGH_IN_GOOGLE);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if(mAuth.getUid() == null){
                                String[] prueba = mAuth.getCurrentUser().getDisplayName().split(" ");
                                Log.d("Demo", "Nombre " + prueba[0]);
                                Log.d("Demo", "Apellidos " + prueba[1]);


                                Map<String, Object> userGoogle = new HashMap<>();
                                userGoogle.put("Apellido", prueba[1]);
                                userGoogle.put("Email", mAuth.getCurrentUser().getEmail());
                                userGoogle.put("Nombre", prueba[0]);
                                userGoogle.put("photoUrl", mAuth.getCurrentUser().getPhotoUrl().toString());

                                db.collection("Users")
                                        .document(mAuth.getCurrentUser().getUid())
                                        .set(userGoogle);

                            }

                            Log.d("Demo", mAuth.getCurrentUser().getEmail() + " Ha entrado");
                            startActivity(new Intent(Login.this, PruebaHome.class));


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Demo", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }


    //    La funcion para comprobar que la frase sea email
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}