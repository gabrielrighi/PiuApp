package com.proj.piu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "piuDatabase.db";
    private static final int DB_VERSION = 2;
    private static final String TB_ESPECIES = "tbEspecies";
    private static final String COL_ESPECIES_ESPECIE = "especie";
    private static final String COL_ESPECIES_NOMECIENTIFICO = "nome_cientifico";
    private static final String COL_ESPECIES_NOMECOMUM = "nome_comum";
    private static final String COL_ESPECIES_URLWIKIPEDIA = "url_wikipedia";
    private static final String COL_ESPECIES_URLWIKIAVES = "url_wikiaves";
    private static final String COL_ESPECIES_URLEBIRD = "url_ebird";

    private static final String TB_GRAVACOES = "tbGravacoes";
    private static final String COL_GRAVACOES_ID = "_id";
    private static final String COL_GRAVACOES_DATAHORA = "datahora";
    private static final String COL_GRAVACOES_ARQUIVO = "arquivo";
    private static final String COL_GRAVACOES_ENVIADO = "enviado";

    private static final String TB_RETORNOS = "tbRetornos";
    private static final String COL_RETORNOS_ID = "_id";
    private static final String COL_RETORNOS_IDGRAVACAO = "id_gravacao";
    private static final String COL_RETORNOS_ESPECIE = "especie";
    private static final String COL_RETORNOS_CONFIANCA = "confianca";
    private static final String COL_RETORNOS_DATAHORA = "datahora";
    private static final String COL_RETORNOS_FEEDBACK = "feedback";

    public DBHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBHANDLER","Entered onCreate method");

        String tbEspecies = "CREATE TABLE tbEspecies (" +
                "especie TEXT PRIMARY KEY," +
                "nome_cientifico TEXT," +
                "nome_comum TEXT," +
                "url_wikipedia TEXT," +
                "url_wikiaves TEXT," +
                "url_ebird TEXT" +
                ")";

        String tbGravacoes = "CREATE TABLE tbGravacoes (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "datahora TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "arquivo TEXT," +
                "enviado INTEGER DEFAULT 0" +
                ")";

        String tbRetornos = "CREATE TABLE tbRetornos (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_gravacao INTEGER NOT NULL," +
                "especie TEXT," +
                "confianca FLOAT," +
                "datahora TIMESTAMP," +
                "feedback INTEGER" +
                ")";

        String[][] dataEspecies = {
                {"furnariusrufus", "Furnarius rufus", "Joao-de-barro", "https://pt.wikipedia.org/wiki/Furnarius_rufus", "https://www.wikiaves.com/wiki/joao-de-barro", "https://ebird.org/species/rufhor2"},
                {"guiraguira", "Guira guira", "Anu-branco", "https://pt.wikipedia.org/wiki/Guira_guira", "https://www.wikiaves.com/wiki/anu-branco", "https://ebird.org/species/guicuc1"},
                {"hydropsalislongirostris", "Hydropsalis longirostris", "Bacurau-da-telha", "https://pt.wikipedia.org/wiki/Hydropsalis_longirostris", "https://www.wikiaves.com/wiki/bacurau-da-telha", "https://ebird.org/species/bawnig1/"},
                {"myiopsittamonachus", "Myiopsitta monachus", "Caturrita", "https://pt.wikipedia.org/wiki/Myiopsitta_monachus", "https://www.wikiaves.com/wiki/caturrita", "https://ebird.org/species/monpar"},
                {"paroariacoronata", "Paroaria coronata", "Cardeal", "https://pt.wikipedia.org/wiki/Paroaria_coronata", "https://www.wikiaves.com/wiki/cardeal", "https://ebird.org/species/reccar"},
                {"passerdomesticus", "Passer domesticus", "Pardal", "https://pt.wikipedia.org/wiki/Passer_domesticus", "https://www.wikiaves.com/wiki/pardal", "https://ebird.org/species/houspa"},
                {"pitangussulphuratus", "Pitangus sulphuratus", "Bem-te-vi", "https://pt.wikipedia.org/wiki/Pitangus_sulphuratus", "https://www.wikiaves.com/wiki/bem-te-vi", "https://ebird.org/species/grekis"},
                {"troglodytesmusculus", "Troglodytes musculus", "Corruira", "https://pt.wikipedia.org/wiki/Troglodytes_musculus", "https://www.wikiaves.com/wiki/corruira", "https://ebird.org/species/houwre/"},
                {"turdusrufiventris", "Turdus rufiventris", "Sabia-laranjeira", "https://pt.wikipedia.org/wiki/Turdus_rufiventris", "https://www.wikiaves.com/wiki/sabia-laranjeira", "https://ebird.org/species/rubthr1"},
                {"vanelluschilensis", "Vanellus chilensis", "Quero-quero", "https://pt.wikipedia.org/wiki/Vanellus_chilensis", "https://www.wikiaves.com/wiki/quero-quero", "https://ebird.org/species/soulap1"}
                //,{"euphoniachlorotica", "Euphonia chlorotica", "Fim-fim", "https://pt.wikipedia.org/wiki/Fim-fim", "https://www.wikiaves.com.br/wiki/fim-fim", "https://ebird.org/species/puteup1"}
        };

        // Aqui pode ser implementada chamada API para verificar especies suportadas

        try{
            Log.i("DBHANDLER","Creating tables");
            db.execSQL(tbEspecies);
            db.execSQL(tbGravacoes);
            db.execSQL(tbRetornos);

            for (String[] especie : dataEspecies) {
                ContentValues values = new ContentValues();

                Log.i("DBHANDLER","Inserting " + especie[0] + " on tbEspecies table");
                values.put(COL_ESPECIES_ESPECIE, especie[0]);
                values.put(COL_ESPECIES_NOMECIENTIFICO, especie[1]);
                values.put(COL_ESPECIES_NOMECOMUM, especie[2]);
                values.put(COL_ESPECIES_URLWIKIPEDIA, especie[3]);
                values.put(COL_ESPECIES_URLWIKIAVES, especie[4]);
                values.put(COL_ESPECIES_URLEBIRD, especie[5]);
                db.insert(TB_ESPECIES, null, values);

                values.clear();
            }
        } catch (SQLException e) {
            Log.e("DBHANDLER",e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        try{
            switch(newVersion){
                case 3: db.execSQL("DELETE FROM " + TB_ESPECIES + " WHERE especie = 'euphoniachlorotica'");
                        db.execSQL("ALTER TABLE " + TB_RETORNOS + " ADD COLUMN " + COL_RETORNOS_FEEDBACK + " INTEGER");
                        break;
                default: db.execSQL("DROP TABLE IF EXISTS " + TB_RETORNOS);
                        db.execSQL("DROP TABLE IF EXISTS " + TB_GRAVACOES);
                        db.execSQL("DROP TABLE IF EXISTS " + TB_ESPECIES);
                        onCreate(db);
                        break;
            }



        } catch (SQLException e) {
            Log.e("DBHANDLER",e.getMessage());
        }
    }

    public long addGravacao(String nomeArquivo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.i("DBHANDLER","Gravando arquivo: " + nomeArquivo);
        values.put(COL_GRAVACOES_ARQUIVO,nomeArquivo);

        long rowId = db.insert(TB_GRAVACOES, null, values);

        db.close();

        return rowId;
    }

    public boolean addRecon(long idGravacao, JSONObject reconData){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        boolean status = true;

        try{
            JSONArray speciesArray = reconData.getJSONArray("species");
            String reconTimestamp = reconData.getString("timestamp");

            for(int i = 0; i < speciesArray.length(); i++){
                JSONObject speciesObject = speciesArray.getJSONObject(i);

                String scientificName = speciesObject.getString("scientificname");
                double confidence = speciesObject.getDouble("confidence");

                ContentValues values = new ContentValues();
                values.put(COL_RETORNOS_CONFIANCA,confidence);
                values.put(COL_RETORNOS_ESPECIE,scientificName);
                values.put(COL_RETORNOS_IDGRAVACAO,idGravacao);
                values.put(COL_RETORNOS_DATAHORA,reconTimestamp);

                long rowId = db.insert(TB_RETORNOS,null,values);
                if(rowId != -1){
                    Log.i("DBHANDLER","Reconhecimento inserido no id " + idGravacao + " - " + scientificName);
                }else{
                    Log.d("DBHANDLER","Erro ao inserir reconhecimento no id " + idGravacao + " - " + scientificName);
                    status = false;
                }
            }
        }catch(Exception e){
            Log.e("DBHANDLER",e.getMessage());
        }

        if(status){
            db.setTransactionSuccessful();
        }

        db.endTransaction();
        db.close();

        return status;

    }

    public void updateGravacaoEnviado(long idGravacao){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_GRAVACOES_ENVIADO,1);

        String whereClause = COL_GRAVACOES_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(idGravacao)};

        db.update(TB_GRAVACOES,values,whereClause,whereArgs);
        Log.i("DBHANDLER","Valor 'enviado' atualizado para TRUE inserido no id " + idGravacao);

        db.close();
    }

    public void deleteGravacaoData(long idGravacao){
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = COL_GRAVACOES_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(idGravacao)};

        db.delete(TB_GRAVACOES,whereClause,whereArgs);
        Log.i("DBHANDLER","Registro excluido da tabela tbGravacoes - id " + idGravacao);

        db.close();
    }

    public ArrayList<HistoryModal> getHistory() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query_select_history = "SELECT " +
                "    tbGravacoes._id, " +
                "    tbGravacoes.datahora, " +
                "    tbGravacoes.arquivo, " +
                "    tbGravacoes.enviado, " +
                "    tbEspecies.nome_cientifico, " +
                "    tbEspecies.nome_comum " +
                "FROM tbGravacoes " +
                "LEFT JOIN (SELECT " +
                "        tbRetornos.id_gravacao, " +
                "        tbRetornos.especie " +
                "    FROM tbRetornos " +
                "    GROUP BY tbRetornos.id_gravacao " +
                "    HAVING MAX(tbRetornos.confianca) " +
                "    ORDER BY tbRetornos.id_gravacao, tbRetornos.confianca DESC) vwRetornos " +
                "ON tbGravacoes._id = vwRetornos.id_gravacao " +
                "LEFT JOIN tbEspecies " +
                "ON vwRetornos.especie = tbEspecies.especie " +
                "ORDER BY tbGravacoes.datahora DESC";
        //Cursor cursorHistory = db.rawQuery("SELECT tbRecons.*, tbEspecies.nomecientifico, tbEspecies.nomecomum  FROM tbRecons LEFT JOIN tbEspecies ON tbRecons.especie = tbEspecies.especie ORDER BY tbRecons.datahora DESC", null);
        Cursor cursorHistory = db.rawQuery(query_select_history,null);

        ArrayList<HistoryModal> historyModalArrayList = new ArrayList<>();

        if (cursorHistory.moveToFirst()){
            do {
                historyModalArrayList.add(new HistoryModal(
                        cursorHistory.getInt(cursorHistory.getColumnIndex(COL_GRAVACOES_ID)),
                        cursorHistory.getString(cursorHistory.getColumnIndex(COL_GRAVACOES_DATAHORA)),
                        cursorHistory.getString(cursorHistory.getColumnIndex(COL_GRAVACOES_ARQUIVO)),
                        cursorHistory.getInt(cursorHistory.getColumnIndex(COL_GRAVACOES_ENVIADO)),
                        cursorHistory.getString(cursorHistory.getColumnIndex(COL_ESPECIES_NOMECIENTIFICO)),
                        cursorHistory.getString(cursorHistory.getColumnIndex(COL_ESPECIES_NOMECOMUM))
                ));
            } while (cursorHistory.moveToNext());
        }

        cursorHistory.close();
        return historyModalArrayList;
    }

    public ArrayList<RetornoModal> getRetornos(long idGravacao){
        SQLiteDatabase db = this.getReadableDatabase();

        String query_select_retorno = "SELECT " +
                "   tbRetornos._id, " +
                "   tbEspecies.especie, " +
                "   tbEspecies.nome_cientifico, " +
                "   tbEspecies.nome_comum, " +
                "   tbRetornos.confianca, " +
                "   tbGravacoes.datahora " +
                "FROM tbGravacoes " +
                "LEFT JOIN tbRetornos " +
                "ON tbGravacoes._id = tbRetornos.id_gravacao " +
                "LEFT JOIN tbEspecies " +
                "ON tbRetornos.especie = tbEspecies.especie " +
                "WHERE tbRetornos.id_gravacao = " + idGravacao + " " +
                "ORDER BY tbRetornos.confianca DESC";
        //Cursor cursorHistory = db.rawQuery("SELECT tbRecons.*, tbEspecies.nomecientifico, tbEspecies.nomecomum  FROM tbRecons LEFT JOIN tbEspecies ON tbRecons.especie = tbEspecies.especie ORDER BY tbRecons.datahora DESC", null);
        Cursor cursorRetorno = db.rawQuery(query_select_retorno,null);

        ArrayList<RetornoModal> retornoModalArrayList = new ArrayList<>();

        if (cursorRetorno.moveToFirst()){
            do {
                retornoModalArrayList.add(new RetornoModal(
                        cursorRetorno.getInt(cursorRetorno.getColumnIndex(COL_RETORNOS_ID)),
                        cursorRetorno.getString(cursorRetorno.getColumnIndex(COL_ESPECIES_ESPECIE)),
                        cursorRetorno.getString(cursorRetorno.getColumnIndex(COL_ESPECIES_NOMECIENTIFICO)),
                        cursorRetorno.getString(cursorRetorno.getColumnIndex(COL_ESPECIES_NOMECOMUM)),
                        cursorRetorno.getFloat(cursorRetorno.getColumnIndex(COL_RETORNOS_CONFIANCA)),
                        cursorRetorno.getString(cursorRetorno.getColumnIndex(COL_GRAVACOES_DATAHORA))
                ));
            } while (cursorRetorno.moveToNext());
        }

        cursorRetorno.close();
        return retornoModalArrayList;
    }

    public HistoryModal getRowData(long idGravacao){
        SQLiteDatabase db = this.getReadableDatabase();

        String query_select_rowdata = "SELECT  " +
                "    tbGravacoes._id, " +
                "    tbGravacoes.datahora, " +
                "    tbGravacoes.arquivo, " +
                "    tbGravacoes.enviado  " +
                "FROM tbGravacoes " +
                "WHERE tbGravacoes._id = " + idGravacao;
        //Cursor cursorRow = db.rawQuery("SELECT _id, datahora, arquivo, enviado, especie FROM tbRecons WHERE _id = " + recId,null);
        Cursor cursorRow = db.rawQuery(query_select_rowdata,null);

        HistoryModal rowData = new HistoryModal();

        if(cursorRow.moveToFirst()){
            do {
                rowData.setDataHora(cursorRow.getString(cursorRow.getColumnIndex(COL_GRAVACOES_DATAHORA)));
                rowData.setArquivo(cursorRow.getString(cursorRow.getColumnIndex(COL_GRAVACOES_ARQUIVO)));
                rowData.setEnviado(cursorRow.getInt(cursorRow.getColumnIndex(COL_GRAVACOES_ENVIADO)));
            }while (cursorRow.moveToNext());
        }

        cursorRow.close();
        return rowData;

    }

    public Especies getEspecieData(String idEspecie){
        SQLiteDatabase db = this.getReadableDatabase();

        String query_select_especiedata = "SELECT " +
                "   tbEspecies.especie, " +
                "   tbEspecies.nome_cientifico, " +
                "   tbEspecies.nome_comum, " +
                "   tbEspecies.url_wikipedia, " +
                "   tbEspecies.url_wikiaves, " +
                "   tbEspecies.url_ebird " +
                "FROM tbEspecies " +
                "WHERE especie = '" + idEspecie + "'";
        //Cursor cursorRow = db.rawQuery("SELECT _id, datahora, arquivo, enviado, especie FROM tbRecons WHERE _id = " + recId,null);
        Cursor cursorRow = db.rawQuery(query_select_especiedata,null);

        Especies rowData = new Especies();

        if(cursorRow.moveToFirst()){
            do {
                rowData.setEspecieId(cursorRow.getString(cursorRow.getColumnIndex(COL_ESPECIES_ESPECIE)));
                rowData.setNomeCientifico(cursorRow.getString(cursorRow.getColumnIndex(COL_ESPECIES_NOMECIENTIFICO)));
                rowData.setNomeComum(cursorRow.getString(cursorRow.getColumnIndex(COL_ESPECIES_NOMECOMUM)));
                rowData.setUrlEbird(cursorRow.getString(cursorRow.getColumnIndex(COL_ESPECIES_URLEBIRD)));
                rowData.setUrlWikiaves(cursorRow.getString(cursorRow.getColumnIndex(COL_ESPECIES_URLWIKIAVES)));
                rowData.setUrlWikipedia(cursorRow.getString(cursorRow.getColumnIndex(COL_ESPECIES_URLWIKIPEDIA)));
            }while (cursorRow.moveToNext());
        }

        cursorRow.close();
        return rowData;

    }

}
