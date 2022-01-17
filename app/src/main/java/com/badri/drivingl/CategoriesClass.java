package com.badri.drivingl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CategoriesClass extends AppCompatActivity {

    LinearLayout linearImageQuestionBilet,scrollBiletebiCategory;
    ImageView imageQuestionBilet;
    TextView shekitxvatextBilet,pasuxiErtiTextBilet,pasuxiOriTextBilet,pasuxiSamiTextBilet,pasuxiOtxiTextBilet;
    RelativeLayout firstQuestionRelativeBilet,secondQuestionRelativeBilet,relativeLayoutMesameQuestBilet
            ,relativeLayoutMeotxeQuestBilet;
    View viewIdBilet;

    Database database;
    //stats database
    Database databaseStats;
    DBManager dbManager;

    int[] category1 = {37,62,123,125,130,204,208,260,263,334,335,390,391,
            471,878,879,883,884,887,888,889,891,892,893,895,896,900,902,905,
            906,910,914,916,918,919,920,921,922,923,924,925,949,961,962,1053,1056,
            1059,1089,1091,1092,1093,1098,1106,1114,1125,1126,1219,1220,1224,1225,
            1226,1227,1228,1230,1340,1341,1342,1379,1380,1482,1486,1572};
    int[] category2 = {79,829,999,1000,1001,1069,1318,1321,1322,1325,1327,1328,1330,1331,1332,1336,1337,1338,1339,1484};
    int[] category3 = {256,497,601,602,605,607,610,611,614,616,618,620,622,625,
            628,630,631,633,635,636,638,639,640,641,645,646,647,649,650,651,652,653};
    int[] category4 = {654,655,656,657,658,659,660,661,662,663,664,665,666,667,668,669,670,671};
    int[] category5 = {254,255,353,545,673,675,677,679,681,683,685,687,689,691,696,697,698,699,
            706,708,710,712,714,716,718,722,724,726,731,732,735,737,738,740,742,745,746,749,750,751,752,754,756,758,760,762,764,766,1102,1262};
    int[] category6 = {64,370,375,1140,1142,1145,1147,1149,1151,1152,1154,1155,1156,1157,1159,1160,1161,1163,1164,1168,1170,1232};
    int[] category7 = {187,189,192,337,484,486,488,768,770,771,774,776,777,778,779,780,782,784,785,787,789,790,792,794,796,798,799,800,912,1234,1236,1238};
    int[] category8 = {1171,1172,1173,1174,1175,1176,1177,1178,1179,1181,1182,1184,1185,1186,1187,1188,1189,1191,1192,1240};
    int[] category9 = {1193,1196,1198,1199,1201,1203,1205,1208,1211,1213,1215,1216,1239,1241,1242,1243,1244,1245,1246};
    int[] category10 = {4,14,21,23,63,75,102,105,106,107,108,109,110,115,116,118,126,141,142,143,144,146,148,149,150,
            151,152,153,157,158,165,166,167,168,169,170,175,176,177,178,181,182,183,184,185,186,327,389,477,480,1252,1276,1278,1649};
    int[] category11 = {15,18,33,35,38,39,45,139,140,193,194,195,196,198,199,200,202,203,205,206,207,
            211,212,213,216,217,218,221,222,427,428,437,875,901,955,1024,1281,1282};
    int[] category12 = {51,161,472,873,1002,1004,1005,1006,1007,1008,1009,1010,1013,1014,1015,1016,1017,1018,1019,1020,1021,1026,1028};
    int[] category13 = {344,356,569,897,964,1030,1032,1037,1038,1039,1040,1041,1042,1045,1047,1049,1086,1087,1088};
    int[] category14 = {306,328,355,403,410,411,820,830,927,929,930,931,932,933,934,935,937,965,968,969,973,974,975,
            976,977,979,980,981,983,984,985,986,987,988,989,991,993,1306,1314};
    int[] category15 =  {2,10,24,27,52,91,97,121,137,138,230,297,303,351,359,360,361,367,369,371,374,376,383,384,385,386,388,392,394,396,397,398,
            402,404,405,406,407,408,409,433,435,439,441,449,455,462,464,468,481,483,489,544,941,942,947,950,951,1048,1334,1430};
    int[] category16 = {261,262,264,265,267,269,270,271,273,274,275,276,277,279,280,281,282,283,285,286,287,288,289,290,291,294,
            295,296,298,299,300,301,302,304,305,310,311,312,313,316,424,426,432,434,438,469,492,506,507,508,509,510,885,954};
    int[] category17 = {224,225,226,228,229,231,232,233,235,248,250,251,257,307,354,540,898,1158,1271,1273};
    int[] category18 = {565,860,861,862,863,899,911,1003,1085,1285,1294,1295,1304,1307,1381,1428,1695,1728};
    int[] category19 = {292,314,315,317,320,321,322,324,326,329,330,339,342,343,345,346,348,350,
            352,357,358,372,373,463,475,478,479,482,485,487,495,496,499,501,803,811,886,894,957,959,1247,1253,1263,1269};
    int[] category20 = {1,5,6,7,13,16,17,20,26,29,41,42,47,48,49,50,53,54,65,66,68,69,73,76,78,80,81,88,89,92,96,113,114,119,120,128,134,171,504,511,512,963,1025,1250,1275,1698};
    int[] category21 = {155,162,179,180,377,414,415,416,417,418,419,421,422,423,425,430,431,436,440,442,444,908};
    int[] category22 = {336,368,378,379,401,445,447,450,451,452,453,454,456,457,458,460};
    int[] category23 = {59,266,268,503,513,515,518,519,533,534,1050};
    int[] category24 = {461,539,541,546,547,548,549,550,552,553,554,555,556,557,559,560,563,566,567,570,590};
    int[] category25 = {903,1131,1132,1133,1134,1136,1137,1138};
    int[] category26 = {325,332,333,476,571,573,574,581,585,586,587,812,813,814,815,816,845,847,849,868,869,870,946,1065};
    int[] category27 = {99,163,172,173,174,188,190,246,520,521,522,524,526,527,528,535,537,806,808,809,822,826,997,1270,1333,1335};
    int[] category28 = {347,400,876,877,948,952,1248,1249,1254,1255,1256,1257,1258,1259,1260,1261};
    int[] category29 = {498,500,505,543,588,592,593,596,597,598,599,600,810,871,872,890,909,913,915,966,970,1011,
            1023,1027,1046,1051,1054,1062,1063,1064,1071,1077,1078,1079,1084,1190,1251,1264,1265,1268,1280,1283,1284};
    int[] category30 = {9,11,25,31,32,34,40,43,56,57,58,67,93,122,219,272,293,382,582,801,802,804,805,818,819,823,827,828,831,
            832,833,834,835,836,837,838,839,840,841,842,843,850,851,852,853,854,855,856,857,858,859,864,865,866,867,874,953,956,978,994,995,1033,
            1055,1061,1066,1067,1068,1070,1286,1287,1288,1290,1296,1297,1298,1299,1301,1305,1308,
            1309,1313,1315,1316,1326,1426,1431,1449,1475,1577,1579,1591,1608,1609,1635,1650,1651,1655,1660,1696,1697,1727,1738};
    int[] category31 = {844,1052,1072,1073,1074,1075,1076,1080,1081,1082,1083,1266,1329,1345,1346,1347,1349,1353,1354,1355,1439,
            1699,1700,1701,1702,1703,1704,1705,1706,1707,1708,1711,1712,1713,1714,1715,1716,1718,1719,1720,1721,1722,1723};
    int[] category32 = {1742,1743,1744,1745,1746,1747,1748,1749,1750,1751,1752,1753,1754,1755,1756,1757,1758,1759,1760,1761,1762,1763,1764,1765,1766,
            1767,1768,1769,1770,1771,1772,1773,1774,1775,1776,1777,1778,1779,1780,1781,1782,1783,1784,1785,1786,1787,1788,1789,1790,1791,1792};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_class);

        database = new Database(this);
        database.open();

        dbManager = new DBManager(this);
        dbManager.open();
        //initializeEnv();
        //arrangeUi(25);

        Intent mIntent = getIntent();
        int switchMode = mIntent.getIntExtra("switchMode", 1);
        if(switchMode == 1)
            for(int i = 0; i < category1.length; i++){
                layoutInflateUserExams(category1[i]);
            }
        if(switchMode == 2)
            for(int i = 0; i < category2.length; i++){
                layoutInflateUserExams(category2[i]);
            }
        if(switchMode == 3)
            for(int i = 0; i < category3.length; i++){
                layoutInflateUserExams(category3[i]);
            }
        if(switchMode == 4)
            for(int i = 0; i < category4.length; i++){
                layoutInflateUserExams(category4[i]);
            }
        if(switchMode == 5)
            for(int i = 0; i < category5.length; i++){
                layoutInflateUserExams(category5[i]);
            }
        if(switchMode == 6)
            for(int i = 0; i < category6.length; i++){
                layoutInflateUserExams(category6[i]);
            }
        if(switchMode == 7)
            for(int i = 0; i < category7.length; i++){
                layoutInflateUserExams(category7[i]);
            }
        if(switchMode == 8)
            for(int i = 0; i < category8.length; i++){
                layoutInflateUserExams(category8[i]);
            }
        if(switchMode == 9)
            for(int i = 0; i < category9.length; i++){
                layoutInflateUserExams(category9[i]);
            }
        if(switchMode == 10)
            for(int i = 0; i < category10.length; i++){
                layoutInflateUserExams(category10[i]);
            }
        if(switchMode == 11)
            for(int i = 0; i < category11.length; i++){
                layoutInflateUserExams(category11[i]);
            }
        if(switchMode == 12)
            for(int i = 0; i < category12.length; i++){
                layoutInflateUserExams(category12[i]);
            }
        if(switchMode == 13)
            for(int i = 0; i < category13.length; i++){
                layoutInflateUserExams(category13[i]);
            }
        if(switchMode == 14)
            for(int i = 0; i < category14.length; i++){
                layoutInflateUserExams(category14[i]);
            }
        if(switchMode == 15)
            for(int i = 0; i < category15.length; i++){
                layoutInflateUserExams(category15[i]);
            }
        if(switchMode == 16)
            for(int i = 0; i < category16.length; i++){
                layoutInflateUserExams(category16[i]);
            }
        if(switchMode == 17)
            for(int i = 0; i < category17.length; i++){
                layoutInflateUserExams(category17[i]);
            }
        if(switchMode == 18)
            for(int i = 0; i < category18.length; i++){
                layoutInflateUserExams(category18[i]);
            }
        if(switchMode == 19)
            for(int i = 0; i < category19.length; i++){
                layoutInflateUserExams(category19[i]);
            }
        if(switchMode == 20)
            for(int i = 0; i < category20.length; i++){
                layoutInflateUserExams(category20[i]);
            }
        if(switchMode == 21)
            for(int i = 0; i < category21.length; i++){
                layoutInflateUserExams(category21[i]);
            }
        if(switchMode == 22)
            for(int i = 0; i < category22.length; i++){
                layoutInflateUserExams(category22[i]);
            }
        if(switchMode == 23)
            for(int i = 0; i < category23.length; i++){
                layoutInflateUserExams(category23[i]);
            }
        if(switchMode == 24)
            for(int i = 0; i < category24.length; i++){
                layoutInflateUserExams(category24[i]);
            }
        if(switchMode == 25)
            for(int i = 0; i < category25.length; i++){
                layoutInflateUserExams(category25[i]);
            }
        if(switchMode == 26)
            for (int j : category26) {
                layoutInflateUserExams(j);
            }
        if(switchMode == 27)
            for (int j : category27) {
                layoutInflateUserExams(j);
            }
        if(switchMode == 28)
            for (int j : category28) {
                layoutInflateUserExams(j);
            }
        if(switchMode == 29)
            for (int j : category29) {
                layoutInflateUserExams(j);
            }
        if(switchMode == 30)
            for (int j : category30) {
                layoutInflateUserExams(j);
            }
        if(switchMode == 31)
            for (int j : category31) {
                layoutInflateUserExams(j);
            }
        if(switchMode == 32)
            for (int j : category32) {
                layoutInflateUserExams(j);
            }

    }
    private void initializeEnv(){
        scrollBiletebiCategory = findViewById(R.id.scrollBiletebiCategory);
        linearImageQuestionBilet = findViewById(R.id.linearImageQuestionBilet);
        imageQuestionBilet = findViewById(R.id.imageQuestionBilet);
        shekitxvatextBilet = findViewById(R.id.shekitxvatextBilet);
        pasuxiErtiTextBilet = findViewById(R.id.pasuxiErtiTextBilet);
        pasuxiOriTextBilet = findViewById(R.id.pasuxiOriTextBilet);
        pasuxiSamiTextBilet = findViewById(R.id.pasuxiSamiTextBilet);
        pasuxiOtxiTextBilet = findViewById(R.id.pasuxiOtxiTextBilet);
        firstQuestionRelativeBilet = findViewById(R.id.firstQuestionRelativeBilet);
        secondQuestionRelativeBilet = findViewById(R.id.secondQuestionRelativeBilet);
        relativeLayoutMesameQuestBilet = findViewById(R.id.relativeLayoutMesameQuestBilet);
        relativeLayoutMeotxeQuestBilet = findViewById(R.id.relativeLayoutMeotxeQuestBilet);

    }

    String correctAnswerValue;
    private void arrangeUi(int i){
        database.getAnswers(i);
        if(database.imageValue.equals("")){
            linearImageQuestionBilet.setVisibility(View.GONE);
        }
        else{
            linearImageQuestionBilet.setVisibility(View.VISIBLE);
            int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
            Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
            imageQuestionBilet.setImageResource(id);
        }
        shekitxvatextBilet.setText(database.questionValue);

        if(!database.answerOneValue.equals(""))
            pasuxiErtiTextBilet.setText(database.answerOneValue);
        else
            pasuxiErtiTextBilet.setVisibility(View.GONE);
        if(!database.answerTwoValue.equals(""))
            pasuxiOriTextBilet.setText(database.answerTwoValue);
        else
            pasuxiOriTextBilet.setVisibility(View.GONE);
        if(!database.answerThreeValue.equals("")){
            relativeLayoutMesameQuestBilet.setVisibility(View.VISIBLE);
            pasuxiSamiTextBilet.setVisibility(View.VISIBLE);
            pasuxiSamiTextBilet.setText(database.answerThreeValue);
        }
        else{
            pasuxiSamiTextBilet.setVisibility(View.GONE);
            relativeLayoutMesameQuestBilet.setVisibility(View.GONE);
        }
        if(!database.answerFourValue.equals("")){
            relativeLayoutMeotxeQuestBilet.setVisibility(View.VISIBLE);
            pasuxiOtxiTextBilet.setVisibility(View.VISIBLE);
            pasuxiOtxiTextBilet.setText(database.answerFourValue);
        }
        else{
            pasuxiOtxiTextBilet.setVisibility(View.GONE);
            relativeLayoutMeotxeQuestBilet.setVisibility(View.GONE);
        }
        if(!database.correctAnswerValue.equals(""))
            correctAnswerValue = database.correctAnswerValue;
    }

    @SuppressLint("SetTextI18n")
    void layoutInflateUserExams(int i){

        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.biletiscroll, null, false);

        scrollBiletebiCategory = (LinearLayout) findViewById(R.id.scrollBiletebiCategory);
        scrollBiletebiCategory.addView(layout);


        linearImageQuestionBilet = layout.findViewById(R.id.linearImageQuestionBilet);
        imageQuestionBilet = layout.findViewById(R.id.imageQuestionBilet);
        shekitxvatextBilet = layout.findViewById(R.id.shekitxvatextBilet);
        pasuxiErtiTextBilet = layout.findViewById(R.id.pasuxiErtiTextBilet);
        pasuxiOriTextBilet = layout.findViewById(R.id.pasuxiOriTextBilet);
        pasuxiSamiTextBilet = layout.findViewById(R.id.pasuxiSamiTextBilet);
        pasuxiOtxiTextBilet = layout.findViewById(R.id.pasuxiOtxiTextBilet);
        firstQuestionRelativeBilet = layout.findViewById(R.id.firstQuestionRelativeBilet);
        secondQuestionRelativeBilet = layout.findViewById(R.id.secondQuestionRelativeBilet);
        relativeLayoutMesameQuestBilet = layout.findViewById(R.id.relativeLayoutMesameQuestBilet);
        relativeLayoutMeotxeQuestBilet = layout.findViewById(R.id.relativeLayoutMeotxeQuestBilet);
        viewIdBilet = layout.findViewById(R.id.viewIdBilet);

        database.getAnswersBiletebistvis(i);

        if(database.imageValue.equals("")){
            linearImageQuestionBilet.setVisibility(View.GONE);
        }
        else{
            linearImageQuestionBilet.setVisibility(View.VISIBLE);
            int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
            Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
            imageQuestionBilet.setImageResource(id);
        }
        shekitxvatextBilet.setText(database.questionValue);

        if(!database.answerOneValue.equals(""))
            pasuxiErtiTextBilet.setText(database.answerOneValue);
        else
            pasuxiErtiTextBilet.setVisibility(View.GONE);
        if(!database.answerTwoValue.equals(""))
            pasuxiOriTextBilet.setText(database.answerTwoValue);
        else
            pasuxiOriTextBilet.setVisibility(View.GONE);
        if(!database.answerThreeValue.equals("")){
            relativeLayoutMesameQuestBilet.setVisibility(View.VISIBLE);
            pasuxiSamiTextBilet.setVisibility(View.VISIBLE);
            pasuxiSamiTextBilet.setText(database.answerThreeValue);
        }
        else{
            pasuxiSamiTextBilet.setVisibility(View.GONE);
            relativeLayoutMesameQuestBilet.setVisibility(View.GONE);
        }
        if(!database.answerFourValue.equals("")){
            relativeLayoutMeotxeQuestBilet.setVisibility(View.VISIBLE);
            pasuxiOtxiTextBilet.setVisibility(View.VISIBLE);
            pasuxiOtxiTextBilet.setText(database.answerFourValue);
        }
        else{
            pasuxiOtxiTextBilet.setVisibility(View.GONE);
            relativeLayoutMeotxeQuestBilet.setVisibility(View.GONE);
        }
        if(!database.correctAnswerValue.equals(""))
            correctAnswerValue = database.correctAnswerValue;



    }
}