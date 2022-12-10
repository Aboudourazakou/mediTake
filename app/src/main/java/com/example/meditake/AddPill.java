package com.example.meditake;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.CategorieMedicamentDao;
import com.example.meditake.database.dao.MedicamentDao;
import com.example.meditake.database.entities.CategorieMedicament;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.databinding.FragmentAddPillBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPill#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPill extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CAMERA_REQUEST = 1888;
    Bitmap cameraBitmap;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentAddPillBinding binding;
    AppDatabase db;
    Long idCategorie;
    public AddPill() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPill.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPill newInstance(String param1, String param2) {
        AddPill fragment = new AddPill();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_pill, container, false);
        binding.uploadPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        db=AppDatabase.getDataBase(getContext().getApplicationContext());
        CategorieMedicamentDao categorieMedicamentDao = db.categorieMedicamentDao();

        List<CategorieMedicament> list = categorieMedicamentDao.getAll();

        Log.e("tag",list.toString());
        Toast.makeText(getContext(), list.toString(), Toast.LENGTH_SHORT).show();
        ArrayList<String> categoriesName = new ArrayList<>();
        for (CategorieMedicament c:list
        ) {
            categoriesName.add(c.getLibelle());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,categoriesName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.categoriePill.setAdapter(dataAdapter);


        binding.savePill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // AppDatabase db=AppDatabase.getDataBase(getContext().getApplicationContext());
                MedicamentDao medicamentDao = db.medicamentDao();

                Medicament medicament=new Medicament();
                int qte=Integer.valueOf(binding.qtePill.getText().toString());
                medicament.setQte(qte);

                binding.categoriePill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        idCategorie = list.get(i).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                medicament.setCategorieId(idCategorie);

                medicament.setNom(binding.nommMed.getText().toString());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                cameraBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                medicament.setImage(imageInByte);


                if(  medicamentDao.insert(
                        medicament)>0){
                    binding.insertionMessage.setVisibility(View.VISIBLE);
                    binding.codeBar.setText("");
                    binding.nommMed.setText("");
                    binding.qtePill.setText("");
                    binding.uploadedPicture.setVisibility(View.GONE);

                }
            }
        });
        return   binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
           // imageView.setImageBitmap(photo);
            binding.uploadedPicture.setVisibility(View.VISIBLE);
            binding.uploadedPicture.setImageBitmap(photo);
            cameraBitmap=photo;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}