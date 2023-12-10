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
        //TODO this does not work because the FileInputStream file does not store the file's name, don't think it's possible to check file name here

//        if (file.e) {
//			throw new IOException("Could not read .graph file");
//		}

        /*File input formatting:
        First line will always be the # of points, followed by a space, followed by # of edges
        Next n lines will be points with the format: point name [space] lat [space] long
        Next m lines will be edges with the format: first point [space] second vertex
        */
        try{
            Scanner s = new Scanner(file);
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
        } catch (Exception e){
            throw new IOException("Could not read .graph file, or something else lmao idk");
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
        double minDistance = 0;
        Point closestPoint = null;
        for(Point s : myGraph.keySet()){
            //if the current point is closer than all other previous points or if it is the first point checked
            if((p.distance(s) < minDistance && minDistance!=0) || closestPoint == null){
                minDistance = p.distance(s);
                closestPoint = s;
            }
        }

        return closestPoint;
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
        Point start = route.get(0);
        for(int i=1; i< route.size(); i++){
            double distance = route.get(i).distance(start);
            d += distance;
            start = route.get(i);
        }
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
        List<Point> tester = new ArrayList<Point>();
        return tester.contains(p1) && tester.contains(p2);
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
        // TODO implement route
        return null;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String name = "data/usa.graph";
        GraphProcessor gp = new GraphProcessor();
        gp.initialize(new FileInputStream(name));
        System.out.println("running GraphProcessor");
    }


    
}
