package com.supermarket.shingshing.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.getbouncer.cardscan.ScanActivity;
import com.google.android.material.snackbar.Snackbar;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityMainBinding;
import com.supermarket.shingshing.main.alerta.AlertasFragment;
import com.supermarket.shingshing.main.disponible.DisponibleFragment;
import com.supermarket.shingshing.main.disponible.RetiroListener;
import com.supermarket.shingshing.main.disponible.opciones.DetalleTicketFragment;
import com.supermarket.shingshing.main.disponible.opciones.retiro.RetiroBancarioFragment;
import com.supermarket.shingshing.main.disponible.opciones.retiro.RetiroPaypalFragment;
import com.supermarket.shingshing.main.disponible.opciones.retiro.RetiroRecargaFragment;
import com.supermarket.shingshing.main.menu.MenuFragment;
import com.supermarket.shingshing.main.menu.MenuListener;
import com.supermarket.shingshing.main.menu.opciones.AyudaFragment;
import com.supermarket.shingshing.main.menu.opciones.ContactoFragment;
import com.supermarket.shingshing.main.menu.opciones.CuentasFragment;
import com.supermarket.shingshing.main.menu.opciones.PerfilFragment;
import com.supermarket.shingshing.main.menu.opciones.agregar.NoGuardadoListener;
import com.supermarket.shingshing.main.menu.opciones.agregar.NuevaCuentaFragment;
import com.supermarket.shingshing.main.menu.opciones.agregar.NuevaTarjetaFragment;
import com.supermarket.shingshing.main.menu.opciones.agregar.NuevoListener;
import com.supermarket.shingshing.main.menu.opciones.agregar.NuevoNumeroFragment;
import com.supermarket.shingshing.main.populares.PopularesFragment;
import com.supermarket.shingshing.main.producto.ProductoFragment;
import com.supermarket.shingshing.main.producto.ProductoListener;
import com.supermarket.shingshing.models.MedioBonificacionModel;
import com.supermarket.shingshing.models.ProductoModel;
import com.supermarket.shingshing.resultado.ResultadoActivity;
import com.supermarket.shingshing.util.UsuarioSingleton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainListener, MenuListener, NuevoListener, RetiroListener, NoGuardadoListener, ProductoListener {
    private ActivityMainBinding binding;
    private boolean noGuardado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        iniciarVistas();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ScanActivity.isScanResult(requestCode)) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(NuevaTarjetaFragment.class.getSimpleName());
            fragment.onActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void iniciarVistas() {
        binding.ivMainMenu.setOnClickListener(v -> binding.dlMainDrawer.openDrawer(Gravity.RIGHT, true));
        binding.ivMainHome.setOnClickListener(v -> cargarFragment(new MainFragment(), null, false));
        binding.clMainSaldo.setOnClickListener(v -> cargarFragment(new DisponibleFragment(), null, false));
        binding.ivMainNotificacion.setOnClickListener(v -> cargarFragment(new AlertasFragment(), null, false));

        NumberFormat formatter = new DecimalFormat("#,###");
        String money = "$ " + formatter.format(Objects.requireNonNull(UsuarioSingleton.getUsuario()).getBonificacion());
        binding.tvMainSaldo.setText(money);

        cargarFragment(new MainFragment(), null, false);
        cargarMenu();
    }

    private void cargarMenu() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.flMainMenu.getId(), new MenuFragment());
        fragmentTransaction.commit();
    }

    private void cargarFragment(Fragment fragment, Bundle bundle, boolean clearStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (clearStack) {
            for (int x = 0; x < fragmentManager.getBackStackEntryCount(); x++) {
                fragmentManager.popBackStack();
            }
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragmentAux = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
        if (fragmentAux == null) {
            fragmentAux = fragment;
            fragmentAux.setArguments(bundle);
        }
        fragmentTransaction.replace(binding.flMainContainer.getId(), fragmentAux, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    public void onClickPopulares(ArrayList<ProductoModel> productoModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PopularesFragment.KEY_PRODUCTOS, productoModels);
        cargarFragment(new PopularesFragment(), bundle, false);
    }

    @Override
    public void onClickPopularProducto(ProductoModel producto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ProductoFragment.KEY_PRODUCTO, producto);
        cargarFragment(new ProductoFragment(), bundle, false);
    }

    @Override
    public void onClickTicketResult() {
        startActivity(new Intent(this, ResultadoActivity.class));
    }

    @Override
    public void onMostrarSnackbar(String mensaje) {
        mostrarSnackbar(mensaje);
    }

    @Override
    public void onClickMenuItem(int opcion) {
        binding.dlMainDrawer.closeDrawer(Gravity.RIGHT, true);
        switch (opcion) {
            case MenuFragment.MENU_PERFIL:
                cargarFragment(new PerfilFragment(), null, false);
                break;
            case MenuFragment.MENU_CUENTAS:
                cargarFragment(new CuentasFragment(), null, false);
                break;
            case MenuFragment.MENU_AYUDA:
                cargarFragment(new AyudaFragment(), null, false);
                break;
            case MenuFragment.MENU_CONTACTO:
                cargarFragment(new ContactoFragment(), null, false);
                break;
        }
    }

    @Override
    public void onClickMenuCerrar() {
        binding.dlMainDrawer.closeDrawer(Gravity.RIGHT, true);
    }

    @Override
    public void onClickOpcionDisponible(int opcion) {
        Bundle bundle = new Bundle();
        bundle.putInt(MenuFragment.KEY_OPCION_DISPONIBLE, opcion);
        cargarFragment(new DisponibleFragment(), bundle, false);
        onClickMenuCerrar();
    }

    @Override
    public void onClickNuevo(int opcion) {
        switch (opcion) {
            case CuentasFragment.NUEVO_BANCARIA:
                cargarFragment(new NuevaTarjetaFragment(), null, false);
                break;
            case CuentasFragment.NUEVO_CUENTA:
                cargarFragment(new NuevaCuentaFragment(), null, false);
                break;
            case CuentasFragment.NUEVO_NUMERO:
                cargarFragment(new NuevoNumeroFragment(), null, false);
                break;
        }
    }

    @Override
    public void onClickItemMedio(int opcion, MedioBonificacionModel item) {
        Bundle bundle = new Bundle();
        switch (opcion) {
            case CuentasFragment.NUEVO_BANCARIA:
                bundle.putParcelable(CuentasFragment.KEY_BANCARIA, item);
                cargarFragment(new NuevaTarjetaFragment(), bundle, false);
                break;
            case CuentasFragment.NUEVO_CUENTA:
                bundle = new Bundle();
                bundle.putParcelable(CuentasFragment.KEY_CUENTA, item);
                cargarFragment(new NuevaCuentaFragment(), bundle, false);
                break;
            case CuentasFragment.NUEVO_NUMERO:
                bundle.putParcelable(CuentasFragment.KEY_NUMERO, item);
                cargarFragment(new NuevoNumeroFragment(), bundle, false);
                break;
        }
    }

    @Override
    public void agregarCuenta(int tipo) {
        onClickNuevo(tipo);
    }

    @Override
    public void mostrarCuentas() {
        cargarFragment(new CuentasFragment(), null, false);
    }

    @Override
    public void opcionRetiro(int opcion, ArrayList<MedioBonificacionModel> listaMedio) {
        Bundle bundle = new Bundle();
        switch (opcion) {
            case DisponibleFragment.RETIRO_BANCARIO:
                bundle.putParcelableArrayList(DisponibleFragment.LISTA_BANCARIA, listaMedio);
                cargarFragment(new RetiroBancarioFragment(), bundle, false);
                break;
            case DisponibleFragment.RETIRO_PAYPAL:
                bundle.putParcelableArrayList(DisponibleFragment.LISTA_PAYPAL, listaMedio);
                cargarFragment(new RetiroPaypalFragment(), bundle, false);
                break;
            case DisponibleFragment.RECARGA_TELEFONICA:
                bundle.putParcelableArrayList(DisponibleFragment.LISTA_NUMERO, listaMedio);
                cargarFragment(new RetiroRecargaFragment(), bundle, false);
                break;
        }
    }

    @Override
    public void mostrarDetalleTicket(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("idTicket", id);
        cargarFragment(new DetalleTicketFragment(), bundle, false);
    }

    @Override
    public void actualizarHeader(boolean visible) {
        binding.clMainTab.setBackgroundColor(ContextCompat.getColor(this, visible ? R.color.blanco : R.color.naranja));
        binding.ivMainHome.setImageDrawable(ContextCompat.getDrawable(this, visible ? R.drawable.ic_home_grey : R.drawable.ic_home_white));
        binding.ivMainNotificacion.setImageDrawable(ContextCompat.getDrawable(this, visible ? R.drawable.ic_notificacion_grey : R.drawable.ic_notificacion_white));
        binding.ivMainMenu.setImageDrawable(ContextCompat.getDrawable(this, visible ? R.drawable.ic_menu_grey : R.drawable.ic_menu_white));
        binding.clMainSaldo.setVisibility(visible ? View.VISIBLE : View.GONE);
        binding.vMainSeparator.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClickMostrarSnackbar(String mensaje) {
        onBackPressed();
        mostrarSnackbar(mensaje);
    }

    @Override
    public void actualizarColorHeader(String[] colores) {
        Window window = getWindow();
        if (colores != null) {
            binding.clMainTab.setBackgroundColor(Color.argb(255, Integer.parseInt(colores[0].trim()), Integer.parseInt(colores[1].trim()), Integer.parseInt(colores[2].trim())));
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.argb(255, Integer.parseInt(colores[0].trim()), Integer.parseInt(colores[1].trim()), Integer.parseInt(colores[2].trim())));
        } else {
            binding.clMainTab.setBackgroundColor(ContextCompat.getColor(this, R.color.blanco));
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blanco));
        }
    }

    private void mostrarSnackbar(String mensaje) {
        Snackbar snackbar = Snackbar.make(binding.clMainContainer, mensaje, Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        TextView textView = snackbarLayout.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.dimen_icon));
        snackbar.setBackgroundTint(getResources().getColor(R.color.verde_mensaje));
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if(noGuardado) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("")
                    .setMessage(getString(R.string.alerta_no_guardado))
                    .setCancelable(false)
                    .setPositiveButton("Seguir Aquí", (dialog, which) -> dialog.dismiss())
                    .setNegativeButton("Salir", (dialog, which) -> {if (fm.getBackStackEntryCount() == 1) {
                        finish();
                    } else {
                        fm.popBackStack();
                        noGuardado = false;
                    }});

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            if (fm.getBackStackEntryCount() == 1) {
                finish();
            } else {
                fm.popBackStack();
            }
        }
    }

    @Override
    public void onEditar(boolean noGuardado) {
        this.noGuardado = noGuardado;
    }
}
