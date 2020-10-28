package com.example.practica01.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.practica01.dao.AppDB;
import com.example.practica01.dao.PedidoDAO;
import com.example.practica01.model.Pedido;

import java.util.List;

public class PedidoRepository {
    private PedidoDAO pedidoDao;
    private PlatoRepository.OnResultCallback callback;



    public PedidoRepository(Application application, PlatoRepository.OnResultCallback context){
        AppDB db = AppDB.getInstance(application);
        pedidoDao = db.pedidoDAO();
        callback = context;
    }

    public interface OnResultCallback<T> {
        void onResult(List<T> result);
        void onResult(T result);
    }

    public void insertPedido(Pedido pedido){
        new InsertarPedido(pedidoDao).execute(pedido);
    }

    public void findPedidoById(String id){
        new BuscarPedidoById(pedidoDao,callback).execute(id);
    }

    public void findAllPedidos(){
        new BuscarPedidos(pedidoDao,callback).execute();
    }

    public void updatePedido(Pedido pedido){
        new ActualizarPedido(pedidoDao).execute(pedido);
    }

    public void deletePedido(Pedido pedido){
        new BorrarPedido(pedidoDao).execute(pedido);
    }


    class InsertarPedido extends AsyncTask<Pedido, Void, Void> {

        private PedidoDAO pedidoDAO;
        private List<Pedido> asd;

        public InsertarPedido(PedidoDAO pedidoDAO) {
            this.pedidoDAO = pedidoDAO;
        }

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            AppDB.databaseWriteExecutor.execute(() -> pedidoDao.insert(pedidos[0]));
            asd = pedidoDAO.getAll();
            return null;
        }

    }

    class BuscarPedidoById extends AsyncTask<String, Void, Pedido>{

        private PedidoDAO pedidoDAO;
        private PlatoRepository.OnResultCallback<Pedido> callback;

        public BuscarPedidoById(PedidoDAO pedidoDAO, PlatoRepository.OnResultCallback<Pedido> callback) {
            this.pedidoDAO = pedidoDAO;
            this.callback = callback;
        }

        @Override
        protected Pedido doInBackground(String... strings) {
            return null;
        }
    }

    class BuscarPedidos extends AsyncTask<Void, Void, List<Pedido>>{

        private PedidoDAO pedidoDAO;
        private PlatoRepository.OnResultCallback<Pedido> callback;

        public BuscarPedidos(PedidoDAO pedidoDAO, PlatoRepository.OnResultCallback<Pedido> callback) {
            this.pedidoDAO = pedidoDAO;
            this.callback = callback;
        }

        @Override
        protected List<Pedido> doInBackground(Void... voids) {
            List<Pedido> pedidos = pedidoDAO.getAll();
            return pedidos;
        }

        @Override
        protected void onPostExecute(List<Pedido> pedidos) {
            super.onPostExecute(pedidos);
            callback.onResult(pedidos);
        }
    }

    class ActualizarPedido extends AsyncTask<Pedido, Void, Void>{

        private PedidoDAO pedidoDAO;

        public ActualizarPedido(PedidoDAO pedidoDAO) {
            this.pedidoDAO = pedidoDAO;
        }

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            AppDB.databaseWriteExecutor.execute(() -> pedidoDao.insert(pedidos[0]));
            return null;
        }
    }

    class BorrarPedido extends AsyncTask<Pedido, Void, Void>{

        private PedidoDAO pedidoDAO;

        public BorrarPedido(PedidoDAO pedidoDAO) {
            this.pedidoDAO = pedidoDAO;
        }

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            AppDB.databaseWriteExecutor.execute(() -> pedidoDao.insert(pedidos[0]));
            return null;
        }
    }

}
