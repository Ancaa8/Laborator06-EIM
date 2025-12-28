package ro.pub.cs.systems.eim.fcm;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;

    private EditText etTopic;
    private Button btnSubscribe, btnUnsubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_REQUEST_CODE
                );
            }
        }

        etTopic = findViewById(R.id.etTopic);
        btnSubscribe = findViewById(R.id.btnSubscribe);
        btnUnsubscribe = findViewById(R.id.btnUnsubscribe);

        btnSubscribe.setOnClickListener(v -> {
            String topic = etTopic.getText().toString().trim().replace(" ", "_");
            if (topic.isEmpty()) {
                Toast.makeText(this, "Enter topic", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
                    .addOnCompleteListener(t ->
                            Toast.makeText(this, t.isSuccessful() ? "Subscribed" : "Error", Toast.LENGTH_SHORT).show());
        });

        btnUnsubscribe.setOnClickListener(v -> {
            String topic = etTopic.getText().toString().trim().replace(" ", "_");
            if (topic.isEmpty()) {
                Toast.makeText(this, "Enter topic", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                    .addOnCompleteListener(t ->
                            Toast.makeText(this, t.isSuccessful() ? "Unsubscribed" : "Error", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
