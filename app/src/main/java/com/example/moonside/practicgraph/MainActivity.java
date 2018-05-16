package com.example.moonside.practicgraph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    int V = 4;
    private int[][] graph = {{0, 1, 1, 1},
                             {1, 0, 1, 1},
                             {1, 1, 0, 1},
                             {1, 1, 1, 0}};

    private Boolean[] DOP = new Boolean[V];
    private int[] X = new int[100];
    private int i, j;
    private int n = 4;
    private int m = 4;
    private int k;
    ArrayList<ArrayList<Integer>> globalList = null;
    ArrayList<Integer> tempList = null;
    static ArrayList<ArrayList> combinatesList = null;
    static ArrayList<Integer> combinatesTempList = null;
    int[][] customGraph = null;
    Boolean customGraphFlag = false;
    Boolean emptyFlag = false;
    Integer tempVol = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    static void combinationUtil(Integer arr[], Integer data[], int start,
                                int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            for (int j=0; j<r; j++)
                combinatesTempList.add(data[j]);
            combinatesList.add(combinatesTempList);
            combinatesTempList = new ArrayList<>();
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    static void printCombination(Integer arr[], int n, int r)
    {
        // A temporary array to store all combination one by one
        Integer data[]=new Integer[r];

        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, data, 0, n-1, 0, r);
    }
    /*
    *
    * End of find all combination of vertex
    *
     */
    public void cycle(int i, int[] X, Boolean[] DOP, int[][] graph) {
        Byte j;
        for (int u = 0; u < n; u++) {
            if (graph[X[i - 1]][u] == 1) {
                if ((u == k) & i >= 2) {
                    for (j = 0; j < i; j++)
                        tempList.add(X[j]);
//                        out.setText(out.getText().toString() + X[j] + " ");
//                    out.setText(out.getText().toString() + k + "\n");
                    tempList.add(k);
                    globalList.add(tempList);
                    tempList = new ArrayList<>();

                } else if (DOP[u]) {
                    X[i] = u;
                    DOP[u] = false;
                    cycle(i + 1, X, DOP, graph);
                    X[i] = 0;
                    DOP[u] = true;
                }
            }
        }
    }
    /*
    *
    * End of find all paths of directed graph
    *
     */

    public boolean checkInclude(ArrayList temp) {
        boolean flag = false;
        for (int i = 0; i < globalList.size(); i++) {
            for (Integer j : globalList.get(i)) {
                for (int k = 0; k < temp.size(); k++) {
                    if (j == temp.get(k)) {
                        flag = true;
                        break;
                    }
                    else
                        flag = false;
                }
                if (flag) break;
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }
    /*
    *
    * End of checking including array of vertex in array of paths
    *
     */

    public boolean isExist(Integer arr[]) {
        Boolean flag = false;
        for (ArrayList tempArr: combinatesList) {
            if (checkInclude(tempArr)) {
                return true;
            }
        }
        if (!flag)
            return false;
        return true;
    }

    public Integer[] makeArray(int n) {
        Integer temp[] = new Integer[n];
        for (int i = 0; i < n; i++) {
            temp[i] = i;
        }
        return temp;
    }

    public void findPathSwitchOff(View view) {
        TextView out = findViewById(R.id.textView);
        tempList = new ArrayList<>();
        globalList = new ArrayList<ArrayList<Integer>>();
        combinatesList = new ArrayList<>();
        combinatesTempList = new ArrayList<>();

        EditText volVertex = findViewById(R.id.vertexVol);
        tempVol = Integer.parseInt(volVertex.getText().toString());

        EditText vertexCounter = findViewById(R.id.vertexCountFinder);
        Switch exampleSwitch = (Switch) findViewById(R.id.switch1);

        if (!exampleSwitch.isChecked()) {
            //Первая эвристика
            if (!emptyCheck(graph, tempVol)) {
                //Вторая эвристика
                if (tempVol != 0) {
                    if (Integer.parseInt(vertexCounter.getText().toString()) < tempVol) {
                        for (int i = 0; i < m; i++) {
                            X[i] = 0;
                            DOP[i] = true;
                        }
                        for (int l = 0; l < n; l++) {
                            k = l;
                            X[0] = k;
                            cycle(1, X, DOP, graph);

                        }
                        Integer arr[] = makeArray(n);
                        int r = Integer.parseInt(vertexCounter.getText().toString());
                        if (!globalList.isEmpty())
                            printCombination(arr, arr.length, r);
                        else {
                            out.setText("Граф не имеет контуров - ответ не может быть получен!");
                            return;
                        }
                        if (isExist(arr)) out.setText("Да, такое множество существует!");
                        else out.setText("Нет, такого множества не существует!");
                    } else out.setText("Да, такое множество существует!");
                } else out.setText("В графе отсутствуют вершины - ответ не может быть получен!");
            } else out.setText("Все вершины графа изолированы - ответ не может быть получен!");
        } else {
            //Первая эвристика
            if (emptyFlag == true) {
                //Вторая эвристика
                if (tempVol != 0) {
                    if (Integer.parseInt(vertexCounter.getText().toString()) < tempVol) {
                        for (int i = 0; i < m; i++) {
                            X[i] = 0;
                            DOP[i] = true;
                        }
                        for (int l = 0; l < n; l++) {
                            k = l;
                            X[0] = k;
                            cycle(1, X, DOP, customGraph);

                        }
                        Integer arr[] = makeArray(n);
                        int r = Integer.parseInt(vertexCounter.getText().toString());
                        if (!globalList.isEmpty())
                            printCombination(arr, arr.length, r);
                        else {
                            out.setText("Граф не имеет контуров - ответ не может быть получен!");
                            return;
                        }

                        if (isExist(arr)) out.setText("Да, такое множество существует!");
                        else out.setText("Нет, такого множества не существует!");
                    } else out.setText("Да, такое множество существует!");
                } else out.setText("В графе отсутствуют вершины - ответ не может быть получен!");
            }else out.setText("Все вершины графа изолированы - ответ не может быть получен!");
        }
    }

    public boolean emptyCheck(int[][] arr, int v) {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (arr[i][j] != 0)
                    return false;
            }
            return true;
    }

    public void addArc(View view) {
        Switch exampleSwitch = (Switch) findViewById(R.id.switch1);
        EditText firstVertex = findViewById(R.id.editText4);
        EditText secondVertex = findViewById(R.id.editText2);
        EditText vertexCounter = findViewById(R.id.vertexCountFinder);
        EditText volVertex = findViewById(R.id.vertexVol);
        tempVol = Integer.parseInt(volVertex.getText().toString());
            if (!customGraphFlag) {
                customGraph = new int[tempVol][tempVol];
                for (int i = 0; i < tempVol; i++)
                    for (int j = 0; j < tempVol; j++)
                        customGraph[i][j] = 0;
            }

        if (exampleSwitch.isChecked() & !firstVertex.getText().toString().isEmpty() & !secondVertex.getText().toString().isEmpty() & tempVol >= Integer.parseInt(vertexCounter.getText().toString())) {
            emptyFlag = true;
            customGraph[Integer.parseInt(firstVertex.getText().toString())][Integer.parseInt(secondVertex.getText().toString())] = 1;
            firstVertex.setText("");
            secondVertex.setText("");
            customGraphFlag = true;
        }
        else {
            return;
        }
    }
}


