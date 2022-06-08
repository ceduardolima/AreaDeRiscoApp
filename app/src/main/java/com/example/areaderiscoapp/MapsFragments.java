package com.example.areaderiscoapp;

import android.media.metrics.Event;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.areaderiscoapp.model.location.Geocode;
import com.example.areaderiscoapp.model.map.MapManager;
import com.example.areaderiscoapp.model.map.Place;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

public class MapsFragments extends Fragment {
    private ArrayList<Place> places;
    private EditText search;
    private LatLng latLng = null;
    private MapManager map; // Responsavel por criar e administrar o mapa
    private boolean inProcess = false;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Inicia o supportMapFragment para ser usado no maps
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map_fragment);

        // Inicia as variaveis do fragmento
        init(view, supportMapFragment);

        // Cria o mapa
        map.createMap();

        // Click do edit text do fragmento (EditText principal)
        search.setOnKeyListener((v, keyCode, event) -> {

            // Verifica se o houve um evento de click, e se o click foi no enter

            if((keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN) && !this.inProcess) {
                this.inProcess = true; // Desabilita a requisição caso ja tenha alguma em progresso
                editTextEnterEvent(view);
                return true;
            }
            return false;
        });

        return view;
    }

    private void editTextEnterEvent(View view) {
        // Executa o evento de click (enter) no edit text

        String[] buffer = filter(view);

        if(buffer == null) return;

        // Inicia a classe que faze a requisicao
        Geocode geocode = new Geocode();
        geocode.urlBuilder(buffer[0], buffer[1], null);

        // Inicia a thread de requisição
        new Thread( () -> {
            try {
                requestSetPosThread(view, geocode, map);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void requestSetPosThread(View view, Geocode geocode, MapManager map) throws InterruptedException {
        // Inicia a thread de consulta no Geoapify
        geocode.request(view.getContext());
        Thread.sleep(3000);
        latLng = geocode.getLatLng();

        // Faz com que a UI possa ser atualizada durante  thread
        view.postDelayed(() -> {
            if(latLng != null){
                map.setPosition("teste", latLng);
            }
            else {
                Toast.makeText(
                        view.getContext(),
                        "Não foi possível encontrar o endereço",
                        Toast.LENGTH_SHORT).show();
            }
            this.inProcess = false;
            }, 10);
    }

    private String[] filter(View view) {
        String[] buffer = this.search.getText().toString().split(", ");
        boolean haveNum = haveNumber(Arrays.toString(buffer));

        // Caso o requisito de: Cidade, Rua, não seja atendido, não ocorrerá a requisição
        if(buffer.length != 2 || haveNum) {
            Toast.makeText(view.getContext(), "Endereço inválido", Toast.LENGTH_SHORT).show();
            return null;
        }
        return buffer;
    }

    private boolean haveNumber(String str) {
        // Verifica se exite algum numero dentro da string
        for( int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private void initPlaces() {
        this.places.add(
                new Place(
                        "Alagamento",
                        new LatLng(-8.047020646310422, -34.88983073209863),
                        "Av. João de Barros - Soledade"
                )
        );

        this.places.add(
                new Place(
                        "Alagamento",
                        new LatLng(-8.04238025430913, -34.8908287302524),
                        "Av. João de Barros - Soledade"
                )
        );

        this.places.add(
                new Place(
                        "Alagamento",
                        new LatLng(-8.041455296575782, -34.90834841291173),
                        "R. Ascendino Neves - Torre"
                )
        );

        this.places.add(
                new Place(
                        "Alagamento",
                        new LatLng(-8.03166400679533, -34.908642177882236),
                        "Pão de Açúcar - Parnamirim"
                )
        );

        this.places.add(
                new Place(
                        "Alagamento",
                        new LatLng(-7.998590687590105, -34.8948083761807),
                        "Sapucaia, Olinda"
                )
        );

        this.places.add(
                new Place(
                        "Alagamento",
                        new LatLng(-7.996133175351881, -34.89626112484867),
                        "R. Severina Acácia - Águas Compridas"
                )
        );

        this.places.add(
                new Place(
                        "Alagamento",
                        new LatLng(-8.026686710236088, -34.956944751486354),
                        "R. Ribeiro Pessoa - Caxangá"
                )
        );
    }

    void init(View view, SupportMapFragment supportMapFragment) {
        this.places = new ArrayList<>();
        search = view.findViewById(R.id.txt_pesquisa);
        initPlaces();
        map = new MapManager(view.getContext(), supportMapFragment, places);
    }
}
