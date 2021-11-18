import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<int[]> pacificAtlantic(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new ArrayList<>();
        }

        int m = matrix.length, n = matrix[0].length;
        boolean[][] pacificVisited = new boolean[m][n];
        boolean[][] atlanticVisited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            dfs(matrix, pacificVisited, Integer.MIN_VALUE, i, 0);
            dfs(matrix, atlanticVisited, Integer.MIN_VALUE, i, n - 1);
        }
        for (int i = 0; i < n; i++) {
            dfs(matrix, pacificVisited, Integer.MIN_VALUE, 0, i);
            dfs(matrix, atlanticVisited, Integer.MIN_VALUE, m - 1, i);
        }

        List<int[]> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacificVisited[i][j] && atlanticVisited[i][j]) {
                    ans.add(new int[]{i, j});
                }
            }
        }


        return ans;
    }

    private void dfs(int[][] matrix, boolean[][] visited, int preHeight, int x, int y) {
        if (x < 0 || y < 0 ||
                x >= matrix.length || y >= matrix[0].length
                || visited[x][y] || matrix[x][y] < preHeight) {
            return;
        }

        int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        visited[x][y] = true;
        for (int[] dir : DIRS) {
            dfs(matrix, visited, matrix[x][y], x + dir[0], y + dir[1]);
        }
    }

}

class test {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] matrix = {{1,2,2,3,5},{3,2,3,4,4},{2,4,5,3,1},{6,7,1,4,5},{5,1,1,2,4}};
        List<int[]> result = solution.pacificAtlantic(matrix);
        for (int[]ints : result) {
            System.out.println(Arrays.toString(ints));
        }
    }
}