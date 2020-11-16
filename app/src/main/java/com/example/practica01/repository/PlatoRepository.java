package com.example.practica01.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.practica01.dao.AppDB;
import com.example.practica01.dao.PlatoDAO;
import com.example.practica01.model.Plato;

import java.util.List;

public class PlatoRepository {
    private PlatoDAO platoDao;
    private OnResultCallback callback;


    public PlatoRepository(Application application, OnResultCallback context) {
        AppDB db = AppDB.getInstance(application);
        platoDao = db.platoDAO();
        callback = context;
    }

    public interface OnResultCallback<T> {
        void onResult(List<T> result);

        void onResult(T result);

        void onResult(Long resultId);
    }

    /*

    PLATOS REPOSITORY

     */

    public void insertPlato(final Plato plato) {
        // AppDB.databaseWriteExecutor.execute(() -> platoDao.insert(plato));
        new InsertarPlato(platoDao,callback).execute(plato);
    }

    public void deletePlato(final Plato plato) {
        new BorrarPlato(platoDao).execute(plato);
        //AppDB.databaseWriteExecutor.execute(() -> platoDao.delete(plato));
    }

    public void updatePlato(final Plato plato) {
        new ActualizarPlato(platoDao).execute(plato);
        //AppDB.databaseWriteExecutor.execute(() -> platoDao.update(plato));
    }

    public void findPlatoById(String id) {

        new BuscarPlatoById(platoDao, callback).execute(id);

    }

    public void findAllPlatos() {

        new BuscarPlatos(platoDao, callback).execute();

    }

    class InsertarPlato extends AsyncTask<Plato, Void, Long> {

        private PlatoDAO platoDao;
        private OnResultCallback<Plato> callback;

        public InsertarPlato(PlatoDAO dao, OnResultCallback<Plato> context) {
            this.platoDao = dao;
            this.callback=context;
        }

        @Override
        protected Long doInBackground(Plato... platoes) {

            long id = platoDao.insert(platoes[0]);
            return id;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            callback.onResult(aLong);
        }
    }

    class BorrarPlato extends AsyncTask<Plato, Void, Void> {

        private PlatoDAO platoDAO;

        public BorrarPlato(PlatoDAO platoDAO) {
            this.platoDAO = platoDAO;
        }

        @Override
        protected Void doInBackground(Plato... platoes) {
            AppDB.databaseWriteExecutor.execute(() -> platoDao.delete(platoes[0]));
            return null;
        }
    }

    class ActualizarPlato extends AsyncTask<Plato, Void, Void> {

        private PlatoDAO platoDAO;

        public ActualizarPlato(PlatoDAO platoDAO) {
            this.platoDAO = platoDAO;
        }

        @Override
        protected Void doInBackground(Plato... platoes) {
            AppDB.databaseWriteExecutor.execute(() -> platoDao.update(platoes[0]));
            return null;
        }
    }

    class BuscarPlatoById extends AsyncTask<String, Void, Plato> {

        private PlatoDAO dao;
        private OnResultCallback<Plato> callback;

        public BuscarPlatoById(PlatoDAO dao, OnResultCallback<Plato> context) {
            this.dao = dao;
            this.callback = context;
        }

        @Override
        protected Plato doInBackground(String... strings) {
            Plato plato = dao.getById(strings[0]);
            return plato;
        }

        @Override
        protected void onPostExecute(Plato plato) {
            super.onPostExecute(plato);
            callback.onResult(plato);
        }
    }

    class BuscarPlatos extends AsyncTask<Void, Void, List<Plato>> {

        private PlatoDAO dao;
        private OnResultCallback<Plato> callback;

        public BuscarPlatos(PlatoDAO dao, OnResultCallback<Plato> context) {
            this.dao = dao;
            this.callback = context;
        }

        @Override
        protected List<Plato> doInBackground(Void... voids) {
            List<Plato> platos = dao.getAll();
            return platos;
        }

        @Override
        protected void onPostExecute(List<Plato> platos) {
            super.onPostExecute(platos);
            callback.onResult(platos);
        }


    }

}
