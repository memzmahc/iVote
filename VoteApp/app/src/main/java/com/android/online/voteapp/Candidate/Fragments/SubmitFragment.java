package com.android.online.voteapp.Candidate.Fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.online.voteapp.Candidate.FormModelClass;
import com.android.online.voteapp.R;
import com.android.online.voteapp.Session.Prevalent;
import com.android.online.voteapp.registration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Kiduyu klaus
 * on 06/11/2020 13:32 2020
 */
public class SubmitFragment extends Fragment {
    ImageView photo;
    EditText fname,idno,regno,depart,descr,location;
    Spinner seat;
    TextView choose;
    private Bitmap bitmap;
    Button upload;
    Uri filePath;

    private int PICK_IMAGE_REQUEST = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit, container, false);

        fname=view.findViewById(R.id.upload_fullname);
        idno=view.findViewById(R.id.upload_idnumber);
        regno=view.findViewById(R.id.upload_regnumber);
        depart=view.findViewById(R.id.upload_department);
        descr=view.findViewById(R.id.upload_description);
        location=view.findViewById(R.id.location_upload);
        seat=view.findViewById(R.id.spinner);
        photo=view.findViewById(R.id.post_image);
        upload=view.findViewById(R.id.upload_button);

        upload.setOnClickListener(v ->
        {
            Validate();
        });


        choose=view.findViewById(R.id.choose_image_text);
        choose.setOnClickListener(v ->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        return view;
    }

    private void Validate() {
        String mfname = fname.getText().toString().trim();
        String mid = idno.getText().toString().trim();
        String mregno = regno.getText().toString().trim();
        String mdepart = depart.getText().toString().trim();
        String mdescr = descr.getText().toString().trim();
        String mlocation = location.getText().toString().trim();
        String mseat = seat.getSelectedItem().toString();

        if (TextUtils.isEmpty(mfname)){
            fname.setError("Required");
            fname.requestFocus();
        } else  if (TextUtils.isEmpty(mid)){
            idno.setError("Required");
            idno.requestFocus();
        }else  if (TextUtils.isEmpty(mregno)){
            regno.setError("Required");
            regno.requestFocus();
        } else  if (TextUtils.isEmpty(mdepart)){
            depart.setError("Required");
            depart.requestFocus();
        } else  if (TextUtils.isEmpty(mdescr)){
            descr.setError("Required");
            descr.requestFocus();
        }else  if (TextUtils.isEmpty(mlocation)){
            location.setError("Required");
            location.requestFocus();
        }else  if (TextUtils.isEmpty(mlocation)){
            location.setError("Required");
            location.requestFocus();
        } else if (mseat.equals("Choose a Seat")){
            TextView errorText = (TextView)seat.getSelectedView();
            errorText.setError("anything here, just to add the icon");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please Choose A seat");//changes the selected item text to this

        } else  if (filePath==null){
            Toast.makeText(getActivity(), "No Image Found", Toast.LENGTH_SHORT).show();
        }
        else{
            ProgressDialog loadingBar = new ProgressDialog(getActivity());
            loadingBar.setTitle("Saving..");
            loadingBar.setMessage("Please wait, while we are Save the Details...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            SaveDetails(mfname,mid,mregno,mdepart,mdescr,mlocation,mseat,filePath,loadingBar);
        }

    }

    private void SaveDetails(String mfname, String mid, String mregno, String mdepart, String mdescr, String mlocation, String mseat, Uri filePath, ProgressDialog loadingBar) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //change 123 to Prevalent.currentOnlineUser.getPhone()

                if (!(dataSnapshot.child("CandidateForms").child("123").exists()))
                {
                    StorageReference mStorageRef;
                    mStorageRef = FirebaseStorage.getInstance().getReference().child("FormImages");
                    final StorageReference filereference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(filePath));

                    filereference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getActivity(), "image Uploaded", Toast.LENGTH_LONG).show();
                            filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Uri umageuri = uri;
                                    String imge = umageuri.toString();

                                    FormModelClass uploadtaskform = new FormModelClass(mfname,mid,mregno,mdepart,mdescr,mlocation,mseat,imge);

                                    RootRef.child("Voter").child("phone").setValue(uploadtaskform)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getActivity(), "Form Submitted Sucessfully", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();

                                                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container_admin,
                                                            new Myforms()).commit();

                                                }
                                            });

                                }
                            });

                        }
                    });


                } else{
                    Toast.makeText(getActivity(), "That Form Already Exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingBar.dismiss();
                Toast.makeText(getActivity(), String.valueOf(databaseError.getMessage()), Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr=getActivity().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}
