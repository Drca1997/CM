package com.example.guerradasestrelas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BaseDados extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="Cartas.db";
    //private static final String DELETE_DATABASE = "DROP DATABASE Cartas;";
    public static final String CARD_TABLE_NAME = "cartas";
    public static final String ID_COLUMN = "ID";
    private static final String INTEGER = " INTEGER";
    private static final String ID_CONSTRAINTS = " NOT NULL PRIMARY KEY AUTOINCREMENT";
    public static final String NAME_COLUMN = "Nome";
    private static final String TEXT_DEFINITION = " TEXT";
    private static final String NOT_NULL_CONSTRAINT = " NOT NULL";
    public static final String PODER_COLUMN = "Poder";
    public static final String FILA_COLUMN = "Fila";
    private static final String FILA_CONSTRAINTS = " NOT NULL CHECK(" + FILA_COLUMN + " >=0 AND " + FILA_COLUMN + " < " + Singleton.NUM_FILAS + "), ";
    public static final String IMG_MINI_COLUMN = "Imagem_Miniatura";
    public static final String IMG_MAX_COLUMN = "Imagem_Original";
    private static final String CARD_TABLE_CREATE = "CREATE TABLE " + CARD_TABLE_NAME + " (" +
                                                    ID_COLUMN + INTEGER + ID_CONSTRAINTS + ", " +
                                                    NAME_COLUMN +  TEXT_DEFINITION + NOT_NULL_CONSTRAINT + ", " +
                                                    PODER_COLUMN + INTEGER + NOT_NULL_CONSTRAINT +  ", " +
                                                    FILA_COLUMN + INTEGER + FILA_CONSTRAINTS  +
                                                    IMG_MAX_COLUMN + INTEGER + NOT_NULL_CONSTRAINT + ", " +
                                                    IMG_MINI_COLUMN + INTEGER + NOT_NULL_CONSTRAINT +");";
    public static final String SKILL_TABLE_NAME = "habilidades";
    private static final String SKILL_ID_CONSTRAINTS = " FOREIGN KEY(" + ID_COLUMN + ") REFERENCES " + CARD_TABLE_NAME + "(" + ID_COLUMN + ")";
    private static final String SKILL_COLUMN = "Habilidade";
    private static final String SKILL_COLUMN_CONSTRAINTS = " PRIMARY KEY";
    private static final String MODIFIER_COLUMN = "Modificador";
    private static final String FILA1_COLUMN = "Fila1";
    private static final String FILA2_COLUMN = "Fila2";
    private static final String ORIGEM_COLUMN = "Origem";
    private static final String AlEATORIO_COLUMN = "Aleatorio";
    private static  final String ONPLAY_COLUMN = "OnPlay";

    private static final String SKILL_TABLE_CREATE = "CREATE TABLE " + SKILL_TABLE_NAME + " (" +
                                                        ID_COLUMN + INTEGER + ", " +
                                                        SKILL_COLUMN + TEXT_DEFINITION + ", " +
                                                        MODIFIER_COLUMN + INTEGER  + ", " +
                                                        FILA1_COLUMN + INTEGER + ", " +
                                                        FILA2_COLUMN + INTEGER + ", " +
                                                        ORIGEM_COLUMN + TEXT_DEFINITION + ", " +
                                                        AlEATORIO_COLUMN + INTEGER + ", " +
                                                        ONPLAY_COLUMN + INTEGER + ", " +
                                                        SKILL_ID_CONSTRAINTS  + ");" ;


    public BaseDados(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CARD_TABLE_CREATE);
        SetupCardsDataBase(db);
        db.execSQL(SKILL_TABLE_CREATE);
        SetupSkillsDataBase(db);
        System.out.println("BASE DE DADOS CRIADA COM SUCESSO");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void SetupCardsDataBase(SQLiteDatabase bd){
        insereCartaNaBD(bd, "D.Afonso Henriques", 9, 0, R.drawable.afonso_henriques,  R.drawable.mini_afonso_henriques);
        insereCartaNaBD(bd, "Bruno Carvalho", 2, 0, R.drawable.bruno_carvalho, R.drawable.mini_bruno_carvalho);
        insereCartaNaBD(bd, "Camões", 3, 0, R.drawable.camoes, R.drawable.mini_camoes);
        insereCartaNaBD(bd, "Cristiano Ronaldo", 7, 0, R.drawable.cristiano_ronaldo, R.drawable.mini_cristiano_ronaldo);
        insereCartaNaBD(bd, "D.Sebastião", 8, 0, R.drawable.d_sebastiao, R.drawable.mini_d_sebastiao);
        insereCartaNaBD(bd, "Daniel Oliveira", 4, 0, R.drawable.daniel_oliveira, R.drawable.mini_daniel_oliveira);
        insereCartaNaBD(bd, "Diogo Morgado", 3, 0, R.drawable.diogo_morgado, R.drawable.mini_diogo_morgado);
        insereCartaNaBD(bd, "Éder", 9, 0, R.drawable.eder, R.drawable.mini_eder);
        insereCartaNaBD(bd, "Estudantes Académicos", 9, 0, R.drawable.estudantes, R.drawable.mini_estudantes);
        insereCartaNaBD(bd, "Fernando Mendes", 10, 0, R.drawable.fernando_mendes, R.drawable.mini_fernando_mendes);
        insereCartaNaBD(bd, "Jorge Jesus", 5, 0, R.drawable.jorge_jesus, R.drawable.mini_jorge_jesus);
        insereCartaNaBD(bd, "José Castelo Branco", 4, 0, R.drawable.jose_castelo_branco, R.drawable.mini_jose_castelo_branco);
        insereCartaNaBD(bd, "Luciana Abreu", 2, 0, R.drawable.luciana_abreu, R.drawable.mini_luciana_abreu);
        insereCartaNaBD(bd, "Luís Filipe VIeira", 2, 0, R.drawable.luis_filipe_vieira, R.drawable.mini_luis_filipe_vieira);
        //primeiro acabar de meter cartas de Portugal por ordem alfabetica
        //depois meter cartas do mundo, tb por ordem alfabetica
        System.out.println("CARTAS INSERIDAS NA BASE DADOS COM SUCESSO");
    }

    public boolean insereCartaNaBD(SQLiteDatabase bd, String nome, int poder, int fila, int imgMax, int imgMini){
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, nome);
        values.put(PODER_COLUMN, poder);
        values.put(FILA_COLUMN, fila);
        values.put(IMG_MAX_COLUMN, imgMax);
        values.put(IMG_MINI_COLUMN, imgMini);
        // Insert the new row, returning the primary key value of the <new row
        long res = bd.insert(CARD_TABLE_NAME, null, values);
        return res != -1;
    }

    public void SetupSkillsDataBase(SQLiteDatabase bd){
        insereSkillNaBD(bd, 3, "AddModifier", 1, 1, null, null, null, null);
        insereSkillNaBD(bd, 6, "AddModifier", -1, 3, 4, null, null, null);
        insereSkillNaBD(bd, 13,"AddModifier", 1, 1, 2, null, null, null);
        insereSkillNaBD(bd, 4, "MultiFila", null, null, null, null, null, null);
        insereSkillNaBD(bd, 2, "Ligacao", null, null, null, null, null, null);
        insereSkillNaBD(bd, 14, "Ligacao", null, null, null, null, null, null);
        insereSkillNaBD(bd, 7, "AddCardToHand", null, null, null, "self", 0, 0);
    }

    public boolean insereSkillNaBD(SQLiteDatabase bd, int id, String habilidade, Integer mod, Integer f1, Integer f2, String origem, Integer aleatorio, Integer onPlay){
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ID_COLUMN, id);
        values.put(SKILL_COLUMN, habilidade);
        if (mod != null){
            values.put(MODIFIER_COLUMN, mod);
        }
        if (f1 != null){
            values.put(FILA1_COLUMN, f1);
        }
        if (f2 != null){
            values.put(FILA2_COLUMN, f2);
        }
        if (origem != null){
            values.put(ORIGEM_COLUMN, origem);
        }
        if (aleatorio != null){
            values.put(AlEATORIO_COLUMN, aleatorio);
        }
        if (onPlay != null){
            values.put(ONPLAY_COLUMN, onPlay);
        }
        // Insert the new row, returning the primary key value of the <new row
        long res = bd.insert(SKILL_TABLE_NAME, null, values);
        return res != -1;
    }

    public Carta [] GetAllCards(){
        Carta [] all_cartas = new Carta [Singleton.NUM_CARTAS];
        int i= 0;
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM " + CARD_TABLE_NAME, null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
                String nome = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
                int poder = cursor.getInt(cursor.getColumnIndex(PODER_COLUMN));
                int fila = cursor.getInt(cursor.getColumnIndex(FILA_COLUMN));
                int imgMini = cursor.getInt(cursor.getColumnIndex(IMG_MINI_COLUMN));
                int imgMax = cursor.getInt(cursor.getColumnIndex(IMG_MAX_COLUMN));
                all_cartas[i] = new Carta(id, nome, poder, imgMini, imgMax, fila); //criacao de carta
                i++;
            }while (cursor.moveToNext());
            cursor.close();
        }

        return all_cartas;
    }

    public void getCardsSkills(Carta[] cartas){
        SQLiteDatabase bd = this.getReadableDatabase();
        for (Carta c : cartas){
            //get from skill table, carta.getId()
            Cursor cursor = bd.rawQuery("SELECT * FROM " + SKILL_TABLE_NAME +
                                        " WHERE " + ID_COLUMN + " == " + c.getId(), null);
            if (cursor.moveToFirst()){
                String skillName = cursor.getString(cursor.getColumnIndex(SKILL_COLUMN));
                switch(skillName){
                    case "AddModifier":
                        int mod = cursor.getInt(cursor.getColumnIndex(MODIFIER_COLUMN));
                        int f1 = cursor.getInt(cursor.getColumnIndex(FILA1_COLUMN));
                        int f2 = cursor.getInt(cursor.getColumnIndex(FILA2_COLUMN));
                        AddModififier addModSkill = new AddModififier(skillName, mod, f1, f2);
                        c.assignSkill(addModSkill);
                        break;
                    case "AddCardToHand":
                        String origem = cursor.getString(cursor.getColumnIndex(ORIGEM_COLUMN));
                        boolean onPlay = cursor.getInt(cursor.getColumnIndex(ONPLAY_COLUMN)) == 1;
                        boolean aleatorio = cursor.getInt(cursor.getColumnIndex(AlEATORIO_COLUMN)) == 1;
                        AddCardToHand addCardSkill = new AddCardToHand(skillName, origem, onPlay, aleatorio);
                        c.assignSkill(addCardSkill);
                        break;
                    default:
                        OutraHabilidade outra = new OutraHabilidade(skillName);
                        c.assignSkill(outra);
                        break;
                }
            }
        }
    }

    /*
    public void ApagaBD(SQLiteDatabase db){
        db.execSQL(DELETE_DATABASE);
    }*/
}
