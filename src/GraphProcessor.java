import java.security.InvalidAlgorithmParameterException;
import java.io.*;
import java.util.*;


/**
 * Models a weighted graph of latitude-longitude points
 * and supports various distance and routing operations.
 * To do: Add your name(s) as additional authors
 * @author Brandon Fain
 * @author Owen Astrachan modified in Fall 2023
 *
 */
public class GraphProcessor {
    /**
     * Creates and initializes a graph from a source data
     * file in the .graph format. Should be called
     * before any other methods work.
     * @param file a FileInputStream of the .graph file
     * @throws Exception if file not found or error reading
     */

    // include instance variables here
    private Map<Point, Set<Point>> myGraph = new HashMap<Point, Set<Point>>();

    public GraphProcessor(){
        // TODO initialize instance variables

    }

    /**
     * Creates and initializes a graph from a source data
     * file in the .graph format. Should be called
     * before any other methods work.
     * @param file a FileInputStream of the .graph file
     * @throws IOException if file not found or error reading
     */

     //Status: FINISHED BUT UNTESTED
    public void initialize(FileInputStream file) throws IOException {
        if (!testFileType("" + file)) {
			throw new IOException("Could not read .graph file");
		}

        /*File input formatting:
        First line will always be the # of points, followed by a space, followed by # of edges
        Next n lines will be points with the format: point name [space] lat [space] long
        Next m lines will be edges with the format: first point [space] second vertex
        */

        //need to account for different cases

        Scanner s = new Scanner(new FileInputStream(new File("file")));
        String first = s.nextLine();
        String[] firstdata = first.split(" ");
        int pts = Integer.parseInt(firstdata[1]);
        int egs = Integer.parseInt(firstdata[1]);
        String[] names = new String[pts];
        Point[] ref = new Point[pts];

        for(int i = 0; i < pts; i++)
        {
            String line = s.nextLine();
            String[] data = line.split(" ");

            //not sure if this is needed bruh
            names[i] = data[0];
            Point input = new Point(Double.parseDouble(data[1]), Double.parseDouble(data[2]));
            myGraph.put(input, new HashSet<Point>());
            ref[i] = input;
        }

        for(int j = 0; j < egs; j++)
        {
            String line = s.nextLine();
            String[] data = line.split(" ");

            Point p1 = ref[Integer.parseInt(data[0])];
            Point p2 = ref[Integer.parseInt(data[1])];
            myGraph.get(p1).add(p2);
            myGraph.get(p2).add(p1);
        }



        


    }

    public boolean testFileType(String name){
        int indx = name.lastIndexOf(".");

        return indx > 0 && name.substring(indx + 1).equals("graph");
    }

    /**
     * NOT USED IN FALL 2023, no need to implement
     * @return list of all vertices in graph
     */

    public List<Point> getVertices(){
        return null;
    }

    /**
     * NOT USED IN FALL 2023, no need to implement
     * @return all edges in graph
     */
    public List<Point[]> getEdges(){
        return null;
    }

    /**
     * Searches for the point in the graph that is closest in
     * straight-line distance to the parameter point p
     * @param p is a point, not necessarily in the graph
     * @return The closest point in the graph to p
     */
    public Point nearestPoint(Point p) {
        // TODO implement nearestPoint

        return null;
    }


    /**
     * Calculates the total distance along the route, summing
     * the distance between the first and the second Points, 
     * the second and the third, ..., the second to last and
     * the last. Distance returned in miles.
     * @param start Beginning point. May or may not be in the graph.
     * @param end Destination point May or may not be in the graph.
     * @return The distance to get from start to end
     */
    public double routeDistance(List<Point> route) {
        double d = 0.0;
        // TODO implement routeDistance
        return d;
    }
    

    /**
     * Checks if input points are part of a connected component
     * in the graph, that is, can one get from one to the other
     * only traversing edges in the graph
     * @param p1 one point
     * @param p2 another point
     * @return true if and onlyu if p2 is reachable from p1 (and vice versa)
     */
    public boolean connected(Point p1, Point p2) {
        try{
            List<Point> test = route(p1, p2);
        }catch(Exception IllegalArgumentException){
            return false;
        }

        return true;
    }

    /**
     * Returns the shortest path, traversing the graph, that begins at start
     * and terminates at end, including start and end as the first and last
     * points in the returned list. If there is no such route, either because
     * start is not connected to end or because start equals end, throws an
     * exception.
     * @param start Beginning point.
     * @param end Destination point.
     * @return The shortest path [start, ..., end].
     * @throws IllegalArgumentException if there is no such route, 
     * either because start is not connected to end or because start equals end.
     */
    public List<Point> route(Point start, Point end) throws IllegalArgumentException {
        Map<Point, Double> distanceMap = new HashMap<>();
        Map<Point, Point> predMap = new HashMap<>();
        predMap.put(start, null);
        final Comparator<Point> comp = new Comparator<Point>(){
            @Override
            public int compare(Point o1, Point o2) {
                // TODO Auto-generated method stub
                return 0;
            }
        };
        PriorityQueue<Point> pq = new PriorityQueue<>(comp);
        Point current = start;
        distanceMap.put(start, 0.0);
        pq.add(current);

        while(pq.size() > 0){
            current = pq.remove();
            if(current.equals(end))break;
        }

        for(Point p : myGraph.get(current)){
            double weight = current.distance(p);
            double newDist = distanceMap.get(current) + weight;
            if(newDist < distanceMap.get(p)){
                distanceMap.put(p,newDist);
                predMap.put(p,current);
                pq.add(p);
            }
        }
        return null;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String name = "data/usa.graph";
        GraphProcessor gp = new GraphProcessor();
        gp.initialize(new FileInputStream(name));
        System.out.println("running GraphProcessor");
    }


    
}
