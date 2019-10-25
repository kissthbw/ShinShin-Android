package com.supermarket.shingshing.main.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.FragmentMenuBinding;
import com.supermarket.shingshing.login.LoginActivity;
import com.supermarket.shingshing.models.LoginModel;
import com.supermarket.shingshing.models.UsuarioModel;
import com.supermarket.shingshing.privacidad.PrivacidadActivity;
import com.supermarket.shingshing.util.UserPreferences;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.Utils;

import java.util.Locale;
import java.util.Objects;

public class MenuFragment extends Fragment {
    public static final String KEY_OPCION_DISPONIBLE = "opciones";
    public static final int OPCION_RETIRO = 0;
    public static final int OPCION_TICKETS = 1;
    public static final int OPCION_HISTORIAL = 2;
    public static final int MENU_PERFIL = 1;
    public static final int MENU_CUENTAS = 2;
    public static final int MENU_AYUDA = 3;
    public static final int MENU_CONTACTO = 4;

    private MenuListener listener;
    private FragmentMenuBinding binding;
    private GoogleSignInClient mGoogleSignInClient;

    public MenuFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (MenuListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);

        iniciarVistas();

        return binding.getRoot();
    }

    private void iniciarVistas() {
        UsuarioModel usuario = UsuarioSingleton.getUsuario();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso);

        binding.tvMenuSaldo.setText(String.format(Locale.US, "$ %d", usuario.getBonificacion()));
        if (usuario.getImgUrl() != null && !usuario.getImgUrl().trim().isEmpty()) {
            binding.ivMenuPerfil.setPadding(0, 0, 0, 0);
            Picasso.get().load(usuario.getImgUrl()).into(binding.ivMenuPerfil);
        }

        binding.ivMenuCerrar.setOnClickListener(v -> listener.onClickMenuCerrar());
        binding.clMenuRetirar.setOnClickListener(v -> listener.onClickOpcionDisponible(OPCION_RETIRO));
        binding.clMenuTickets.setOnClickListener(v -> listener.onClickOpcionDisponible(OPCION_TICKETS));
        binding.clMenuHistorial.setOnClickListener(v -> listener.onClickOpcionDisponible(OPCION_HISTORIAL));
        binding.clMenuOpcionPerfil.setOnClickListener(v -> listener.onClickMenuItem(MENU_PERFIL));
        binding.clMenuOpcionCuentas.setOnClickListener(v -> listener.onClickMenuItem(MENU_CUENTAS));
        binding.clMenuOpcionAyuda.setOnClickListener(v -> listener.onClickMenuItem(MENU_AYUDA));
        binding.clMenuOpcionContacto.setOnClickListener(v -> listener.onClickMenuItem(MENU_CONTACTO));
        binding.tvMenuPrivacidad.setOnClickListener(v -> getActivity().startActivity(new Intent(getActivity(), PrivacidadActivity.class)));
        binding.tvMenuCerrarSesion.setOnClickListener(v -> {
            int tipo = usuario.getIdRedSocial();
            if (tipo == 1) {
                mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), task -> cerrarSesion());
            }
            else if (tipo == 2) {
                LoginManager.getInstance().logOut();
                cerrarSesion();
            } else {
                cerrarSesion();
            }
        });
    }

    private void cerrarSesion() {
        Utils.eliminarArchivoUsuario(getContext());
        UserPreferences.setLoginUser(getContext(), new LoginModel(0));
        UsuarioSingleton.destroyUsuario();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        getActivity().finish();
    }
}
