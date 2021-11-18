public class Dispatcher extends DispatcherBase {

    /* Implement all the necessary methods of Dispatcher here */
    private PlaneList planes;

    public Dispatcher() {
        this.planes = new PlaneList();
    }

    private PlaneList getPlanes() {
        return this.planes;
    }

    /**
     * Computes the number of planes in the system.
     *
     * @return the number of planes in the system
     */
    @Override
    public int size() {
        return this.planes.getLength();
    }

    /**
     * Adds a plane to the system.
     * The time complexity must be O(n).
     *
     * @param planeNumber string with three letters, followed by four numbers.
     *                    Example: "ABC1243", "ENC3455"
     * @param time        represents time in 24 hour format.
     */
    @Override
    public void addPlane(String planeNumber, String time) {
        Plane planeToAdd = new Plane(planeNumber, time);
        PlaneList planes = this.getPlanes();
        planes.insert(planeToAdd);
    }

    /**
     * Allocates the landing slot to the next plane in line if it is already
     * waiting, or if it arrives no later than 5 minutes from the current time.
     * <p>
     * Removes the plane that has been granted a landing slot and returns its number.
     * Otherwise, returns null.
     * <p>
     * The complexity must be O(1).
     *
     * @param currentTime represents the current time in 24 hour format.
     *                    Example: "9:24", "15:32"
     * @return plane number or null
     */
    @Override
    public String allocateLandingSlot(String currentTime) {
        Plane next = this.getPlanes().getHead().plane;

        String[] temp = currentTime.split(":");
        int convertedTime = Integer.parseInt(temp[0]) * 60 + Integer.parseInt(temp[1]);
        int timeDifference = convertedTime - next.getConvertedTime();

        if (timeDifference >= -5) {
            this.getPlanes().removeByNumber(next.getPlaneNumber());
            return next.getPlaneNumber();
        } else {
            return null;
        }
    }

    /**
     * Find and remove a plane by its number.
     * The complexity must be O(n).
     *
     * @param planeNumber string with 3 letters, followed by 4 numbers.
     *                    Example: "ABC1236", "ENC3455"
     * @return plane number or null
     */
    @Override
    public String emergencyLanding(String planeNumber) {
        return this.getPlanes().removeByNumber(planeNumber).plane.getPlaneNumber();
    }

    /**
     * Returns true if the plane is in the system, otherwise return false.
     *
     * @param planeNumber string with 3 letters, followed by 4 numbers.
     *                    Example: "ABC1235", "ENC3454"
     * @return true if the plane is present, false otherwise
     */
    @Override
    public boolean isPresent(String planeNumber) {
        return this.getPlanes().isPresent(planeNumber);
    }


}




/* Add any additional helper classes here */

class PlaneNode {
    protected Plane plane;
    protected PlaneNode next;
    protected PlaneNode prev;

    public PlaneNode(Plane plane) {
        this.plane = plane;
        this.next = null;
        this.prev = null;
    }

    @Override
    public String toString() {
        return this.plane.toString();
    }
}

class PlaneList {

    private PlaneNode head;
    private int length;

    public PlaneList() {
        this.length = 0;
    }

    public PlaneNode getHead() {
        return this.head;
    }

    public int getLength() {
        return this.length;
    }

    public void insert(Plane plane) {
        PlaneNode newPlaneNode = new PlaneNode(plane);
        if (this.head == null) {
            this.head = newPlaneNode;
        } else {
            PlaneNode searchPointer = this.head;
            if (searchPointer.plane.compareTo(plane) < 0) {
                while (searchPointer.next != null) {
                    PlaneNode nextPointer = searchPointer.next;
                    if (searchPointer.plane.compareTo(plane) < 0
                            && nextPointer.plane.compareTo(plane) > 0) {
                        newPlaneNode.next = nextPointer;
                        nextPointer.prev = newPlaneNode;
                        break;
                    }
                    searchPointer = nextPointer;
                }
                searchPointer.next = newPlaneNode;
                newPlaneNode.prev = searchPointer;
            } else {
                this.head = newPlaneNode;
                newPlaneNode.next = searchPointer;
                searchPointer.prev = this.head;
            }
        }
        length += 1;
    }

    public PlaneNode removeByNumber(String planeNumber) {
        PlaneNode searchPointer = this.head;

        do {
            PlaneNode nextPointer = searchPointer.next;
            PlaneNode prevPointer = searchPointer.prev;
            if (searchPointer.plane.getPlaneNumber().equals(planeNumber)) {
                length -= 1;

                if (prevPointer != null) {
                    prevPointer.next = nextPointer;
                } else {
                    this.head = nextPointer;
                }

                if (nextPointer != null) {
                    nextPointer.prev = prevPointer;
                }

                return searchPointer;
            }
            searchPointer = nextPointer;
        }
        while (searchPointer.next != null);

        if (searchPointer.plane.getPlaneNumber().equals(planeNumber)) {
            length -= 1;
            if (searchPointer.prev != null) {
                searchPointer.prev.next = null;
            } else {
                this.head = null;
            }
            return searchPointer;
        } else {
            return null;
        }
    }

    public boolean isPresent(String planeNumber) {
        PlaneNode searchPointer = this.head;
        if (searchPointer == null) {
            return false;
        } else {
            do {
                if (searchPointer.plane.getPlaneNumber().equals(planeNumber)) {
                    return true;
                }
                searchPointer = searchPointer.next;
            }
            while (searchPointer.next != null);
        }

        return searchPointer.plane.getPlaneNumber().equals(planeNumber);

    }


}