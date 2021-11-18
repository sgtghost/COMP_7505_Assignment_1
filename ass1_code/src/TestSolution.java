import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSolution {
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new ArrayList<>();
        }

        int length = matrix.length, width = matrix[0].length;
        boolean[][] pacificVisited = new boolean[length][width];
        boolean[][] atlanticVisited = new boolean[length][width];

        for (int i = 0; i < length; i++) {
            dfs(matrix, pacificVisited, Integer.MIN_VALUE, i, 0);
            dfs(matrix, atlanticVisited, Integer.MIN_VALUE, i, width - 1);
        }

        for (int i = 0; i < width; i++) {
            dfs(matrix, pacificVisited, Integer.MIN_VALUE, 0, i);
            dfs(matrix, atlanticVisited, Integer.MIN_VALUE, length - 1, i);
        }

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (atlanticVisited[i][j] && pacificVisited[i][j]) {

                    List<Integer> visited = new ArrayList<>();
                    visited.add(i);
                    visited.add(j);
                    result.add(visited);
                }
            }
        }


        return result;
    }

    private void dfs (int[][] matrix, boolean[][] visited, int preHeight, int x, int y) {
        if (x < 0 || y < 0 ||
                x >= matrix.length || y >= matrix[0].length||
                visited[x][y] || matrix[x][y] < preHeight) {
            return;
        }

        int[][] movement = {{-1,0},{1,0},{0,-1},{0,1}};
        visited[x][y] = true;
        for (int[] move : movement) {
            dfs(matrix, visited, matrix[x][y], x + move[0], y + move[1]);
        }
    }
}
class test1 {
    public static void main(String[] args) {
        TestSolution solution = new TestSolution();
        int[][] matrix = {{1,2,2,3,5},{3,2,3,4,4},{2,4,5,3,1},{6,7,1,4,5},{5,1,1,2,4}};
        List<List<Integer>> result = solution.pacificAtlantic(matrix);
        for (List<Integer>ints : result) {
            System.out.println(ints.toString());
        }

    }
}