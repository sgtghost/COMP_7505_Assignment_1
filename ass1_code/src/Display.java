//import java.util.Random;

class DisplayRandom extends DisplayRandomBase {
    /* Implement all the necessary methods here */

    public DisplayRandom(String[] csvLines) {
        super(csvLines);
    }

    /**
     * Sorts the planes stored in this.data.
     * Quicksort is used to reduce the time for random array
     *
     * @return the sorted array of planes
     */
    @Override
    public Plane[] sort() {
        Plane[] data = this.getData();
        quickSort(data, 0, data.length -1 );
        return data;
    }

//    private static int randomNumber(int head, int tail) {
//        Random rand = new Random();
//        return rand.nextInt(head - tail) + tail;
//    }

    private static void swap(Plane[] planes, int i, int j) {
        Plane temp = planes[i];
        planes[i] = planes[j];
        planes[j] = temp;
    }

    private static int partition(Plane[] planes, int start, int end) {
//        int pivot = randomNumber(end, start);
//        swap(planes, pivot, end);
        Plane x = planes[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (planes[j].compareTo(x) <= 0) {
                i++;
                swap(planes, i, j);
            }
        }
        swap(planes, i + 1, end);
        return i + 1;
    }


    private static void quickSort(Plane[] planes, int l, int r) {
        if (l < r) {
            int newIndex = partition(planes, l, r);
            quickSort(planes, l, newIndex - 1);
            quickSort(planes, newIndex + 1, r);
        }

    }


}

class DisplayPartiallySorted extends DisplayPartiallySortedBase {

    /* Implement all the necessary methods here */

    public DisplayPartiallySorted(String[] scheduleLines, String[] extraLines) {
        super(scheduleLines, extraLines);
    }

    /**
     * Sorts the planes stored in this.data.
     * Use insertion sort to utilise the existing sorted sequence
     *
     * @return the sorted array of planes
     */
    @Override
    Plane[] sort() {
        Plane[] data1 = this.getExtraPlanes();
        Plane[] data2 = this.getSchedule();
        Plane[] sortedData = new Plane[data1.length + data2.length];
        System.arraycopy(data1, 0, sortedData, 0, data1.length);
        System.arraycopy(data2, 0, sortedData, data1.length, data2.length);
        insertionSort(sortedData);
        return sortedData;
    }

    public static void insertionSort(Plane[] planes) {
        for (int j = 1; j < planes.length; j++) {
            Plane temp = planes[j];
            int i = j - 1;
            while (i >= 0 && planes[i].compareTo(temp) > 0) {
                planes[i + 1] = planes[i];
                i--;
            }
            planes[i + 1] = temp;
        }
    }
}
