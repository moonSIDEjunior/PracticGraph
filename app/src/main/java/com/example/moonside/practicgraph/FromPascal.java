//package com.example.moonside.practicgraph;
//
//import android.view.View;
//import android.widget.TextView;
//
//import javax.xml.transform.Templates;
//
//public class FromPascal {
//    TextView out;
//    int V = 4;
//    private int[][] graph = {{0, 1, 1, 1},
//                     {1, 0, 1, 1},
//                     {1, 1, 0, 1},
//                     {1, 1, 1, 0}};
//    private Boolean[] DOP = new Boolean[V];
//    private int[] X = new int[4];
//    private int i, j;
//    private int n = 4;
//    private int m = 4;
//    private int k = 0;
//
//    public void cycle(int i){
//        Byte j;
//        for(int u = 0; u < n; u++){
//            if (graph[X[i-1]][u] == 1) {
//                if ((u == k) & i >= 3) {
//                    for(j = 0; j < i ; j++)
//                        out.setText(X[j] + " ");
//                    out.setText("\n" + k);
//                }
//                else if (DOP[u]) {
//                    X[i] = u;
//                    DOP[u] = false;
//                    cycle(i+1);
//                    X[i] = 0;
//                    DOP[u] = true;
//                }
//            }
//        }
//    }
//
//    public FindPath(View view) {
//        for(int i = 0; i < m; i++) {
//            X[i] = 0;
//            DOP[i] = true;
//            X[0] = k;
//            cycle(1);
//        }
//    }
//}
